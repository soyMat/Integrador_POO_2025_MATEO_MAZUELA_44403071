package estrategia;

import modelo.InscripcionCarrera;
import modelo.Materia;
import modelo.PlanDeEstudios;

public interface CondicionInscripcion {
    boolean puedeInscribirse(InscripcionCarrera inscripcion, Materia materia, PlanDeEstudios plan);

    String getNombre();

    String getDescripcion();
}
