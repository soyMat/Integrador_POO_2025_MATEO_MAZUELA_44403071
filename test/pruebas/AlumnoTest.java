package pruebas;

import estrategia.CondicionInscripcionA;
import modelo.Alumno;
import modelo.Carrera;
import modelo.ConstructorPlanDeEstudios;
import modelo.InscripcionCarrera;
import modelo.Materia;
import modelo.PlanDeEstudios;
import modelo.ReglaDeNegocioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AlumnoTest {
    private Alumno alumno;
    private Carrera carrera;

    @BeforeEach
    void prepararDatosDePrueba() {
        // Inicializacion de los datos antes de cada prueba
        Materia materia = new Materia("POO", "Programacion orientada a objetos", 1, false);
        PlanDeEstudios plan = new ConstructorPlanDeEstudios("Plan 2025")
                .agregarObligatoria(materia)
                .conMinimoDeOptativas(0)
                .conCondicion(new CondicionInscripcionA())
                .construir();
        carrera = new Carrera("LSI", "Licenciatura en Sistemas", plan);
        alumno = new Alumno("a100", "Mateo", "Mazuela", "44403071");
    }

    @Test
    void testConstructorNormalizaLegajoYNombreCompleto() {
        assertEquals("A100", alumno.getLegajo());
        assertEquals("Mazuela, Mateo", alumno.getNombreCompleto());
    }

    @Test
    void testAlumnoPuedeInscribirseEnUnaCarrera() {
        InscripcionCarrera inscripcion = alumno.inscribirseEn(carrera);

        assertSame(carrera, inscripcion.getCarrera());
        assertSame(alumno, inscripcion.getAlumno());
        assertEquals(1, alumno.getInscripciones().size());
    }

    @Test
    void testInscripcionSeBuscaConCodigoEnMinuscula() {
        InscripcionCarrera esperada = alumno.inscribirseEn(carrera);

        assertSame(esperada, alumno.getInscripcionEn("lsi"));
    }

    @Test
    void testInscripcionDuplicadaEnLaMismaCarreraLanzaExcepcion() {
        alumno.inscribirseEn(carrera);

        assertThrows(ReglaDeNegocioException.class,
                () -> alumno.inscribirseEn(carrera),
                "Un alumno no puede inscribirse dos veces en la misma carrera");
    }

    @Test
    void testInscripcionConCarreraNulaLanzaExcepcion() {
        assertThrows(ReglaDeNegocioException.class,
                () -> alumno.inscribirseEn(null),
                "La carrera es obligatoria");
    }

    @Test
    void testBuscarInscripcionConCodigoNuloLanzaExcepcion() {
        assertThrows(ReglaDeNegocioException.class,
                () -> alumno.getInscripcionEn(null),
                "El codigo de carrera no puede ser nulo");
    }

    @Test
    void testBuscarCarreraNoInscriptaLanzaExcepcion() {
        assertThrows(ReglaDeNegocioException.class,
                () -> alumno.getInscripcionEn("OTRA"),
                "Debe fallar si el alumno no esta inscripto");
    }

    @Test
    void testConstructorFallaSiUnDatoEsNulo() {
        assertThrows(ReglaDeNegocioException.class,
                () -> new Alumno(null, "Mateo", "Mazuela", "44403071"),
                "Todos los datos del alumno son obligatorios");
    }

    @Test
    void testConstructorFallaSiUnDatoEstaVacio() {
        assertThrows(ReglaDeNegocioException.class,
                () -> new Alumno("A100", " ", "Mazuela", "44403071"),
                "Los datos vacios no deben aceptarse");
    }
}
