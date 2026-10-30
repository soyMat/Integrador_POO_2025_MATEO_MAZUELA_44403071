package estrategia;

import modelo.InscripcionCarrera;
import modelo.Materia;
import modelo.PlanDeEstudios;

public class CondicionInscripcionC extends CondicionBase {
    @Override
    public boolean puedeInscribirse(InscripcionCarrera inscripcion, Materia materia, PlanDeEstudios plan) {
        return aproboCursadasCorrelativas(inscripcion, materia)
                && aproboFinalesAnteriores(inscripcion, materia, plan, 5);
    }

    @Override
    public String getNombre() {
        return "C";
    }

    @Override
    public String getDescripcion() {
        return "Cursadas correlativas y finales de 5 cuatrimestres previos";
    }
}
