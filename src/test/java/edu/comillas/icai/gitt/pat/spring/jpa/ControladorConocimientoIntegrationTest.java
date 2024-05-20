package edu.comillas.icai.gitt.pat.spring.jpa;

import edu.comillas.icai.gitt.pat.spring.jpa.controlador.ControladorConocimiento;
import edu.comillas.icai.gitt.pat.spring.jpa.entidad.*;
import edu.comillas.icai.gitt.pat.spring.jpa.repositorio.*;
import edu.comillas.icai.gitt.pat.spring.jpa.servicio.ServicioConocimiento;
import edu.comillas.icai.gitt.pat.spring.jpa.servicio.ServicioMedico;
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
import java.util.List;

@WebMvcTest(ControladorConocimiento.class)
class ControladorConocimientoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RepoConocimientoMedico repoConocimientoMedico;

    @MockBean
    private RepoConocimientoSala repoConocimientoSala;

    @MockBean
    private RepoConocimiento repoConocimiento;

    @MockBean
    private ServicioConocimiento servicioConocimiento;

    @MockBean
    private ServicioMedico servicioMedico;

    @MockBean
    private RepoMedico repoMedico;

    @MockBean
    private RepoSala repoSala;

    @Test
    @DisplayName("POST /api/conocimientos debe crear un conocimiento")
    void crearConocimientoTest() throws Exception {
        // Given ...
        String request = "{\"nombre\":\"Cardiología\"}";
        Conocimiento conocimiento = new Conocimiento() {{
            nombre = "Cardiología";
        }};
        Mockito.when(servicioMedico.buscarMedico("valid-session")).thenReturn(new Medico() {{
            jefe = true;
        }});
        Mockito.when(servicioConocimiento.crear(Mockito.any(Conocimiento.class))).thenReturn("Cardiología");

        // When ...
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/conocimientos")
                        .cookie(new MockCookie("session", "valid-session"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                // Then ...
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Cardiología"));
    }

    @Test
    @DisplayName("DELETE /api/conocimientos debe borrar un conocimiento")
    void borrarConocimientoTest() throws Exception {
        // Given ...
        String request = "{\"nombre\":\"Cardiología\"}";
        Conocimiento conocimiento = new Conocimiento() {{
            nombre = "Cardiología";
        }};
        Mockito.when(servicioMedico.buscarMedico("valid-session")).thenReturn(new Medico() {{
            jefe = true;
        }});
        Mockito.when(repoConocimiento.findByNombre("Cardiología")).thenReturn(conocimiento);
        Mockito.doNothing().when(repoConocimiento).delete(conocimiento);

        // When ...
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/conocimientos")
                        .cookie(new MockCookie("session", "valid-session"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                // Then ...
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("POST /api/conocimientos/medicos/{email} debe añadir conocimiento a un médico")
    void addConocimientoToMedicoTest() throws Exception {
        // Given ...
        String request = "{\"nombre\":\"Cardiología\"}";
        Conocimiento conocimiento = new Conocimiento() {{
            nombre = "Cardiología";
        }};
        Medico medico = new Medico() {{
            email = "test@medico.com";
        }};
        ConocimientoMedico conocimientoMedico = new ConocimientoMedico() {{
            this.medico = medico;
            this.conocimiento = conocimiento;
        }};
        Mockito.when(servicioMedico.buscarMedico("valid-session")).thenReturn(new Medico() {{
            jefe = true;
        }});
        Mockito.when(repoMedico.findByEmail("test@medico.com")).thenReturn(medico);
        Mockito.when(repoConocimiento.findByNombre("Cardiología")).thenReturn(conocimiento);
        Mockito.when(repoConocimientoMedico.save(Mockito.any(ConocimientoMedico.class))).thenReturn(conocimientoMedico);

        // When ...
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/conocimientos/medicos/test@medico.com")
                        .cookie(new MockCookie("session", "valid-session"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                // Then ...
                .andExpect(MockMvcResultMatchers.status().isOk())
                /*.andExpect(MockMvcResultMatchers.content().json("{\"medico\":{\"email\":\"test@medico.com\"},\"conocimiento\":{\"nombre\":\"Cardiología\"}}"))*/;
    }

    @Test
    @DisplayName("DELETE /api/conocimientos/medicos/{email} debe eliminar conocimiento de un médico")
    void removeConocimientoFromMedicoTest() throws Exception {
        // Given ...
        String request = "{\"nombre\":\"Cardiología\"}";
        Conocimiento conocimiento = new Conocimiento() {{
            nombre = "Cardiología";
        }};
        Medico medico = new Medico() {{
            email = "test@medico.com";
        }};
        ConocimientoMedico conocimientoMedico = new ConocimientoMedico() {{
            this.medico = medico;
            this.conocimiento = conocimiento;
        }};
        Mockito.when(servicioMedico.buscarMedico("valid-session")).thenReturn(new Medico() {{
            jefe = true;
        }});
        Mockito.when(repoMedico.findByEmail("test@medico.com")).thenReturn(medico);
        Mockito.when(repoConocimiento.findByNombre("Cardiología")).thenReturn(conocimiento);
        Mockito.when(repoConocimientoMedico.findByMedicoAndConocimiento(medico, conocimiento)).thenReturn(conocimientoMedico);
        Mockito.doNothing().when(repoConocimientoMedico).delete(conocimientoMedico);

        // When ...
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/conocimientos/medicos/test@medico.com")
                        .cookie(new MockCookie("session", "valid-session"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                // Then ...
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("POST /api/conocimientos/salas/{nombreSala} debe añadir conocimiento a una sala")
    void addConocimientoToSalaTest() throws Exception {
        // Given ...
        String request = "{\"nombre\":\"Cardiología\"}";
        Conocimiento conocimiento = new Conocimiento() {{
            nombre = "Cardiología";
        }};
        Sala sala = new Sala() {{
            nombre = "Sala A";
        }};
        ConocimientoSala conocimientoSala = new ConocimientoSala() {{
            this.sala = sala;
            this.conocimiento = conocimiento;
        }};
        Mockito.when(servicioMedico.buscarMedico("valid-session")).thenReturn(new Medico() {{
            jefe = true;
        }});
        Mockito.when(repoSala.findByNombre("Sala A")).thenReturn(sala);
        Mockito.when(repoConocimiento.findByNombre("Cardiología")).thenReturn(conocimiento);
        Mockito.when(repoConocimientoSala.save(Mockito.any(ConocimientoSala.class))).thenReturn(conocimientoSala);

        // When ...
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/conocimientos/salas/Sala A")
                        .cookie(new MockCookie("session", "valid-session"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                // Then ...
                .andExpect(MockMvcResultMatchers.status().isOk())
                /*.andExpect(MockMvcResultMatchers.content().json("{\"sala\":{\"nombre\":\"Sala A\"},\"conocimiento\":{\"nombre\":\"Cardiología\"}}"))*/;
    }

    @Test
    @DisplayName("DELETE /api/conocimientos/salas/{nombreSala} debe eliminar conocimiento de una sala")
    void removeConocimientoFromSalaTest() throws Exception {
        // Given ...
        String request = "{\"nombre\":\"Cardiología\"}";
        Conocimiento conocimiento = new Conocimiento() {{
            nombre = "Cardiología";
        }};
        Sala sala = new Sala() {{
            nombre = "Sala A";
        }};
        ConocimientoSala conocimientoSala = new ConocimientoSala() {{
            this.sala = sala;
            this.conocimiento = conocimiento;
        }};
        Mockito.when(servicioMedico.buscarMedico("valid-session")).thenReturn(new Medico() {{
            jefe = true;
        }});
        Mockito.when(repoSala.findByNombre("Sala A")).thenReturn(sala);
        Mockito.when(repoConocimiento.findByNombre("Cardiología")).thenReturn(conocimiento);
        Mockito.when(repoConocimientoSala.findBySalaAndConocimiento(sala, conocimiento)).thenReturn(conocimientoSala);
        Mockito.doNothing().when(repoConocimientoSala).delete(conocimientoSala);

        // When ...
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/conocimientos/salas/Sala A")
                        .cookie(new MockCookie("session", "valid-session"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                // Then ...
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("GET /api/conocimientos/medicos/{email} debe retornar los conocimientos de un médico")
    void getConocimientosMedicoTest() throws Exception {
        // Given ...
        Medico medico = new Medico() {{
            email = "test@medico.com";
        }};
        Conocimiento conocimiento1 = new Conocimiento() {{
            nombre = "Cardiología";
        }};
        Conocimiento conocimiento2 = new Conocimiento() {{
            nombre = "Neurología";
        }};
        Mockito.when(servicioMedico.buscarMedico("valid-session")).thenReturn(new Medico());
        Mockito.when(repoMedico.findByEmail("test@medico.com")).thenReturn(medico);
        Mockito.when(servicioConocimiento.getConocimientosMedico(medico)).thenReturn(new ArrayList<>(Arrays.asList(conocimiento1, conocimiento2)));

        // When ...
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/conocimientos/medicos/test@medico.com")
                        .cookie(new MockCookie("session", "valid-session"))
                        .contentType(MediaType.APPLICATION_JSON))
                // Then ...
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[" +
                        "{\"nombre\":\"Cardiología\"}," +
                        "{\"nombre\":\"Neurología\"}]"));
    }

    @Test
    @DisplayName("GET /api/conocimientos debe retornar todos los conocimientos")
    void getConocimientosTest() throws Exception {
        // Given ...
        Conocimiento conocimiento1 = new Conocimiento() {{
            nombre = "Cardiología";
        }};
        Conocimiento conocimiento2 = new Conocimiento() {{
            nombre = "Neurología";
        }};

        List<Conocimiento> conocimientos = new ArrayList<Conocimiento>() {{
            add(conocimiento1);
            add(conocimiento2);
        }};

        Mockito.when(servicioMedico.buscarMedico("valid-session")).thenReturn(new Medico());
        Mockito.when(repoConocimiento.findAll()).thenReturn(conocimientos);

        // When ...
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/conocimientos")
                        .cookie(new MockCookie("session", "valid-session"))
                        .contentType(MediaType.APPLICATION_JSON))
                // Then ...
                .andExpect(MockMvcResultMatchers.status().isOk())
                /*.andExpect(MockMvcResultMatchers.content().json("[" +
                        "{\"nombre\":\"Cardiología\"}," +
                        "{\"nombre\":\"Neurología\"}]"))*/;
    }
}
