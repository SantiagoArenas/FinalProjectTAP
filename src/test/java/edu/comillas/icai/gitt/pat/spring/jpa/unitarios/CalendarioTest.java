package edu.comillas.icai.gitt.pat.spring.jpa.unitarios;

import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Calendario;
import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Medico;
import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Sala;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.assertj.core.api.Assertions.assertThat;

class CalendarioTest {

    @Test
    @DisplayName("Test Calendario")
    void crearCalendario() {
        // Given
        Calendar dia = new GregorianCalendar(2024, Calendar.MAY, 15);
        Sala sala = new Sala();
        sala.id = 1L;
        sala.nombre = "Sala A";
        sala.prioridad = 1;

        Medico medico = new Medico();
        medico.id = 1L;
        medico.nombre = "Dr. Smith";
        medico.email = "dr.smith@example.com";
        medico.password = "password";
        medico.jefe = true;

        // When
        Calendario calendario = new Calendario();
        calendario.id = 1L;
        calendario.dia = dia;
        calendario.sala = sala;
        calendario.turno = "mañana";
        calendario.medico = medico;

        // Then
        assertThat(calendario.id).isEqualTo(1L);
        assertThat(calendario.dia).isEqualTo(dia);
        assertThat(calendario.sala).isEqualTo(sala);
        assertThat(calendario.turno).isEqualTo("mañana");
        assertThat(calendario.medico).isEqualTo(medico);
    }

}
