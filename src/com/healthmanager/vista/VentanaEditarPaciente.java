package com.healthmanager.vista;

import com.healthmanager.modelo.Paciente;
import com.healthmanager.controlador.PacienteController;
import com.healthmanager.util.Validador;
import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class VentanaEditarPaciente extends JDialog {
    private JTextField txtDni, txtNombre, txtApellido, txtTelefono, txtDireccion;
    private JComboBox<String> comboGenero, comboGrupoSang;
    private JSpinner spinnerFecha;
    private JButton btnGuardar, btnCancelar;
    private PacienteController pacienteController;
    private Paciente pacienteOriginal;
    private boolean guardadoExitoso = false;

    public VentanaEditarPaciente(JFrame parent, String dni) {
        super(parent, "Editar Paciente", true);
        setSize(500, 600);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());

        pacienteController = new PacienteController();
        
        // Buscar el paciente
        pacienteOriginal = pacienteController.buscarPaciente(dni);
        if (pacienteOriginal == null) {
            JOptionPane.showMessageDialog(parent, "Paciente no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // DNI (solo lectura)
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("DNI:"), gbc);
        gbc.gridx = 1;
        txtDni = new JTextField(15);
        txtDni.setText(pacienteOriginal.getDni() != null ? pacienteOriginal.getDni() : "");
        txtDni.setEditable(false);
        txtDni.setBackground(Color.LIGHT_GRAY);
        add(txtDni, gbc);

        // Nombre
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        txtNombre = new JTextField(15);
        txtNombre.setText(pacienteOriginal.getNombre() != null ? pacienteOriginal.getNombre() : "");
        add(txtNombre, gbc);

        // Apellido
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Apellido:"), gbc);
        gbc.gridx = 1;
        txtApellido = new JTextField(15);
        txtApellido.setText(pacienteOriginal.getApellido() != null ? pacienteOriginal.getApellido() : "");
        add(txtApellido, gbc);

        // Fecha de Nacimiento
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Fecha Nacimiento:"), gbc);
        gbc.gridx = 1;
        Date fechaNac = pacienteOriginal.getFechaNacimiento();
        if (fechaNac == null) {
            fechaNac = new Date(); // Fecha por defecto si es null
        }
        spinnerFecha = new JSpinner(new SpinnerDateModel(fechaNac, null, null, java.util.Calendar.DAY_OF_MONTH));
        spinnerFecha.setEditor(new JSpinner.DateEditor(spinnerFecha, "dd/MM/yyyy"));
        add(spinnerFecha, gbc);

        // Género
        gbc.gridx = 0; gbc.gridy = 4;
        add(new JLabel("Género:"), gbc);
        gbc.gridx = 1;
        comboGenero = new JComboBox<>(new String[]{"Masculino", "Femenino"});
        if (pacienteOriginal.getGenero() != null) {
            comboGenero.setSelectedItem(pacienteOriginal.getGenero());
        }
        add(comboGenero, gbc);

        // Grupo Sanguíneo
        gbc.gridx = 0; gbc.gridy = 5;
        add(new JLabel("Grupo Sanguíneo:"), gbc);
        gbc.gridx = 1;
        comboGrupoSang = new JComboBox<>(new String[]{"O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-"});
        if (pacienteOriginal.getGrupoSanguineo() != null) {
            comboGrupoSang.setSelectedItem(pacienteOriginal.getGrupoSanguineo());
        }
        add(comboGrupoSang, gbc);

        // Teléfono
        gbc.gridx = 0; gbc.gridy = 6;
        add(new JLabel("Teléfono:"), gbc);
        gbc.gridx = 1;
        txtTelefono = new JTextField(15);
        txtTelefono.setText(pacienteOriginal.getTelefono() != null ? pacienteOriginal.getTelefono() : "");
        add(txtTelefono, gbc);

        // Dirección
        gbc.gridx = 0; gbc.gridy = 7;
        add(new JLabel("Dirección:"), gbc);
        gbc.gridx = 1;
        txtDireccion = new JTextField(15);
        txtDireccion.setText(pacienteOriginal.getDireccion() != null ? pacienteOriginal.getDireccion() : "");
        add(txtDireccion, gbc);

        // Botones
        JPanel panelBotones = new JPanel();
        btnGuardar = new JButton("Guardar Cambios");
        btnCancelar = new JButton("Cancelar");

        btnGuardar.addActionListener(e -> guardarCambios());
        btnCancelar.addActionListener(e -> dispose());

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        gbc.gridx = 0; gbc.gridy = 8;
        gbc.gridwidth = 2;
        add(panelBotones, gbc);

        setVisible(true);
    }

    private void guardarCambios() {
        // Validaciones
        if (!Validador.noEstaVacio(txtNombre.getText())) {
            JOptionPane.showMessageDialog(this, "Nombre es obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!Validador.noEstaVacio(txtApellido.getText())) {
            JOptionPane.showMessageDialog(this, "Apellido es obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear paciente con los datos actualizados
        Paciente pacienteActualizado = new Paciente();
        pacienteActualizado.setDni(pacienteOriginal.getDni()); // DNI no cambia
        pacienteActualizado.setNombre(txtNombre.getText().trim());
        pacienteActualizado.setApellido(txtApellido.getText().trim());
        pacienteActualizado.setFechaNacimiento((Date) spinnerFecha.getValue());
        pacienteActualizado.setGenero((String) comboGenero.getSelectedItem());
        pacienteActualizado.setGrupoSanguineo((String) comboGrupoSang.getSelectedItem());
        pacienteActualizado.setTelefono(txtTelefono.getText().trim());
        pacienteActualizado.setDireccion(txtDireccion.getText().trim());

        // Actualizar en BD usando el controlador
        if (pacienteController.actualizarPaciente(pacienteActualizado)) {
            guardadoExitoso = true;
            JOptionPane.showMessageDialog(this, "Paciente actualizado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar paciente. Verifique los datos ingresados.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean fueGuardadoExitosamente() {
        return guardadoExitoso;
    }
}

