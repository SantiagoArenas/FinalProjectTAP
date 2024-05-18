package edu.comillas.icai.gitt.pat.spring.jpa.entidad;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class ConocimientoMedico{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) public Long id;

    @OnDelete(action = OnDeleteAction.CASCADE)@ManyToOne public Medico medico;

    @OnDelete(action = OnDeleteAction.CASCADE)@ManyToOne public Conocimiento conocimiento;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Conocimiento getConocimiento() {
        return conocimiento;
    }

    public void setConocimiento(Conocimiento conocimiento) {
        this.conocimiento = conocimiento;
    }

}
