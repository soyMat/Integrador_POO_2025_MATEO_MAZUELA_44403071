package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class InscripcionCarrera {
    private final Alumno alumno;
    private final Carrera carrera;
    private final Map<Materia, List<Cursada>> cursadasPorMateria = new LinkedHashMap<>();

    public InscripcionCarrera(Alumno alumno, Carrera carrera) {
        this.alumno = alumno;
        this.carrera = carrera;
    }

    public Cursada inscribirEn(Materia materia) {
        PlanDeEstudios plan = carrera.getPlanDeEstudios();
        if (!plan.contiene(materia)) {
            throw new ReglaDeNegocioException("La materia no pertenece al plan de estudios");
        }
        Cursada ultima = buscarUltimaCursada(materia);
        if (ultima != null && !ultima.permiteRecursar()) {
            throw new ReglaDeNegocioException("El alumno ya tiene una cursada vigente o aprobada para esa materia");
        }
        if (!plan.puedeInscribirse(this, materia)) {
            throw new ReglaDeNegocioException("El alumno no cumple la condicion "
                    + plan.getCondicionInscripcion().getNombre() + " del plan");
        }
        Cursada cursada = new Cursada(materia);
        List<Cursada> intentos = cursadasPorMateria.get(materia);
        if (intentos == null) {
            intentos = new ArrayList<>();
            cursadasPorMateria.put(materia, intentos);
        }
        intentos.add(cursada);
        return cursada;
    }

    public Cursada getUltimaCursada(Materia materia) {
        Cursada cursada = buscarUltimaCursada(materia);
        if (cursada == null) {
            throw new ReglaDeNegocioException("El alumno no tiene una cursada para esa materia");
        }
        return cursada;
    }

    private Cursada buscarUltimaCursada(Materia materia) {
        List<Cursada> intentos = cursadasPorMateria.get(materia);
        if (intentos == null || intentos.isEmpty()) {
            return null;
        }
        return intentos.get(intentos.size() - 1);
    }

    public boolean aproboCursada(Materia materia) {
        List<Cursada> intentos = cursadasPorMateria.get(materia);
        if (intentos == null) {
            return false;
        }
        for (Cursada cursada : intentos) {
            if (cursada.tieneCursadaAprobada()) {
                return true;
            }
        }
        return false;
    }

    public boolean aproboFinal(Materia materia) {
        List<Cursada> intentos = cursadasPorMateria.get(materia);
        if (intentos == null) {
            return false;
        }
        for (Cursada cursada : intentos) {
            if (cursada.tieneFinalAprobado()) {
                return true;
            }
        }
        return false;
    }

    public List<Materia> materiasHabilitadas() {
        PlanDeEstudios plan = carrera.getPlanDeEstudios();
        List<Materia> habilitadas = new ArrayList<>();
        for (Materia materia : plan.getTodasLasMaterias()) {
            Cursada ultimaCursada = buscarUltimaCursada(materia);
            boolean puedeIntentar = ultimaCursada == null || ultimaCursada.permiteRecursar();
            if (puedeIntentar && plan.puedeInscribirse(this, materia)) {
                habilitadas.add(materia);
            }
        }
        return habilitadas;
    }

    public boolean finalizoCarrera() {
        PlanDeEstudios plan = carrera.getPlanDeEstudios();
        for (Materia materia : plan.getMateriasObligatorias()) {
            if (!aproboFinal(materia)) {
                return false;
            }
        }
        int optativasAprobadas = 0;
        for (Materia materia : plan.getMateriasOptativas()) {
            if (aproboFinal(materia)) {
                optativasAprobadas++;
            }
        }
        return optativasAprobadas >= plan.getCantidadMinimaOptativas();
    }

    public List<Cursada> getUltimasCursadas() {
        List<Cursada> ultimas = new ArrayList<>();
        for (List<Cursada> intentos : cursadasPorMateria.values()) {
            ultimas.add(intentos.get(intentos.size() - 1));
        }
        return Collections.unmodifiableList(ultimas);
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public Carrera getCarrera() {
        return carrera;
    }
}
