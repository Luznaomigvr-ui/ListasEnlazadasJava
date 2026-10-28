package vista;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    public VentanaPrincipal() {
        setTitle("Estructuras de Datos - Listas Enlazadas");
        setSize(900, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Lista Simple", new PanelListaSimple());
        tabs.addTab("Reproductor", new PanelReproductor());

        add(tabs, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
            new VentanaPrincipal().setVisible(true);
        });
    }
}
