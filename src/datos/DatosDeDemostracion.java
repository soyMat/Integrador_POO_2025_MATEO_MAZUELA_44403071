package datos;

import modelo.Alumno;
import modelo.Carrera;
import modelo.ConstructorPlanDeEstudios;
import modelo.Cursada;
import modelo.Facultad;
import modelo.Materia;
import modelo.PlanDeEstudios;
import estrategia.CondicionInscripcionA;
import estrategia.CondicionInscripcionB;

public final class DatosDeDemostracion {
    private DatosDeDemostracion() {
    }

    public static Facultad crearFacultad() {
        Facultad facultad = new Facultad();

        Materia programacion1 = materia(facultad, "PROG1", "Programacion I", 1, false);
        Materia matematica1 = materia(facultad, "MAT1", "Matematica I", 1, false);
        Materia programacion2 = materia(facultad, "PROG2", "Programacion II", 2, false);
        Materia arquitectura = materia(facultad, "ARQ", "Arquitectura de computadoras", 2, false);
        Materia poo = materia(facultad, "POO", "Programacion orientada a objetos", 3, true);
        Materia bases = materia(facultad, "BBDD", "Bases de datos", 3, false);
        Materia ingenieria = materia(facultad, "IS", "Ingenieria de software", 4, false);
        Materia inteligencia = materia(facultad, "IA", "Inteligencia artificial", 5, false);
        Materia web = materia(facultad, "WEB", "Desarrollo web", 4, true);
        Materia datos = materia(facultad, "CD", "Ciencia de datos", 5, true);

        programacion2.agregarCorrelativa(programacion1);
        arquitectura.agregarCorrelativa(programacion1);
        poo.agregarCorrelativa(programacion2);
        bases.agregarCorrelativa(programacion2);
        ingenieria.agregarCorrelativa(poo);
        inteligencia.agregarCorrelativa(bases);
        web.agregarCorrelativa(poo);
        datos.agregarCorrelativa(bases);

        PlanDeEstudios planLicenciatura = new ConstructorPlanDeEstudios("Plan LSI 2025")
                .agregarObligatoria(programacion1)
                .agregarObligatoria(matematica1)
                .agregarObligatoria(programacion2)
                .agregarObligatoria(arquitectura)
                .agregarObligatoria(poo)
                .agregarObligatoria(bases)
                .agregarObligatoria(ingenieria)
                .agregarObligatoria(inteligencia)
                .agregarOptativa(web)
                .agregarOptativa(datos)
                .conMinimoDeOptativas(1)
                .conCondicion(new CondicionInscripcionA())
                .construir();

        PlanDeEstudios planTecnicatura = new ConstructorPlanDeEstudios("Plan TUDA 2025")
                .agregarObligatoria(programacion1)
                .agregarObligatoria(programacion2)
                .agregarObligatoria(poo)
                .agregarObligatoria(bases)
                .agregarOptativa(web)
                .agregarOptativa(datos)
                .conMinimoDeOptativas(1)
                .conCondicion(new CondicionInscripcionB())
                .construir();

        Carrera licenciatura = new Carrera("LSI", "Licenciatura en Sistemas", planLicenciatura);
        Carrera tecnicatura = new Carrera("TUDA", "Tecnicatura Universitaria en Desarrollo de Aplicaciones",
                planTecnicatura);
        facultad.agregarCarrera(licenciatura);
        facultad.agregarCarrera(tecnicatura);

        Alumno lionel = new Alumno("A100", "Lionel", "Messi", "40111222");
        Alumno ricardo = new Alumno("A101", "Ricardo", "Darin", "41222333");
        Alumno mercedes = new Alumno("A102", "Mercedes", "Sosa", "39333444");
        facultad.agregarAlumno(lionel);
        facultad.agregarAlumno(ricardo);
        facultad.agregarAlumno(mercedes);

        facultad.inscribirAlumnoEnCarrera("A100", "LSI");
        aprobarConFinal(facultad.inscribirAlumnoEnMateria("A100", "LSI", "PROG1"));
        aprobarConFinal(facultad.inscribirAlumnoEnMateria("A100", "LSI", "MAT1"));
        aprobarCursada(facultad.inscribirAlumnoEnMateria("A100", "LSI", "PROG2"));
        facultad.inscribirAlumnoEnMateria("A100", "LSI", "POO");

        facultad.inscribirAlumnoEnCarrera("A102", "LSI");
        aprobarConFinal(facultad.inscribirAlumnoEnMateria("A102", "LSI", "PROG1"));
        aprobarConFinal(facultad.inscribirAlumnoEnMateria("A102", "LSI", "MAT1"));
        aprobarConFinal(facultad.inscribirAlumnoEnMateria("A102", "LSI", "PROG2"));
        aprobarConFinal(facultad.inscribirAlumnoEnMateria("A102", "LSI", "ARQ"));
        promocionar(facultad.inscribirAlumnoEnMateria("A102", "LSI", "POO"));
        aprobarConFinal(facultad.inscribirAlumnoEnMateria("A102", "LSI", "BBDD"));
        aprobarConFinal(facultad.inscribirAlumnoEnMateria("A102", "LSI", "IS"));
        aprobarConFinal(facultad.inscribirAlumnoEnMateria("A102", "LSI", "IA"));
        promocionar(facultad.inscribirAlumnoEnMateria("A102", "LSI", "WEB"));

        return facultad;
    }

    private static Materia materia(Facultad facultad, String codigo, String nombre,
                                   int cuatrimestre, boolean promocion) {
        Materia materia = new Materia(codigo, nombre, cuatrimestre, promocion);
        facultad.agregarMateria(materia);
        return materia;
    }

    private static void aprobarCursada(Cursada cursada) {
        cursada.aprobarParcial();
        cursada.aprobarCursada();
    }

    private static void aprobarConFinal(Cursada cursada) {
        aprobarCursada(cursada);
        cursada.aprobarFinal();
    }

    private static void promocionar(Cursada cursada) {
        cursada.aprobarParcial();
        cursada.promocionar();
    }
}
