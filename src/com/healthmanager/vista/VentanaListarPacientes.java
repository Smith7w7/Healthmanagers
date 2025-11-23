package com.healthmanager.vista;

import com.healthmanager.modelo.Paciente;
import com.healthmanager.controlador.PacienteController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaListarPacientes extends JDialog {
    private JTable tablaPacientes;
    private DefaultTableModel modeloTabla;
    private JTextField txtBuscar;
    private JButton btnBuscar, btnEliminar, btnEditar;
    private PacienteController pacienteController;

    public VentanaListarPacientes(JFrame parent) {
        super(parent, "Lista de Pacientes", true);
        setSize(900, 500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        pacienteController = new PacienteController();

        // Panel superior con búsqueda
        JPanel panelSuperior = new JPanel();
        panelSuperior.add(new JLabel("Buscar por nombre:"));
        txtBuscar = new JTextField(20);
        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscar());
        panelSuperior.add(txtBuscar);
        panelSuperior.add(btnBuscar);
        add(panelSuperior, BorderLayout.NORTH);

        // Tabla
        String[] columnas = {"DNI", "Nombre", "Apellido", "Edad", "Género", "Teléfono", "Dirección"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaPacientes = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaPacientes);
        add(scrollPane, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel();
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        btnEditar.addActionListener(e -> editar());
        btnEliminar.addActionListener(e -> eliminar());
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        add(panelBotones, BorderLayout.SOUTH);

        cargarPacientes();
        setVisible(true);
    }

    private void cargarPacientes() {
        modeloTabla.setRowCount(0);
        List<Paciente> pacientes = pacienteController.listarPacientes();
        
        for (Paciente p : pacientes) {
            int edad = p.calcularEdad();
            Object[] fila = {
                p.getDni() != null ? p.getDni() : "",
                p.getNombre() != null ? p.getNombre() : "",
                p.getApellido() != null ? p.getApellido() : "",
                edad >= 0 ? edad : "N/A",
                p.getGenero() != null ? p.getGenero() : "",
                p.getTelefono() != null ? p.getTelefono() : "",
                p.getDireccion() != null ? p.getDireccion() : ""
            };
            modeloTabla.addRow(fila);
        }
    }

    private void buscar() {
        // Implementar búsqueda
        JOptionPane.showMessageDialog(this, "Funcionalidad en desarrollo");
    }

    private void editar() {
        int fila = tablaPacientes.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un paciente", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String dni = (String) modeloTabla.getValueAt(fila, 0);
        
        // Abrir ventana de edición (modal)
        VentanaEditarPaciente ventanaEditar = new VentanaEditarPaciente((JFrame) getParent(), dni);
        
        // Recargar la tabla después de cerrar la ventana de edición
        // Como es una ventana modal, el código continúa después de que se cierra
        cargarPacientes();
    }

    private void eliminar() {
        int fila = tablaPacientes.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un paciente", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String dni = (String) modeloTabla.getValueAt(fila, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "¿Confirma eliminar?", "Confirmar", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (pacienteController.eliminarPaciente(dni)) {
                JOptionPane.showMessageDialog(this, "Paciente eliminado", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarPacientes();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar. El paciente puede tener citas asociadas.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}