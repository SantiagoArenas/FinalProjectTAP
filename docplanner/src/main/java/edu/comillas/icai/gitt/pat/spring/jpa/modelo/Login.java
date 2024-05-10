package edu.comillas.icai.gitt.pat.spring.jpa.modelo;

import jakarta.validation.constraints.NotBlank;

public record Login(@NotBlank String email, @NotBlank String password){}
