package edu.comillas.icai.gitt.pat.spring.jpa.controlador;

import edu.comillas.icai.gitt.pat.spring.jpa.entidad.*;
import edu.comillas.icai.gitt.pat.spring.jpa.repositorio.*;
import edu.comillas.icai.gitt.pat.spring.jpa.servicio.ServicioConocimiento;
import edu.comillas.icai.gitt.pat.spring.jpa.servicio.ServicioMedico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@RestController
public class ControladorConocimiento {

    @Autowired
    private RepoConocimientoMedico repoConocimientoMedico;

    @Autowired
    private RepoConocimientoSala repoConocimientoSala;

    @Autowired
    private RepoConocimiento repoConocimiento;

    @Autowired
    private ServicioConocimiento servicioConocimiento;

    @Autowired
    private ServicioMedico servicioMedico;

    @Autowired
    private RepoMedico repoMedico;

    @Autowired
    private RepoSala repoSala;

    @PostMapping("/api/conocimientos")
    public String crear(@RequestBody Conocimiento c, @RequestHeader("session") String sesion){
        Medico medico = servicioMedico.buscarMedico(sesion);
        if (medico == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        if (!medico.jefe) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        return servicioConocimiento.crear(c);
    }

    @DeleteMapping("/api/conocimientos")
    public void borrar(@RequestBody Conocimiento c, @RequestHeader("session") String sesion){
        Medico medico = servicioMedico.buscarMedico(sesion);
        if (medico == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        if (!medico.isJefe()) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        Conocimiento conocimiento = repoConocimiento.findByNombre(c.getNombre());
        if (conocimiento == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Conocimiento no encontrado");
        }
        repoConocimiento.delete(conocimiento);
    }

    @PostMapping("/api/conocimientos/medicos/{email}")
    public ConocimientoMedico addToMedico(@RequestBody Conocimiento c, @PathVariable String email, @RequestHeader("session") String sesion){
        Medico medico = servicioMedico.buscarMedico(sesion);
        if (medico == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        if (!medico.jefe) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        ConocimientoMedico cm = new ConocimientoMedico();
        Medico m = repoMedico.findByEmail(email);
        if (m == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        cm.setMedico(m);
        cm.setConocimiento(repoConocimiento.findByNombre(c.getNombre()));
        return repoConocimientoMedico.save(cm);
    }

    @DeleteMapping("/api/conocimientos/medicos/{email}")
    public void removeFromMedico(@RequestBody Conocimiento c, @PathVariable String email, @RequestHeader("session") String sesion){
        Medico medico = servicioMedico.buscarMedico(sesion);
        if (medico == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        if (!medico.jefe) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        Medico m = repoMedico.findByEmail(email);
        if (m == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        Conocimiento conocimiento = repoConocimiento.findByNombre(c.getNombre());
        ConocimientoMedico cm = repoConocimientoMedico.findByMedicoAndConocimiento(m, conocimiento);
        repoConocimientoMedico.delete(cm);
    }

    @PostMapping("/api/conocimientos/salas/{nombreSala}")
    public ConocimientoSala addToSala(@RequestBody Conocimiento c, @PathVariable String nombreSala, @RequestHeader("session") String sesion){
        Medico medico = servicioMedico.buscarMedico(sesion);
        if (medico == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        if (!medico.jefe) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        ConocimientoSala cs = new ConocimientoSala();
        Sala sala = repoSala.findByNombre(nombreSala);
        if (sala == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        cs.setSala(sala);
        cs.setConocimiento(repoConocimiento.findByNombre(c.getNombre()));
        return repoConocimientoSala.save(cs);
    }

    @DeleteMapping("/api/conocimientos/salas/{nombreSala}")
    public void removeFromSala(@RequestBody Conocimiento c, @PathVariable String nombreSala, @RequestHeader("session") String sesion){
        Medico medico = servicioMedico.buscarMedico(sesion);
        if (medico == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        if (!medico.jefe) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        Sala s = repoSala.findByNombre(nombreSala);
        if (s == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        Conocimiento conocimiento = repoConocimiento.findByNombre(c.getNombre());
        ConocimientoSala cs = repoConocimientoSala.findBySalaAndConocimiento(s, conocimiento);
        repoConocimientoSala.delete(cs);
    }

    @GetMapping("/api/conocimientos/medicos/{email}")
    public ArrayList<Conocimiento> getConocimientos(@PathVariable String email, @RequestHeader("session") String sesion){
        Medico medico = servicioMedico.buscarMedico(sesion);
        if (medico == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        Medico m = repoMedico.findByEmail(email);
        if (m == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return servicioConocimiento.getConocimientosMedico(m);
    }



}
