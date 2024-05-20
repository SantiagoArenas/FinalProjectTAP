package edu.comillas.icai.gitt.pat.spring.jpa.repositorio;

import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Medico;
import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Token;
import org.springframework.data.repository.CrudRepository;

public interface RepoToken extends CrudRepository<Token, String> {

    public Token findByMedico(Medico medico);

}
