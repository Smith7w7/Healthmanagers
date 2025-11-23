package com.healthmanager.vista;

import com.healthmanager.modelo.Cita;
import com.healthmanager.modelo.Paciente;
import com.healthmanager.modelo.Medico;
import com.healthmanager.controlador.CitaController;
import com.healthmanager.controlador.PacienteController;
import com.healthmanager.controlador.MedicoController;
import com.healthmanager.util.Validador;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class VentanaAgendarCita extends JDialog {
    private JTextField txtDniPaciente, txtCmpMedico, txtHora;
    private JTextArea txtMotivo;
    private JSpinner spinnerFecha;
    private JComboBox<String> comboPacientes, comboMedicos;
    private JButton btnGuardar, btnCancelar, btnBuscarPaciente, btnBuscarMedico;
    private CitaController citaController;
    private PacienteController pacienteController;
    private MedicoController medicoController;

    public VentanaAgendarCita(JFrame parent) {
        super(parent, "Agendar Nueva Cita", true);
        setSize(600, 550);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());

        citaController = new CitaController();
        pacienteController = new PacienteController();
        medicoController = new MedicoController();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 3;
        JLabel lblTitulo = new JLabel("Agendar Nueva Cita Médica");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(255, 152, 0));
        add(lblTitulo, gbc);
        gbc.gridwidth = 1;

        // Paciente - DNI
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("DNI del Paciente:"), gbc);
        gbc.gridx = 1;
        txtDniPaciente = new JTextField(15);
        add(txtDniPaciente, gbc);
        gbc.gridx = 2;
        btnBuscarPaciente = new JButton("Buscar");
        btnBuscarPaciente.addActionListener(e -> buscarPaciente());
        add(btnBuscarPaciente, gbc);

        // Paciente - Selección
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Paciente:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        comboPacientes = new JComboBox<>();
        comboPacientes.setEditable(false);
        cargarPacientes();
        add(comboPacientes, gbc);
        gbc.gridwidth = 1;

        // Médico - CMP
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("CMP del Médico:"), gbc);
        gbc.gridx = 1;
        txtCmpMedico = new JTextField(15);
        add(txtCmpMedico, gbc);
        gbc.gridx = 2;
        btnBuscarMedico = new JButton("Buscar");
        btnBuscarMedico.addActionListener(e -> buscarMedico());
        add(btnBuscarMedico, gbc);

        // Médico - Selección
        gbc.gridx = 0; gbc.gridy = 4;
        add(new JLabel("Médico:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        comboMedicos = new JComboBox<>();
        comboMedicos.setEditable(false);
        cargarMedicos();
        add(comboMedicos, gbc);
        gbc.gridwidth = 1;

        // Fecha
        gbc.gridx = 0; gbc.gridy = 5;
        add(new JLabel("Fecha:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        spinnerFecha = new JSpinner(new SpinnerDateModel(new Date(), null, null, java.util.Calendar.DAY_OF_MONTH));
        spinnerFecha.setEditor(new JSpinner.DateEditor(spinnerFecha, "dd/MM/yyyy"));
        add(spinnerFecha, gbc);
        gbc.gridwidth = 1;

        // Hora
        gbc.gridx = 0; gbc.gridy = 6;
        add(new JLabel("Hora (HH:mm):"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        txtHora = new JTextField(15);
        txtHora.setToolTipText("Formato: HH:mm (ejemplo: 14:30)");
        add(txtHora, gbc);
        gbc.gridwidth = 1;

        // Motivo
        gbc.gridx = 0; gbc.gridy = 7;
        add(new JLabel("Motivo:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        txtMotivo = new JTextArea(3, 20);
        txtMotivo.setLineWrap(true);
        txtMotivo.setWrapStyleWord(true);
        JScrollPane scrollMotivo = new JScrollPane(txtMotivo);
        add(scrollMotivo, gbc);
        gbc.gridwidth = 1;

        // Botones
        JPanel panelBotones = new JPanel();
        btnGuardar = new JButton("Agendar Cita");
        btnCancelar = new JButton("Cancelar");
        btnGuardar.addActionListener(e -> guardarCita());
        btnCancelar.addActionListener(e -> dispose());
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        gbc.gridx = 0; gbc.gridy = 8;
        gbc.gridwidth = 3;
        add(panelBotones, gbc);

        setVisible(true);
    }

    private void cargarPacientes() {
        comboPacientes.removeAllItems();
        List<Paciente> pacientes = pacienteController.listarPacientes();
        for (Paciente p : pacientes) {
            String nombre = p.getNombre() != null ? p.getNombre() : "";
            String apellido = p.getApellido() != null ? p.getApellido() : "";
            String dni = p.getDni() != null ? p.getDni() : "";
            comboPacientes.addItem(dni + " - " + nombre + " " + apellido);
        }
    }

    private void cargarMedicos() {
        comboMedicos.removeAllItems();
        List<Medico> medicos = medicoController.listarMedicos();
        for (Medico m : medicos) {
            String nombre = m.getNombre() != null ? m.getNombre() : "";
            String apellido = m.getApellido() != null ? m.getApellido() : "";
            String cmp = m.getCmp() != null ? m.getCmp() : "";
            String especialidad = m.getEspecialidad() != null ? m.getEspecialidad() : "No especificada";
            comboMedicos.addItem(cmp + " - Dr. " + nombre + " " + apellido + " (" + especialidad + ")");
        }
    }

    private void buscarPaciente() {
        String dni = txtDniPaciente.getText().trim();
        if (!Validador.validarDni(dni)) {
            JOptionPane.showMessageDialog(this, "Ingrese un DNI válido (8 dígitos)", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Paciente paciente = pacienteController.buscarPaciente(dni);
        if (paciente != null) {
            String nombre = paciente.getNombre() != null ? paciente.getNombre() : "";
            String apellido = paciente.getApellido() != null ? paciente.getApellido() : "";
            String dniPac = paciente.getDni() != null ? paciente.getDni() : "";
            String item = dniPac + " - " + nombre + " " + apellido;
            for (int i = 0; i < comboPacientes.getItemCount(); i++) {
                if (comboPacientes.getItemAt(i).equals(item)) {
                    comboPacientes.setSelectedIndex(i);
                    break;
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Paciente no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarMedico() {
        String cmp = txtCmpMedico.getText().trim();
        if (!Validador.noEstaVacio(cmp)) {
            JOptionPane.showMessageDialog(this, "Ingrese un CMP válido", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Medico medico = medicoController.buscarMedico(cmp);
        if (medico != null) {
            String nombre = medico.getNombre() != null ? medico.getNombre() : "";
            String apellido = medico.getApellido() != null ? medico.getApellido() : "";
            String cmpMed = medico.getCmp() != null ? medico.getCmp() : "";
            String especialidad = medico.getEspecialidad() != null ? medico.getEspecialidad() : "No especificada";
            String item = cmpMed + " - Dr. " + nombre + " " + apellido + " (" + especialidad + ")";
            for (int i = 0; i < comboMedicos.getItemCount(); i++) {
                if (comboMedicos.getItemAt(i).equals(item)) {
                    comboMedicos.setSelectedIndex(i);
                    break;
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Médico no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void guardarCita() {
        // Validaciones
        if (comboPacientes.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un paciente", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (comboMedicos.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un médico", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String hora = txtHora.getText().trim();
        if (!Validador.validarHora(hora)) {
            JOptionPane.showMessageDialog(this, "Ingrese una hora válida (formato: HH:mm)", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Obtener DNI y CMP de los combos
        String pacienteStr = (String) comboPacientes.getSelectedItem();
        if (pacienteStr == null || !pacienteStr.contains(" - ")) {
            JOptionPane.showMessageDialog(this, "Error al obtener datos del paciente", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String dniPaciente = pacienteStr.split(" - ")[0];

        String medicoStr = (String) comboMedicos.getSelectedItem();
        if (medicoStr == null || !medicoStr.contains(" - ")) {
            JOptionPane.showMessageDialog(this, "Error al obtener datos del médico", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String cmpMedico = medicoStr.split(" - ")[0];

        // Crear objetos
        Paciente paciente = pacienteController.buscarPaciente(dniPaciente);
        Medico medico = medicoController.buscarMedico(cmpMedico);

        if (paciente == null || medico == null) {
            JOptionPane.showMessageDialog(this, "Error al obtener datos del paciente o médico", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear cita
        Cita cita = new Cita();
        cita.setPaciente(paciente);
        cita.setMedico(medico);
        cita.setFecha((Date) spinnerFecha.getValue());
        cita.setHora(hora);
        cita.setMotivo(txtMotivo.getText().trim());
        cita.setEstado("PENDIENTE");

        // Guardar
        if (citaController.agendarCita(cita)) {
            JOptionPane.showMessageDialog(this, "Cita agendada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error al agendar la cita. Verifique que el médico no tenga otra cita a la misma hora.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

