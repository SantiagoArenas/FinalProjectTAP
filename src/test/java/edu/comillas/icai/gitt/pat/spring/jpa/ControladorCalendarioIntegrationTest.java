package edu.comillas.icai.gitt.pat.spring.jpa;

import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Medico;
import edu.comillas.icai.gitt.pat.spring.jpa.modelo.PeticionCalendario;
import edu.comillas.icai.gitt.pat.spring.jpa.servicio.ServicioCalendario;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class ControladorCalendarioIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ServicioCalendario servicioCalendario;

	@MockBean
	private ServicioMedico servicioMedico;

	private Medico medico;
	private PeticionCalendario peticionCalendario;

	@BeforeEach
	public void setUp() {
		medico = new Medico();
		medico.setId(1L);
		medico.setNombre("Dr. Juan Pérez");

		peticionCalendario = new PeticionCalendario(2023, 5);

		Mockito.when(servicioMedico.buscarMedico(Mockito.anyString())).thenReturn(medico);
	}

	@Test
	public void testOrganizarCalendarioAuthorized() throws Exception {
		Mockito.doNothing().when(servicioCalendario).gestionarMes(Mockito.anyInt(), Mockito.anyInt());

		mockMvc.perform(MockMvcRequestBuilders.post("/api/medicos/me/calendario")
						.header("session", "valid-session")
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(peticionCalendario)))
				.andExpect(status().isOk());
	}

	@Test
	public void testOrganizarCalendarioUnauthorized() throws Exception {
		Mockito.when(servicioMedico.buscarMedico(Mockito.anyString())).thenReturn(null);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/medicos/me/calendario")
						.header("session", "invalid-session")
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(peticionCalendario)))
				.andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.message", is("Unauthorized")));
	}

	@Test
	public void testOrganizarCalendarioInvalidData() throws Exception {
		PeticionCalendario invalidRequest = new PeticionCalendario(2023, 0);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/medicos/me/calendario")
						.header("session", "valid-session")
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(invalidRequest)))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void testServicioCalendarioThrowsException() throws Exception {
		Mockito.doThrow(new RuntimeException("Service error")).when(servicioCalendario).gestionarMes(Mockito.anyInt(), Mockito.anyInt());

		mockMvc.perform(MockMvcRequestBuilders.post("/api/medicos/me/calendario")
						.header("session", "valid-session")
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(peticionCalendario)))
				.andExpect(status().isInternalServerError())
				.andExpect(jsonPath("$.message", is("Service error")));
	}

	@Test
	public void testServicioMedicoThrowsException() throws Exception {
		Mockito.when(servicioMedico.buscarMedico(Mockito.anyString())).thenThrow(new RuntimeException("Service error"));

		mockMvc.perform(MockMvcRequestBuilders.post("/api/medicos/me/calendario")
						.header("session", "valid-session")
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(peticionCalendario)))
				.andExpect(status().isInternalServerError())
				.andExpect(jsonPath("$.message", is("Service error")));
	}
}
