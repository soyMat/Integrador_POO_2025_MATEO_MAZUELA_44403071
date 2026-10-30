package estrategia;

import modelo.InscripcionCarrera;
import modelo.Materia;
import modelo.PlanDeEstudios;

abstract class CondicionBase implements CondicionInscripcion {
    protected boolean aproboCursadasCorrelativas(InscripcionCarrera inscripcion, Materia materia) {
        for (Materia correlativa : materia.getCorrelativas()) {
            if (!inscripcion.aproboCursada(correlativa)) {
                return false;
            }
        }
        return true;
    }

    protected boolean aproboFinalesCorrelativas(InscripcionCarrera inscripcion, Materia materia) {
        for (Materia correlativa : materia.getCorrelativas()) {
            if (!inscripcion.aproboFinal(correlativa)) {
                return false;
            }
        }
        return true;
    }

    protected boolean aproboFinalesAnteriores(InscripcionCarrera inscripcion, Materia materia,
                                               PlanDeEstudios plan, int cuatrimestresPrevios) {
        int ultimoCuatrimestreExigido = materia.getCuatrimestre() - cuatrimestresPrevios;
        for (Materia materiaAnterior : plan.getTodasLasMaterias()) {
            if (materiaAnterior.getCuatrimestre() <= ultimoCuatrimestreExigido
                    && !inscripcion.aproboFinal(materiaAnterior)) {
                return false;
            }
        }
        return true;
    }
}
