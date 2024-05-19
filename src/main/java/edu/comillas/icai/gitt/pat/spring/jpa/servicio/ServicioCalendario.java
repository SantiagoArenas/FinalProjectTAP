package edu.comillas.icai.gitt.pat.spring.jpa.servicio;

import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Calendario;
import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Medico;
import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Sala;
import edu.comillas.icai.gitt.pat.spring.jpa.repositorio.RepoCalendario;
import edu.comillas.icai.gitt.pat.spring.jpa.repositorio.RepoMedico;
import edu.comillas.icai.gitt.pat.spring.jpa.repositorio.RepoSala;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.*;

@Service
public class ServicioCalendario {

    @Autowired
    RepoCalendario repoCalendario;

    @Autowired
    RepoMedico repoMedico;

    @Autowired
    RepoSala repoSala;

    @Autowired
    ServicioConocimiento servicioConocimiento;

    private void gestionarDia(int año, int mes, int d){
        Calendar dia = Calendar.getInstance();
        dia.set(año, mes, d);
        dia.set(Calendar.HOUR_OF_DAY, 0);
        dia.set(Calendar.MINUTE, 0);
        dia.set(Calendar.SECOND, 0);
        dia.set(Calendar.MILLISECOND, 0);


        ArrayList<Medico> ocupados = new ArrayList<>();
        TreeSet<Sala> salas = new TreeSet<>();
        for (Sala sala : repoSala.findAll()){ // Para que se organicen por prioridad (1 es máxima prioridad y 3 mínima)
            salas.add(sala);
        }
        for (Sala sala : salas){
            for (int turno=0;turno<2;turno++) {
                Calendario c = new Calendario();
                c.dia = dia;
                c.sala = sala;
                c.turno = String.valueOf(turno);
                for (Medico medico : repoMedico.findAll()) {
                    if (servicioConocimiento.getConocimientosMedico(medico).containsAll(servicioConocimiento.getConocimientosSala(sala)) && !ocupados.contains(medico)) {
                        c.medico = medico;
                        ocupados.add(medico);
                        break;
                    }
                }
                repoCalendario.save(c);
            }
        }
    }

    public void gestionarMes(int año, int mes){
        YearMonth ym = YearMonth.of(año, mes);
        int ultimo_dia = ym.atEndOfMonth().getDayOfMonth();
        for (int dia=1;dia<=ultimo_dia;dia++){
            gestionarDia(año, mes-1, dia);
        }
    }

    public List<Calendario> getMes(PeticionCalendario pc){
        List<Calendario> todos = (List<Calendario>) repoCalendario.findAll();
        List<Calendario> peticion = new ArrayList<Calendario>();
        for (Calendario c : todos){
            if (c.dia.get(Calendar.MONTH) == pc.mes()-1 && c.dia.get(Calendar.YEAR) == pc.año()){
                peticion.add(c);
            }
        }
        return peticion;
    }
    
}
