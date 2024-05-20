package edu.comillas.icai.gitt.pat.spring.jpa.unitarios;

import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Conocimiento;
import edu.comillas.icai.gitt.pat.spring.jpa.entidad.ConocimientoSala;
import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Sala;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ConocimientoSalaTest {

    @Test
    @DisplayName("Conocimiento Sala Test")
    void crearConocimientoSala() {
        // Given
        Sala sala = new Sala();
        sala.id = 1L;
        sala.nombre = "Sala A";
        sala.prioridad = 1;

        Conocimiento conocimiento = new Conocimiento();
        conocimiento.id = 1L;
        conocimiento.nombre = "Cardiología";

        // When
        ConocimientoSala conocimientoSala = new ConocimientoSala();
        conocimientoSala.id = 1L;
        conocimientoSala.sala = sala;
        conocimientoSala.conocimiento = conocimiento;

        // Then
        assertThat(conocimientoSala.id).isEqualTo(1L);
        assertThat(conocimientoSala.sala).isEqualTo(sala);
        assertThat(conocimientoSala.conocimiento).isEqualTo(conocimiento);
    }
}
