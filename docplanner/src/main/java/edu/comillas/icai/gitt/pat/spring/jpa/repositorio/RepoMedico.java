package edu.comillas.icai.gitt.pat.spring.jpa.repositorio;

import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Medico;
import org.springframework.data.repository.CrudRepository;

public interface RepoMedico extends CrudRepository<Medico, Long> {

    public Medico findByEmail(String email);

    public Medico findByNombre(String nombre);

}
