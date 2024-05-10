package edu.comillas.icai.gitt.pat.spring.jpa.modelo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record Modificacion(@NotBlank String nombre, @NotBlank String password, @NotNull boolean jefe) {}
