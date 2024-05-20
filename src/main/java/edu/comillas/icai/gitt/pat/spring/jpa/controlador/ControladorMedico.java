package edu.comillas.icai.gitt.pat.spring.jpa.controlador;

import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Medico;
import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Token;
import edu.comillas.icai.gitt.pat.spring.jpa.modelo.Login;
import edu.comillas.icai.gitt.pat.spring.jpa.modelo.Modificacion;
import edu.comillas.icai.gitt.pat.spring.jpa.modelo.Registro;
import edu.comillas.icai.gitt.pat.spring.jpa.modelo.RespuestaMedico;
import edu.comillas.icai.gitt.pat.spring.jpa.servicio.ServicioMedico;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ControladorMedico {

    @Autowired
    ServicioMedico servicioMedico;

    @PostMapping("/api/medicos")
    @ResponseStatus(HttpStatus.CREATED)
    public RespuestaMedico registrar(@Valid @RequestBody Registro registro) {
        try {
            return servicioMedico.crear(registro);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }

    @PostMapping("/api/medicos/me/sesion")
    public ResponseEntity<Void> login(@Valid @RequestBody Login credenciales) {
        Token token = servicioMedico.guardarToken(credenciales.email(), credenciales.password());
        if (token == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        ResponseCookie session = ResponseCookie
                .from("session", token.id)
                .httpOnly(true)
                .path("/")
                .sameSite("Strict")
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.SET_COOKIE, session.toString()).build();
    }

    @DeleteMapping("/api/medicos/me/sesion")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> logout(@CookieValue(value = "session", required = true) String sesion) {
        Medico medico = servicioMedico.buscarMedico(sesion);
        if (medico == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        servicioMedico.logout(sesion);
        ResponseCookie expireSession = ResponseCookie
                .from("session")
                .httpOnly(true)
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).header(HttpHeaders.SET_COOKIE, expireSession.toString()).build();
    }

    @GetMapping("/api/medicos/me")
    @ResponseStatus(HttpStatus.OK)
    public RespuestaMedico informacion(@CookieValue(value = "session", required = true) String sesion) {
        Medico medico = servicioMedico.buscarMedico(sesion);
        if (medico == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        return servicioMedico.getDatos(medico);
    }

    @PutMapping("/api/medicos/me")
    @ResponseStatus(HttpStatus.OK)
    public RespuestaMedico actualizar(@Valid @RequestBody Modificacion modificacion, @CookieValue(value = "session", required = true) String sesion) {
        Medico medico = servicioMedico.buscarMedico(sesion);
        if (medico == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        return servicioMedico.modificar(medico, modificacion);
    }

    @DeleteMapping("/api/medicos/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void borrar(@CookieValue(value = "session", required = true) String sesion) {
        Medico medico = servicioMedico.buscarMedico(sesion);
        if (medico == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        servicioMedico.borrar(medico);
    }
}
