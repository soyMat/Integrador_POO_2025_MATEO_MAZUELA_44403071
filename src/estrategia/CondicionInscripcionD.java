package estrategia;

import modelo.InscripcionCarrera;
import modelo.Materia;
import modelo.PlanDeEstudios;

public class CondicionInscripcionD extends CondicionBase {
    @Override
    public boolean puedeInscribirse(InscripcionCarrera inscripcion, Materia materia, PlanDeEstudios plan) {
        return aproboCursadasCorrelativas(inscripcion, materia)
                && aproboFinalesAnteriores(inscripcion, materia, plan, 3);
    }

    @Override
    public String getNombre() {
        return "D";
    }

    @Override
    public String getDescripcion() {
        return "Cursadas correlativas y finales de 3 cuatrimestres previos";
    }
}
