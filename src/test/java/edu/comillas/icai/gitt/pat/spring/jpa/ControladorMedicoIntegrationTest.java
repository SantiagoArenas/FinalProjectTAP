package edu.comillas.icai.gitt.pat.spring.jpa;

import edu.comillas.icai.gitt.pat.spring.jpa.entidad.*;
import edu.comillas.icai.gitt.pat.spring.jpa.modelo.Login;
import edu.comillas.icai.gitt.pat.spring.jpa.modelo.Modificacion;
import edu.comillas.icai.gitt.pat.spring.jpa.modelo.Registro;
import edu.comillas.icai.gitt.pat.spring.jpa.modelo.RespuestaMedico;
import edu.comillas.icai.gitt.pat.spring.jpa.repositorio.RepoConocimientoSala;
import edu.comillas.icai.gitt.pat.spring.jpa.servicio.ServicioConocimiento;
import edu.comillas.icai.gitt.pat.spring.jpa.servicio.ServicioMedico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class ControladorMedicoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServicioMedico servicioMedico;

    @MockBean
    private ServicioConocimiento servicioConocimiento;

    private Medico medico;
    private Registro registro;
    private Login login;
    private Token token;
    private Modificacion modificacion;
    private RespuestaMedico respuestaMedico;
    private RepoConocimientoSala repoConocimientoSala;
    private Medico medicoNoJefe;
    private Medico medicoJefe;
    private Conocimiento conocimiento;
    private ConocimientoMedico conocimientoMedico;
    private ConocimientoSala conocimientoSala;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Sala sala;

    @BeforeEach
    public void setUp() {
        medico = new Medico();
        medico.setId(1L);
        medico.setNombre("Dr. Juan Pérez");
        medico.setJefe(true);

        medicoJefe = new Medico();
        medicoJefe.setId(1L);
        medicoJefe.setNombre("Dr. Juan Pérez");
        medicoJefe.setJefe(true);

        medicoNoJefe = new Medico();
        medicoNoJefe.setId(2L);
        medicoNoJefe.setNombre("Dr. Maria Lopez");
        medicoNoJefe.setJefe(false);

        conocimiento = new Conocimiento("Conocimiento A");
        conocimiento.setId(1L);
        conocimiento.setNombre("Conocimiento A");

        conocimientoMedico = new ConocimientoMedico();
        conocimientoMedico.setId(1L);
        conocimientoMedico.setMedico(medico);
        conocimientoMedico.setConocimiento(conocimiento);

        sala = new Sala();
        sala.setIdSala(1L);
        sala.setNombreSala("Sala A");

        conocimientoSala = new ConocimientoSala();
        conocimientoSala.setId(1L);
        conocimientoSala.setSala(sala);
        conocimientoSala.setConocimiento(conocimiento);

        registro = new Registro("Dr. Juan Pérez", "juan.perez@example.com", "password", true);
        login = new Login("juan.perez@example.com", "password");
        token = new Token("valid-token-id");
        modificacion = new Modificacion("Dr. Juan Pérez", "newpassword", true);
        respuestaMedico = new RespuestaMedico("Dr. Juan Pérez", "juan.perez@example.com", true);
    }

    @Test
    void testRegistrar() throws Exception {
        String registroJson = objectMapper.writeValueAsString(registro);

        // Configuración del comportamiento de los mocks
        Mockito.when(servicioMedico.crear(Mockito.any(Registro.class))).thenReturn(respuestaMedico);

        // Ejecución y verificación de la petición
        mockMvc.perform(post("/api/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registroJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value(respuestaMedico.nombre()))
                .andExpect(jsonPath("$.email").value(respuestaMedico.email()));
    }

    @Test
    public void testRegistrarConConflicto() throws Exception {
        Mockito.when(servicioMedico.crear(Mockito.any(Registro.class))).thenThrow(new DataIntegrityViolationException("Conflict"));

        mockMvc.perform(post("/api/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registro)))
                .andExpect(status().isConflict());
    }

    @Test
    void testLogin() throws Exception {
        String loginJson = objectMapper.writeValueAsString(login);

        // Configuración del comportamiento de los mocks
        Mockito.when(servicioMedico.guardarToken(login.email(), login.password())).thenReturn(token);

        // Ejecución y verificación de la petición
        mockMvc.perform(post("/api/medicos/me/sesion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isCreated())
                .andExpect(header().string("session", token.getId()));
    }

    @Test
    public void testLoginUnauthorized() throws Exception {
        Mockito.when(servicioMedico.guardarToken(Mockito.anyString(), Mockito.anyString())).thenReturn(null);

        mockMvc.perform(post("/api/medicos/me/sesion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testLogout() throws Exception {
        Mockito.when(servicioMedico.buscarMedico(token.getId())).thenReturn(medico);

        mockMvc.perform(delete("/api/medicos/me/sesion")
                        .header("session", token.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testLogoutUnauthorized() throws Exception {
        Mockito.when(servicioMedico.buscarMedico(Mockito.anyString())).thenReturn(null);

        mockMvc.perform(delete("/api/medicos/me/sesion")
                        .header("session", "invalid-token-id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testInformacion() throws Exception {
        Mockito.when(servicioMedico.buscarMedico(token.getId())).thenReturn(medico);
        Mockito.when(servicioMedico.getDatos(medico)).thenReturn(respuestaMedico);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/medicos/me")
                        .header("session", token.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is(respuestaMedico.nombre())))
                .andExpect(jsonPath("$.email", is(respuestaMedico.email())))
                .andExpect(jsonPath("$.jefe", is(respuestaMedico.jefe())));
    }

    @Test
    public void testInformacionUnauthorized() throws Exception {
        Mockito.when(servicioMedico.buscarMedico(Mockito.anyString())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/medicos/me")
                        .header("session", "invalid-token-id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testActualizar() throws Exception {
        String token = "valid-token-id";
        String modificacionJson = objectMapper.writeValueAsString(modificacion);

        // Configuración del comportamiento de los mocks
        Mockito.when(servicioMedico.buscarMedico(Mockito.anyString())).thenReturn(medico);
        Mockito.when(servicioMedico.modificar(Mockito.any(Medico.class), Mockito.any(Modificacion.class))).thenReturn(respuestaMedico);

        // Ejecución y verificación de la petición
        mockMvc.perform(put("/api/medicos/me")
                        .header("session", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(modificacionJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value(respuestaMedico.nombre()))
                .andExpect(jsonPath("$.email").value(respuestaMedico.email()));
    }

    @Test
    public void testActualizarUnauthorized() throws Exception {
        Mockito.when(servicioMedico.buscarMedico(Mockito.anyString())).thenReturn(null);

        mockMvc.perform(put("/api/medicos/me")
                        .header("session", "invalid-token-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modificacion)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testBorrar() throws Exception {
        String token = "valid-token-id";

        // Configuración del comportamiento de los mocks
        Mockito.when(servicioMedico.buscarMedico(token)).thenReturn(medico);

        // Ejecución y verificación de la petición
        mockMvc.perform(delete("/api/medicos/me")
                        .header("session", token))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testBorrarUnauthorized() throws Exception {
        Mockito.when(servicioMedico.buscarMedico(Mockito.anyString())).thenReturn(null);

        mockMvc.perform(delete("/api/medicos/me")
                        .header("session", "invalid-token-id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testRemoveConocimientoFromSalaComoNoJefe() throws Exception {
        Mockito.when(servicioMedico.buscarMedico(Mockito.anyString())).thenReturn(medicoNoJefe);

        mockMvc.perform(delete("/api/conocimientos/salas/Sala A")
                        .header("session", "valid-session-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\": \"Conocimiento A\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testAddConocimientoToSalaComoNoJefe() throws Exception {
        Mockito.when(servicioMedico.buscarMedico(Mockito.anyString())).thenReturn(medicoNoJefe);

        mockMvc.perform(post("/api/conocimientos/salas/Sala A")
                        .header("session", "valid-session-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\": \"Conocimiento A\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testRemoveConocimientoFromMedicoComoNoJefe() throws Exception {
        Mockito.when(servicioMedico.buscarMedico(Mockito.anyString())).thenReturn(medicoNoJefe);

        mockMvc.perform(delete("/api/conocimientos/medicos/juan.perez@example.com")
                        .header("session", "valid-session-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\": \"Conocimiento A\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testAddConocimientoToMedicoComoNoJefe() throws Exception {
        Mockito.when(servicioMedico.buscarMedico(Mockito.anyString())).thenReturn(medicoNoJefe);

        mockMvc.perform(post("/api/conocimientos/medicos/juan.perez@example.com")
                        .header("session", "valid-session-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\": \"Conocimiento A\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testBorrarConocimientoComoNoJefe() throws Exception {
        Mockito.when(servicioMedico.buscarMedico(Mockito.anyString())).thenReturn(medicoNoJefe);

        mockMvc.perform(delete("/api/conocimientos")
                        .header("session", "valid-session-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\": \"Conocimiento A\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testCrearConocimientoComoNoJefe() throws Exception {
        Mockito.when(servicioMedico.buscarMedico(Mockito.anyString())).thenReturn(medicoNoJefe);

        mockMvc.perform(post("/api/conocimientos")
                        .header("session", "valid-session-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\": \"Conocimiento A\"}"))
                .andExpect(status().isUnauthorized());
    }
    
    @Test
    void testCrearConocimientoSinSerJefe() throws Exception {
        String token = "valid-token-id";
        Conocimiento conocimiento = new Conocimiento("Conocimiento A");
        String conocimientoJson = objectMapper.writeValueAsString(conocimiento);

        // Configuración del comportamiento de los mocks
        Mockito.when(servicioMedico.buscarMedico(token)).thenReturn(medicoNoJefe);

        // Ejecución y verificación de la petición
        mockMvc.perform(post("/api/conocimientos")
                        .header("session", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(conocimientoJson))
                .andExpect(status().isUnauthorized());
    }



    @Test
    void testBorrarConocimientoSinSerJefe() throws Exception {
        String token = "valid-token-id";
        Conocimiento conocimiento = new Conocimiento("Conocimiento A");
        String conocimientoJson = objectMapper.writeValueAsString(conocimiento);

        // Configuración del comportamiento de los mocks
        Mockito.when(servicioMedico.buscarMedico(token)).thenReturn(medicoNoJefe);

        // Ejecución y verificación de la petición
        mockMvc.perform(delete("/api/conocimientos")
                        .header("session", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(conocimientoJson))
                .andExpect(status().isUnauthorized());
    }
}
