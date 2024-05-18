package edu.comillas.icai.gitt.pat.spring.jpa;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.comillas.icai.gitt.pat.spring.jpa.entidad.*;
import edu.comillas.icai.gitt.pat.spring.jpa.repositorio.*;
import edu.comillas.icai.gitt.pat.spring.jpa.servicio.ServicioConocimiento;
import edu.comillas.icai.gitt.pat.spring.jpa.servicio.ServicioMedico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ControladorConocimientoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServicioConocimiento servicioConocimiento;

    @MockBean
    private ServicioMedico servicioMedico;

    @MockBean
    private RepoConocimiento repoConocimiento;

    @MockBean
    private RepoMedico repoMedico;

    @MockBean
    private RepoConocimientoMedico repoConocimientoMedico;

    @MockBean
    private RepoSala repoSala;

    @MockBean
    private RepoConocimientoSala repoConocimientoSala;

    private Medico medico;
    private Conocimiento conocimiento;
    private ConocimientoMedico conocimientoMedico;
    private ConocimientoSala conocimientoSala;
    private Sala sala;

    @BeforeEach
    public void setUp() {
        medico = new Medico();
        medico.setId(1L);
        medico.setNombre("Dr. Juan Pérez");
        medico.setJefe(true);

        conocimiento = new Conocimiento("Conocimiento A");
        conocimiento.setId(1L);
        conocimiento.setNombre("Conocimiento A");

        conocimientoMedico = new ConocimientoMedico();
        conocimientoMedico.setId(1L);
        conocimientoMedico.setMedico(medico);
        conocimientoMedico.setConocimiento(conocimiento);

        sala = new Sala();
        sala.setId(1L);
        sala.setNombreSala("Sala A");

        conocimientoSala = new ConocimientoSala();
        conocimientoSala.setId(1L);
        conocimientoSala.setSala(sala);
        conocimientoSala.setConocimiento(conocimiento);

        Mockito.when(servicioMedico.buscarMedico(Mockito.anyString())).thenReturn(medico);
        Mockito.when(servicioConocimiento.crear(Mockito.any(Conocimiento.class))).thenReturn(conocimiento.getNombre());
        Mockito.doNothing().when(servicioConocimiento).borrar(Mockito.any(Conocimiento.class));
        Mockito.when(repoConocimiento.findByNombre(Mockito.anyString())).thenReturn(conocimiento);
        Mockito.when(repoMedico.findByEmail(Mockito.anyString())).thenReturn(medico);
        Mockito.when(repoConocimientoMedico.save(Mockito.any(ConocimientoMedico.class))).thenReturn(conocimientoMedico);
        Mockito.when(repoSala.findByNombre(Mockito.anyString())).thenReturn(sala);
        Mockito.when(repoConocimientoSala.save(Mockito.any(ConocimientoSala.class))).thenReturn(conocimientoSala);
        Mockito.doNothing().when(repoConocimientoMedico).delete(Mockito.any(ConocimientoMedico.class));
        Mockito.doNothing().when(repoConocimientoSala).delete(Mockito.any(ConocimientoSala.class));
        Mockito.when(servicioConocimiento.getConocimientosMedico(Mockito.any(Medico.class))).thenReturn(new ArrayList<>(List.of(conocimiento)));
    }

    @Test
    public void testCrearConocimiento() throws Exception {
        mockMvc.perform(post("/api/conocimientos")
                        .header("session", "valid-session-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(conocimiento)))
                .andExpect(status().isOk())
                .andExpect(content().string(conocimiento.getNombre()));
    }

    @Test
    public void testBorrarConocimiento() throws Exception {
        mockMvc.perform(delete("/api/conocimientos")
                        .header("session", "valid-session-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(conocimiento)))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddConocimientoToMedico() throws Exception {
        mockMvc.perform(post("/api/conocimientos/medicos/juan.perez@example.com")
                        .header("session", "valid-session-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(conocimiento)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.medico.nombre").value(medico.getNombre()))
                .andExpect(jsonPath("$.conocimiento.nombre").value(conocimiento.getNombre()));
    }

    @Test
    public void testRemoveConocimientoFromMedico() throws Exception {
        mockMvc.perform(delete("/api/conocimientos/medicos/juan.perez@example.com")
                        .header("session", "valid-session-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(conocimiento)))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddConocimientoToSala() throws Exception {
        mockMvc.perform(post("/api/conocimientos/salas/Sala A")
                        .header("session", "valid-session-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(conocimiento)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sala.nombreSala").value(sala.getNombreSala()))
                .andExpect(jsonPath("$.conocimiento.nombre").value(conocimiento.getNombre()));
    }

    @Test
    public void testRemoveConocimientoFromSala() throws Exception {
        mockMvc.perform(delete("/api/conocimientos/salas/Sala A")
                        .header("session", "valid-session-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(conocimiento)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetConocimientosMedico() throws Exception {
        mockMvc.perform(get("/api/conocimientos/medicos/juan.perez@example.com")
                        .header("session", "valid-session-id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value(conocimiento.getNombre()));
    }

    @Test
    void crear() {
    }

    @Test
    void borrar() {
    }

    @Test
    void addToMedico() {
    }

    @Test
    void removeFromMedico() {
    }

    @Test
    void addToSala() {
    }

    @Test
    void removeFromSala() {
    }

    @Test
    void getConocimientos() {
    }

    @Test
    void testBorrar() {
    }
}
