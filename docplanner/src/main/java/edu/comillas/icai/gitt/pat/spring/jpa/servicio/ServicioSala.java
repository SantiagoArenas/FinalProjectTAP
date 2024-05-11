package edu.comillas.icai.gitt.pat.spring.jpa.servicio;

import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Sala;
import edu.comillas.icai.gitt.pat.spring.jpa.modelo.RespuestaSala;
import edu.comillas.icai.gitt.pat.spring.jpa.repositorio.RepoConocimientoSala;
import edu.comillas.icai.gitt.pat.spring.jpa.repositorio.RepoMedico;
import edu.comillas.icai.gitt.pat.spring.jpa.repositorio.RepoSala;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ServicioSala {

    @Autowired
    RepoSala repoSala;

    @Autowired
    RepoConocimientoSala repoConocimientoSala;

    @Autowired
    RepoMedico repoMedico;

    public RespuestaSala crear(Sala sala){
        repoSala.save(sala);
        return new RespuestaSala(sala.nombre, sala.prioridad);
    }

    public void borrar(Sala sala){
        repoSala.delete(sala);
    }

    public RespuestaSala informacion(Sala sala){
        return new RespuestaSala(sala.nombre, sala.prioridad);
    }

    public Sala buscarSala(String nombre){
        return repoSala.findByNombre(nombre);
    }

    public ArrayList<Sala> salas(){
        return (ArrayList<Sala>) repoSala.findAll();
    }

}
