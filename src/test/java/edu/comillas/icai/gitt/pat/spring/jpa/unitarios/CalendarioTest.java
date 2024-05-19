package edu.comillas.icai.gitt.pat.spring.jpa.unitarios;

import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Calendario;
import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Medico;
import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Sala;
import org.junit.jupiter.api.Test;
import java.util.Calendar;
import static org.junit.jupiter.api.Assertions.*;

public class CalendarioTest {

    @Test
    void testGettersAndSetters() {
        Calendario calendario = new Calendario();

        Long id = 1L;
        Calendar dia = Calendar.getInstance();
        Sala sala = new Sala();
        sala.setIdSala(1L);
        sala.setNombreSala("Sala A");

        String turno = "Mañana";
        Medico medico = new Medico();
        medico.setId(1L);
        medico.setNombre("Dr. Juan Pérez");

        calendario.setId(id);
        calendario.setDia(dia);
        calendario.setSala(sala);
        calendario.setTurno(turno);
        calendario.setMedico(medico);

        assertEquals(id, calendario.getId());
        assertEquals(dia, calendario.getDia());
        assertEquals(sala, calendario.getSala());
        assertEquals(turno, calendario.getTurno());
        assertEquals(medico, calendario.getMedico());
    }

    @Test
    void testConstructor() {
        Long id = 1L;
        Calendar dia = Calendar.getInstance();
        Sala sala = new Sala();
        sala.setIdSala(1L);
        sala.setNombreSala("Sala A");

        String turno = "Mañana";
        Medico medico = new Medico();
        medico.setId(1L);
        medico.setNombre("Dr. Juan Pérez");

        Calendario calendario = new Calendario();
        calendario.setId(id);
        calendario.setDia(dia);
        calendario.setSala(sala);
        calendario.setTurno(turno);
        calendario.setMedico(medico);

        assertEquals(id, calendario.getId());
        assertEquals(dia, calendario.getDia());
        assertEquals(sala, calendario.getSala());
        assertEquals(turno, calendario.getTurno());
        assertEquals(medico, calendario.getMedico());
    }
}
