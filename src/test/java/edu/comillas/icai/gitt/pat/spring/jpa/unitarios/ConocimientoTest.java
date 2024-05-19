package edu.comillas.icai.gitt.pat.spring.jpa.unitarios;

import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Conocimiento;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ConocimientoTest {

    @Test
    void testGettersAndSetters() {
        Conocimiento conocimiento = new Conocimiento();

        Long id = 1L;
        String nombre = "Conocimiento A";

        conocimiento.setId(id);
        conocimiento.setNombre(nombre);

        assertEquals(id, conocimiento.getId());
        assertEquals(nombre, conocimiento.getNombre());
    }

    @Test
    void testConstructor() {
        Long id = 1L;
        String nombre = "Conocimiento A";

        Conocimiento conocimiento = new Conocimiento(id, nombre);

        assertEquals(id, conocimiento.getId());
        assertEquals(nombre, conocimiento.getNombre());

        Conocimiento conocimiento2 = new Conocimiento(nombre);
        assertNull(conocimiento2.getId());
        assertEquals(nombre, conocimiento2.getNombre());
    }

    @Test
    void testEqualsAndHashCode() {
        Conocimiento conocimiento1 = new Conocimiento(1L, "Conocimiento A");
        Conocimiento conocimiento2 = new Conocimiento(1L, "Conocimiento A");
        Conocimiento conocimiento3 = new Conocimiento(2L, "Conocimiento B");

        assertEquals(conocimiento1, conocimiento2);
        assertNotEquals(conocimiento1, conocimiento3);
        assertEquals(conocimiento1.hashCode(), conocimiento2.hashCode());
        assertNotEquals(conocimiento1.hashCode(), conocimiento3.hashCode());
    }

    @Test
    void testToString() {
        Conocimiento conocimiento = new Conocimiento(1L, "Conocimiento A");
        String expected = "Conocimiento{id=1, nombre='Conocimiento A'}";

        assertEquals(expected, conocimiento.toString());
    }
}
