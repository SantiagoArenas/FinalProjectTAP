package edu.comillas.icai.gitt.pat.spring.jpa.servicio;

import edu.comillas.icai.gitt.pat.spring.jpa.entidad.*;
import edu.comillas.icai.gitt.pat.spring.jpa.repositorio.RepoConocimiento;
import edu.comillas.icai.gitt.pat.spring.jpa.repositorio.RepoConocimientoMedico;
import edu.comillas.icai.gitt.pat.spring.jpa.repositorio.RepoConocimientoSala;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ServicioConocimiento {

    @Autowired
    RepoConocimiento repoConocimiento;

    @Autowired
    RepoConocimientoMedico repoConocimientoMedico;

    @Autowired
    RepoConocimientoSala repoConocimientoSala;

    public String crear(Conocimiento c){
        repoConocimiento.save(c);
        return c.nombre;
    }

    public Conocimiento buscar(String nombre){
        return repoConocimiento.findByNombre(nombre);
    }

    public ArrayList<Conocimiento> getConocimientosMedico(Medico medico){
        ArrayList<ConocimientoMedico> cms = repoConocimientoMedico.findAllByMedico(medico);
        ArrayList<Conocimiento> conocimientos = new ArrayList<Conocimiento>();
        for (ConocimientoMedico cm : cms){
            conocimientos.add(cm.conocimiento);
        }
        return conocimientos;
    }

    public ArrayList<Conocimiento> getConocimientosSala(Sala sala){
        ArrayList<ConocimientoSala> css = repoConocimientoSala.findAllBySala(sala);
        ArrayList<Conocimiento> conocimientos = new ArrayList<Conocimiento>();
        for (ConocimientoSala cs : css){
            conocimientos.add(cs.conocimiento);
        }
        return conocimientos;
    }


    public void borrar(Conocimiento conocimiento) {
        Conocimiento c = repoConocimiento.findByNombre(conocimiento.getNombre());
        if (c != null) {
            repoConocimiento.delete(c);
        }
    }

    public Conocimiento findByNombre(String nombre) {
        return repoConocimiento.findByNombre(nombre);
    }
    public boolean existe(Conocimiento conocimiento) {
        return repoConocimiento.existsByNombre(conocimiento.getNombre());
    }


}