package modelo;

import estrategia.CondicionInscripcion;

import java.util.ArrayList;
import java.util.List;

public class ConstructorPlanDeEstudios {
    private final String nombre;
    private final List<Materia> obligatorias = new ArrayList<>();
    private final List<Materia> optativas = new ArrayList<>();
    private int cantidadMinimaOptativas;
    private CondicionInscripcion condicionInscripcion;

    public ConstructorPlanDeEstudios(String nombre) {
        this.nombre = nombre;
    }

    public ConstructorPlanDeEstudios agregarObligatoria(Materia materia) {
        obligatorias.add(materia);
        return this;
    }

    public ConstructorPlanDeEstudios agregarOptativa(Materia materia) {
        optativas.add(materia);
        return this;
    }

    public ConstructorPlanDeEstudios conMinimoDeOptativas(int cantidad) {
        cantidadMinimaOptativas = cantidad;
        return this;
    }

    public ConstructorPlanDeEstudios conCondicion(CondicionInscripcion condicion) {
        condicionInscripcion = condicion;
        return this;
    }

    public PlanDeEstudios construir() {
        PlanDeEstudios.validar(nombre, obligatorias, optativas,
                cantidadMinimaOptativas, condicionInscripcion);
        return new PlanDeEstudios(nombre.trim(), obligatorias, optativas,
                cantidadMinimaOptativas, condicionInscripcion);
    }
}
