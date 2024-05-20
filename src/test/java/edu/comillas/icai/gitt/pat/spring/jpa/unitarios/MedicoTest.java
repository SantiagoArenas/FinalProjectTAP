package edu.comillas.icai.gitt.pat.spring.jpa.unitarios;

import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Medico;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MedicoTest {

    @Test
    @DisplayName("Test Medico")
    void crearMedico() {
        // Given
        String nombre = "Dr. Smith";
        String email = "dr.smith@example.com";
        String password = "password";
        boolean jefe = true;

        // When
        Medico medico = new Medico();
        medico.id = 1L;
        medico.nombre = nombre;
        medico.email = email;
        medico.password = password;
        medico.jefe = jefe;

        // Then
        assertThat(medico.id).isEqualTo(1L);
        assertThat(medico.nombre).isEqualTo(nombre);
        assertThat(medico.email).isEqualTo(email);
        assertThat(medico.password).isEqualTo(password);
        assertThat(medico.jefe).isEqualTo(jefe);
    }
}
