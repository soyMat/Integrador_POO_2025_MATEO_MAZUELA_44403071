package estrategia;

import modelo.InscripcionCarrera;
import modelo.Materia;
import modelo.PlanDeEstudios;

public class CondicionInscripcionB extends CondicionBase {
    @Override
    public boolean puedeInscribirse(InscripcionCarrera inscripcion, Materia materia, PlanDeEstudios plan) {
        return aproboFinalesCorrelativas(inscripcion, materia);
    }

    @Override
    public String getNombre() {
        return "B";
    }

    @Override
    public String getDescripcion() {
        return "Finales aprobados de las correlativas";
    }
}
