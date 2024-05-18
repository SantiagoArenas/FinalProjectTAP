package edu.comillas.icai.gitt.pat.spring.jpa.modelo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record Login(
        @Email @NotBlank String email,
        @NotBlank String password
) {}