package edu.comillas.icai.gitt.pat.spring.jpa.repositorio;

import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Conocimiento;
import org.springframework.data.repository.CrudRepository;

public interface RepoConocimiento extends CrudRepository<Conocimiento, Long> {

    public Conocimiento findByNombre(String nombre);

}
