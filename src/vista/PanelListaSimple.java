package vista;

import modelo.ListaSimple;

import javax.swing.*;
import java.awt.*;

public class PanelListaSimple extends JPanel {

    private final ListaSimple<String> lista = new ListaSimple<>();
    private final JTextField txtValor = new JTextField(15);
    private final JTextArea areaSalida = new JTextArea(12, 60);

    public PanelListaSimple() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel superior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        superior.add(new JLabel("Valor:"));
        superior.add(txtValor);

        JButton btnInicio   = new JButton("Insertar al inicio");
        JButton btnFinal    = new JButton("Insertar al final");
        JButton btnEliminar = new JButton("Eliminar por valor");
        JButton btnBuscar   = new JButton("Buscar");
        JButton btnMostrar  = new JButton("Mostrar lista");
        JButton btnLimpiar  = new JButton("Limpiar salida");

        superior.add(btnInicio);
        superior.add(btnFinal);
        superior.add(btnEliminar);
        superior.add(btnBuscar);
        superior.add(btnMostrar);
        superior.add(btnLimpiar);

        add(superior, BorderLayout.NORTH);

        areaSalida.setEditable(false);
        areaSalida.setFont(new Font("Monospaced", Font.PLAIN, 13));
        add(new JScrollPane(areaSalida), BorderLayout.CENTER);

        JLabel lblEstado = new JLabel("Listo.");
        lblEstado.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(lblEstado, BorderLayout.SOUTH);

        btnInicio.addActionListener(e -> {
            String v = obtenerValor();
            if (v == null) return;
            lista.insertarInicio(v);
            escribir("Insertado al inicio: " + v);
            lblEstado.setText("Insertado al inicio.");
        });

        btnFinal.addActionListener(e -> {
            String v = obtenerValor();
            if (v == null) return;
            lista.insertarFinal(v);
            escribir("Insertado al final: " + v);
            lblEstado.setText("Insertado al final.");
        });

        btnEliminar.addActionListener(e -> {
            String v = obtenerValor();
            if (v == null) return;
            boolean ok = lista.eliminarPorValor(v);
            escribir(ok ? "Eliminado: " + v : "No encontrado: " + v);
            lblEstado.setText(ok ? "Eliminado." : "No encontrado.");
        });

        btnBuscar.addActionListener(e -> {
            String v = obtenerValor();
            if (v == null) return;
            boolean en = lista.buscar(v);
            escribir(en ? "✔ Encontrado: " + v : "✘ No encontrado: " + v);
            lblEstado.setText(en ? "Encontrado." : "No encontrado.");
        });

        btnMostrar.addActionListener(e -> {
            escribir("=== Lista simple ===");
            escribir(lista.toString());
            lblEstado.setText("Lista mostrada.");
        });

        btnLimpiar.addActionListener(e -> {
            areaSalida.setText("");
            lblEstado.setText("Salida limpia.");
        });
    }

    private String obtenerValor() {
        String v = txtValor.getText().trim();
        if (v.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un valor.");
            return null;
        }
        txtValor.setText("");
        txtValor.requestFocus();
        return v;
    }

    private void escribir(String texto) {
        areaSalida.append(texto + "\n");
        areaSalida.setCaretPosition(areaSalida.getDocument().getLength());
    }
}