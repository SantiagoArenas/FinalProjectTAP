package edu.comillas.icai.gitt.pat.spring.jpa.unitarios;

import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Medico;
import edu.comillas.icai.gitt.pat.spring.jpa.servicio.ServicioMedico;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ServicioMedicoTest {

    @Test
    void testCrearMedico() {
        ServicioMedico servicioMedico = new ServicioMedico();

        Medico medico = servicioMedico.crear(1L, "Dr. Juan Pérez", "juan.perez@example.com", "password", true);

        assertEquals(1L, medico.getId());
        assertEquals("Dr. Juan Pérez", medico.getNombre());
        assertEquals("juan.perez@example.com", medico.getEmail());
        assertEquals("password", medico.getPassword());
        assertTrue(medico.isJefe());
    }
}
