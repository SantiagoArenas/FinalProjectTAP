package edu.comillas.icai.gitt.pat.spring.jpa.entidad;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Conocimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) public Long id;

    @Column(nullable = false, unique = true) public String nombre;
    //@Column(nullable = false, unique = true) public Conocimiento conocimiento;


    // Constructors
    public Conocimiento(String conocimientoA) {}

    public Conocimiento(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Getters and Setters
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

    // equals, hashCode and toString methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conocimiento that = (Conocimiento) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(nombre, that.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre);
    }

    @Override
    public String toString() {
        return "Conocimiento{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}