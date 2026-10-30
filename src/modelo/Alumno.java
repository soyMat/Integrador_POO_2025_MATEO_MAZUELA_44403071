package modelo;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class Alumno {
    private final String legajo;
    private final String nombre;
    private final String apellido;
    private final String documento;
    private final Map<String, InscripcionCarrera> inscripciones = new LinkedHashMap<>();

    public Alumno(String legajo, String nombre, String apellido, String documento) {
        if (legajo == null || legajo.isBlank() || nombre == null || nombre.isBlank()
                || apellido == null || apellido.isBlank() || documento == null || documento.isBlank()) {
            throw new ReglaDeNegocioException("Todos los datos del alumno son obligatorios");
        }
        this.legajo = legajo.trim().toUpperCase();
        this.nombre = nombre.trim();
        this.apellido = apellido.trim();
        this.documento = documento.trim();
    }

    public InscripcionCarrera inscribirseEn(Carrera carrera) {
        if (carrera == null) {
            throw new ReglaDeNegocioException("La carrera es obligatoria");
        }
        if (inscripciones.containsKey(carrera.getCodigo())) {
            throw new ReglaDeNegocioException("El alumno ya esta inscripto en esa carrera");
        }
        InscripcionCarrera inscripcion = new InscripcionCarrera(this, carrera);
        inscripciones.put(carrera.getCodigo(), inscripcion);
        return inscripcion;
    }

    public InscripcionCarrera getInscripcionEn(String codigoCarrera) {
        if (codigoCarrera == null) {
            throw new ReglaDeNegocioException("El codigo de la carrera es obligatorio");
        }
        InscripcionCarrera inscripcion = inscripciones.get(codigoCarrera.toUpperCase());
        if (inscripcion == null) {
            throw new ReglaDeNegocioException("El alumno no esta inscripto en esa carrera");
        }
        return inscripcion;
    }

    public Collection<InscripcionCarrera> getInscripciones() {
        return Collections.unmodifiableCollection(inscripciones.values());
    }

    public String getLegajo() {
        return legajo;
    }

    public String getNombreCompleto() {
        return apellido + ", " + nombre;
    }

    public String getDocumento() {
        return documento;
    }

    @Override
    public boolean equals(Object otro) {
        if (this == otro) {
            return true;
        }
        if (!(otro instanceof Alumno)) {
            return false;
        }
        Alumno alumno = (Alumno) otro;
        return legajo.equals(alumno.legajo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(legajo);
    }

    @Override
    public String toString() {
        return legajo + " - " + getNombreCompleto();
    }
}
