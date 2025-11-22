package com.healthmanager.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class VentanaPrincipal extends JFrame {
    private JMenuBar menuBar;
    private JMenu menuPacientes, menuMedicos, menuCitas, menuReportes;
    private JPanel panelCentral;
    private JLabel lblTitulo;

    public VentanaPrincipal() {
        setTitle("HealthManager - Sistema de Gestión Hospitalaria");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        // Crear menú
        crearMenu();

        // Panel central
        panelCentral = new JPanel();
        panelCentral.setLayout(new BorderLayout());
        panelCentral.setBackground(new Color(240, 240, 240));

        lblTitulo = new JLabel("BIENVENIDO A HEALTHMANAGER");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setHorizontalAlignment(JLabel.CENTER);
        panelCentral.add(lblTitulo, BorderLayout.CENTER);

        add(panelCentral);
        setVisible(true);
    }

    private void crearMenu() {
        menuBar = new JMenuBar();

        // Menú Pacientes
        menuPacientes = new JMenu("Pacientes");
        JMenuItem itemRegistrar = new JMenuItem("Registrar Paciente");
        JMenuItem itemBuscar = new JMenuItem("Buscar Paciente");
        JMenuItem itemListar = new JMenuItem("Listar Todos");

        itemRegistrar.addActionListener(e -> new VentanaRegistroPaciente(this));
        itemBuscar.addActionListener(e -> new VentanaBuscarPaciente(this));
        itemListar.addActionListener(e -> new VentanaListarPacientes(this));

        menuPacientes.add(itemRegistrar);
        menuPacientes.add(itemBuscar);
        menuPacientes.add(itemListar);

        // Menú Médicos
        menuMedicos = new JMenu("Médicos");
        JMenuItem itemRegMedico = new JMenuItem("Registrar Médico");
        JMenuItem itemBuscarMedico = new JMenuItem("Buscar Médico");
        JMenuItem itemListarMedicos = new JMenuItem("Listar Todos");

        itemRegMedico.addActionListener(e -> new VentanaRegistroMedico(this));
        itemBuscarMedico.addActionListener(e -> JOptionPane.showMessageDialog(this, "Funcionalidad en desarrollo"));
        itemListarMedicos.addActionListener(e -> new VentanaListarMedicos(this));

        menuMedicos.add(itemRegMedico);
        menuMedicos.add(itemBuscarMedico);
        menuMedicos.add(itemListarMedicos);

        // Menú Citas
        menuCitas = new JMenu("Citas");
        JMenuItem itemAgendarCita = new JMenuItem("Agendar Cita");
        JMenuItem itemAgendaDelDia = new JMenuItem("Agenda del Día");

        itemAgendarCita.addActionListener(e -> JOptionPane.showMessageDialog(this, "Funcionalidad en desarrollo"));
        itemAgendaDelDia.addActionListener(e -> JOptionPane.showMessageDialog(this, "Funcionalidad en desarrollo"));

        menuCitas.add(itemAgendarCita);
        menuCitas.add(itemAgendaDelDia);

        // Menú Reportes
        menuReportes = new JMenu("Reportes");
        JMenuItem itemReporte1 = new JMenuItem("Citas por Médico");
        JMenuItem itemReporte2 = new JMenuItem("Estadísticas Generales");

        menuReportes.add(itemReporte1);
        menuReportes.add(itemReporte2);

        menuBar.add(menuPacientes);
        menuBar.add(menuMedicos);
        menuBar.add(menuCitas);
        menuBar.add(menuReportes);

        setJMenuBar(menuBar);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaPrincipal());
    }
}