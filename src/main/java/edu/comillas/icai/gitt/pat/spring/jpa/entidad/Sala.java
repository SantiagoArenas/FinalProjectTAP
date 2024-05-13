package edu.comillas.icai.gitt.pat.spring.jpa.entidad;

import jakarta.persistence.*;

@Entity
public class Sala implements Comparable<Sala>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) public Long id;

    @Column(nullable = false, unique = true) public String nombre;

    @Column(nullable = false) public Integer prioridad;

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

}