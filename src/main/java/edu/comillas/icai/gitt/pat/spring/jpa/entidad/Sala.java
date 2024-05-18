package edu.comillas.icai.gitt.pat.spring.jpa.entidad;

import jakarta.persistence.*;

@Entity
public class Sala implements Comparable<Sala>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) public Long id;

    @Column(nullable = false, unique = true) public String nombre;

    @Column(nullable = false) public Integer prioridad;
    @Column(nullable = false) public Integer capacidad;

    @Override
    public int compareTo(Sala sala){
        if (this.prioridad < sala.prioridad){
            return -1;
        }
        else if (this.prioridad > sala.prioridad){
            return 1;
        }
        return 1;
    }
    // Getters y Setters
    public Long getIdSala() {
        return id;
    }

    public void setIdSala(Long id) {
        this.id = id;
    }

    public String getNombreSala() {
        return nombre;
    }

    public void setNombreSala(String nombre) {
        this.nombre = nombre;
    }
    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }


    public void setId(long l) {
        this.id=l;
    }
}