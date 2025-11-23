package com.healthmanager.vista;

import com.healthmanager.modelo.Paciente;
import com.healthmanager.controlador.PacienteController;
import javax.swing.*;
import java.awt.*;

public class VentanaBuscarPaciente extends JDialog {
    private JTextField txtDni;
    private JButton btnBuscar;
    private JTextArea textResultado;
    private PacienteController pacienteController;

    public VentanaBuscarPaciente(JFrame parent) {
        super(parent, "Buscar Paciente", true);
        setSize(500, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        pacienteController = new PacienteController();

        // Panel búsqueda
        JPanel panelBusqueda = new JPanel();
        panelBusqueda.add(new JLabel("DNI:"));
        txtDni = new JTextField(15);
        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscar());
        panelBusqueda.add(txtDni);
        panelBusqueda.add(btnBuscar);
        add(panelBusqueda, BorderLayout.NORTH);

        // Panel resultado
        textResultado = new JTextArea();
        textResultado.setEditable(false);
        textResultado.setFont(new Font("Arial", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(textResultado);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private void buscar() {
        String dni = txtDni.getText().trim();
        if (dni.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese DNI", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Paciente paciente = pacienteController.buscarPaciente(dni);
        if (paciente != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("=== INFORMACIÓN DEL PACIENTE ===\n");
            sb.append("DNI: ").append(paciente.getDni() != null ? paciente.getDni() : "No especificado").append("\n");
            sb.append("Nombre: ").append(paciente.getNombre() != null ? paciente.getNombre() : "").append(" ")
              .append(paciente.getApellido() != null ? paciente.getApellido() : "").append("\n");
            int edad = paciente.calcularEdad();
            sb.append("Edad: ").append(edad >= 0 ? edad + " años" : "No especificada").append("\n");
            sb.append("Género: ").append(paciente.getGenero() != null ? paciente.getGenero() : "No especificado").append("\n");
            sb.append("Teléfono: ").append(paciente.getTelefono() != null ? paciente.getTelefono() : "No especificado").append("\n");
            sb.append("Dirección: ").append(paciente.getDireccion() != null ? paciente.getDireccion() : "No especificada").append("\n");
            sb.append("Grupo Sanguíneo: ").append(paciente.getGrupoSanguineo() != null ? paciente.getGrupoSanguineo() : "No especificado").append("\n");
            textResultado.setText(sb.toString());
        } else {
            textResultado.setText("No se encontró paciente con ese DNI");
        }
    }
}