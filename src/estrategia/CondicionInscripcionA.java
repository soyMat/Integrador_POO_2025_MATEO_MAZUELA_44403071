package estrategia;

import modelo.InscripcionCarrera;
import modelo.Materia;
import modelo.PlanDeEstudios;

public class CondicionInscripcionA extends CondicionBase {
    @Override
    public boolean puedeInscribirse(InscripcionCarrera inscripcion, Materia materia, PlanDeEstudios plan) {
        return aproboCursadasCorrelativas(inscripcion, materia);
    }

    @Override
    public String getNombre() {
        return "A";
    }

    @Override
    public String getDescripcion() {
        return "Cursadas aprobadas de las correlativas";
    }
}
