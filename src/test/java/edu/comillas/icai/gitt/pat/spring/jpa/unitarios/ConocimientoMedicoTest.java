package edu.comillas.icai.gitt.pat.spring.jpa.unitarios;

import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Conocimiento;
import edu.comillas.icai.gitt.pat.spring.jpa.entidad.ConocimientoMedico;
import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Medico;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ConocimientoMedicoTest {

    @Test
    @DisplayName("Conocimiento Medico Test")
    void crearConocimientoMedico() {
        // Given
        Medico medico = new Medico();
        medico.id = 1L;
        medico.nombre = "Dr. Smith";
        medico.email = "dr.smith@example.com";
        medico.password = "password";
        medico.jefe = true;

        Conocimiento conocimiento = new Conocimiento();
        conocimiento.id = 1L;
        conocimiento.nombre = "Cardiología";

        // When
        ConocimientoMedico conocimientoMedico = new ConocimientoMedico();
        conocimientoMedico.id = 1L;
        conocimientoMedico.medico = medico;
        conocimientoMedico.conocimiento = conocimiento;

        // Then
        assertThat(conocimientoMedico.id).isEqualTo(1L);
        assertThat(conocimientoMedico.medico).isEqualTo(medico);
        assertThat(conocimientoMedico.conocimiento).isEqualTo(conocimiento);
    }
}
