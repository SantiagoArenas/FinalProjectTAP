package edu.comillas.icai.gitt.pat.spring.jpa.unitarios;

import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Conocimiento;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ConocimientoTest {

    @Test
    @DisplayName("Test conocimiento")
    void crearConocimiento() {
        // Given
        Conocimiento conocimiento = new Conocimiento() {{
            id = 1L;
            nombre = "Cardiología";
        }};

        // When y Then
        assertThat(conocimiento.id).isEqualTo(1L);
        assertThat(conocimiento.nombre).isEqualTo("Cardiología");
    }
}
