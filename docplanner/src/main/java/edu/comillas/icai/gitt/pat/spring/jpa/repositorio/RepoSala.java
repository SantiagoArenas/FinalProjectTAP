package edu.comillas.icai.gitt.pat.spring.jpa.repositorio;

import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Sala;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface RepoSala extends CrudRepository<Sala, Long> {

    public Sala findByNombre(String nombre);

}
