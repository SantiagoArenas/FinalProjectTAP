package edu.comillas.icai.gitt.pat.spring.jpa.modelo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RespuestaMedico (@NotBlank String nombre, @NotBlank String email, @NotNull boolean jefe){}