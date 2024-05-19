package edu.comillas.icai.gitt.pat.spring.jpa.unitarios;

import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Sala;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SalaTest {

    @Test
    public void testConstructor() {
        Long id = 1L;
        String nombre = "Sala A";
        Integer prioridad = 1;
        Integer capacidad = 30;

        Sala sala = new Sala();
        sala.setId(id);
        sala.setNombreSala(nombre);
        sala.setPrioridad(prioridad);
        sala.setCapacidad(capacidad);

        assertEquals(id, sala.getIdSala());
        assertEquals(nombre, sala.getNombreSala());
        assertEquals(prioridad, sala.getPrioridad());
        assertEquals(capacidad, sala.getCapacidad());
    }

    @Test
    public void testGettersAndSetters() {
        Sala sala = new Sala();

        sala.setId(2L);
        sala.setNombreSala("Sala B");
        sala.setPrioridad(2);
        sala.setCapacidad(50);

        assertEquals(2L, sala.getIdSala());
        assertEquals("Sala B", sala.getNombreSala());
        assertEquals(2, sala.getPrioridad());
        assertEquals(50, sala.getCapacidad());
    }

    @Test
    public void testCompareTo() {
        Sala sala1 = new Sala();
        sala1.setPrioridad(1);

        Sala sala2 = new Sala();
        sala2.setPrioridad(2);

        Sala sala3 = new Sala();
        sala3.setPrioridad(1);

        assertTrue(sala1.compareTo(sala2) < 0);
        assertTrue(sala2.compareTo(sala1) > 0);
        assertTrue(sala1.compareTo(sala3) == 1);
    }
}
