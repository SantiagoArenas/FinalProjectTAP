package edu.comillas.icai.gitt.pat.spring.jpa.servicio;

import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Medico;
import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Token;
import edu.comillas.icai.gitt.pat.spring.jpa.modelo.Modificacion;
import edu.comillas.icai.gitt.pat.spring.jpa.modelo.Registro;
import edu.comillas.icai.gitt.pat.spring.jpa.modelo.RespuestaMedico;
import edu.comillas.icai.gitt.pat.spring.jpa.repositorio.RepoConocimiento;
import edu.comillas.icai.gitt.pat.spring.jpa.repositorio.RepoMedico;
import edu.comillas.icai.gitt.pat.spring.jpa.repositorio.RepoSala;
import edu.comillas.icai.gitt.pat.spring.jpa.repositorio.RepoToken;
import edu.comillas.icai.gitt.pat.spring.jpa.util.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServicioMedico {

    Hashing hashing = new Hashing();

    @Autowired
    RepoMedico repoMedico;

    @Autowired
    RepoToken repoToken;

    @Autowired
    RepoSala repoSala;

    @Autowired
    RepoConocimiento repoConocimiento;

    public Token guardarToken(String email, String password) { // TENEMOS QUE GUARDAR UN NUEVO TOKEN PARA LA SESIÓN DEL USUARIO. SI NO EXISTE UN TOKEN PARA ESE USUARIO, LO CREAMOS. SI EL USUARIO NO EXISTE O LA CONTRASEÑA ES INVÁLIDA, DEVOLVEMOS NULL.
        Medico medico = repoMedico.findByEmail(email);
        if (medico == null) return null;
        if (!hashing.compare(medico.password, password)) return null;

        Token token = repoToken.findByMedico(medico);
        if (token != null) return token;

        token = new Token();
        token.medico = medico;
        repoToken.save(token);
        return token;
    }

    public Medico buscarMedico(String tokenId) { // DEVUELVE EL USUARIO AL QUE ESTÁ ASOCIADO UN TOKEN
        Optional<Token> token = repoToken.findById(tokenId);
        if (token.isPresent()) return token.get().medico;
        return null;
    }

    public RespuestaMedico getDatos(Medico medico) { // SE USA PARA DEVOLVER LOS DATOS DE UN USUARIO (LA CONTRASEÑA NO)
        return new RespuestaMedico(medico.nombre, medico.email, medico.jefe);
    }
    public RespuestaMedico modificar(Medico m, Modificacion perfil) { // SE USA PARA MODIFICAR LOS DATOS DEL USUARIO A PARTIR DE UN PERFIL
        Medico medico = repoMedico.findByEmail(m.email);
        medico.nombre = perfil.nombre();
        medico.jefe = perfil.jefe();
        medico.password = hashing.hash(perfil.password());
        repoMedico.save(medico);
        return new RespuestaMedico(medico.nombre, medico.email, medico.jefe);
    }
    public RespuestaMedico crear(Registro registro) { // CREA UN USUARIO NUEVO
        Medico medico = new Medico();
        medico.nombre = registro.nombre();
        medico.email = registro.email();
        medico.password = hashing.hash(registro.password());
        medico.jefe = registro.jefe();
        repoMedico.save(medico);
        return new RespuestaMedico(medico.nombre, medico.email, medico.jefe);
    }

    public void logout(String tokenId) { // BORRA EL TOKEN ASOCIADO A LA SESIÓN DE UN USUARIO
        repoToken.deleteById(tokenId);
    }

    public void borrar(Medico medico) { // BORRA UN USUARIO
        repoMedico.delete(medico);
    }

}
