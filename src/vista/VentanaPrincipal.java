package vista;

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
import modelo.Facultad;
import modelo.InscripcionCarrera;
import modelo.Materia;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VentanaPrincipal extends JFrame {
    private final Facultad facultad;

    private final JComboBox<String> alumnoInscripcion = new JComboBox<>();
    private final JComboBox<String> carreraInscripcion = new JComboBox<>();
    private final JComboBox<String> materiaInscripcion = new JComboBox<>();
    private final JComboBox<String> alumnoCursada = new JComboBox<>();
    private final JComboBox<String> carreraCursada = new JComboBox<>();
    private final JComboBox<String> materiaCursada = new JComboBox<>();
    private final JComboBox<String> alumnoGraduacion = new JComboBox<>();
    private final JComboBox<String> carreraGraduacion = new JComboBox<>();

    public VentanaPrincipal(Facultad facultad) {
        super("Sistema academico - TP Integrador 2025");
        this.facultad = facultad;
        configurarVentana();
        armarContenido();
        actualizarCombos();
    }

    private void configurarVentana() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(950, 680));
        setSize(1050, 740);
        setLocationRelativeTo(null);
    }

    private void armarContenido() {
        JPanel raiz = new JPanel(new BorderLayout(0, 12));
        raiz.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));

        JLabel titulo = new JLabel("Gestion de carreras y alumnos", SwingConstants.LEFT);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 24f));
        raiz.add(titulo, BorderLayout.NORTH);

        JTabbedPane pestanas = new JTabbedPane();
        pestanas.addTab("Inicio", crearPanelInicio());
        pestanas.addTab("Alumnos", crearPanelAlumnos());
        pestanas.addTab("Materias", crearPanelMaterias());
        pestanas.addTab("Carreras y planes", crearPanelCarreras());
        pestanas.addTab("Inscripciones", crearPanelInscripciones());
        pestanas.addTab("Cursadas", crearPanelCursadas());
        pestanas.addTab("Graduacion", crearPanelGraduacion());
        raiz.add(pestanas, BorderLayout.CENTER);
        setContentPane(raiz);
    }

    private JPanel crearPanelInicio() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JTextArea texto = areaSalida();
        texto.setText("TP Integrador 2025 - Programacion y Diseno Orientado a Objetos\n\n"
                + resumen() + "\n\n"
                + "Recorrido sugerido para la demo:\n"
                + "- A100 (Lionel Messi) tiene una trayectoria en curso en LSI.\n"
                + "- A101 (Ricardo Darin) esta disponible para nuevas inscripciones.\n"
                + "- A102 (Mercedes Sosa) cumple todos los requisitos de graduacion en LSI.\n\n"
                + "Las pestanas permiten probar Builder, Strategy, State y los casos minimos de la consigna.");
        panel.add(new JScrollPane(texto), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelAlumnos() {
        JPanel panel = panelFormulario("Alta de alumno");
        JTextField legajo = new JTextField(22);
        JTextField nombre = new JTextField(22);
        JTextField apellido = new JTextField(22);
        JTextField documento = new JTextField(22);
        agregarCampo(panel, 0, "Legajo", legajo);
        agregarCampo(panel, 1, "Nombre", nombre);
        agregarCampo(panel, 2, "Apellido", apellido);
        agregarCampo(panel, 3, "Documento", documento);
        JButton guardar = new JButton("Registrar alumno");
        guardar.addActionListener(evento -> ejecutar(() ->
                        altaAlumno(legajo.getText(), nombre.getText(),
                                apellido.getText(), documento.getText()),
                "Alumno registrado", true));
        agregarBoton(panel, 4, guardar);
        return envolverArriba(panel);
    }

    private JPanel crearPanelMaterias() {
        JPanel panel = panelFormulario("Alta de materia");
        JTextField codigo = new JTextField(22);
        JTextField nombre = new JTextField(22);
        JTextField cuatrimestre = new JTextField(22);
        JTextField correlativas = new JTextField(22);
        JCheckBox promocion = new JCheckBox("Permite promocion");
        agregarCampo(panel, 0, "Codigo", codigo);
        agregarCampo(panel, 1, "Nombre", nombre);
        agregarCampo(panel, 2, "Cuatrimestre", cuatrimestre);
        agregarCampo(panel, 3, "Correlativas (codigos separados por coma)", correlativas);
        agregarCampo(panel, 4, "Regimen", promocion);
        JButton guardar = new JButton("Registrar materia");
        guardar.addActionListener(evento -> ejecutar(() ->
                        altaMateria(codigo.getText(), nombre.getText(), cuatrimestre.getText(),
                                promocion.isSelected(), correlativas.getText()),
                "Materia registrada", true));
        agregarBoton(panel, 5, guardar);
        return envolverArriba(panel);
    }

    private JPanel crearPanelCarreras() {
        JPanel panel = panelFormulario("Alta de carrera y plan de estudios");
        JTextField codigo = new JTextField(24);
        JTextField nombre = new JTextField(24);
        JTextField nombrePlan = new JTextField(24);
        JTextField obligatorias = new JTextField(24);
        JTextField optativas = new JTextField(24);
        JTextField minimo = new JTextField("0", 24);
        JComboBox<String> condicion = new JComboBox<>(new String[]{"A", "B", "C", "D", "E"});
        agregarCampo(panel, 0, "Codigo de carrera", codigo);
        agregarCampo(panel, 1, "Nombre de carrera", nombre);
        agregarCampo(panel, 2, "Nombre del plan", nombrePlan);
        agregarCampo(panel, 3, "Materias obligatorias (codigos por coma)", obligatorias);
        agregarCampo(panel, 4, "Materias optativas (codigos por coma)", optativas);
        agregarCampo(panel, 5, "Cantidad minima de optativas", minimo);
        agregarCampo(panel, 6, "Condicion de inscripcion", condicion);
        JButton guardar = new JButton("Crear carrera y plan");
        guardar.addActionListener(evento -> ejecutar(() -> altaCarreraYPlan(
                        codigo.getText(), nombre.getText(), nombrePlan.getText(), minimo.getText(),
                        (String) condicion.getSelectedItem(), obligatorias.getText(), optativas.getText()),
                "Carrera y plan registrados", true));
        agregarBoton(panel, 7, guardar);
        return envolverArriba(panel);
    }

    private JPanel crearPanelInscripciones() {
        JPanel panel = panelFormulario("Inscripcion academica");
        JTextArea salida = areaSalida();
        agregarCampo(panel, 0, "Alumno", alumnoInscripcion);
        agregarCampo(panel, 1, "Carrera", carreraInscripcion);
        JButton inscribirCarrera = new JButton("Inscribir en carrera");
        inscribirCarrera.addActionListener(evento -> ejecutar(() -> inscribirEnCarrera(
                        codigoSeleccionado(alumnoInscripcion), codigoSeleccionado(carreraInscripcion)),
                "Alumno inscripto en la carrera", false));
        agregarBoton(panel, 2, inscribirCarrera);

        agregarCampo(panel, 3, "Materia", materiaInscripcion);
        JButton verHabilitadas = new JButton("Ver materias habilitadas");
        verHabilitadas.addActionListener(evento -> ejecutarConSalida(() -> materiasHabilitadas(
                codigoSeleccionado(alumnoInscripcion), codigoSeleccionado(carreraInscripcion)), salida));
        JButton inscribirMateria = new JButton("Inscribir en materia");
        inscribirMateria.addActionListener(evento -> ejecutar(() -> inscribirEnMateria(
                        codigoSeleccionado(alumnoInscripcion), codigoSeleccionado(carreraInscripcion),
                        codigoSeleccionado(materiaInscripcion)),
                "Alumno inscripto en la materia", false));
        JPanel botones = new JPanel();
        botones.add(verHabilitadas);
        botones.add(inscribirMateria);
        agregarCampo(panel, 4, "Acciones", botones);
        agregarCampoExpandido(panel, 5, new JScrollPane(salida));
        return panel;
    }

    private JPanel crearPanelCursadas() {
        JPanel panel = panelFormulario("Ciclo de vida de una cursada");
        JTextArea salida = areaSalida();
        JComboBox<String> accion = new JComboBox<>(new String[]{
                "Aprobar parcial", "Desaprobar parcial", "Aprobar cursada",
                "Promocionar", "Desaprobar cursada", "Aprobar final"
        });
        agregarCampo(panel, 0, "Alumno", alumnoCursada);
        agregarCampo(panel, 1, "Carrera", carreraCursada);
        agregarCampo(panel, 2, "Materia", materiaCursada);
        agregarCampo(panel, 3, "Transicion", accion);
        JButton aplicar = new JButton("Aplicar transicion");
        aplicar.addActionListener(evento -> ejecutar(() -> {
            avanzarCursada(codigoSeleccionado(alumnoCursada),
                    codigoSeleccionado(carreraCursada), codigoSeleccionado(materiaCursada),
                    (String) accion.getSelectedItem());
            mostrarLista(detalleCursadas(codigoSeleccionado(alumnoCursada),
                    codigoSeleccionado(carreraCursada)), salida);
        }, "Estado actualizado", false));
        JButton consultar = new JButton("Consultar cursadas");
        consultar.addActionListener(evento -> ejecutarConSalida(() -> detalleCursadas(
                codigoSeleccionado(alumnoCursada), codigoSeleccionado(carreraCursada)), salida));
        JPanel botones = new JPanel();
        botones.add(aplicar);
        botones.add(consultar);
        agregarCampo(panel, 4, "Acciones", botones);
        agregarCampoExpandido(panel, 5, new JScrollPane(salida));
        return panel;
    }

    private JPanel crearPanelGraduacion() {
        JPanel panel = panelFormulario("Verificacion de graduacion");
        JTextArea salida = areaSalida();
        agregarCampo(panel, 0, "Alumno", alumnoGraduacion);
        agregarCampo(panel, 1, "Carrera", carreraGraduacion);
        JButton verificar = new JButton("Verificar finalizacion");
        verificar.addActionListener(evento -> {
            try {
                salida.setText(verificarGraduacion(codigoSeleccionado(alumnoGraduacion),
                        codigoSeleccionado(carreraGraduacion)));
            } catch (RuntimeException excepcion) {
                mostrarError(excepcion);
            }
        });
        agregarBoton(panel, 2, verificar);
        agregarCampoExpandido(panel, 3, new JScrollPane(salida));
        return panel;
    }

    private void altaAlumno(String legajo, String nombre, String apellido, String documento) {
        facultad.agregarAlumno(new Alumno(legajo, nombre, apellido, documento));
    }

    private void altaMateria(String codigo, String nombre, String cuatrimestre,
                             boolean promocion, String codigosCorrelativas) {
        int numeroCuatrimestre = convertirEntero(cuatrimestre, "El cuatrimestre debe ser un numero");
        Materia materia = new Materia(codigo, nombre, numeroCuatrimestre, promocion);
        for (String codigoCorrelativa : separarCodigos(codigosCorrelativas)) {
            materia.agregarCorrelativa(facultad.buscarMateria(codigoCorrelativa));
        }
        facultad.agregarMateria(materia);
    }

    private void altaCarreraYPlan(String codigoCarrera, String nombreCarrera, String nombrePlan,
                                  String minimoOptativas, String tipoCondicion,
                                  String codigosObligatorias, String codigosOptativas) {
        ConstructorPlanDeEstudios constructor = new ConstructorPlanDeEstudios(nombrePlan)
                .conMinimoDeOptativas(convertirEntero(minimoOptativas,
                        "La cantidad minima de optativas debe ser un numero"))
                .conCondicion(crearCondicion(tipoCondicion));
        for (String codigo : separarCodigos(codigosObligatorias)) {
            constructor.agregarObligatoria(facultad.buscarMateria(codigo));
        }
        for (String codigo : separarCodigos(codigosOptativas)) {
            constructor.agregarOptativa(facultad.buscarMateria(codigo));
        }
        facultad.agregarCarrera(new Carrera(codigoCarrera, nombreCarrera, constructor.construir()));
    }

    private void inscribirEnCarrera(String legajo, String codigoCarrera) {
        facultad.inscribirAlumnoEnCarrera(legajo, codigoCarrera);
    }

    private void inscribirEnMateria(String legajo, String codigoCarrera, String codigoMateria) {
        facultad.inscribirAlumnoEnMateria(legajo, codigoCarrera, codigoMateria);
    }

    private List<String> materiasHabilitadas(String legajo, String codigoCarrera) {
        InscripcionCarrera inscripcion = facultad.buscarAlumno(legajo).getInscripcionEn(codigoCarrera);
        List<String> resultado = new ArrayList<>();
        for (Materia materia : inscripcion.materiasHabilitadas()) {
            resultado.add(materia.toString());
        }
        return resultado;
    }

    private String verificarGraduacion(String legajo, String codigoCarrera) {
        Alumno alumno = facultad.buscarAlumno(legajo);
        Carrera carrera = facultad.buscarCarrera(codigoCarrera);
        if (facultad.finalizoCarrera(legajo, codigoCarrera)) {
            return alumno.getNombreCompleto() + " finalizo " + carrera.getNombre() + ".";
        }
        return alumno.getNombreCompleto() + " todavia no finalizo " + carrera.getNombre() + ".";
    }

    private void avanzarCursada(String legajo, String codigoCarrera,
                                String codigoMateria, String accion) {
        Materia materia = facultad.buscarMateria(codigoMateria);
        Cursada cursada = facultad.buscarAlumno(legajo).getInscripcionEn(codigoCarrera)
                .getUltimaCursada(materia);
        switch (accion) {
            case "Aprobar parcial":
                cursada.aprobarParcial();
                break;
            case "Desaprobar parcial":
                cursada.desaprobarParcial();
                break;
            case "Aprobar cursada":
                cursada.aprobarCursada();
                break;
            case "Promocionar":
                cursada.promocionar();
                break;
            case "Desaprobar cursada":
                cursada.desaprobarCursada();
                break;
            case "Aprobar final":
                cursada.aprobarFinal();
                break;
            default:
                throw new IllegalArgumentException("Accion de cursada desconocida");
        }
    }

    private List<String> detalleCursadas(String legajo, String codigoCarrera) {
        List<Cursada> cursadas = facultad.buscarAlumno(legajo).getInscripcionEn(codigoCarrera)
                .getUltimasCursadas();
        List<String> resultado = new ArrayList<>();
        for (Cursada cursada : cursadas) {
            resultado.add(cursada.toString());
        }
        return resultado;
    }

    private List<String> opcionesAlumnos() {
        List<String> opciones = new ArrayList<>();
        for (Alumno alumno : facultad.getAlumnos()) {
            opciones.add(alumno.toString());
        }
        return opciones;
    }

    private List<String> opcionesCarreras() {
        List<String> opciones = new ArrayList<>();
        for (Carrera carrera : facultad.getCarreras()) {
            opciones.add(carrera.toString());
        }
        return opciones;
    }

    private List<String> opcionesMaterias() {
        List<String> opciones = new ArrayList<>();
        for (Materia materia : facultad.getMaterias()) {
            opciones.add(materia.toString());
        }
        return opciones;
    }

    private String resumen() {
        return "Datos cargados: " + facultad.getAlumnos().size() + " alumnos, "
                + facultad.getCarreras().size() + " carreras y "
                + facultad.getMaterias().size() + " materias.";
    }

    private CondicionInscripcion crearCondicion(String tipo) {
        switch (tipo) {
            case "A":
                return new CondicionInscripcionA();
            case "B":
                return new CondicionInscripcionB();
            case "C":
                return new CondicionInscripcionC();
            case "D":
                return new CondicionInscripcionD();
            case "E":
                return new CondicionInscripcionE();
            default:
                throw new IllegalArgumentException("Condicion de inscripcion desconocida");
        }
    }

    private int convertirEntero(String texto, String mensaje) {
        try {
            return Integer.parseInt(texto.trim());
        } catch (NumberFormatException excepcion) {
            throw new IllegalArgumentException(mensaje);
        }
    }

    private List<String> separarCodigos(String texto) {
        if (texto == null || texto.isBlank()) {
            return Collections.emptyList();
        }
        List<String> codigos = new ArrayList<>();
        String[] partes = texto.split(",");
        for (String parte : partes) {
            String codigo = parte.trim();
            if (!codigo.isEmpty()) {
                codigos.add(codigo);
            }
        }
        return codigos;
    }

    private JPanel panelFormulario(String titulo) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder(titulo));
        return panel;
    }

    private JPanel envolverArriba(JPanel contenido) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(contenido, BorderLayout.NORTH);
        return panel;
    }

    private void agregarCampo(JPanel panel, int fila, String etiqueta, Component componente) {
        GridBagConstraints restricciones = restricciones(fila);
        restricciones.gridx = 0;
        restricciones.weightx = 0;
        restricciones.fill = GridBagConstraints.NONE;
        restricciones.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel(etiqueta + ":"), restricciones);

        restricciones = restricciones(fila);
        restricciones.gridx = 1;
        restricciones.weightx = 1;
        restricciones.fill = GridBagConstraints.HORIZONTAL;
        panel.add(componente, restricciones);
    }

    private void agregarBoton(JPanel panel, int fila, JButton boton) {
        GridBagConstraints restricciones = restricciones(fila);
        restricciones.gridx = 1;
        restricciones.anchor = GridBagConstraints.WEST;
        panel.add(boton, restricciones);
    }

    private void agregarCampoExpandido(JPanel panel, int fila, Component componente) {
        GridBagConstraints restricciones = restricciones(fila);
        restricciones.gridx = 0;
        restricciones.gridwidth = 2;
        restricciones.weightx = 1;
        restricciones.weighty = 1;
        restricciones.fill = GridBagConstraints.BOTH;
        panel.add(componente, restricciones);
    }

    private GridBagConstraints restricciones(int fila) {
        GridBagConstraints restricciones = new GridBagConstraints();
        restricciones.gridy = fila;
        restricciones.insets = new Insets(7, 7, 7, 7);
        return restricciones;
    }

    private JTextArea areaSalida() {
        JTextArea area = new JTextArea(9, 50);
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setMargin(new Insets(8, 8, 8, 8));
        return area;
    }

    private void ejecutar(Runnable operacion, String mensajeExito, boolean actualizar) {
        try {
            operacion.run();
            if (actualizar) {
                actualizarCombos();
            }
            JOptionPane.showMessageDialog(this, mensajeExito, "Operacion exitosa",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (RuntimeException excepcion) {
            mostrarError(excepcion);
        }
    }

    private void ejecutarConSalida(ProveedorLista proveedor, JTextArea salida) {
        try {
            mostrarLista(proveedor.obtener(), salida);
        } catch (RuntimeException excepcion) {
            mostrarError(excepcion);
        }
    }

    private void mostrarLista(List<String> elementos, JTextArea salida) {
        salida.setText(elementos.isEmpty() ? "No hay resultados." : String.join("\n", elementos));
    }

    private void mostrarError(RuntimeException excepcion) {
        JOptionPane.showMessageDialog(this, excepcion.getMessage(), "No se pudo completar la operacion",
                JOptionPane.ERROR_MESSAGE);
    }

    private void actualizarCombos() {
        List<String> alumnos = opcionesAlumnos();
        List<String> carreras = opcionesCarreras();
        List<String> materias = opcionesMaterias();
        cargarCombo(alumnoInscripcion, alumnos);
        cargarCombo(alumnoCursada, alumnos);
        cargarCombo(alumnoGraduacion, alumnos);
        cargarCombo(carreraInscripcion, carreras);
        cargarCombo(carreraCursada, carreras);
        cargarCombo(carreraGraduacion, carreras);
        cargarCombo(materiaInscripcion, materias);
        cargarCombo(materiaCursada, materias);
    }

    private void cargarCombo(JComboBox<String> combo, List<String> opciones) {
        combo.removeAllItems();
        opciones.forEach(combo::addItem);
    }

    private String codigoSeleccionado(JComboBox<String> combo) {
        Object seleccion = combo.getSelectedItem();
        if (seleccion == null) {
            throw new IllegalArgumentException("Debe seleccionar una opcion");
        }
        String texto = seleccion.toString();
        int separador = texto.indexOf(" - ");
        return separador < 0 ? texto : texto.substring(0, separador);
    }

    @FunctionalInterface
    private interface ProveedorLista {
        List<String> obtener();
    }
}
