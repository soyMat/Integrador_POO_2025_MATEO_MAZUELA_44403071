package modelo;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class Facultad {
    private final Map<String, Alumno> alumnos = new LinkedHashMap<>();
    private final Map<String, Carrera> carreras = new LinkedHashMap<>();
    private final Map<String, Materia> materias = new LinkedHashMap<>();

    public void agregarAlumno(Alumno alumno) {
        if (alumno == null || alumnos.containsKey(alumno.getLegajo())) {
            throw new ReglaDeNegocioException("Ya existe un alumno con ese legajo");
        }
        for (Alumno alumnoExistente : alumnos.values()) {
            if (alumnoExistente.getDocumento().equals(alumno.getDocumento())) {
                throw new ReglaDeNegocioException("Ya existe un alumno con ese documento");
            }
        }
        alumnos.put(alumno.getLegajo(), alumno);
    }

    public void agregarMateria(Materia materia) {
        if (materia == null || materias.containsKey(materia.getCodigo())) {
            throw new ReglaDeNegocioException("Ya existe una materia con ese codigo");
        }
        materias.put(materia.getCodigo(), materia);
    }

    public void agregarCarrera(Carrera carrera) {
        if (carrera == null || carreras.containsKey(carrera.getCodigo())) {
            throw new ReglaDeNegocioException("Ya existe una carrera con ese codigo");
        }
        carreras.put(carrera.getCodigo(), carrera);
    }

    public Alumno buscarAlumno(String legajo) {
        Alumno alumno = alumnos.get(normalizar(legajo));
        if (alumno == null) {
            throw new ReglaDeNegocioException("No se encontro el alumno");
        }
        return alumno;
    }

    public Materia buscarMateria(String codigo) {
        Materia materia = materias.get(normalizar(codigo));
        if (materia == null) {
            throw new ReglaDeNegocioException("No se encontro la materia");
        }
        return materia;
    }

    public Carrera buscarCarrera(String codigo) {
        Carrera carrera = carreras.get(normalizar(codigo));
        if (carrera == null) {
            throw new ReglaDeNegocioException("No se encontro la carrera");
        }
        return carrera;
    }

    public InscripcionCarrera inscribirAlumnoEnCarrera(String legajo, String codigoCarrera) {
        return buscarAlumno(legajo).inscribirseEn(buscarCarrera(codigoCarrera));
    }

    public Cursada inscribirAlumnoEnMateria(String legajo, String codigoCarrera, String codigoMateria) {
        Alumno alumno = buscarAlumno(legajo);
        return alumno.getInscripcionEn(codigoCarrera).inscribirEn(buscarMateria(codigoMateria));
    }

    public boolean finalizoCarrera(String legajo, String codigoCarrera) {
        return buscarAlumno(legajo).getInscripcionEn(codigoCarrera).finalizoCarrera();
    }

    public Collection<Alumno> getAlumnos() {
        return Collections.unmodifiableCollection(alumnos.values());
    }

    public Collection<Carrera> getCarreras() {
        return Collections.unmodifiableCollection(carreras.values());
    }

    public Collection<Materia> getMaterias() {
        return Collections.unmodifiableCollection(materias.values());
    }

    private String normalizar(String texto) {
        if (texto == null) {
            return "";
        }
        return texto.trim().toUpperCase();
    }
}
