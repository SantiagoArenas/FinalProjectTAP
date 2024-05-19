package edu.comillas.icai.gitt.pat.spring.jpa.controlador;

import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Medico;
import edu.comillas.icai.gitt.pat.spring.jpa.modelo.PeticionCalendario;
import edu.comillas.icai.gitt.pat.spring.jpa.servicio.ServicioCalendario;
import edu.comillas.icai.gitt.pat.spring.jpa.servicio.ServicioMedico;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ControladorCalendario {

    private final ServicioCalendario servicioCalendario;
    private final ServicioMedico servicioMedico;

    public ControladorCalendario(ServicioCalendario servicioCalendario, ServicioMedico servicioMedico) {
        this.servicioCalendario = servicioCalendario;
        this.servicioMedico = servicioMedico;
    }

    @PostMapping("/api/medicos/me/calendario")
    public void organizar(@Valid @RequestBody PeticionCalendario pc, @RequestHeader(value = "session", required = true) String sesion) {
        Medico medico = servicioMedico.buscarMedico(sesion);
        if (medico == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        try {
            servicioCalendario.gestionarMes(pc.getAnio(), pc.getMes());
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, String>> handleResponseStatusException(ResponseStatusException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getReason());
        return new ResponseEntity<>(response, ex.getStatusCode());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/api/medicos/me/calendario")
    public List<Calendario> getMes(@RequestBody PeticionCalendario pc, @CookieValue(value = "session", required = true) String sesion){
        Medico medico = servicioMedico.buscarMedico(sesion);
        if (medico == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        return servicioCalendario.getMes(pc);
    }
    
}
