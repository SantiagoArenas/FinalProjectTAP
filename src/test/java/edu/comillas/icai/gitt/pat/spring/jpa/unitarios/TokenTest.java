package edu.comillas.icai.gitt.pat.spring.jpa.unitarios;

import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Medico;
import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Token;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TokenTest {

    @Test
    @DisplayName("Token Test")
    void crearToken() {
        // Given
        Medico medico = new Medico();
        medico.id = 1L;
        medico.nombre = "Dr. Smith";
        medico.email = "dr.smith@example.com";
        medico.password = "password";
        medico.jefe = true;

        // When
        Token token = new Token();
        token.id = "uuid-1234-5678-91011";  // Simulación de un UUID
        token.medico = medico;

        // Then
        assertThat(token.id).isEqualTo("uuid-1234-5678-91011");
        assertThat(token.medico).isEqualTo(medico);
    }
}
