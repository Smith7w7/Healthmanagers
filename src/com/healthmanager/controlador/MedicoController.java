package com.healthmanager.controlador;

import com.healthmanager.modelo.Medico;
import com.healthmanager.dao.MedicoDAO;
import com.healthmanager.util.Validador;
import java.util.List;

/**
 * Controlador para gestionar las operaciones de Médicos
 * Actúa como intermediario entre la Vista y el DAO
 */
public class MedicoController {
    private MedicoDAO medicoDAO;

    public MedicoController() {
        this.medicoDAO = new MedicoDAO();
    }

    /**
     * Registra un nuevo médico
     * @param medico El médico a registrar
     * @return true si se registró correctamente, false en caso contrario
     */
    public boolean registrarMedico(Medico medico) {
        // Validaciones de negocio
        if (medico == null) {
            return false;
        }

        if (!Validador.noEstaVacio(medico.getCmp())) {
            return false;
        }

        if (!Validador.validarDni(medico.getDni())) {
            return false;
        }

        if (!Validador.noEstaVacio(medico.getNombre()) || 
            !Validador.noEstaVacio(medico.getApellido())) {
            return false;
        }

        // Verificar si el médico ya existe
        Medico existente = medicoDAO.buscarPorCmp(medico.getCmp());
        if (existente != null) {
            return false; // Ya existe un médico con ese CMP
        }

        return medicoDAO.insertar(medico);
    }

    /**
     * Busca un médico por su CMP
     * @param cmp El CMP del médico
     * @return El médico encontrado o null si no existe
     */
    public Medico buscarMedico(String cmp) {
        if (!Validador.noEstaVacio(cmp)) {
            return null;
        }
        return medicoDAO.buscarPorCmp(cmp);
    }

    /**
     * Obtiene la lista de todos los médicos
     * @return Lista de médicos
     */
    public List<Medico> listarMedicos() {
        return medicoDAO.listarTodos();
    }

    /**
     * Actualiza los datos de un médico
     * @param medico El médico con los datos actualizados
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean actualizarMedico(Medico medico) {
        if (medico == null || !Validador.noEstaVacio(medico.getCmp())) {
            return false;
        }

        // Verificar que el médico existe
        Medico existente = medicoDAO.buscarPorCmp(medico.getCmp());
        if (existente == null) {
            return false; // El médico no existe
        }

        return medicoDAO.actualizar(medico);
    }

    /**
     * Elimina un médico por su CMP
     * @param cmp El CMP del médico a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean eliminarMedico(String cmp) {
        if (!Validador.noEstaVacio(cmp)) {
            return false;
        }

        // Verificar que el médico existe
        Medico existente = medicoDAO.buscarPorCmp(cmp);
        if (existente == null) {
            return false; // El médico no existe
        }

        return medicoDAO.eliminar(cmp);
    }

    /**
     * Verifica si un médico existe
     * @param cmp El CMP del médico
     * @return true si existe, false en caso contrario
     */
    public boolean existeMedico(String cmp) {
        if (!Validador.noEstaVacio(cmp)) {
            return false;
        }
        return medicoDAO.buscarPorCmp(cmp) != null;
    }
}

