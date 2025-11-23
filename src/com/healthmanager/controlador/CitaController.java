package com.healthmanager.controlador;

import com.healthmanager.modelo.Cita;
import com.healthmanager.modelo.Paciente;
import com.healthmanager.modelo.Medico;
import com.healthmanager.dao.CitaDAO;
import com.healthmanager.dao.PacienteDAO;
import com.healthmanager.dao.MedicoDAO;
import com.healthmanager.util.Validador;
import java.util.Date;
import java.util.List;

/**
 * Controlador para gestionar las operaciones de Citas
 * Actúa como intermediario entre la Vista y el DAO
 */
public class CitaController {
    private CitaDAO citaDAO;
    private PacienteDAO pacienteDAO;
    private MedicoDAO medicoDAO;

    public CitaController() {
        this.citaDAO = new CitaDAO();
        this.pacienteDAO = new PacienteDAO();
        this.medicoDAO = new MedicoDAO();
    }

    /**
     * Agenda una nueva cita
     * @param cita La cita a agendar
     * @return true si se agendó correctamente, false en caso contrario
     */
    public boolean agendarCita(Cita cita) {
        // Validaciones de negocio
        if (cita == null) {
            return false;
        }

        if (cita.getPaciente() == null || cita.getMedico() == null) {
            return false;
        }

        if (cita.getFecha() == null || !Validador.validarHora(cita.getHora())) {
            return false;
        }

        // Verificar que el paciente existe
        Paciente paciente = pacienteDAO.buscarPorDni(cita.getPaciente().getDni());
        if (paciente == null) {
            return false; // El paciente no existe
        }

        // Verificar que el médico existe
        Medico medico = medicoDAO.buscarPorCmp(cita.getMedico().getCmp());
        if (medico == null) {
            return false; // El médico no existe
        }

        // Establecer estado inicial
        if (cita.getEstado() == null || cita.getEstado().isEmpty()) {
            cita.setEstado("PENDIENTE");
        }

        // Asignar objetos completos
        cita.setPaciente(paciente);
        cita.setMedico(medico);

        return citaDAO.insertar(cita);
    }

    /**
     * Busca una cita por su ID
     * @param id El ID de la cita
     * @return La cita encontrada o null si no existe
     */
    public Cita buscarCita(int id) {
        if (id <= 0) {
            return null;
        }
        return citaDAO.buscarPorId(id);
    }

    /**
     * Obtiene la lista de todas las citas
     * @return Lista de citas
     */
    public List<Cita> listarCitas() {
        return citaDAO.listarTodas();
    }

    /**
     * Obtiene las citas de un médico específico
     * @param cmp El CMP del médico
     * @return Lista de citas del médico
     */
    public List<Cita> listarCitasPorMedico(String cmp) {
        if (!Validador.noEstaVacio(cmp)) {
            return null;
        }
        return citaDAO.listarPorMedico(cmp);
    }

    /**
     * Obtiene las citas de una fecha específica
     * @param fecha La fecha a consultar
     * @return Lista de citas de la fecha
     */
    public List<Cita> listarCitasPorFecha(Date fecha) {
        if (fecha == null) {
            return null;
        }
        return citaDAO.listarPorFecha(new java.sql.Date(fecha.getTime()));
    }

    /**
     * Actualiza el estado de una cita
     * @param id El ID de la cita
     * @param estado El nuevo estado (PENDIENTE, ATENDIDA, CANCELADA)
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean actualizarEstadoCita(int id, String estado) {
        if (id <= 0) {
            return false;
        }

        if (!Validador.noEstaVacio(estado)) {
            return false;
        }

        // Validar que el estado sea válido
        if (!estado.equals("PENDIENTE") && 
            !estado.equals("ATENDIDA") && 
            !estado.equals("CANCELADA")) {
            return false;
        }

        return citaDAO.actualizarEstado(id, estado);
    }

    /**
     * Atiende una cita y registra el diagnóstico
     * @param id El ID de la cita
     * @param diagnostico El diagnóstico de la consulta
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean atenderCita(int id, String diagnostico) {
        if (id <= 0) {
            return false;
        }

        // Verificar que la cita existe
        Cita cita = citaDAO.buscarPorId(id);
        if (cita == null) {
            return false; // La cita no existe
        }

        // Verificar que la cita esté pendiente
        if (!cita.getEstado().equals("PENDIENTE")) {
            return false; // La cita ya fue atendida o cancelada
        }

        return citaDAO.actualizarDiagnostico(id, diagnostico);
    }

    /**
     * Cancela una cita
     * @param id El ID de la cita
     * @return true si se canceló correctamente, false en caso contrario
     */
    public boolean cancelarCita(int id) {
        if (id <= 0) {
            return false;
        }

        // Verificar que la cita existe
        Cita cita = citaDAO.buscarPorId(id);
        if (cita == null) {
            return false; // La cita no existe
        }

        // Solo se pueden cancelar citas pendientes
        if (!cita.getEstado().equals("PENDIENTE")) {
            return false; // La cita ya fue atendida o cancelada
        }

        return citaDAO.actualizarEstado(id, "CANCELADA");
    }

    /**
     * Elimina una cita
     * @param id El ID de la cita
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean eliminarCita(int id) {
        if (id <= 0) {
            return false;
        }
        return citaDAO.eliminar(id);
    }
}

