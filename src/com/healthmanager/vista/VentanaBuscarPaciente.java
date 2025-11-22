package com.healthmanager.vista;

import com.healthmanager.modelo.Paciente;
import com.healthmanager.dao.PacienteDAO;
import javax.swing.*;
import java.awt.*;

public class VentanaBuscarPaciente extends JDialog {
    private JTextField txtDni;
    private JButton btnBuscar;
    private JTextArea textResultado;
    private PacienteDAO pacienteDAO;

    public VentanaBuscarPaciente(JFrame parent) {
        super(parent, "Buscar Paciente", true);
        setSize(500, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        pacienteDAO = new PacienteDAO();

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

        Paciente paciente = pacienteDAO.buscarPorDni(dni);
        if (paciente != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("=== INFORMACIÓN DEL PACIENTE ===\n");
            sb.append("DNI: ").append(paciente.getDni()).append("\n");
            sb.append("Nombre: ").append(paciente.getNombre()).append(" ").append(paciente.getApellido()).append("\n");
            sb.append("Edad: ").append(paciente.calcularEdad()).append(" años\n");
            sb.append("Género: ").append(paciente.getGenero()).append("\n");
            sb.append("Teléfono: ").append(paciente.getTelefono()).append("\n");
            sb.append("Dirección: ").append(paciente.getDireccion()).append("\n");
            sb.append("Grupo Sanguíneo: ").append(paciente.getGrupoSanguineo()).append("\n");
            textResultado.setText(sb.toString());
        } else {
            textResultado.setText("No se encontró paciente con ese DNI");
        }
    }
}