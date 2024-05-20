package edu.comillas.icai.gitt.pat.spring.jpa.e2e;

import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Medico;
import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Sala;
import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Token;
import edu.comillas.icai.gitt.pat.spring.jpa.repositorio.RepoMedico;
import edu.comillas.icai.gitt.pat.spring.jpa.repositorio.RepoSala;
import edu.comillas.icai.gitt.pat.spring.jpa.repositorio.RepoToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockCookie;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ControladorSalaE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RepoMedico repoMedico;

    @Autowired
    private RepoSala repoSala;

    @Autowired
    private RepoToken repoToken;

    private MockCookie tokenCookie;
    private static int testCounter = 0;

    @BeforeEach
    void setup() {
        // Limpiar base de datos antes de cada prueba
        repoSala.deleteAll();
        repoMedico.deleteAll();
        repoToken.deleteAll();

        // Crear datos iniciales para las pruebas con un email único
        Medico medico = new Medico();
        medico.nombre = "Dr. John Doe";
        medico.email = "valid-session" + testCounter + "@example.com";
        medico.password = "password";
        medico.jefe = true;
        repoMedico.save(medico);

        // Crear un token para el médico
        Token token = new Token();
        token.medico = medico;
        repoToken.save(token);

        tokenCookie = new MockCookie("session", token.id); // Usamos el ID del token como el valor de la cookie
        testCounter++;
    }

    @Test
    @DisplayName("POST /api/medicos/me/salas debe crear una nueva sala")
    void crearSalaTest() throws Exception {
        String request = "{\"nombre\":\"Sala A\",\"prioridad\":1}";

        this.mockMvc.perform(post("/api/medicos/me/salas")
                        .cookie(tokenCookie)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"nombre\":\"Sala A\",\"prioridad\":1}"));
    }

    @Test
    @DisplayName("DELETE /api/medicos/me/salas/{nombreSala} debe borrar una sala existente")
    void borrarSalaTest() throws Exception {
        Sala sala = new Sala();
        sala.nombre = "Sala B";
        sala.prioridad = 2;
        repoSala.save(sala);

        this.mockMvc.perform(delete("/api/medicos/me/salas/Sala B")
                        .cookie(tokenCookie))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api/medicos/me/salas/{nombreSala} debe devolver la información de una sala existente")
    void getSalaInformacionTest() throws Exception {
        Sala sala = new Sala();
        sala.nombre = "Sala C";
        sala.prioridad = 3;
        repoSala.save(sala);

        this.mockMvc.perform(get("/api/medicos/me/salas/Sala C")
                        .cookie(tokenCookie))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"nombre\":\"Sala C\",\"prioridad\":3}"));
    }

    @Test
    @DisplayName("GET /api/medicos/me/salas debe devolver la lista de todas las salas")
    void getSalasTest() throws Exception {
        Sala sala1 = new Sala();
        sala1.nombre = "Sala D";
        sala1.prioridad = 1;
        repoSala.save(sala1);

        Sala sala2 = new Sala();
        sala2.nombre = "Sala E";
        sala2.prioridad = 2;
        repoSala.save(sala2);

        this.mockMvc.perform(get("/api/medicos/me/salas")
                        .cookie(tokenCookie))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"nombre\":\"Sala D\",\"prioridad\":1},{\"nombre\":\"Sala E\",\"prioridad\":2}]"));
    }
}
