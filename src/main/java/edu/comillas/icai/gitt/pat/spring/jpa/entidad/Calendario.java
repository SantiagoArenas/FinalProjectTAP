package edu.comillas.icai.gitt.pat.spring.jpa.entidad;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Calendar;
import java.util.Date;

@Entity
public class Calendario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) public Long id;

    @Column(nullable = false) public Calendar dia;

    @OnDelete(action = OnDeleteAction.CASCADE) @ManyToOne public Sala sala;

    @Column(nullable = false) public String turno;

    @OnDelete(action = OnDeleteAction.CASCADE) @ManyToOne public Medico medico;

}