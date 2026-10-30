package pruebas;

import modelo.Materia;
import modelo.ReglaDeNegocioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MateriaTest {
    private Materia materiaInicial;
    private Materia materiaAvanzada;

    @BeforeEach
    void prepararDatosDePrueba() {
        // Inicializacion de los datos antes de cada prueba
        materiaInicial = new Materia("MAT1", "Materia inicial", 1, false);
        materiaAvanzada = new Materia("MAT2", "Materia avanzada", 2, true);
    }

    @Test
    void testAgregarCorrelativaAnteriorLaGuardaCorrectamente() {
        materiaAvanzada.agregarCorrelativa(materiaInicial);

        assertEquals(1, materiaAvanzada.getCorrelativas().size());
        assertTrue(materiaAvanzada.getCorrelativas().contains(materiaInicial));
    }

    @Test
    void testAgregarCorrelativaNulaLanzaExcepcion() {
        assertThrows(ReglaDeNegocioException.class,
                () -> materiaAvanzada.agregarCorrelativa(null),
                "Una correlativa no puede ser nula");
    }

    @Test
    void testMateriaNoPuedeSerCorrelativaDeSiMisma() {
        assertThrows(ReglaDeNegocioException.class,
                () -> materiaAvanzada.agregarCorrelativa(materiaAvanzada),
                "Una materia no puede depender de si misma");
    }

    @Test
    void testCorrelativaDelMismoCuatrimestreLanzaExcepcion() {
        Materia otraMateria = new Materia("MAT3", "Otra materia", 2, false);

        assertThrows(ReglaDeNegocioException.class,
                () -> materiaAvanzada.agregarCorrelativa(otraMateria),
                "La correlativa debe ser de un cuatrimestre anterior");
    }

    @Test
    void testConstructorFallaConCodigoNulo() {
        assertThrows(ReglaDeNegocioException.class,
                () -> new Materia(null, "Materia", 1, false),
                "El codigo es obligatorio");
    }

    @Test
    void testConstructorFallaConNombreVacio() {
        assertThrows(ReglaDeNegocioException.class,
                () -> new Materia("MAT", " ", 1, false),
                "El nombre es obligatorio");
    }

    @Test
    void testConstructorFallaConCuatrimestreCero() {
        assertThrows(ReglaDeNegocioException.class,
                () -> new Materia("MAT", "Materia", 0, false),
                "El cuatrimestre debe ser mayor a cero");
    }
}
