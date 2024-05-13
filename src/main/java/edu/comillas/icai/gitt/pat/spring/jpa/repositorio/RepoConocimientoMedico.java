package edu.comillas.icai.gitt.pat.spring.jpa.repositorio;

import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Conocimiento;
import edu.comillas.icai.gitt.pat.spring.jpa.entidad.ConocimientoMedico;
import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Medico;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface RepoConocimientoMedico extends CrudRepository<ConocimientoMedico, Long> {

    public ConocimientoMedico findByMedicoAndConocimiento(Medico medico, Conocimiento conocimiento);

    public ArrayList<ConocimientoMedico> findAllByMedico(Medico medico);

}
