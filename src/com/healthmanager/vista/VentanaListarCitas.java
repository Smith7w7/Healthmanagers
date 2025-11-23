package com.healthmanager.vista;

import com.healthmanager.modelo.Cita;
import com.healthmanager.controlador.CitaController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class VentanaListarCitas extends JDialog {
    private JTable tablaCitas;
    private DefaultTableModel modeloTabla;
    private CitaController citaController;
    private JButton btnAtender, btnCancelar, btnEliminar, btnRefrescar;

    public VentanaListarCitas(JFrame parent) {
        super(parent, "Lista de Citas", true);
        setSize(1100, 600);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        citaController = new CitaController();

        // Panel superior
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSuperior.add(new JLabel("Lista de Todas las Citas"));
        panelSuperior.add(Box.createHorizontalStrut(20));
        btnRefrescar = new JButton("🔄 Actualizar");
        btnRefrescar.addActionListener(e -> cargarCitas());
        panelSuperior.add(btnRefrescar);
        add(panelSuperior, BorderLayout.NORTH);

        // Tabla
        String[] columnas = {"ID", "Paciente", "Médico", "Especialidad", "Fecha", "Hora", "Motivo", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaCitas = new JTable(modeloTabla);
        tablaCitas.setRowHeight(25);
        tablaCitas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tablaCitas);
        add(scrollPane, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel();
        btnAtender = new JButton("Atender Cita");
        btnAtender.setBackground(new Color(76, 175, 80));
        btnAtender.setForeground(Color.WHITE);
        btnAtender.setOpaque(true);
        btnAtender.setBorderPainted(false);
        btnAtender.addActionListener(e -> atenderCita());
        panelBotones.add(btnAtender);

        btnCancelar = new JButton("Cancelar Cita");
        btnCancelar.setBackground(new Color(255, 152, 0));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setOpaque(true);
        btnCancelar.setBorderPainted(false);
        btnCancelar.addActionListener(e -> cancelarCita());
        panelBotones.add(btnCancelar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBackground(new Color(244, 67, 54));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setOpaque(true);
        btnEliminar.setBorderPainted(false);
        btnEliminar.addActionListener(e -> eliminarCita());
        panelBotones.add(btnEliminar);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        panelBotones.add(btnCerrar);

        add(panelBotones, BorderLayout.SOUTH);

        cargarCitas();
        setVisible(true);
    }

    private void cargarCitas() {
        modeloTabla.setRowCount(0);
        List<Cita> citas = citaController.listarCitas();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        for (Cita c : citas) {
            String fechaStr = c.getFecha() != null ? sdf.format(c.getFecha()) : "N/A";
            String pacienteStr = c.getPaciente() != null ? 
                c.getPaciente().getNombre() + " " + c.getPaciente().getApellido() : "N/A";
            String medicoStr = c.getMedico() != null ? 
                "Dr. " + c.getMedico().getNombre() + " " + c.getMedico().getApellido() : "N/A";
            String especialidadStr = c.getMedico() != null && c.getMedico().getEspecialidad() != null ?
                c.getMedico().getEspecialidad() : "N/A";
            String motivoStr = c.getMotivo() != null ? 
                (c.getMotivo().length() > 30 ? c.getMotivo().substring(0, 30) + "..." : c.getMotivo()) : "";

            Object[] fila = {
                c.getId(),
                pacienteStr,
                medicoStr,
                especialidadStr,
                fechaStr,
                c.getHora() != null ? c.getHora() : "N/A",
                motivoStr,
                c.getEstado() != null ? c.getEstado() : "N/A"
            };
            modeloTabla.addRow(fila);
        }
    }

    private void atenderCita() {
        int fila = tablaCitas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una cita", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idCita = (Integer) modeloTabla.getValueAt(fila, 0);
        String estado = (String) modeloTabla.getValueAt(fila, 7);

        if (!estado.equals("PENDIENTE")) {
            JOptionPane.showMessageDialog(this, "Solo se pueden atender citas pendientes", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String diagnostico = JOptionPane.showInputDialog(this, "Ingrese el diagnóstico:", "Atender Cita", JOptionPane.QUESTION_MESSAGE);
        if (diagnostico != null && !diagnostico.trim().isEmpty()) {
            if (citaController.atenderCita(idCita, diagnostico)) {
                JOptionPane.showMessageDialog(this, "Cita atendida exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarCitas();
            } else {
                JOptionPane.showMessageDialog(this, "Error al atender la cita", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void cancelarCita() {
        int fila = tablaCitas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una cita", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idCita = (Integer) modeloTabla.getValueAt(fila, 0);
        String estado = (String) modeloTabla.getValueAt(fila, 7);

        if (!estado.equals("PENDIENTE")) {
            JOptionPane.showMessageDialog(this, "Solo se pueden cancelar citas pendientes", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "¿Confirma cancelar esta cita?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (citaController.cancelarCita(idCita)) {
                JOptionPane.showMessageDialog(this, "Cita cancelada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarCitas();
            } else {
                JOptionPane.showMessageDialog(this, "Error al cancelar la cita", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminarCita() {
        int fila = tablaCitas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una cita", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idCita = (Integer) modeloTabla.getValueAt(fila, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Confirma eliminar esta cita permanentemente?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (citaController.eliminarCita(idCita)) {
                JOptionPane.showMessageDialog(this, "Cita eliminada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarCitas();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar la cita", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

