package com.healthmanager.vista;

import com.healthmanager.modelo.Medico;
import com.healthmanager.dao.MedicoDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaListarMedicos extends JDialog {
    private JTable tablaMedicos;
    private DefaultTableModel modeloTabla;
    private MedicoDAO medicoDAO;

    public VentanaListarMedicos(JFrame parent) {
        super(parent, "Lista de Médicos", true);
        setSize(900, 500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        medicoDAO = new MedicoDAO();

        // Tabla
        String[] columnas = {"CMP", "DNI", "Nombre", "Apellido", "Especialidad", "Teléfono"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaMedicos = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaMedicos);
        add(scrollPane, BorderLayout.CENTER);

        cargarMedicos();
        setVisible(true);
    }

    private void cargarMedicos() {
        modeloTabla.setRowCount(0);
        List<Medico> medicos = medicoDAO.listarTodos();
        
        for (Medico m : medicos) {
            Object[] fila = {
                m.getCmp(),
                m.getDni(),
                m.getNombre(),
                m.getApellido(),
                m.getEspecialidad(),
                m.getTelefono()
            };
            modeloTabla.addRow(fila);
        }
    }
}