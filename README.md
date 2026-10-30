# Trabajo Practico Integrador POO 2025

Sistema para administrar alumnos, carreras, planes de estudio, materias e inscripciones de una facultad.

El proyecto esta hecho en Java con una interfaz Swing. Se usaron los patrones Builder, Strategy y State.

## Funciones principales

- Dar de alta alumnos, carreras y planes de estudio.
- Inscribir un alumno en una carrera.
- Inscribir un alumno en una materia.
- Consultar las materias habilitadas para cursar.
- Verificar si un alumno termino la carrera.

## Como ejecutarlo

1. Abrir la carpeta del proyecto en IntelliJ IDEA.
2. Elegir un JDK 11 o superior.
3. Abrir `src/Main.java`.
4. Ejecutar el metodo `main`.

## Pruebas

Las pruebas unitarias se encuentran en la carpeta `test/pruebas` y usan JUnit 5. Se pueden ejecutar haciendo clic derecho sobre esa carpeta y eligiendo **Run Tests**.

Si IntelliJ todavia no tiene JUnit configurado, se puede colocar el cursor sobre un `import org.junit.jupiter.api`, presionar `Alt + Enter` y elegir **Add JUnit 5 to classpath**.
