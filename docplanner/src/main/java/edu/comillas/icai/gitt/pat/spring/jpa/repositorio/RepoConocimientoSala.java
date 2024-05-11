package edu.comillas.icai.gitt.pat.spring.jpa.repositorio;

import edu.comillas.icai.gitt.pat.spring.jpa.entidad.*;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface RepoConocimientoSala extends CrudRepository<ConocimientoSala, Long> {

    public ConocimientoSala findBySalaAndConocimiento(Sala sala, Conocimiento conocimiento);

    public ArrayList<ConocimientoSala> findAllBySala(Sala sala);

}
