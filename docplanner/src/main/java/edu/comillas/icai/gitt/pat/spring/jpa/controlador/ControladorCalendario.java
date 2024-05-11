package edu.comillas.icai.gitt.pat.spring.jpa.controlador;

import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Medico;
import edu.comillas.icai.gitt.pat.spring.jpa.modelo.PeticionCalendario;
import edu.comillas.icai.gitt.pat.spring.jpa.servicio.ServicioCalendario;
import edu.comillas.icai.gitt.pat.spring.jpa.servicio.ServicioMedico;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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

}
