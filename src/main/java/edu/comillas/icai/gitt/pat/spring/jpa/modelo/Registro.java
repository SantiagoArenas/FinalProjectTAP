package edu.comillas.icai.gitt.pat.spring.jpa.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record Registro (@NotBlank String nombre, @NotBlank@Email String email, @NotBlank String password, @NotNull boolean jefe){}
