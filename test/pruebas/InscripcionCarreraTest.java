package pruebas;

import estrategia.CondicionInscripcionA;
import modelo.Alumno;
import modelo.Carrera;
import modelo.ConstructorPlanDeEstudios;
import modelo.Cursada;
import modelo.InscripcionCarrera;
import modelo.Materia;
import modelo.PlanDeEstudios;
import modelo.ReglaDeNegocioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InscripcionCarreraTest {
    private Materia obligatoria;
    private Materia optativaUno;
    private Materia optativaDos;
    private Materia materiaExterna;
    private InscripcionCarrera inscripcion;

    @BeforeEach
    void prepararDatosDePrueba() {
        // Inicializacion de los datos antes de cada prueba
        obligatoria = new Materia("OBL", "Obligatoria", 1, false);
        optativaUno = new Materia("OPT1", "Optativa uno", 1, true);
        optativaDos = new Materia("OPT2", "Optativa dos", 1, true);
        materiaExterna = new Materia("EXT", "Externa", 1, false);

        PlanDeEstudios plan = new ConstructorPlanDeEstudios("Plan de prueba")
                .agregarObligatoria(obligatoria)
                .agregarOptativa(optativaUno)
                .agregarOptativa(optativaDos)
                .conMinimoDeOptativas(1)
                .conCondicion(new CondicionInscripcionA())
                .construir();
        Carrera carrera = new Carrera("CAR", "Carrera", plan);
        Alumno alumno = new Alumno("A1", "Ana", "Perez", "100");
        inscripcion = alumno.inscribirseEn(carrera);
    }

    @Test
    void testInscribirMateriaDelPlanCreaUnaCursada() {
        Cursada cursada = inscripcion.inscribirEn(obligatoria);

        assertEquals(obligatoria, cursada.getMateria());
        assertEquals(1, inscripcion.getUltimasCursadas().size());
    }

    @Test
    void testInscribirMateriaFueraDelPlanLanzaExcepcion() {
        assertThrows(ReglaDeNegocioException.class,
                () -> inscripcion.inscribirEn(materiaExterna),
                "No se puede cursar una materia de otro plan");
    }

    @Test
    void testInscribirDosVecesUnaCursadaVigenteLanzaExcepcion() {
        inscripcion.inscribirEn(obligatoria);

        assertThrows(ReglaDeNegocioException.class,
                () -> inscripcion.inscribirEn(obligatoria),
                "No deben existir dos cursadas vigentes de la misma materia");
    }

    @Test
    void testRecursarLuegoDeDesaprobarCreaUnNuevoIntento() {
        Cursada primerIntento = inscripcion.inscribirEn(obligatoria);
        primerIntento.desaprobarParcial();
        primerIntento.desaprobarCursada();

        Cursada segundoIntento = inscripcion.inscribirEn(obligatoria);

        assertNotSame(primerIntento, segundoIntento);
        assertEquals("Inscripto", segundoIntento.getNombreEstado());
    }

    @Test
    void testBuscarCursadaInexistenteLanzaExcepcion() {
        assertThrows(ReglaDeNegocioException.class,
                () -> inscripcion.getUltimaCursada(obligatoria),
                "Debe fallar si la materia nunca fue cursada");
    }

    @Test
    void testAlumnoNoFinalizaSiFaltaUnaOptativa() {
        aprobarConFinal(inscripcion.inscribirEn(obligatoria));

        assertFalse(inscripcion.finalizoCarrera());
    }

    @Test
    void testAlumnoFinalizaConObligatoriasYMinimoDeOptativas() {
        aprobarConFinal(inscripcion.inscribirEn(obligatoria));
        promocionar(inscripcion.inscribirEn(optativaUno));

        assertTrue(inscripcion.finalizoCarrera());
    }

    @Test
    void testGraduacionNoExigeAprobarTodasLasOptativas() {
        aprobarConFinal(inscripcion.inscribirEn(obligatoria));
        promocionar(inscripcion.inscribirEn(optativaUno));

        assertFalse(inscripcion.aproboFinal(optativaDos));
        assertTrue(inscripcion.finalizoCarrera());
    }

    @Test
    void testMateriasHabilitadasIncluyeLasMateriasSinCorrelativas() {
        assertEquals(3, inscripcion.materiasHabilitadas().size());
        assertTrue(inscripcion.materiasHabilitadas().contains(obligatoria));
    }

    @Test
    void testMateriaConCursadaVigenteNoApareceComoHabilitada() {
        inscripcion.inscribirEn(obligatoria);

        assertFalse(inscripcion.materiasHabilitadas().contains(obligatoria));
    }

    private void aprobarConFinal(Cursada cursada) {
        cursada.aprobarParcial();
        cursada.aprobarCursada();
        cursada.aprobarFinal();
    }

    private void promocionar(Cursada cursada) {
        cursada.aprobarParcial();
        cursada.promocionar();
    }
}
