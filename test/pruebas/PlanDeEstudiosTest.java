package pruebas;

import estrategia.CondicionInscripcionA;
import modelo.ConstructorPlanDeEstudios;
import modelo.Materia;
import modelo.PlanDeEstudios;
import modelo.ReglaDeNegocioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlanDeEstudiosTest {
    private Materia obligatoria;
    private Materia optativaUno;
    private Materia optativaDos;
    private CondicionInscripcionA condicionA;

    @BeforeEach
    void prepararDatosDePrueba() {
        // Inicializacion de los datos antes de cada prueba
        obligatoria = new Materia("MAT1", "Programacion", 1, false);
        optativaUno = new Materia("OPT1", "Taller uno", 2, true);
        optativaDos = new Materia("OPT2", "Taller dos", 2, true);
        condicionA = new CondicionInscripcionA();
    }

    @Test
    void testConstructorConstruyePlanConMateriasYCondicionConfiguradas() {
        PlanDeEstudios plan = crearConstructorValido()
                .agregarOptativa(optativaDos)
                .construir();

        assertEquals("Plan de prueba", plan.getNombre());
        assertEquals(1, plan.getMateriasObligatorias().size());
        assertEquals(2, plan.getMateriasOptativas().size());
        assertEquals(1, plan.getCantidadMinimaOptativas());
        assertSame(condicionA, plan.getCondicionInscripcion());
    }

    @Test
    void testPlanReconoceMateriasQueLePertenecen() {
        PlanDeEstudios plan = crearConstructorValido().construir();

        assertTrue(plan.contiene(obligatoria));
        assertTrue(plan.contiene(optativaUno));
        assertFalse(plan.contiene(optativaDos));
    }

    @Test
    void testConstructorPermitePlanSinOptativasCuandoElMinimoEsCero() {
        PlanDeEstudios plan = new ConstructorPlanDeEstudios("Plan basico")
                .agregarObligatoria(obligatoria)
                .conMinimoDeOptativas(0)
                .conCondicion(condicionA)
                .construir();

        assertTrue(plan.getMateriasOptativas().isEmpty());
        assertEquals(0, plan.getCantidadMinimaOptativas());
    }

    @Test
    void testConstructorFallaSiNoTieneMateriasObligatorias() {
        assertThrows(ReglaDeNegocioException.class,
                () -> new ConstructorPlanDeEstudios("Plan vacio")
                        .conMinimoDeOptativas(0)
                        .conCondicion(condicionA)
                        .construir(),
                "El plan debe tener al menos una materia obligatoria");
    }

    @Test
    void testConstructorFallaSiLaCondicionEsNula() {
        assertThrows(ReglaDeNegocioException.class,
                () -> new ConstructorPlanDeEstudios("Plan sin condicion")
                        .agregarObligatoria(obligatoria)
                        .construir(),
                "El plan debe tener una estrategia de inscripcion");
    }

    @Test
    void testConstructorFallaSiUnaMateriaEsNula() {
        assertThrows(ReglaDeNegocioException.class,
                () -> crearConstructorValido().agregarOptativa(null).construir(),
                "Las materias nulas no deben aceptarse");
    }

    @Test
    void testConstructorFallaSiElMinimoDeOptativasEsNegativo() {
        assertThrows(ReglaDeNegocioException.class,
                () -> crearConstructorValido().conMinimoDeOptativas(-1).construir(),
                "El minimo de optativas no puede ser negativo");
    }

    @Test
    void testConstructorFallaSiElMinimoSuperaLasOptativasDisponibles() {
        assertThrows(ReglaDeNegocioException.class,
                () -> crearConstructorValido().conMinimoDeOptativas(2).construir(),
                "El minimo no puede superar la cantidad de optativas");
    }

    @Test
    void testConstructorFallaSiUnaMateriaEstaRepetida() {
        assertThrows(ReglaDeNegocioException.class,
                () -> crearConstructorValido().agregarOptativa(obligatoria).construir(),
                "Una materia no puede ser obligatoria y optativa al mismo tiempo");
    }

    @Test
    void testColeccionDeMateriasNoPuedeModificarseDesdeAfuera() {
        PlanDeEstudios plan = crearConstructorValido().construir();

        assertThrows(UnsupportedOperationException.class,
                () -> plan.getMateriasObligatorias().add(optativaDos),
                "La lista del plan debe protegerse de cambios externos");
    }

    private ConstructorPlanDeEstudios crearConstructorValido() {
        return new ConstructorPlanDeEstudios("Plan de prueba")
                .agregarObligatoria(obligatoria)
                .agregarOptativa(optativaUno)
                .conMinimoDeOptativas(1)
                .conCondicion(condicionA);
    }
}
