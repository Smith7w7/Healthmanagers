package com.healthmanager.vista;

import com.healthmanager.modelo.Cita;
import com.healthmanager.controlador.CitaController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class VentanaAgendaDelDia extends JDialog {
    private JTable tablaCitas;
    private DefaultTableModel modeloTabla;
    private CitaController citaController;
    private JSpinner spinnerFecha;
    private JButton btnBuscar, btnRefrescar;

    public VentanaAgendaDelDia(JFrame parent) {
        super(parent, "Agenda del Día", true);
        setSize(1000, 550);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        citaController = new CitaController();

        // Panel superior con fecha
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSuperior.add(new JLabel("Fecha:"));
        spinnerFecha = new JSpinner(new SpinnerDateModel(new Date(), null, null, java.util.Calendar.DAY_OF_MONTH));
        spinnerFecha.setEditor(new JSpinner.DateEditor(spinnerFecha, "dd/MM/yyyy"));
        panelSuperior.add(spinnerFecha);
        panelSuperior.add(Box.createHorizontalStrut(10));
        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> cargarCitasDelDia());
        panelSuperior.add(btnBuscar);
        panelSuperior.add(Box.createHorizontalStrut(20));
        btnRefrescar = new JButton("🔄 Actualizar");
        btnRefrescar.addActionListener(e -> cargarCitasDelDia());
        panelSuperior.add(btnRefrescar);
        add(panelSuperior, BorderLayout.NORTH);

        // Tabla
        String[] columnas = {"ID", "Hora", "Paciente", "Médico", "Especialidad", "Motivo", "Estado"};
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

        // Panel inferior
        JPanel panelInferior = new JPanel();
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        panelInferior.add(btnCerrar);
        add(panelInferior, BorderLayout.SOUTH);

        // Cargar citas del día actual
        cargarCitasDelDia();
        setVisible(true);
    }

    private void cargarCitasDelDia() {
        modeloTabla.setRowCount(0);
        Date fechaSeleccionada = (Date) spinnerFecha.getValue();
        List<Cita> citas = citaController.listarCitasPorFecha(fechaSeleccionada);

        if (citas == null || citas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay citas programadas para esta fecha", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        for (Cita c : citas) {
            String pacienteStr = c.getPaciente() != null ? 
                c.getPaciente().getNombre() + " " + c.getPaciente().getApellido() : "N/A";
            String medicoStr = c.getMedico() != null ? 
                "Dr. " + c.getMedico().getNombre() + " " + c.getMedico().getApellido() : "N/A";
            String especialidadStr = c.getMedico() != null && c.getMedico().getEspecialidad() != null ?
                c.getMedico().getEspecialidad() : "N/A";
            String motivoStr = c.getMotivo() != null ? 
                (c.getMotivo().length() > 40 ? c.getMotivo().substring(0, 40) + "..." : c.getMotivo()) : "";

            Object[] fila = {
                c.getId(),
                c.getHora() != null ? c.getHora() : "N/A",
                pacienteStr,
                medicoStr,
                especialidadStr,
                motivoStr,
                c.getEstado() != null ? c.getEstado() : "N/A"
            };
            modeloTabla.addRow(fila);
        }
    }
}

