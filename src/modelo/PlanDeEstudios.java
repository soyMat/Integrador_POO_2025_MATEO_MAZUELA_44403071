package modelo;

import estrategia.CondicionInscripcion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class PlanDeEstudios {
    private final String nombre;
    private final List<Materia> materiasObligatorias;
    private final List<Materia> materiasOptativas;
    private final int cantidadMinimaOptativas;
    private final CondicionInscripcion condicionInscripcion;

    PlanDeEstudios(String nombre, List<Materia> obligatorias, List<Materia> optativas,
                   int cantidadMinimaOptativas, CondicionInscripcion condicionInscripcion) {
        this.nombre = nombre;
        this.materiasObligatorias = new ArrayList<>(obligatorias);
        this.materiasOptativas = new ArrayList<>(optativas);
        this.cantidadMinimaOptativas = cantidadMinimaOptativas;
        this.condicionInscripcion = condicionInscripcion;
    }

    static void validar(String nombre, List<Materia> obligatorias, List<Materia> optativas,
                        int minimoOptativas, CondicionInscripcion condicion) {
        if (nombre == null || nombre.isBlank()) {
            throw new ReglaDeNegocioException("El nombre del plan es obligatorio");
        }
        if (condicion == null) {
            throw new ReglaDeNegocioException("El plan debe definir una condicion de inscripcion");
        }
        if (obligatorias.isEmpty()) {
            throw new ReglaDeNegocioException("El plan debe tener al menos una materia obligatoria");
        }
        for (Materia materia : obligatorias) {
            if (materia == null) {
                throw new ReglaDeNegocioException("Las materias del plan no pueden ser nulas");
            }
        }
        for (Materia materia : optativas) {
            if (materia == null) {
                throw new ReglaDeNegocioException("Las materias del plan no pueden ser nulas");
            }
        }
        if (minimoOptativas < 0 || minimoOptativas > optativas.size()) {
            throw new ReglaDeNegocioException("La cantidad minima de optativas no es valida");
        }
        Set<Materia> materiasSinRepetir = new LinkedHashSet<>(obligatorias);
        materiasSinRepetir.addAll(optativas);
        if (materiasSinRepetir.size() != obligatorias.size() + optativas.size()) {
            throw new ReglaDeNegocioException("Una materia no puede repetirse dentro del plan");
        }
    }

    public boolean contiene(Materia materia) {
        return materiasObligatorias.contains(materia) || materiasOptativas.contains(materia);
    }

    public boolean puedeInscribirse(InscripcionCarrera inscripcion, Materia materia) {
        return contiene(materia) && condicionInscripcion.puedeInscribirse(inscripcion, materia, this);
    }

    public List<Materia> getTodasLasMaterias() {
        List<Materia> materias = new ArrayList<>(materiasObligatorias);
        materias.addAll(materiasOptativas);
        return Collections.unmodifiableList(materias);
    }

    public String getNombre() {
        return nombre;
    }

    public List<Materia> getMateriasObligatorias() {
        return Collections.unmodifiableList(materiasObligatorias);
    }

    public List<Materia> getMateriasOptativas() {
        return Collections.unmodifiableList(materiasOptativas);
    }

    public int getCantidadMinimaOptativas() {
        return cantidadMinimaOptativas;
    }

    public CondicionInscripcion getCondicionInscripcion() {
        return condicionInscripcion;
    }
}
