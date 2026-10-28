package vista;

import modelo.Cancion;
import modelo.Reproductor;

import javax.swing.*;
import java.awt.*;

public class PanelReproductor extends JPanel {

    private final Reproductor reproductor = new Reproductor();
    private final JTextArea areaSalida = new JTextArea(14, 60);
    private final JLabel lblActual = new JLabel("Sin reproducción.");
    private final JTextField txtId       = new JTextField(5);
    private final JTextField txtTitulo   = new JTextField(12);
    private final JTextField txtArtista  = new JTextField(12);
    private final JTextField txtDuracion = new JTextField(5);

    public PanelReproductor() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Alta de canción
        JPanel alta = new JPanel(new FlowLayout(FlowLayout.LEFT));
        alta.setBorder(BorderFactory.createTitledBorder("Agregar canción"));
        alta.add(new JLabel("ID:"));       alta.add(txtId);
        alta.add(new JLabel("Título:"));   alta.add(txtTitulo);
        alta.add(new JLabel("Artista:"));  alta.add(txtArtista);
        alta.add(new JLabel("Duración:")); alta.add(txtDuracion);
        JButton btnAgregar = new JButton("Agregar");
        alta.add(btnAgregar);

        // Controles
        JPanel controles = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controles.setBorder(BorderFactory.createTitledBorder("Controles"));
        JButton btnPlay    = new JButton("▶ Reproducir actual");
        JButton btnNext    = new JButton("⏭ Siguiente");
        JButton btnPrev    = new JButton("⏮ Anterior");
        JButton btnDelete  = new JButton("🗑 Eliminar por ID");
        JButton btnList    = new JButton("📋 Mostrar lista");
        JButton btnRecorr  = new JButton("🔁 Recorrer adelante/atrás");
        JButton btnLimpiar = new JButton("Limpiar salida");
        controles.add(btnPlay);
        controles.add(btnNext);
        controles.add(btnPrev);
        controles.add(btnDelete);
        controles.add(btnList);
        controles.add(btnRecorr);
        controles.add(btnLimpiar);

        JPanel norte = new JPanel();
        norte.setLayout(new BoxLayout(norte, BoxLayout.Y_AXIS));
        norte.add(alta);
        norte.add(controles);
        add(norte, BorderLayout.NORTH);

        areaSalida.setEditable(false);
        areaSalida.setFont(new Font("Monospaced", Font.PLAIN, 13));
        add(new JScrollPane(areaSalida), BorderLayout.CENTER);

        lblActual.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        lblActual.setFont(new Font("SansSerif", Font.BOLD, 13));
        add(lblActual, BorderLayout.SOUTH);

        // Acciones
        btnAgregar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText().trim());
                String titulo = txtTitulo.getText().trim();
                String artista = txtArtista.getText().trim();
                double dur = Double.parseDouble(txtDuracion.getText().trim());
                if (titulo.isEmpty() || artista.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Complete todos los campos.");
                    return;
                }
                reproductor.agregarCancion(new Cancion(id, titulo, artista, dur));
                escribir("Agregada: [" + id + "] " + titulo + " - " + artista);
                txtId.setText(""); txtTitulo.setText("");
                txtArtista.setText(""); txtDuracion.setText("");
                txtId.requestFocus();
                refrescarEstado();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID y duración deben ser numéricos.");
            }
        });

        btnPlay.addActionListener(e -> {
            refrescarEstado();
            Cancion c = reproductor.getActual();
            escribir(c == null ? "No hay canción actual." : "▶ Reproduciendo: " + c);
        });

        btnNext.addActionListener(e -> {
            reproductor.siguienteCancion();
            refrescarEstado();
            Cancion c = reproductor.getActual();
            if (c != null) escribir("⏭ " + c);
        });

        btnPrev.addActionListener(e -> {
            reproductor.cancionAnterior();
            refrescarEstado();
            Cancion c = reproductor.getActual();
            if (c != null) escribir("⏮ " + c);
        });

        btnDelete.addActionListener(e -> {
            String s = JOptionPane.showInputDialog(this, "ID a eliminar:");
            if (s == null) return;
            try {
                int id = Integer.parseInt(s.trim());
                boolean ok = reproductor.eliminarDeLista(id);
                escribir(ok ? "Eliminada la canción con id " + id
                            : "No existe la canción con id " + id);
                refrescarEstado();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido.");
            }
        });

        btnList.addActionListener(e -> {
            areaSalida.setText("");
            areaSalida.setText(serializarReproductor());
        });

        btnRecorr.addActionListener(e -> {
            areaSalida.append("\n=== Recorrido circular (adelante) ===\n");
            areaSalida.append(recorrido(true) + "\n");
            areaSalida.append("=== Recorrido circular (atrás) ===\n");
            areaSalida.append(recorrido(false) + "\n");
        });

        btnLimpiar.addActionListener(e -> areaSalida.setText(""));
    }

    private void refrescarEstado() {
        if (reproductor.getLista().estaVacia()) {
            lblActual.setText("Sin canciones en la lista.");
            return;
        }
        Cancion c = reproductor.getActual();
        lblActual.setText(c == null ? "Sin reproducción." : "♪ Actual: " + c);
    }

    private void escribir(String s) {
        areaSalida.append(s + "\n");
        areaSalida.setCaretPosition(areaSalida.getDocument().getLength());
    }

    private String serializarReproductor() {
        if (reproductor.getLista().estaVacia()) return "Lista de reproducción vacía.";
        StringBuilder sb = new StringBuilder("=== Lista de reproducción ===\n");
        var aux = reproductor.getLista().getCabeza();
        Cancion actual = reproductor.getActual();
        var inicio = aux;
        do {
            String marca = (aux.getDato().equals(actual)) ? "  ← (actual)" : "";
            sb.append(aux.getDato()).append(marca).append("\n");
            aux = aux.getSiguiente();
        } while (aux != inicio);
        return sb.toString();
    }

    private String recorrido(boolean adelante) {
        var lista = reproductor.getLista();
        if (lista.estaVacia()) return "Lista vacía.";
        StringBuilder sb = new StringBuilder();
        var aux = lista.getCabeza();
        int vueltas = lista.getTamanio() + 2;
        for (int i = 0; i < vueltas; i++) {
            sb.append(aux.getDato()).append("  ");
            aux = adelante ? aux.getSiguiente() : aux.getAnterior();
        }
        sb.append("\n(vueltas: ").append(vueltas)
          .append(", elementos: ").append(lista.getTamanio()).append(")");
        return sb.toString();
    }
}