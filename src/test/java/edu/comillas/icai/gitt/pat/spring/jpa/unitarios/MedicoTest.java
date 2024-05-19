package edu.comillas.icai.gitt.pat.spring.jpa.unitarios;

import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Medico;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MedicoTest {

    @Test
    public void testConstructor() {
        Long id = 1L;
        String nombre = "Dr. Juan Pérez";
        String email = "juan.perez@example.com";
        String password = "password";
        boolean jefe = true;

        Medico medico = new Medico();
        medico.setId(id);
        medico.setNombre(nombre);
        medico.setEmail(email);
        medico.setPassword(password);
        medico.setJefe(jefe);

        assertEquals(id, medico.getId());
        assertEquals(nombre, medico.getNombre());
        assertEquals(email, medico.getEmail());
        assertEquals(password, medico.getPassword());
        assertTrue(medico.isJefe());
    }

    @Test
    public void testGettersAndSetters() {
        Medico medico = new Medico();

        medico.setId(2L);
        medico.setNombre("Dr. Maria Lopez");
        medico.setEmail("maria.lopez@example.com");
        medico.setPassword("password123");
        medico.setJefe(false);

        assertEquals(2L, medico.getId());
        assertEquals("Dr. Maria Lopez", medico.getNombre());
        assertEquals("maria.lopez@example.com", medico.getEmail());
        assertEquals("password123", medico.getPassword());
        assertFalse(medico.isJefe());
    }
}
