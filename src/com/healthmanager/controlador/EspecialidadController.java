package com.healthmanager.controlador;

import com.healthmanager.modelo.Especialidad;
import com.healthmanager.dao.EspecialidadDAO;
import com.healthmanager.util.Validador;
import java.util.List;

/**
 * Controlador para gestionar las operaciones de Especialidades
 * Actúa como intermediario entre la Vista y el DAO
 */
public class EspecialidadController {
    private EspecialidadDAO especialidadDAO;

    public EspecialidadController() {
        this.especialidadDAO = new EspecialidadDAO();
    }

    /**
     * Registra una nueva especialidad
     * @param especialidad La especialidad a registrar
     * @return true si se registró correctamente, false en caso contrario
     */
    public boolean registrarEspecialidad(Especialidad especialidad) {
        if (especialidad == null || !Validador.noEstaVacio(especialidad.getNombre())) {
            return false;
        }

        // Verificar si la especialidad ya existe
        Especialidad existente = especialidadDAO.buscarPorNombre(especialidad.getNombre());
        if (existente != null) {
            return false; // Ya existe una especialidad con ese nombre
        }

        return especialidadDAO.insertar(especialidad);
    }

    /**
     * Busca una especialidad por su ID
     * @param id El ID de la especialidad
     * @return La especialidad encontrada o null si no existe
     */
    public Especialidad buscarEspecialidad(int id) {
        if (id <= 0) {
            return null;
        }
        return especialidadDAO.buscarPorId(id);
    }

    /**
     * Busca una especialidad por su nombre
     * @param nombre El nombre de la especialidad
     * @return La especialidad encontrada o null si no existe
     */
    public Especialidad buscarEspecialidadPorNombre(String nombre) {
        if (!Validador.noEstaVacio(nombre)) {
            return null;
        }
        return especialidadDAO.buscarPorNombre(nombre);
    }

    /**
     * Obtiene la lista de todas las especialidades
     * @return Lista de especialidades
     */
    public List<Especialidad> listarEspecialidades() {
        return especialidadDAO.listarTodas();
    }

    /**
     * Actualiza los datos de una especialidad
     * @param especialidad La especialidad con los datos actualizados
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean actualizarEspecialidad(Especialidad especialidad) {
        if (especialidad == null || especialidad.getId() <= 0) {
            return false;
        }

        // Verificar que la especialidad existe
        Especialidad existente = especialidadDAO.buscarPorId(especialidad.getId());
        if (existente == null) {
            return false; // La especialidad no existe
        }

        return especialidadDAO.actualizar(especialidad);
    }

    /**
     * Elimina una especialidad por su ID
     * @param id El ID de la especialidad a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    public boolean eliminarEspecialidad(int id) {
        if (id <= 0) {
            return false;
        }

        // Verificar que la especialidad existe
        Especialidad existente = especialidadDAO.buscarPorId(id);
        if (existente == null) {
            return false; // La especialidad no existe
        }

        return especialidadDAO.eliminar(id);
    }
}

