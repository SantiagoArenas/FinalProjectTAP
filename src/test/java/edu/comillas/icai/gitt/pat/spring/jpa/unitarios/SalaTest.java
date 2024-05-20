package edu.comillas.icai.gitt.pat.spring.jpa.unitarios;

import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Sala;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SalaTest {

    @Test
    @DisplayName("Sala Test")
    void crearSala() {
        // Given
        String nombre = "Sala A";
        int prioridad = 1;

        // When
        Sala sala = new Sala();
        sala.id = 1L;
        sala.nombre = nombre;
        sala.prioridad = prioridad;

        // Then
        assertThat(sala.id).isEqualTo(1L);
        assertThat(sala.nombre).isEqualTo(nombre);
        assertThat(sala.prioridad).isEqualTo(prioridad);
    }

    @Test
    @DisplayName("Sala Test de comparar")
    void compararSalas() {
        // Given
        Sala sala1 = new Sala();
        sala1.id = 1L;
        sala1.nombre = "Sala A";
        sala1.prioridad = 1;

        Sala sala2 = new Sala();
        sala2.id = 2L;
        sala2.nombre = "Sala B";
        sala2.prioridad = 2;

        Sala sala3 = new Sala();
        sala3.id = 3L;
        sala3.nombre = "Sala C";
        sala3.prioridad = 1;

        // When & Then
        assertThat(sala1.compareTo(sala2)).isLessThan(0); // sala1 tiene mayor prioridad que sala2
        assertThat(sala2.compareTo(sala1)).isGreaterThan(0); // sala2 tiene menor prioridad que sala1
        assertThat(sala1.compareTo(sala3)).isEqualTo(1); // misma prioridad, retorna 1 por default
    }
}
