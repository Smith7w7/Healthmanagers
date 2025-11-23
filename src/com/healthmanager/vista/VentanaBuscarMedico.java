package com.healthmanager.vista;

import com.healthmanager.modelo.Medico;
import com.healthmanager.controlador.MedicoController;
import javax.swing.*;
import java.awt.*;

public class VentanaBuscarMedico extends JDialog {
    private JTextField txtCmp;
    private JButton btnBuscar;
    private JTextArea textResultado;
    private MedicoController medicoController;

    public VentanaBuscarMedico(JFrame parent) {
        super(parent, "Buscar Médico", true);
        setSize(500, 450);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        medicoController = new MedicoController();

        // Panel búsqueda
        JPanel panelBusqueda = new JPanel();
        panelBusqueda.add(new JLabel("CMP:"));
        txtCmp = new JTextField(15);
        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscar());
        panelBusqueda.add(txtCmp);
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
        String cmp = txtCmp.getText().trim();
        if (cmp.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese CMP", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Medico medico = medicoController.buscarMedico(cmp);
        if (medico != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("=== INFORMACIÓN DEL MÉDICO ===\n\n");
            sb.append("CMP: ").append(medico.getCmp() != null ? medico.getCmp() : "No especificado").append("\n");
            sb.append("DNI: ").append(medico.getDni() != null ? medico.getDni() : "No especificado").append("\n");
            sb.append("Nombre: ").append(medico.getNombre() != null ? medico.getNombre() : "").append(" ")
              .append(medico.getApellido() != null ? medico.getApellido() : "").append("\n");
            int edad = medico.calcularEdad();
            sb.append("Edad: ").append(edad >= 0 ? edad + " años" : "No especificada").append("\n");
            sb.append("Especialidad: ").append(medico.getEspecialidad() != null ? medico.getEspecialidad() : "No especificada").append("\n");
            sb.append("Teléfono: ").append(medico.getTelefono() != null ? medico.getTelefono() : "No especificado").append("\n");
            sb.append("Horario: ").append(medico.getHorario() != null ? medico.getHorario() : "No especificado").append("\n");
            textResultado.setText(sb.toString());
        } else {
            textResultado.setText("No se encontró médico con ese CMP");
        }
    }
}

