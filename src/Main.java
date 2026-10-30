import datos.DatosDeDemostracion;
import vista.VentanaPrincipal;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            usarAparienciaDelSistema();
            new VentanaPrincipal(DatosDeDemostracion.crearFacultad()).setVisible(true);
        });
    }

    private static void usarAparienciaDelSistema() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception excepcion) {
            // Swing conserva su apariencia predeterminada si la del sistema no esta disponible.
        }
    }
}
