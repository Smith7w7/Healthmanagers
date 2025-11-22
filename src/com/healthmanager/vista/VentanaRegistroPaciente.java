package com.healthmanager.vista;

import com.healthmanager.modelo.Paciente;
import com.healthmanager.dao.PacienteDAO;
import com.healthmanager.util.Validador;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VentanaRegistroPaciente extends JDialog {
    private JTextField txtDni, txtNombre, txtApellido, txtTelefono, txtDireccion;
    private JComboBox<String> comboGenero, comboGrupoSang;
    private JSpinner spinnerFecha;
    private JButton btnGuardar, btnCancelar;
    private PacienteDAO pacienteDAO;

    public VentanaRegistroPaciente(JFrame parent) {
        super(parent, "Registrar Nuevo Paciente", true);
        setSize(500, 600);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());

        pacienteDAO = new PacienteDAO();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // DNI
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("DNI:"), gbc);
        gbc.gridx = 1;
        txtDni = new JTextField(15);
        add(txtDni, gbc);

        // Nombre
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        txtNombre = new JTextField(15);
        add(txtNombre, gbc);

        // Apellido
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Apellido:"), gbc);
        gbc.gridx = 1;
        txtApellido = new JTextField(15);
        add(txtApellido, gbc);

        // Fecha de Nacimiento
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Fecha Nacimiento:"), gbc);
        gbc.gridx = 1;
        spinnerFecha = new JSpinner(new SpinnerDateModel());
        spinnerFecha.setEditor(new JSpinner.DateEditor(spinnerFecha, "dd/MM/yyyy"));
        add(spinnerFecha, gbc);

        // Género
        gbc.gridx = 0; gbc.gridy = 4;
        add(new JLabel("Género:"), gbc);
        gbc.gridx = 1;
        comboGenero = new JComboBox<>(new String[]{"Masculino", "Femenino"});
        add(comboGenero, gbc);

        // Grupo Sanguíneo
        gbc.gridx = 0; gbc.gridy = 5;
        add(new JLabel("Grupo Sanguíneo:"), gbc);
        gbc.gridx = 1;
        comboGrupoSang = new JComboBox<>(new String[]{"O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-"});
        add(comboGrupoSang, gbc);

        // Teléfono
        gbc.gridx = 0; gbc.gridy = 6;
        add(new JLabel("Teléfono:"), gbc);
        gbc.gridx = 1;
        txtTelefono = new JTextField(15);
        add(txtTelefono, gbc);

        // Dirección
        gbc.gridx = 0; gbc.gridy = 7;
        add(new JLabel("Dirección:"), gbc);
        gbc.gridx = 1;
        txtDireccion = new JTextField(15);
        add(txtDireccion, gbc);

        // Botones
        JPanel panelBotones = new JPanel();
        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");

        btnGuardar.addActionListener(e -> guardarPaciente());
        btnCancelar.addActionListener(e -> dispose());

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        gbc.gridx = 0; gbc.gridy = 8;
        gbc.gridwidth = 2;
        add(panelBotones, gbc);

        setVisible(true);
    }

    private void guardarPaciente() {
        // Validaciones
        if (!Validador.noEstaVacio(txtDni.getText())) {
            JOptionPane.showMessageDialog(this, "DNI es obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!Validador.validarDni(txtDni.getText())) {
            JOptionPane.showMessageDialog(this, "DNI debe tener 8 dígitos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!Validador.noEstaVacio(txtNombre.getText())) {
            JOptionPane.showMessageDialog(this, "Nombre es obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear paciente
        Paciente paciente = new Paciente();
        paciente.setDni(txtDni.getText().trim());
        paciente.setNombre(txtNombre.getText().trim());
        paciente.setApellido(txtApellido.getText().trim());
        paciente.setFechaNacimiento((Date) spinnerFecha.getValue());
        paciente.setGenero((String) comboGenero.getSelectedItem());
        paciente.setGrupoSanguineo((String) comboGrupoSang.getSelectedItem());
        paciente.setTelefono(txtTelefono.getText().trim());
        paciente.setDireccion(txtDireccion.getText().trim());

        // Guardar en BD
        if (pacienteDAO.insertar(paciente)) {
            JOptionPane.showMessageDialog(this, "Paciente registrado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error al registrar paciente", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}