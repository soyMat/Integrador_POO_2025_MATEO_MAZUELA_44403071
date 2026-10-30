package pruebas;

import estrategia.CondicionInscripcion;
import estrategia.CondicionInscripcionA;
import estrategia.CondicionInscripcionB;
import estrategia.CondicionInscripcionC;
import estrategia.CondicionInscripcionD;
import estrategia.CondicionInscripcionE;
import modelo.Alumno;
import modelo.Carrera;
import modelo.ConstructorPlanDeEstudios;
import modelo.Cursada;
import modelo.InscripcionCarrera;
import modelo.Materia;
import modelo.PlanDeEstudios;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CondicionesInscripcionTest {
    private Materia materiaInicial;
    private Materia materiaCorrelativa;

    @BeforeEach
    void prepararDatosDePrueba() {
        // Inicializacion de los datos antes de cada prueba
        materiaInicial = new Materia("MAT1", "Materia inicial", 1, false);
        materiaCorrelativa = new Materia("MAT2", "Materia correlativa", 2, false);
        materiaCorrelativa.agregarCorrelativa(materiaInicial);
    }

    @Test
    void testCondicionARechazaSiFaltaLaCursadaCorrelativa() {
        Escenario escenario = crearEscenario(new CondicionInscripcionA(),
                materiaInicial, materiaCorrelativa);

        assertFalse(escenario.plan.puedeInscribirse(escenario.inscripcion, materiaCorrelativa));
    }

    @Test
    void testCondicionAAceptaConCursadaCorrelativaAprobada() {
        Escenario escenario = crearEscenario(new CondicionInscripcionA(),
                materiaInicial, materiaCorrelativa);
        aprobarCursada(escenario.inscripcion.inscribirEn(materiaInicial));

        assertTrue(escenario.plan.puedeInscribirse(escenario.inscripcion, materiaCorrelativa));
    }

    @Test
    void testCondicionBRechazaSiLaCorrelativaNoTieneFinal() {
        Escenario escenario = crearEscenario(new CondicionInscripcionB(),
                materiaInicial, materiaCorrelativa);
        aprobarCursada(escenario.inscripcion.inscribirEn(materiaInicial));

        assertFalse(escenario.plan.puedeInscribirse(escenario.inscripcion, materiaCorrelativa));
    }

    @Test
    void testCondicionBAceptaConFinalCorrelativoAprobado() {
        Escenario escenario = crearEscenario(new CondicionInscripcionB(),
                materiaInicial, materiaCorrelativa);
        aprobarConFinal(escenario.inscripcion.inscribirEn(materiaInicial));

        assertTrue(escenario.plan.puedeInscribirse(escenario.inscripcion, materiaCorrelativa));
    }

    @Test
    void testCondicionCRechazaSiFaltaFinalDeCincoCuatrimestresPrevios() {
        Materia previa = new Materia("PRE1", "Materia previa", 1, false);
        Materia correlativa = new Materia("COR5", "Correlativa", 5, false);
        Materia objetivo = new Materia("OBJ6", "Objetivo", 6, false);
        objetivo.agregarCorrelativa(correlativa);
        Escenario escenario = crearEscenario(new CondicionInscripcionC(),
                previa, correlativa, objetivo);
        aprobarCursada(escenario.inscripcion.inscribirEn(correlativa));

        assertFalse(escenario.plan.puedeInscribirse(escenario.inscripcion, objetivo));
    }

    @Test
    void testCondicionCAceptaCursadasCorrelativasYFinalesPrevios() {
        Materia previa = new Materia("PRE1", "Materia previa", 1, false);
        Materia correlativa = new Materia("COR5", "Correlativa", 5, false);
        Materia objetivo = new Materia("OBJ6", "Objetivo", 6, false);
        objetivo.agregarCorrelativa(correlativa);
        Escenario escenario = crearEscenario(new CondicionInscripcionC(),
                previa, correlativa, objetivo);
        aprobarCursada(escenario.inscripcion.inscribirEn(correlativa));
        aprobarConFinal(escenario.inscripcion.inscribirEn(previa));

        assertTrue(escenario.plan.puedeInscribirse(escenario.inscripcion, objetivo));
    }

    @Test
    void testCondicionDRechazaSiFaltaFinalDeTresCuatrimestresPrevios() {
        Materia previa = new Materia("PRE1", "Materia previa", 1, false);
        Materia correlativa = new Materia("COR3", "Correlativa", 3, false);
        Materia objetivo = new Materia("OBJ4", "Objetivo", 4, false);
        objetivo.agregarCorrelativa(correlativa);
        Escenario escenario = crearEscenario(new CondicionInscripcionD(),
                previa, correlativa, objetivo);
        aprobarCursada(escenario.inscripcion.inscribirEn(correlativa));

        assertFalse(escenario.plan.puedeInscribirse(escenario.inscripcion, objetivo));
    }

    @Test
    void testCondicionDAceptaCursadasCorrelativasYFinalesPrevios() {
        Materia previa = new Materia("PRE1", "Materia previa", 1, false);
        Materia correlativa = new Materia("COR3", "Correlativa", 3, false);
        Materia objetivo = new Materia("OBJ4", "Objetivo", 4, false);
        objetivo.agregarCorrelativa(correlativa);
        Escenario escenario = crearEscenario(new CondicionInscripcionD(),
                previa, correlativa, objetivo);
        aprobarCursada(escenario.inscripcion.inscribirEn(correlativa));
        aprobarConFinal(escenario.inscripcion.inscribirEn(previa));

        assertTrue(escenario.plan.puedeInscribirse(escenario.inscripcion, objetivo));
    }

    @Test
    void testCondicionERechazaSiLaCorrelativaSoloTieneCursadaAprobada() {
        Materia previa = new Materia("PRE1", "Materia previa", 1, false);
        Materia correlativa = new Materia("COR3", "Correlativa", 3, false);
        Materia objetivo = new Materia("OBJ4", "Objetivo", 4, false);
        objetivo.agregarCorrelativa(correlativa);
        Escenario escenario = crearEscenario(new CondicionInscripcionE(),
                previa, correlativa, objetivo);
        aprobarConFinal(escenario.inscripcion.inscribirEn(previa));
        aprobarCursada(escenario.inscripcion.inscribirEn(correlativa));

        assertFalse(escenario.plan.puedeInscribirse(escenario.inscripcion, objetivo));
    }

    @Test
    void testCondicionERechazaSiFaltaUnFinalPrevio() {
        Materia previa = new Materia("PRE1", "Materia previa", 1, false);
        Materia correlativa = new Materia("COR3", "Correlativa", 3, false);
        Materia objetivo = new Materia("OBJ4", "Objetivo", 4, false);
        objetivo.agregarCorrelativa(correlativa);
        Escenario escenario = crearEscenario(new CondicionInscripcionE(),
                previa, correlativa, objetivo);
        aprobarConFinal(escenario.inscripcion.inscribirEn(correlativa));

        assertFalse(escenario.plan.puedeInscribirse(escenario.inscripcion, objetivo));
    }

    @Test
    void testCondicionEAceptaFinalesCorrelativosYPrevios() {
        Materia previa = new Materia("PRE1", "Materia previa", 1, false);
        Materia correlativa = new Materia("COR3", "Correlativa", 3, false);
        Materia objetivo = new Materia("OBJ4", "Objetivo", 4, false);
        objetivo.agregarCorrelativa(correlativa);
        Escenario escenario = crearEscenario(new CondicionInscripcionE(),
                previa, correlativa, objetivo);
        aprobarConFinal(escenario.inscripcion.inscribirEn(previa));
        aprobarConFinal(escenario.inscripcion.inscribirEn(correlativa));

        assertTrue(escenario.plan.puedeInscribirse(escenario.inscripcion, objetivo));
    }

    private Escenario crearEscenario(CondicionInscripcion condicion, Materia... materias) {
        ConstructorPlanDeEstudios constructorPlan = new ConstructorPlanDeEstudios("Plan de prueba")
                .conMinimoDeOptativas(0)
                .conCondicion(condicion);
        for (Materia materia : materias) {
            constructorPlan.agregarObligatoria(materia);
        }
        PlanDeEstudios plan = constructorPlan.construir();
        Carrera carrera = new Carrera("CAR", "Carrera de prueba", plan);
        Alumno alumno = new Alumno("A1", "Ana", "Perez", "100");
        return new Escenario(plan, alumno.inscribirseEn(carrera));
    }

    private void aprobarCursada(Cursada cursada) {
        cursada.aprobarParcial();
        cursada.aprobarCursada();
    }

    private void aprobarConFinal(Cursada cursada) {
        aprobarCursada(cursada);
        cursada.aprobarFinal();
    }

    private static class Escenario {
        private final PlanDeEstudios plan;
        private final InscripcionCarrera inscripcion;

        private Escenario(PlanDeEstudios plan, InscripcionCarrera inscripcion) {
            this.plan = plan;
            this.inscripcion = inscripcion;
        }
    }
}
