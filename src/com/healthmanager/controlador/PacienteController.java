package com.healthmanager.controlador;

import com.healthmanager.modelo.Paciente;
import com.healthmanager.dao.PacienteDAO;
import com.healthmanager.util.Validador;
import java.util.List;

/**
 * Controlador para gestionar las operaciones de Pacientes
 * Actúa como intermediario entre la Vista y el DAO
 */
public class PacienteController {
    private PacienteDAO pacienteDAO;

    public PacienteController() {
        this.pacienteDAO = new PacienteDAO();
    }

    /**
     * Registra un nuevo paciente
     * @param paciente El paciente a registrar
     * @return true si se registró correctamente, false en caso contrario
     */
    public boolean registrarPaciente(Paciente paciente) {
        // Validaciones de negocio
        if (paciente == null) {
            return false;
        }

        if (!Validador.validarDni(paciente.getDni())) {
            return false;
        }

        if (!Validador.noEstaVacio(paciente.getNombre()) || 
            !Validador.noEstaVacio(paciente.getApellido())) {
            return false;
        }

        // Verificar si el paciente ya existe
        Paciente existente = pacienteDAO.buscarPorDni(paciente.getDni());
        if (existente != null) {
            return false; // Ya existe un paciente con ese DNI
        }

        return pacienteDAO.insertar(paciente);
    }

    /**
     * Busca un paciente por su DNI
     * @param dni El DNI del paciente
     * @return El paciente encontrado o null si no existe
     */
    public Paciente buscarPaciente(String dni) {
        if (!Validador.validarDni(dni)) {
            return null;
        }
        return pacienteDAO.buscarPorDni(dni);
    }

    /**
     * Obtiene la lista de todos los pacientes
     * @return Lista de pacientes
     */
    public List<Paciente> listarPacientes() {
        return pacienteDAO.listarTodos();
    }

    /**
     * Actualiza los datos de un paciente
     * @param paciente El paciente con los datos actualizados
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean actualizarPaciente(Paciente paciente) {
        if (paciente == null || !Validador.validarDni(paciente.getDni())) {
            return false;
        }

        // Verificar que el paciente existe
        Paciente existente = pacienteDAO.buscarPorDni(paciente.getDni());
        if (existente == null) {
            return false; // El paciente no existe
        }

        return pacienteDAO.actualizar(paciente);
    }

    /**
     * Elimina un paciente por su DNI
     * @param dni El DNI del paciente a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean eliminarPaciente(String dni) {
        if (!Validador.validarDni(dni)) {
            return false;
        }

        // Verificar que el paciente existe
        Paciente existente = pacienteDAO.buscarPorDni(dni);
        if (existente == null) {
            return false; // El paciente no existe
        }

        return pacienteDAO.eliminar(dni);
    }

    /**
     * Verifica si un paciente existe
     * @param dni El DNI del paciente
     * @return true si existe, false en caso contrario
     */
    public boolean existePaciente(String dni) {
        if (!Validador.validarDni(dni)) {
            return false;
        }
        return pacienteDAO.buscarPorDni(dni) != null;
    }
}

