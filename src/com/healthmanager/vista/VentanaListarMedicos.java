package com.healthmanager.vista;

import com.healthmanager.modelo.Medico;
import com.healthmanager.controlador.MedicoController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class VentanaListarMedicos extends JDialog {
    private JTable tablaMedicos;
    private DefaultTableModel modeloTabla;
    private MedicoController medicoController;

    public VentanaListarMedicos(JFrame parent) {
        super(parent, "Lista de Médicos", true);
        setSize(900, 500);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        medicoController = new MedicoController();

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(new Color(240, 245, 250));

        // Panel superior
        JPanel panelSuperior = new JPanel();
        panelSuperior.setBackground(new Color(156, 39, 176));
        panelSuperior.setPreferredSize(new Dimension(0, 60));
        JLabel lblTitulo = new JLabel("👨‍⚕️ Lista de Médicos");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        panelSuperior.add(lblTitulo);
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);

        // Tabla
        String[] columnas = {"CMP", "DNI", "Nombre", "Apellido", "Especialidad", "Teléfono"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaMedicos = new JTable(modeloTabla);
        tablaMedicos.setFont(new Font("Arial", Font.PLAIN, 11));
        tablaMedicos.setRowHeight(25);
        tablaMedicos.setSelectionBackground(new Color(156, 39, 176));
        tablaMedicos.setSelectionForeground(Color.WHITE);
        tablaMedicos.setGridColor(new Color(200, 200, 200));

        // Personalizar encabezado
        JTableHeader header = tablaMedicos.getTableHeader();
        header.setBackground(new Color(123, 31, 162));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(tablaMedicos);
        scrollPane.setBackground(Color.WHITE);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);

        // Panel botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panelBotones.setBackground(new Color(240, 245, 250));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JButton btnRefrescar = new JButton("🔄 Actualizar");
        btnRefrescar.setFont(new Font("Arial", Font.BOLD, 12));
        btnRefrescar.setBackground(new Color(76, 175, 80));
        btnRefrescar.setForeground(Color.WHITE);
        btnRefrescar.setOpaque(true);
        btnRefrescar.setBorderPainted(false);
        btnRefrescar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRefrescar.addActionListener(e -> cargarMedicos());
        panelBotones.add(btnRefrescar);

        JButton btnCerrar = new JButton("✕ Cerrar");
        btnCerrar.setFont(new Font("Arial", Font.BOLD, 12));
        btnCerrar.setBackground(new Color(120, 120, 120));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setOpaque(true);
        btnCerrar.setBorderPainted(false);
        btnCerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCerrar.addActionListener(e -> dispose());
        panelBotones.add(btnCerrar);

        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);
        cargarMedicos();
        setVisible(true);
    }

    private void cargarMedicos() {
        modeloTabla.setRowCount(0);
        List<Medico> medicos = medicoController.listarMedicos();
        
        for (Medico m : medicos) {
            Object[] fila = {
                m.getCmp() != null ? m.getCmp() : "",
                m.getDni() != null ? m.getDni() : "",
                m.getNombre() != null ? m.getNombre() : "",
                m.getApellido() != null ? m.getApellido() : "",
                m.getEspecialidad() != null ? m.getEspecialidad() : "No especificada",
                m.getTelefono() != null ? m.getTelefono() : ""
            };
            modeloTabla.addRow(fila);
        }
    }
}