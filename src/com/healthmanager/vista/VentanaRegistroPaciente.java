package com.healthmanager.vista;

import com.healthmanager.modelo.Paciente;
import com.healthmanager.dao.PacienteDAO;
import com.healthmanager.util.Validador;
import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class VentanaRegistroPaciente extends JDialog {
    private JTextField txtDni, txtNombre, txtApellido, txtTelefono, txtDireccion;
    private JComboBox<String> comboGenero, comboGrupoSang;
    private JSpinner spinnerFecha;
    private JButton btnGuardar, btnCancelar;
    private PacienteDAO pacienteDAO;

    public VentanaRegistroPaciente(JFrame parent) {
        super(parent, "Registrar Nuevo Paciente", true);
        setSize(600, 700);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        pacienteDAO = new PacienteDAO();

        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(new Color(240, 245, 250));

        // Panel superior con título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(25, 118, 210));
        panelTitulo.setPreferredSize(new Dimension(0, 60));
        JLabel lblTitulo = new JLabel("Registrar Nuevo Paciente");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);
        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);

        // Panel de formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(new Color(240, 245, 250));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // DNI
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblDni = new JLabel("DNI: *");
        lblDni.setFont(new Font("Arial", Font.BOLD, 12));
        panelFormulario.add(lblDni, gbc);
        gbc.gridx = 1;
        txtDni = new JTextField(15);
        txtDni.setFont(new Font("Arial", Font.PLAIN, 12));
        panelFormulario.add(txtDni, gbc);

        // Nombre
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel lblNombre = new JLabel("Nombre: *");
        lblNombre.setFont(new Font("Arial", Font.BOLD, 12));
        panelFormulario.add(lblNombre, gbc);
        gbc.gridx = 1;
        txtNombre = new JTextField(15);
        txtNombre.setFont(new Font("Arial", Font.PLAIN, 12));
        panelFormulario.add(txtNombre, gbc);

        // Apellido
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel lblApellido = new JLabel("Apellido: *");
        lblApellido.setFont(new Font("Arial", Font.BOLD, 12));
        panelFormulario.add(lblApellido, gbc);
        gbc.gridx = 1;
        txtApellido = new JTextField(15);
        txtApellido.setFont(new Font("Arial", Font.PLAIN, 12));
        panelFormulario.add(txtApellido, gbc);

        // Fecha de Nacimiento
        gbc.gridx = 0; gbc.gridy = 3;
        JLabel lblFecha = new JLabel("Fecha Nacimiento: *");
        lblFecha.setFont(new Font("Arial", Font.BOLD, 12));
        panelFormulario.add(lblFecha, gbc);
        gbc.gridx = 1;
        spinnerFecha = new JSpinner(new SpinnerDateModel());
        spinnerFecha.setEditor(new JSpinner.DateEditor(spinnerFecha, "dd/MM/yyyy"));
        panelFormulario.add(spinnerFecha, gbc);

        // Género
        gbc.gridx = 0; gbc.gridy = 4;
        JLabel lblGenero = new JLabel("Género: *");
        lblGenero.setFont(new Font("Arial", Font.BOLD, 12));
        panelFormulario.add(lblGenero, gbc);
        gbc.gridx = 1;
        comboGenero = new JComboBox<>(new String[]{"Seleccionar...", "Masculino", "Femenino"});
        panelFormulario.add(comboGenero, gbc);

        // Grupo Sanguíneo
        gbc.gridx = 0; gbc.gridy = 5;
        JLabel lblGrupo = new JLabel("Grupo Sanguíneo: *");
        lblGrupo.setFont(new Font("Arial", Font.BOLD, 12));
        panelFormulario.add(lblGrupo, gbc);
        gbc.gridx = 1;
        comboGrupoSang = new JComboBox<>(new String[]{"Seleccionar...", "O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-"});
        panelFormulario.add(comboGrupoSang, gbc);

        // Teléfono
        gbc.gridx = 0; gbc.gridy = 6;
        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setFont(new Font("Arial", Font.BOLD, 12));
        panelFormulario.add(lblTelefono, gbc);
        gbc.gridx = 1;
        txtTelefono = new JTextField(15);
        txtTelefono.setFont(new Font("Arial", Font.PLAIN, 12));
        panelFormulario.add(txtTelefono, gbc);

        // Dirección
        gbc.gridx = 0; gbc.gridy = 7;
        JLabel lblDireccion = new JLabel("Dirección:");
        lblDireccion.setFont(new Font("Arial", Font.BOLD, 12));
        panelFormulario.add(lblDireccion, gbc);
        gbc.gridx = 1;
        txtDireccion = new JTextField(15);
        txtDireccion.setFont(new Font("Arial", Font.PLAIN, 12));
        panelFormulario.add(txtDireccion, gbc);

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panelBotones.setBackground(new Color(240, 245, 250));

        btnGuardar = crearBotonGuardar("✓ Guardar");
        btnGuardar.addActionListener(e -> guardarPaciente());
        panelBotones.add(btnGuardar);

        btnCancelar = crearBotonCancelar("✕ Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        panelBotones.add(btnCancelar);

        gbc.gridx = 0; gbc.gridy = 8;
        gbc.gridwidth = 2;
        panelFormulario.add(panelBotones, gbc);

        // Scroll pane
        JScrollPane scrollPane = new JScrollPane(panelFormulario);
        scrollPane.setBackground(new Color(240, 245, 250));
        scrollPane.getViewport().setBackground(new Color(240, 245, 250));
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);

        add(panelPrincipal);
        setVisible(true);
    }

    private void guardarPaciente() {
        // Validaciones
        if (!Validador.noEstaVacio(txtDni.getText())) {
            mostrarError("DNI es obligatorio");
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

        if (comboGenero.getSelectedIndex() == 0 || comboGrupoSang.getSelectedIndex() == 0) {
            mostrarError("Seleccione género y grupo sanguíneo");
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

        // Guardar
        if (pacienteDAO.insertar(paciente)) {
            mostrarExito("✓ Paciente registrado exitosamente");
            dispose();
        } else {
            mostrarError("Error al registrar paciente");
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    private JButton crearBotonGuardar(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(76, 175, 80)); // Verde
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(120, 40));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
    private JButton crearBotonCancelar(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(244, 67, 54)); // Rojo
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(120, 40));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
}
