package edu.comillas.icai.gitt.pat.spring.jpa;

import edu.comillas.icai.gitt.pat.spring.jpa.controlador.ControladorMedico;
import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Medico;
import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Token;
import edu.comillas.icai.gitt.pat.spring.jpa.modelo.Login;
import edu.comillas.icai.gitt.pat.spring.jpa.modelo.Modificacion;
import edu.comillas.icai.gitt.pat.spring.jpa.modelo.Registro;
import edu.comillas.icai.gitt.pat.spring.jpa.modelo.RespuestaMedico;
import edu.comillas.icai.gitt.pat.spring.jpa.repositorio.RepoMedico;
import edu.comillas.icai.gitt.pat.spring.jpa.servicio.ServicioMedico;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockCookie;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

@WebMvcTest(ControladorMedico.class)
class ControladorMedicoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServicioMedico servicioMedico;

    @MockBean
    private RepoMedico repoMedico;

    @Test
    @DisplayName("POST /api/medicos debe registrar un nuevo médico")
    void registrarTest() throws Exception {
        // Given ...
        String request = "{\"nombre\":\"Dr. John Doe\",\"email\":\"john.doe@example.com\",\"password\":\"password\",\"jefe\":true}";
        Registro registro = new Registro("Dr. John Doe", "john.doe@example.com", "password", true);
        RespuestaMedico respuestaMedico = new RespuestaMedico("Dr. John Doe", "john.doe@example.com", true);
        Mockito.when(servicioMedico.crear(Mockito.any(Registro.class))).thenReturn(respuestaMedico);

        // When ...
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                // Then ...
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json("{\"nombre\":\"Dr. John Doe\",\"email\":\"john.doe@example.com\",\"jefe\":true}"));
    }

    @Test
    @DisplayName("POST /api/medicos/me/sesion debe iniciar sesión y devolver una cookie de sesión")
    void loginTest() throws Exception {
        // Given ...
        String request = "{\"email\":\"john.doe@example.com\",\"password\":\"password\"}";
        Login login = new Login("john.doe@example.com", "password");
        Token token = new Token() {{
            id = "token-id";
        }};
        Mockito.when(servicioMedico.guardarToken(login.email(), login.password())).thenReturn(token);

        // When ...
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/medicos/me/sesion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                // Then ...
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists(HttpHeaders.SET_COOKIE));
    }

    @Test
    @DisplayName("DELETE /api/medicos/me/sesion debe cerrar sesión y eliminar la cookie de sesión")
    void logoutTest() throws Exception {
        // Given ...
        Medico medico = new Medico();
        Mockito.when(servicioMedico.buscarMedico("valid-session")).thenReturn(medico);
        Mockito.doNothing().when(servicioMedico).logout("valid-session");

        // When ...
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/medicos/me/sesion")
                        .cookie(new MockCookie("session", "valid-session")))
                // Then ...
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andExpect(MockMvcResultMatchers.header().exists(HttpHeaders.SET_COOKIE));
    }

    @Test
    @DisplayName("GET /api/medicos/me debe retornar la información del médico autenticado")
    void informacionTest() throws Exception {
        // Given ...
        Medico medico = new Medico() {{
            nombre = "Dr. John Doe";
            email = "john.doe@example.com";
            jefe = true;
        }};
        RespuestaMedico respuestaMedico = new RespuestaMedico("Dr. John Doe", "john.doe@example.com", true);
        Mockito.when(servicioMedico.buscarMedico("valid-session")).thenReturn(medico);
        Mockito.when(servicioMedico.getDatos(medico)).thenReturn(respuestaMedico);

        // When ...
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/medicos/me")
                        .cookie(new MockCookie("session", "valid-session")))
                // Then ...
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"nombre\":\"Dr. John Doe\",\"email\":\"john.doe@example.com\",\"jefe\":true}"));
    }

    @Test
    @DisplayName("PUT /api/medicos/me debe actualizar la información del médico autenticado")
    void actualizarTest() throws Exception {
        // Given ...
        String request = "{\"nombre\":\"Dr. Jane Doe\",\"password\":\"newpassword\",\"jefe\":false}";
        Medico medico = new Medico() {{
            nombre = "Dr. John Doe";
            email = "john.doe@example.com";
            jefe = true;
        }};
        Modificacion modificacion = new Modificacion("Dr. Jane Doe", "newpassword", false);
        RespuestaMedico respuestaMedico = new RespuestaMedico("Dr. Jane Doe", "john.doe@example.com", false);
        Mockito.when(servicioMedico.buscarMedico("valid-session")).thenReturn(medico);
        Mockito.when(servicioMedico.modificar(medico, modificacion)).thenReturn(respuestaMedico);

        // When ...
        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/medicos/me")
                        .cookie(new MockCookie("session", "valid-session"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                // Then ...
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"nombre\":\"Dr. Jane Doe\",\"email\":\"john.doe@example.com\",\"jefe\":false}"));
    }

    @Test
    @DisplayName("DELETE /api/medicos/me debe borrar el médico autenticado")
    void borrarTest() throws Exception {
        // Given ...
        Medico medico = new Medico();
        Mockito.when(servicioMedico.buscarMedico("valid-session")).thenReturn(medico);
        Mockito.doNothing().when(servicioMedico).borrar(medico);

        // When ...
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/medicos/me")
                        .cookie(new MockCookie("session", "valid-session")))
                // Then ...
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("GET /api/medicos debe retornar la lista de todos los médicos")
    void listaMedicosTest() throws Exception {
        // Given ...
        Medico medico1 = new Medico() {{
            nombre = "Dr. John Doe";
            email = "john.doe@example.com";
            jefe = true;
        }};
        Medico medico2 = new Medico() {{
            nombre = "Dr. Jane Doe";
            email = "jane.doe@example.com";
            jefe = false;
        }};
        List<Medico> medicos = Arrays.asList(medico1, medico2);
        Mockito.when(servicioMedico.buscarMedico("valid-session")).thenReturn(new Medico());
        Mockito.when(repoMedico.findAll()).thenReturn(medicos);

        // When ...
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/medicos")
                        .cookie(new MockCookie("session", "valid-session")))
                // Then ...
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[{\"nombre\":\"Dr. John Doe\",\"email\":\"john.doe@example.com\",\"jefe\":true},{\"nombre\":\"Dr. Jane Doe\",\"email\":\"jane.doe@example.com\",\"jefe\":false}]"));
    }
}
