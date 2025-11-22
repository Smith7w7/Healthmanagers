package com.healthmanager.vista;

import com.healthmanager.modelo.Paciente;
import com.healthmanager.dao.PacienteDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaListarPacientes extends JDialog {
    private JTable tablaPacientes;
    private DefaultTableModel modeloTabla;
    private JTextField txtBuscar;
    private JButton btnBuscar, btnEliminar, btnEditar;
    private PacienteDAO pacienteDAO;

    public VentanaListarPacientes(JFrame parent) {
        super(parent, "Lista de Pacientes", true);
        setSize(900, 500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        pacienteDAO = new PacienteDAO();

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
        List<Paciente> pacientes = pacienteDAO.listarTodos();
        
        for (Paciente p : pacientes) {
            Object[] fila = {
                p.getDni(),
                p.getNombre(),
                p.getApellido(),
                p.calcularEdad(),
                p.getGenero(),
                p.getTelefono(),
                p.getDireccion()
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
        JOptionPane.showMessageDialog(this, "Funcionalidad en desarrollo");
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
            if (pacienteDAO.eliminar(dni)) {
                JOptionPane.showMessageDialog(this, "Paciente eliminado", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarPacientes();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}