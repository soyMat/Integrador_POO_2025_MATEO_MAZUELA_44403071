package estrategia;

import modelo.InscripcionCarrera;
import modelo.Materia;
import modelo.PlanDeEstudios;

public class CondicionInscripcionE extends CondicionBase {
    @Override
    public boolean puedeInscribirse(InscripcionCarrera inscripcion, Materia materia, PlanDeEstudios plan) {
        return aproboFinalesCorrelativas(inscripcion, materia)
                && aproboFinalesAnteriores(inscripcion, materia, plan, 3);
    }

    @Override
    public String getNombre() {
        return "E";
    }

    @Override
    public String getDescripcion() {
        return "Finales de correlativas y de 3 cuatrimestres previos";
    }
}
