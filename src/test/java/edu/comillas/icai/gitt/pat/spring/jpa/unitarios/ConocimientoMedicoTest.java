package edu.comillas.icai.gitt.pat.spring.jpa.unitarios;

import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Conocimiento;
import edu.comillas.icai.gitt.pat.spring.jpa.entidad.ConocimientoMedico;
import edu.comillas.icai.gitt.pat.spring.jpa.entidad.Medico;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ConocimientoMedicoTest {

    @Test
    void testGettersAndSetters() {
        ConocimientoMedico conocimientoMedico = new ConocimientoMedico();

        Long id = 1L;
        Medico medico = new Medico();
        medico.setId(1L);
        medico.setNombre("Dr. Juan Pérez");
        Conocimiento conocimiento = new Conocimiento();
        conocimiento.setId(1L);
        conocimiento.setNombre("Conocimiento A");

        conocimientoMedico.setId(id);
        conocimientoMedico.setMedico(medico);
        conocimientoMedico.setConocimiento(conocimiento);

        assertEquals(id, conocimientoMedico.getId());
        assertEquals(medico, conocimientoMedico.getMedico());
        assertEquals(conocimiento, conocimientoMedico.getConocimiento());
    }

    @Test
    void testConstructor() {
        Long id = 1L;
        Medico medico = new Medico();
        medico.setId(1L);
        medico.setNombre("Dr. Juan Pérez");
        Conocimiento conocimiento = new Conocimiento();
        conocimiento.setId(1L);
        conocimiento.setNombre("Conocimiento A");

        ConocimientoMedico conocimientoMedico = new ConocimientoMedico();
        conocimientoMedico.setId(id);
        conocimientoMedico.setMedico(medico);
        conocimientoMedico.setConocimiento(conocimiento);

        assertEquals(id, conocimientoMedico.getId());
        assertEquals(medico, conocimientoMedico.getMedico());
        assertEquals(conocimiento, conocimientoMedico.getConocimiento());
    }
}
