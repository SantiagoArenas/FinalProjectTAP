package edu.comillas.icai.gitt.pat.spring.jpa.entidad;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) public String id;

    @OnDelete(action = OnDeleteAction.CASCADE) @OneToOne
    public Medico medico;

}
