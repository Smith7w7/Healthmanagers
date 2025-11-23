package com.healthmanager.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaMenuCitas extends JDialog {
    
    public VentanaMenuCitas(JFrame parent) {
        super(parent, "Gestión de Citas", true);
        setSize(600, 450);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 245, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 0, 15, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Título
        JLabel lblTitulo = new JLabel("Gestión de Citas Médicas");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(255, 152, 0));
        gbc.gridy = 0;
        panel.add(lblTitulo, gbc);

        // Opciones
        JButton btnAgendar = crearBoton("➕ Agendar Nueva Cita", new Color(76, 175, 80));
        btnAgendar.addActionListener(e -> {
            dispose();
            new VentanaAgendarCita(parent);
        });
        gbc.gridy = 1;
        panel.add(btnAgendar, gbc);

        JButton btnListar = crearBoton("📋 Listar Todas las Citas", new Color(33, 150, 243));
        btnListar.addActionListener(e -> {
            dispose();
            new VentanaListarCitas(parent);
        });
        gbc.gridy = 2;
        panel.add(btnListar, gbc);

        JButton btnAgendaDia = crearBoton("📅 Agenda del Día", new Color(255, 152, 0));
        btnAgendaDia.addActionListener(e -> {
            dispose();
            new VentanaAgendaDelDia(parent);
        });
        gbc.gridy = 3;
        panel.add(btnAgendaDia, gbc);

        JButton btnCerrar = crearBoton("❌ Cerrar", new Color(200, 200, 200));
        btnCerrar.addActionListener(e -> dispose());
        gbc.gridy = 4;
        panel.add(btnCerrar, gbc);

        add(panel);
        setVisible(true);
    }

    private JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(0, 50));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                btn.setBackground(color.brighter());
            }
            public void mouseExited(MouseEvent evt) {
                btn.setBackground(color);
            }
        });
        return btn;
    }
}

