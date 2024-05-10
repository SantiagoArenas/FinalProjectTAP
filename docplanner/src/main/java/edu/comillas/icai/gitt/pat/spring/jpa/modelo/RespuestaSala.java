package edu.comillas.icai.gitt.pat.spring.jpa.modelo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RespuestaSala(@NotBlank String nombre, @NotNull Integer prioridad){}