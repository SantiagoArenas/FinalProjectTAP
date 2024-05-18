package edu.comillas.icai.gitt.pat.spring.jpa;

import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Medico;
import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Sala;
import edu.comillas.icai.gitt.pat.spring.jpa.modelo.RespuestaSala;
import edu.comillas.icai.gitt.pat.spring.jpa.servicio.ServicioMedico;
import edu.comillas.icai.gitt.pat.spring.jpa.servicio.ServicioSala;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class ControladorSalaIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServicioMedico servicioMedico;

    @MockBean
    private ServicioSala servicioSala;

    private Medico medico;
    private Sala sala;
    private RespuestaSala respuestaSala;

    @BeforeEach
    public void setUp() {
        medico = new Medico();
        medico.setId(1L);
        medico.setNombre("Dr. Juan Pérez");
        medico.setJefe(true);

        sala = new Sala();
        sala.setIdSala(1L);
        sala.setNombreSala("Sala A");
        sala.setCapacidad(30);

        respuestaSala = new RespuestaSala(sala.getNombreSala(), sala.getCapacidad());

        Mockito.when(servicioMedico.buscarMedico(Mockito.anyString())).thenReturn(medico);
        Mockito.when(servicioSala.crear(Mockito.any(Sala.class))).thenReturn(respuestaSala);
        Mockito.when(servicioSala.buscarSala(Mockito.anyString())).thenReturn(null);
        Mockito.doNothing().when(servicioSala).borrar(Mockito.any(Sala.class));
        Mockito.when(servicioSala.informacion(Mockito.any(Sala.class))).thenReturn(respuestaSala);
        Mockito.when(servicioSala.salas()).thenReturn(new ArrayList<>());
    }

    @Test
    public void testCrearSala() throws Exception {
        Mockito.when(servicioSala.buscarSala(Mockito.anyString())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/medicos/me/salas")
                        .header("session", "valid-session-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(sala)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre", is(respuestaSala.nombre())))
                .andExpect(jsonPath("$.capacidad", is(respuestaSala.capacidad())));
    }

    @Test
    public void testCrearSalaConflict() throws Exception {
        Mockito.when(servicioSala.buscarSala(Mockito.anyString())).thenReturn(sala);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/medicos/me/salas")
                        .header("session", "valid-session-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(sala)))
                .andExpect(status().isConflict());
    }

    @Test
    public void testCrearSalaUnauthorized() throws Exception {
        Mockito.when(servicioMedico.buscarMedico(Mockito.anyString())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/medicos/me/salas")
                        .header("session", "invalid-session-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(sala)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testBorrarSala() throws Exception {
        Mockito.when(servicioSala.buscarSala(Mockito.anyString())).thenReturn(sala);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/medicos/me/salas/{nombreSala}", sala.getNombreSala())
                        .header("session", "valid-session-id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testBorrarSalaUnauthorized() throws Exception {
        Mockito.when(servicioMedico.buscarMedico(Mockito.anyString())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/medicos/me/salas/{nombreSala}", sala.getNombreSala())
                        .header("session", "invalid-session-id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testBorrarSalaNotFound() throws Exception {
        Mockito.when(servicioSala.buscarSala(Mockito.anyString())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/medicos/me/salas/{nombreSala}", sala.getNombreSala())
                        .header("session", "valid-session-id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testInformacionSala() throws Exception {
        Mockito.when(servicioSala.buscarSala(Mockito.anyString())).thenReturn(sala);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/medicos/me/salas/{nombreSala}", sala.getNombreSala())
                        .header("session", "valid-session-id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is(respuestaSala.nombre())))
                .andExpect(jsonPath("$.capacidad", is(respuestaSala.capacidad())));
    }

    @Test
    public void testInformacionSalaUnauthorized() throws Exception {
        Mockito.when(servicioMedico.buscarMedico(Mockito.anyString())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/medicos/me/salas/{nombreSala}", sala.getNombreSala())
                        .header("session", "invalid-session-id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testInformacionSalaNotFound() throws Exception {
        Mockito.when(servicioSala.buscarSala(Mockito.anyString())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/medicos/me/salas/{nombreSala}", sala.getNombreSala())
                        .header("session", "valid-session-id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testListarSalas() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/medicos/me/salas")
                        .header("session", "valid-session-id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testListarSalasUnauthorized() throws Exception {
        Mockito.when(servicioMedico.buscarMedico(Mockito.anyString())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/medicos/me/salas")
                        .header("session", "invalid-session-id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}
