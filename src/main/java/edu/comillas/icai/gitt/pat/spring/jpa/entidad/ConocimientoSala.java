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

}
