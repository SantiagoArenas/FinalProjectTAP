package edu.comillas.icai.gitt.pat.spring.jpa.controlador;

import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Medico;
import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Sala;
import edu.comillas.icai.gitt.pat.spring.jpa.modelo.RespuestaSala;
import edu.comillas.icai.gitt.pat.spring.jpa.repositorio.RepoConocimientoSala;
import edu.comillas.icai.gitt.pat.spring.jpa.repositorio.RepoSala;
import edu.comillas.icai.gitt.pat.spring.jpa.servicio.ServicioMedico;
import edu.comillas.icai.gitt.pat.spring.jpa.servicio.ServicioSala;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@RestController
public class ControladorSala {

    @Autowired
    ServicioMedico servicioMedico;

    @Autowired
    ServicioSala servicioSala;

    @PostMapping("/api/medicos/me/salas")
    public RespuestaSala crear(@RequestBody Sala sala, @CookieValue(value = "session", required = true) String sesion){
        Medico medico = servicioMedico.buscarMedico(sesion);
        if (medico == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        if (!medico.jefe) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        Sala s = servicioSala.buscarSala(sala.nombre);
        if (s != null) throw new ResponseStatusException(HttpStatus.CONFLICT);
        return servicioSala.crear(sala);
    }

    @DeleteMapping("/api/medicos/me/salas/{nombreSala}")
    public void borrar(@PathVariable String nombreSala, @CookieValue(value = "session", required = true) String sesion){
        Medico medico = servicioMedico.buscarMedico(sesion);
        if (medico == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        if (!medico.jefe) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        Sala sala = servicioSala.buscarSala(nombreSala);
        if (sala == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        servicioSala.borrar(sala);
    }

    @GetMapping("/api/medicos/me/salas/{nombreSala}")
    public RespuestaSala informacion(@PathVariable String nombreSala, @CookieValue(value = "session", required = true) String sesion){
        Medico medico = servicioMedico.buscarMedico(sesion);
        if (medico == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        Sala sala = servicioSala.buscarSala(nombreSala);
        if (sala == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return servicioSala.informacion(sala);
    }

    @GetMapping("/api/medicos/me/salas")
    public ArrayList<Sala> salas(@CookieValue(value = "session", required = true) String sesion){
        Medico medico = servicioMedico.buscarMedico(sesion);
        if (medico == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        return servicioSala.salas();
    }

}
