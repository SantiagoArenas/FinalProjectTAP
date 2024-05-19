package edu.comillas.icai.gitt.pat.spring.jpa.unitarios;

import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Conocimiento;
import edu.comillas.icai.gitt.pat.spring.jpa.entidad.ConocimientoSala;
import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Sala;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ConocimientoSalaTest {

    @Test
    void testGettersAndSetters() {
        ConocimientoSala conocimientoSala = new ConocimientoSala();

        Long id = 1L;
        Sala sala = new Sala();
        sala.setIdSala(1L);
        sala.setNombreSala("Sala A");

        Conocimiento conocimiento = new Conocimiento();
        conocimiento.setId(1L);
        conocimiento.setNombre("Conocimiento A");

        conocimientoSala.setId(id);
        conocimientoSala.setSala(sala);
        conocimientoSala.setConocimiento(conocimiento);

        assertEquals(id, conocimientoSala.getId());
        assertEquals(sala, conocimientoSala.getSala());
        assertEquals(conocimiento, conocimientoSala.getConocimiento());
    }

    @Test
    void testConstructor() {
        Long id = 1L;
        Sala sala = new Sala();
        sala.setIdSala(1L);
        sala.setNombreSala("Sala A");

        Conocimiento conocimiento = new Conocimiento();
        conocimiento.setId(1L);
        conocimiento.setNombre("Conocimiento A");

        ConocimientoSala conocimientoSala = new ConocimientoSala();
        conocimientoSala.setId(id);
        conocimientoSala.setSala(sala);
        conocimientoSala.setConocimiento(conocimiento);

        assertEquals(id, conocimientoSala.getId());
        assertEquals(sala, conocimientoSala.getSala());
        assertEquals(conocimiento, conocimientoSala.getConocimiento());
    }
}
