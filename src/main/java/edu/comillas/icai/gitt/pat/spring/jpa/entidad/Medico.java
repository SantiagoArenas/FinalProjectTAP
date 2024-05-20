package edu.comillas.icai.gitt.pat.spring.jpa.entidad;

import jakarta.persistence.*;

@Entity
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) public Long id;

    @Column(nullable = false) public String nombre;

    @Column(nullable = false, unique = true) public String email;

    @Column(nullable = false) public String password;

    @Column(nullable = false) public boolean jefe;

}