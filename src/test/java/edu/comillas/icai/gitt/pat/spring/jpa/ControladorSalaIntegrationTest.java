package edu.comillas.icai.gitt.pat.spring.jpa;

import edu.comillas.icai.gitt.pat.spring.jpa.controlador.ControladorSala;
import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Medico;
import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Sala;
import edu.comillas.icai.gitt.pat.spring.jpa.modelo.RespuestaSala;
import edu.comillas.icai.gitt.pat.spring.jpa.servicio.ServicioMedico;
import edu.comillas.icai.gitt.pat.spring.jpa.servicio.ServicioSala;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockCookie;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;

@WebMvcTest(ControladorSala.class)
class ControladorSalaIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServicioMedico servicioMedico;

    @MockBean
    private ServicioSala servicioSala;

    @Test
    @DisplayName("POST /api/medicos/me/salas debe crear una nueva sala")
    void crearSalaTest() throws Exception {
        // Given ...
        String request = "{\"nombre\":\"Sala A\",\"prioridad\":1}";
        Sala sala = new Sala() {{
            nombre = "Sala A";
            prioridad = 1;
        }};
        Medico medico = new Medico() {{
            jefe = true;
        }};
        RespuestaSala respuestaSala = new RespuestaSala("Sala A", 1);
        Mockito.when(servicioMedico.buscarMedico("valid-session")).thenReturn(medico);
        Mockito.when(servicioSala.buscarSala("Sala A")).thenReturn(null);
        Mockito.when(servicioSala.crear(Mockito.any(Sala.class))).thenReturn(respuestaSala);

        // When ...
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/medicos/me/salas")
                        .cookie(new MockCookie("session", "valid-session"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                // Then ...
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"nombre\":\"Sala A\",\"prioridad\":1}"));
    }

    @Test
    @DisplayName("DELETE /api/medicos/me/salas/{nombreSala} debe borrar una sala")
    void borrarSalaTest() throws Exception {
        // Given ...
        Medico medico = new Medico() {{
            jefe = true;
        }};
        Sala sala = new Sala() {{
            nombre = "Sala A";
        }};
        Mockito.when(servicioMedico.buscarMedico("valid-session")).thenReturn(medico);
        Mockito.when(servicioSala.buscarSala("Sala A")).thenReturn(sala);
        Mockito.doNothing().when(servicioSala).borrar(sala);

        // When ...
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/medicos/me/salas/Sala A")
                        .cookie(new MockCookie("session", "valid-session")))
                // Then ...
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("GET /api/medicos/me/salas/{nombreSala} debe retornar la información de una sala")
    void informacionSalaTest() throws Exception {
        // Given ...
        Medico medico = new Medico();
        Sala sala = new Sala() {{
            nombre = "Sala A";
            prioridad = 1;
        }};
        RespuestaSala respuestaSala = new RespuestaSala("Sala A", 1);
        Mockito.when(servicioMedico.buscarMedico("valid-session")).thenReturn(medico);
        Mockito.when(servicioSala.buscarSala("Sala A")).thenReturn(sala);
        Mockito.when(servicioSala.informacion(sala)).thenReturn(respuestaSala);

        // When ...
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/medicos/me/salas/Sala A")
                        .cookie(new MockCookie("session", "valid-session")))
                // Then ...
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("{\"nombre\":\"Sala A\",\"prioridad\":1}"));
    }

    @Test
    @DisplayName("GET /api/medicos/me/salas debe retornar la lista de todas las salas")
    void listaSalasTest() throws Exception {
        // Given ...
        Medico medico = new Medico();
        Sala sala1 = new Sala() {{
            nombre = "Sala A";
            prioridad = 1;
        }};
        Sala sala2 = new Sala() {{
            nombre = "Sala B";
            prioridad = 2;
        }};
        ArrayList<Sala> salas = new ArrayList<>(Arrays.asList(sala1, sala2));
        Mockito.when(servicioMedico.buscarMedico("valid-session")).thenReturn(medico);
        Mockito.when(servicioSala.salas()).thenReturn(salas);

        // When ...
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/medicos/me/salas")
                        .cookie(new MockCookie("session", "valid-session")))
                // Then ...
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[{\"nombre\":\"Sala A\",\"prioridad\":1},{\"nombre\":\"Sala B\",\"prioridad\":2}]"));
    }
}
