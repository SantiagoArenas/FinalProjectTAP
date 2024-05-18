package edu.comillas.icai.gitt.pat.spring.jpa.modelo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class PeticionCalendario {
    @NotNull
    @Min(1)
    @Max(12)
    private int mes;

    @NotNull
    private int anio;

    // Constructor
    public PeticionCalendario(@NotNull int anio, @NotNull @Min(1) @Max(12) int mes) {
        this.anio = anio;
        this.mes = mes;
    }

    // Getters y setters
    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }
}
