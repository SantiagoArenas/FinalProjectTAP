package edu.comillas.icai.gitt.pat.spring.jpa.controlador;

import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Calendario;
import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Medico;
import edu.comillas.icai.gitt.pat.spring.jpa.modelo.PeticionCalendario;
import edu.comillas.icai.gitt.pat.spring.jpa.servicio.ServicioCalendario;
import edu.comillas.icai.gitt.pat.spring.jpa.servicio.ServicioMedico;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class ControladorCalendario {

    @Autowired
    ServicioCalendario servicioCalendario;

    @Autowired
    ServicioMedico servicioMedico;

    @PostMapping("/api/medicos/me/calendario")
    public void organizar(@Valid @RequestBody PeticionCalendario pc, @CookieValue(value = "session", required = true) String sesion){
        Medico medico = servicioMedico.buscarMedico(sesion);
        if (medico == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        servicioCalendario.gestionarMes(pc.año(), pc.mes());
    }

    @GetMapping("/api/medicos/me/calendario/{año}/{mes}")
    public List<Calendario> getMes(@PathVariable int año, @PathVariable int mes, @CookieValue(value = "session", required = true) String sesion){
        Medico medico = servicioMedico.buscarMedico(sesion);
        if (medico == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        PeticionCalendario pc = new PeticionCalendario(año, mes);
        return servicioCalendario.getMes(pc);
    }

}
