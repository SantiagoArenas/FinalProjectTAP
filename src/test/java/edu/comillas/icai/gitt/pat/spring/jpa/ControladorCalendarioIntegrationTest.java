package edu.comillas.icai.gitt.pat.spring.jpa;

import edu.comillas.icai.gitt.pat.spring.jpa.controlador.ControladorCalendario;
import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Calendario;
import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Medico;
import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Sala;
import edu.comillas.icai.gitt.pat.spring.jpa.modelo.PeticionCalendario;
import edu.comillas.icai.gitt.pat.spring.jpa.servicio.ServicioCalendario;
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

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

@WebMvcTest(ControladorCalendario.class)
class ControladorCalendarioIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServicioCalendario servicioCalendario;

    @MockBean
    private ServicioMedico servicioMedico;

    @Test
    @DisplayName("POST /api/medicos/me/calendario debe organizar el calendario para el médico autenticado")
    void organizarCalendarioTest() throws Exception {
        // Given ...
        String request = "{\"año\":2024,\"mes\":5}";
        Mockito.doNothing().when(servicioCalendario).gestionarMes(2024, 5);
        Mockito.when(servicioMedico.buscarMedico(Mockito.anyString())).thenReturn(new Medico());

        // When ...
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/medicos/me/calendario")
                        .cookie(new MockCookie("session", "valid-session"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                // Then ...
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("GET /api/medicos/me/calendario debe retornar la lista de calendarios del mes para el médico autenticado")
    void getCalendariosTest() throws Exception {
        // Given ...
        String request = "{\"año\":2024,\"mes\":5}";
        Medico medicoMock = new Medico(); // Asegúrate de inicializar correctamente el objeto Medico.
        Sala salaMock = new Sala(); // Asegúrate de inicializar correctamente el objeto Sala.

        Mockito.when(servicioMedico.buscarMedico(Mockito.anyString())).thenReturn(medicoMock);
        Mockito.when(servicioCalendario.getMes(Mockito.any(PeticionCalendario.class)))
                .thenReturn(Arrays.asList(
                        new Calendario() {{
                            id = 1L;
                            dia = new GregorianCalendar(2024, Calendar.APRIL, 30, 22, 0, 0);
                            turno = "mañana";
                            medico = medicoMock;
                            sala = salaMock;
                        }},
                        new Calendario() {{
                            id = 2L;
                            dia = new GregorianCalendar(2024, Calendar.MAY, 14, 22, 0, 0);
                            turno = "tarde";
                            medico = medicoMock;
                            sala = salaMock;
                        }}
                ));

        // When ...
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/medicos/me/calendario")
                        .cookie(new MockCookie("session", "valid-session"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                // Then ...
                .andExpect(MockMvcResultMatchers.status().isOk());
                /*.andExpect(MockMvcResultMatchers.content().json("[" +
                        "{\"id\":1,\"dia\":\"2024-04-30T22:00:00.000+00:00\",\"turno\":\"mañana\",\"medico\":null,\"sala\":null}," +
                        "{\"id\":2,\"dia\":\"2024-05-14T22:00:00.000+00:00\",\"turno\":\"tarde\",\"medico\":null,\"sala\":null}]"));*/
    }

}

