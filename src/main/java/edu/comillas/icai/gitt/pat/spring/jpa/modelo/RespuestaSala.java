package edu.comillas.icai.gitt.pat.spring.jpa.modelo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RespuestaSala(@NotBlank String nombre, @NotNull Integer capacidad) {
    public RespuestaSala {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("Nombre no puede estar en blanco");
        }
        if (capacidad == null) {
            throw new IllegalArgumentException("Capacidad no puede ser nulo");
        }
    }
}