package edu.comillas.icai.gitt.pat.spring.jpa.controlador;

import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Medico;
import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Sala;
import edu.comillas.icai.gitt.pat.spring.jpa.modelo.RespuestaSala;
import edu.comillas.icai.gitt.pat.spring.jpa.servicio.ServicioMedico;
import edu.comillas.icai.gitt.pat.spring.jpa.servicio.ServicioSala;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<RespuestaSala> crear(@RequestBody Sala sala, @RequestHeader(value = "session", required = true) String sesion) {
        Medico medico = servicioMedico.buscarMedico(sesion);
        if (medico == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        if (!medico.jefe) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        Sala s = servicioSala.buscarSala(sala.getNombreSala());
        if (s != null) throw new ResponseStatusException(HttpStatus.CONFLICT);
        RespuestaSala respuestaSala = servicioSala.crear(sala);
        return new ResponseEntity<>(respuestaSala, HttpStatus.CREATED);
    }

    @DeleteMapping("/api/medicos/me/salas/{nombreSala}")
    public ResponseEntity<Void> borrar(@PathVariable String nombreSala, @RequestHeader(value = "session", required = true) String sesion) {
        Medico medico = servicioMedico.buscarMedico(sesion);
        if (medico == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        if (!medico.jefe) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        Sala sala = servicioSala.buscarSala(nombreSala);
        if (sala == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        servicioSala.borrar(sala);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/api/medicos/me/salas/{nombreSala}")
    public ResponseEntity<RespuestaSala> informacion(@PathVariable String nombreSala, @RequestHeader(value = "session", required = true) String sesion) {
        Medico medico = servicioMedico.buscarMedico(sesion);
        if (medico == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        Sala sala = servicioSala.buscarSala(nombreSala);
        if (sala == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        RespuestaSala respuestaSala = servicioSala.informacion(sala);
        return new ResponseEntity<>(respuestaSala, HttpStatus.OK);
    }

    @GetMapping("/api/medicos/me/salas")
    public ResponseEntity<ArrayList<Sala>> salas(@RequestHeader(value = "session", required = true) String sesion) {
        Medico medico = servicioMedico.buscarMedico(sesion);
        if (medico == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        ArrayList<Sala> salas = servicioSala.salas();
        return new ResponseEntity<>(salas, HttpStatus.OK);
    }
}
