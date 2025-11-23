package com.healthmanager.vista;

import com.healthmanager.modelo.Medico;
import com.healthmanager.dao.MedicoDAO;
import com.healthmanager.util.Validador;
import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class VentanaRegistroMedico extends JDialog {
    private JTextField txtCmp, txtDni, txtNombre, txtApellido, txtTelefono;
    private JComboBox<String> comboEspecialidad;
    private JButton btnGuardar, btnCancelar;
    private MedicoDAO medicoDAO;

    public VentanaRegistroMedico(JFrame parent) {
        super(parent, "Registrar Nuevo Médico", true);
        setSize(600, 650);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        medicoDAO = new MedicoDAO();

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(new Color(240, 245, 250));

        // Panel superior
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(76, 175, 80));
        panelTitulo.setPreferredSize(new Dimension(0, 60));
        JLabel lblTitulo = new JLabel("Registrar Nuevo Médico");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);
        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);

        // Panel formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(new Color(240, 245, 250));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // CMP
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblCmp = new JLabel("CMP: *");
        lblCmp.setFont(new Font("Arial", Font.BOLD, 12));
        panelFormulario.add(lblCmp, gbc);
        gbc.gridx = 1;
        txtCmp = new JTextField(15);
        txtCmp.setFont(new Font("Arial", Font.PLAIN, 12));
        panelFormulario.add(txtCmp, gbc);

        // DNI
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel lblDni = new JLabel("DNI: *");
        lblDni.setFont(new Font("Arial", Font.BOLD, 12));
        panelFormulario.add(lblDni, gbc);
        gbc.gridx = 1;
        txtDni = new JTextField(15);
        txtDni.setFont(new Font("Arial", Font.PLAIN, 12));
        panelFormulario.add(txtDni, gbc);

        // Nombre
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel lblNombre = new JLabel("Nombre: *");
        lblNombre.setFont(new Font("Arial", Font.BOLD, 12));
        panelFormulario.add(lblNombre, gbc);
        gbc.gridx = 1;
        txtNombre = new JTextField(15);
        txtNombre.setFont(new Font("Arial", Font.PLAIN, 12));
        panelFormulario.add(txtNombre, gbc);

        // Apellido
        gbc.gridx = 0; gbc.gridy = 3;
        JLabel lblApellido = new JLabel("Apellido: *");
        lblApellido.setFont(new Font("Arial", Font.BOLD, 12));
        panelFormulario.add(lblApellido, gbc);
        gbc.gridx = 1;
        txtApellido = new JTextField(15);
        txtApellido.setFont(new Font("Arial", Font.PLAIN, 12));
        panelFormulario.add(txtApellido, gbc);

        // Especialidad
        gbc.gridx = 0; gbc.gridy = 4;
        JLabel lblEspecialidad = new JLabel("Especialidad: *");
        lblEspecialidad.setFont(new Font("Arial", Font.BOLD, 12));
        panelFormulario.add(lblEspecialidad, gbc);
        gbc.gridx = 1;
        comboEspecialidad = new JComboBox<>(new String[]{
            "Seleccionar...",
            "Medicina General",
            "Cardiología",
            "Pediatría",
            "Traumatología",
            "Dermatología"
        });
        panelFormulario.add(comboEspecialidad, gbc);

        // Teléfono
        gbc.gridx = 0; gbc.gridy = 5;
        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setFont(new Font("Arial", Font.BOLD, 12));
        panelFormulario.add(lblTelefono, gbc);
        gbc.gridx = 1;
        txtTelefono = new JTextField(15);
        txtTelefono.setFont(new Font("Arial", Font.PLAIN, 12));
        panelFormulario.add(txtTelefono, gbc);

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panelBotones.setBackground(new Color(240, 245, 250));

        btnGuardar = new JButton("✓ Guardar");
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 12));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setBackground(new Color(76, 175, 80));
        btnGuardar.setOpaque(true);
        btnGuardar.setBorderPainted(false);
        btnGuardar.setPreferredSize(new Dimension(120, 40));
        btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGuardar.addActionListener(e -> guardarMedico());
        panelBotones.add(btnGuardar);

        btnCancelar = new JButton("✕ Cancelar");
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 12));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setBackground(new Color(244, 67, 54));
        btnCancelar.setOpaque(true);
        btnCancelar.setBorderPainted(false);
        btnCancelar.setPreferredSize(new Dimension(120, 40));
        btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancelar.addActionListener(e -> dispose());
        panelBotones.add(btnCancelar);

        gbc.gridx = 0; gbc.gridy = 6;
        gbc.gridwidth = 2;
        panelFormulario.add(panelBotones, gbc);

        JScrollPane scrollPane = new JScrollPane(panelFormulario);
        scrollPane.setBackground(new Color(240, 245, 250));
        scrollPane.getViewport().setBackground(new Color(240, 245, 250));
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);

        add(panelPrincipal);
        setVisible(true);
    }

    private void guardarMedico() {
        if (!Validador.noEstaVacio(txtCmp.getText())) {
            mostrarError("CMP es obligatorio");
            return;
        }

        if (!Validador.validarDni(txtDni.getText())) {
            mostrarError("DNI debe tener 8 dígitos");
            return;
        }

        if (!Validador.noEstaVacio(txtNombre.getText())) {
            mostrarError("Nombre es obligatorio");
            return;
        }

        if (comboEspecialidad.getSelectedIndex() == 0) {
            mostrarError("Seleccione especialidad");
            return;
        }

        Medico medico = new Medico(
            txtCmp.getText().trim(),
            txtDni.getText().trim(),
            txtNombre.getText().trim(),
            txtApellido.getText().trim(),
            new Date(),
            txtTelefono.getText().trim(),
            (String) comboEspecialidad.getSelectedItem(),
            "8am-6pm"
        );

        if (medicoDAO.insertar(medico)) {
            mostrarExito("✓ Médico registrado exitosamente");
            dispose();
        } else {
            mostrarError("Error al registrar médico");
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
}