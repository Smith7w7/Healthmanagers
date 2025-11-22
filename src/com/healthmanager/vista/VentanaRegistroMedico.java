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
        setSize(500, 500);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());

        medicoDAO = new MedicoDAO();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // CMP
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("CMP:"), gbc);
        gbc.gridx = 1;
        txtCmp = new JTextField(15);
        add(txtCmp, gbc);

        // DNI
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("DNI:"), gbc);
        gbc.gridx = 1;
        txtDni = new JTextField(15);
        add(txtDni, gbc);

        // Nombre
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        txtNombre = new JTextField(15);
        add(txtNombre, gbc);

        // Apellido
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Apellido:"), gbc);
        gbc.gridx = 1;
        txtApellido = new JTextField(15);
        add(txtApellido, gbc);

        // Especialidad
        gbc.gridx = 0; gbc.gridy = 4;
        add(new JLabel("Especialidad:"), gbc);
        gbc.gridx = 1;
        comboEspecialidad = new JComboBox<>(new String[]{
            "Medicina General", "Cardiología", "Pediatría", "Traumatología", "Dermatología"
        });
        add(comboEspecialidad, gbc);

        // Teléfono
        gbc.gridx = 0; gbc.gridy = 5;
        add(new JLabel("Teléfono:"), gbc);
        gbc.gridx = 1;
        txtTelefono = new JTextField(15);
        add(txtTelefono, gbc);

        // Botones
        JPanel panelBotones = new JPanel();
        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");

        btnGuardar.addActionListener(e -> guardarMedico());
        btnCancelar.addActionListener(e -> dispose());

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        gbc.gridx = 0; gbc.gridy = 6;
        gbc.gridwidth = 2;
        add(panelBotones, gbc);

        setVisible(true);
    }

    private void guardarMedico() {
        if (!Validador.noEstaVacio(txtCmp.getText())) {
            JOptionPane.showMessageDialog(this, "CMP es obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!Validador.validarDni(txtDni.getText())) {
            JOptionPane.showMessageDialog(this, "DNI debe tener 8 dígitos", "Error", JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(this, "Médico registrado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error al registrar médico", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}