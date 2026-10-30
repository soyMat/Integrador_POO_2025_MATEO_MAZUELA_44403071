package pruebas;

import modelo.Cursada;
import modelo.ExcepcionTransicionInvalida;
import modelo.Materia;
import modelo.ReglaDeNegocioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CursadaTest {
    private Materia materiaPromocionable;
    private Materia materiaSinPromocion;
    private Cursada cursada;

    @BeforeEach
    void prepararDatosDePrueba() {
        // Inicializacion de los datos antes de cada prueba
        materiaPromocionable = new Materia("PRO", "Materia promocionable", 1, true);
        materiaSinPromocion = new Materia("FIN", "Materia con final", 1, false);
        cursada = new Cursada(materiaPromocionable);
    }

    @Test
    void testNuevaCursadaComienzaEnEstadoInscripto() {
        assertEquals("Inscripto", cursada.getNombreEstado());
        assertFalse(cursada.tieneCursadaAprobada());
        assertFalse(cursada.tieneFinalAprobado());
    }

    @Test
    void testAprobarParcialCambiaAParcialAprobado() {
        cursada.aprobarParcial();

        assertEquals("Parcial aprobado", cursada.getNombreEstado());
    }

    @Test
    void testRecuperatorioAprobadoCambiaAParcialAprobado() {
        cursada.desaprobarParcial();
        cursada.aprobarParcial();

        assertEquals("Parcial aprobado", cursada.getNombreEstado());
    }

    @Test
    void testAprobarCursadaPermiteAprobarElFinal() {
        Cursada cursadaConFinal = new Cursada(materiaSinPromocion);
        cursadaConFinal.aprobarParcial();
        cursadaConFinal.aprobarCursada();
        cursadaConFinal.aprobarFinal();

        assertEquals("Cursada aprobada", cursadaConFinal.getNombreEstado());
        assertTrue(cursadaConFinal.tieneCursadaAprobada());
        assertTrue(cursadaConFinal.tieneFinalAprobado());
    }

    @Test
    void testPromocionarApruebaCursadaYFinal() {
        cursada.aprobarParcial();
        cursada.promocionar();

        assertEquals("Promocionada", cursada.getNombreEstado());
        assertTrue(cursada.tieneCursadaAprobada());
        assertTrue(cursada.tieneFinalAprobado());
    }

    @Test
    void testDesaprobarCursadaPermiteRecursar() {
        cursada.desaprobarParcial();
        cursada.desaprobarCursada();

        assertEquals("Cursada desaprobada", cursada.getNombreEstado());
        assertTrue(cursada.permiteRecursar());
    }

    @Test
    void testAprobarFinalDesdeInscriptoLanzaExcepcionDeTransicion() {
        assertThrows(ExcepcionTransicionInvalida.class,
                () -> cursada.aprobarFinal(),
                "No se puede aprobar el final antes de aprobar la cursada");
    }

    @Test
    void testAprobarCursadaSinParcialLanzaExcepcionDeTransicion() {
        assertThrows(ExcepcionTransicionInvalida.class,
                () -> cursada.aprobarCursada(),
                "No se puede aprobar la cursada sin aprobar el parcial");
    }

    @Test
    void testPromocionarMateriaNoPromocionableLanzaExcepcion() {
        Cursada cursadaSinPromocion = new Cursada(materiaSinPromocion);
        cursadaSinPromocion.aprobarParcial();

        assertThrows(ReglaDeNegocioException.class,
                () -> cursadaSinPromocion.promocionar(),
                "Una materia sin promocion debe rendir final");
    }

    @Test
    void testCrearCursadaConMateriaNulaLanzaExcepcion() {
        assertThrows(ReglaDeNegocioException.class,
                () -> new Cursada(null),
                "La cursada necesita una materia");
    }
}
