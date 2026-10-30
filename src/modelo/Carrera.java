package modelo;

import java.util.Objects;

public class Carrera {
    private final String codigo;
    private final String nombre;
    private final PlanDeEstudios planDeEstudios;

    public Carrera(String codigo, String nombre, PlanDeEstudios planDeEstudios) {
        if (codigo == null || codigo.isBlank()) {
            throw new ReglaDeNegocioException("El codigo de la carrera es obligatorio");
        }
        if (nombre == null || nombre.isBlank()) {
            throw new ReglaDeNegocioException("El nombre de la carrera es obligatorio");
        }
        if (planDeEstudios == null) {
            throw new ReglaDeNegocioException("La carrera debe tener un plan de estudios");
        }
        this.codigo = codigo.trim().toUpperCase();
        this.nombre = nombre.trim();
        this.planDeEstudios = planDeEstudios;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public PlanDeEstudios getPlanDeEstudios() {
        return planDeEstudios;
    }

    @Override
    public boolean equals(Object otro) {
        if (this == otro) {
            return true;
        }
        if (!(otro instanceof Carrera)) {
            return false;
        }
        Carrera carrera = (Carrera) otro;
        return codigo.equals(carrera.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return codigo + " - " + nombre;
    }
}
