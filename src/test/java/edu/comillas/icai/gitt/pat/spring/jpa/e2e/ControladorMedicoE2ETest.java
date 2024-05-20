package edu.comillas.icai.gitt.pat.spring.jpa.e2e;

import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Medico;
import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Token;
import edu.comillas.icai.gitt.pat.spring.jpa.modelo.Login;
import edu.comillas.icai.gitt.pat.spring.jpa.modelo.Modificacion;
import edu.comillas.icai.gitt.pat.spring.jpa.modelo.Registro;
import edu.comillas.icai.gitt.pat.spring.jpa.repositorio.RepoMedico;
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
class ControladorMedicoE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RepoMedico repoMedico;

    @Autowired
    private RepoToken repoToken;

    private MockCookie tokenCookie;
    private static int testCounter = 0;
    private String email;

    @BeforeEach
    void setup() {
        // Limpiar base de datos antes de cada prueba
        repoMedico.deleteAll();
        repoToken.deleteAll();

        // Crear datos iniciales para las pruebas con un email único
        email = "valid-session" + testCounter + "@example.com";
        Medico medico = new Medico();
        medico.nombre = "Dr. John Doe";
        medico.email = email;
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
    @DisplayName("POST /api/medicos debe registrar un nuevo médico")
    void registrarTest() throws Exception {
        String request = "{\"nombre\":\"Dr. Jane Doe\",\"email\":\"jane.doe@example.com\",\"password\":\"password\",\"jefe\":false}";

        this.mockMvc.perform(post("/api/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"nombre\":\"Dr. Jane Doe\",\"email\":\"jane.doe@example.com\",\"jefe\":false}"));
    }


    @Test
    @DisplayName("DELETE /api/medicos/me/sesion debe cerrar sesión y eliminar la cookie de sesión")
    void logoutTest() throws Exception {
        this.mockMvc.perform(delete("/api/medicos/me/sesion")
                        .cookie(tokenCookie))
                .andExpect(status().isNoContent())
                .andExpect(header().exists("Set-Cookie"));
    }

    @Test
    @DisplayName("GET /api/medicos/me debe retornar la información del médico autenticado")
    void informacionTest() throws Exception {
        this.mockMvc.perform(get("/api/medicos/me")
                        .cookie(tokenCookie))
                .andExpect(status().isOk())
                .andExpect(content().json(String.format("{\"nombre\":\"Dr. John Doe\",\"email\":\"%s\",\"jefe\":true}", email)));
    }

    @Test
    @DisplayName("PUT /api/medicos/me debe actualizar la información del médico autenticado")
    void actualizarTest() throws Exception {
        String request = "{\"nombre\":\"Dr. Jane Doe\",\"password\":\"newpassword\",\"jefe\":false}";

        this.mockMvc.perform(put("/api/medicos/me")
                        .cookie(tokenCookie)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(content().json(String.format("{\"nombre\":\"Dr. Jane Doe\",\"email\":\"%s\",\"jefe\":false}", email)));
    }

    @Test
    @DisplayName("DELETE /api/medicos/me debe borrar el médico autenticado")
    void borrarTest() throws Exception {
        this.mockMvc.perform(delete("/api/medicos/me")
                        .cookie(tokenCookie))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("GET /api/medicos debe retornar la lista de todos los médicos")
    void listaMedicosTest() throws Exception {
        Medico medico1 = new Medico();
        medico1.nombre = "Dr. Jane Doe";
        medico1.email = "jane.doe@example.com";
        medico1.password = "password";
        medico1.jefe = false;
        repoMedico.save(medico1);

        this.mockMvc.perform(get("/api/medicos")
                        .cookie(tokenCookie))
                .andExpect(status().isOk())
                .andExpect(content().json(String.format("[{\"nombre\":\"Dr. John Doe\",\"email\":\"%s\",\"jefe\":true},{\"nombre\":\"Dr. Jane Doe\",\"email\":\"jane.doe@example.com\",\"jefe\":false}]", email)));
    }
}
