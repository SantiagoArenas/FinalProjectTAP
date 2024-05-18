package edu.comillas.icai.gitt.pat.spring.jpa.entidad;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class ConocimientoSala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) public Long id;

    @OnDelete(action = OnDeleteAction.CASCADE)@ManyToOne(cascade = CascadeType.ALL) public Sala sala;

    @OnDelete(action = OnDeleteAction.CASCADE)@ManyToOne(cascade = CascadeType.ALL) public Conocimiento conocimiento;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public Conocimiento getConocimiento() {
        return conocimiento;
    }

    public void setConocimiento(Conocimiento conocimiento) {
        this.conocimiento = conocimiento;
    }




}
