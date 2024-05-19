package edu.comillas.icai.gitt.pat.spring.jpa.unitarios;

import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Medico;
import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Token;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TokenTest {

    @Test
    public void testConstructor() {
        String id = "some-unique-id";

        Token token = new Token(id);

        assertEquals(id, token.getId());
    }

    @Test
    public void testGettersAndSetters() {
        Token token = new Token();
        Medico medico = new Medico();
        medico.setId(1L);
        medico.setNombre("Dr. Juan Pérez");
        medico.setEmail("juan.perez@example.com");
        medico.setPassword("password");
        medico.setJefe(true);

        String id = "some-unique-id";
        token.setId(id);
        token.setMedico(medico);

        assertEquals(id, token.getId());
        assertEquals(medico, token.getMedico());
    }

    @Test
    public void testDefaultConstructor() {
        Token token = new Token();

        assertNull(token.getId());
        assertNull(token.getMedico());
    }
}
