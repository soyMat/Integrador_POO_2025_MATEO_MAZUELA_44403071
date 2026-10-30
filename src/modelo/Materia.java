package modelo;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class Materia {
    private final String codigo;
    private final String nombre;
    private final int cuatrimestre;
    private final boolean permitePromocion;
    private final Set<Materia> correlativas = new LinkedHashSet<>();

    public Materia(String codigo, String nombre, int cuatrimestre, boolean permitePromocion) {
        if (codigo == null || codigo.isBlank()) {
            throw new ReglaDeNegocioException("El codigo de la materia es obligatorio");
        }
        if (nombre == null || nombre.isBlank()) {
            throw new ReglaDeNegocioException("El nombre de la materia es obligatorio");
        }
        if (cuatrimestre < 1) {
            throw new ReglaDeNegocioException("El cuatrimestre debe ser mayor a cero");
        }
        this.codigo = codigo.trim().toUpperCase();
        this.nombre = nombre.trim();
        this.cuatrimestre = cuatrimestre;
        this.permitePromocion = permitePromocion;
    }

    public void agregarCorrelativa(Materia materia) {
        if (materia == null) {
            throw new ReglaDeNegocioException("La correlativa no puede ser nula");
        }
        if (equals(materia)) {
            throw new ReglaDeNegocioException("Una materia no puede ser correlativa de si misma");
        }
        if (materia.getCuatrimestre() >= cuatrimestre) {
            throw new ReglaDeNegocioException("La correlativa debe pertenecer a un cuatrimestre anterior");
        }
        correlativas.add(materia);
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCuatrimestre() {
        return cuatrimestre;
    }

    public boolean permitePromocion() {
        return permitePromocion;
    }

    public Set<Materia> getCorrelativas() {
        return Collections.unmodifiableSet(correlativas);
    }

    @Override
    public boolean equals(Object otro) {
        if (this == otro) {
            return true;
        }
        if (!(otro instanceof Materia)) {
            return false;
        }
        Materia materia = (Materia) otro;
        return codigo.equals(materia.codigo);
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
