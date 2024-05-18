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

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isJefe() {
        return jefe;
    }

    public void setJefe(boolean jefe) {
        this.jefe = jefe;
    }

}