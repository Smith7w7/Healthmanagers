package com.healthmanager.dao;

import com.healthmanager.modelo.Cita;
import com.healthmanager.modelo.Paciente;
import com.healthmanager.modelo.Medico;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CitaDAO {
    private Connection conexion;

    public CitaDAO() {
        this.conexion = ConexionBD.getInstancia().getConexion();
    }

    // CREATE
    public boolean insertar(Cita cita) {
        String sql = "INSERT INTO citas (paciente_dni, medico_cmp, fecha, hora, motivo, estado) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, cita.getPaciente().getDni());
            stmt.setString(2, cita.getMedico().getCmp());
            stmt.setDate(3, new java.sql.Date(cita.getFecha().getTime()));
            stmt.setString(4, cita.getHora());
            stmt.setString(5, cita.getMotivo());
            stmt.setString(6, cita.getEstado());
            
            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                // Obtener el ID generado
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    cita.setId(rs.getInt(1));
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error al insertar: " + e.getMessage());
            return false;
        }
    }

    // CREATE - Retorna el ID generado
    public int insertarYRetornarId(Cita cita) {
        String sql = "INSERT INTO citas (paciente_dni, medico_cmp, fecha, hora, motivo, estado) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, cita.getPaciente().getDni());
            stmt.setString(2, cita.getMedico().getCmp());
            stmt.setDate(3, new java.sql.Date(cita.getFecha().getTime()));
            stmt.setString(4, cita.getHora());
            stmt.setString(5, cita.getMotivo());
            stmt.setString(6, cita.getEstado());
            
            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int idGenerado = rs.getInt(1);
                    cita.setId(idGenerado);
                    return idGenerado;
                }
            }
            return -1;
        } catch (SQLException e) {
            System.err.println("Error al insertar: " + e.getMessage());
            return -1;
        }
    }

    // READ - Por ID
    public Cita buscarPorId(int id) {
        String sql = "SELECT c.*, p.dni as p_dni, p.nombre as p_nombre, p.apellido as p_apellido, " +
                     "p.fecha_nacimiento as p_fecha_nac, p.telefono as p_telefono, p.genero, " +
                     "p.direccion, p.grupo_sanguineo, " +
                     "m.cmp as m_cmp, m.dni as m_dni, m.nombre as m_nombre, m.apellido as m_apellido, " +
                     "m.fecha_nacimiento as m_fecha_nac, m.telefono as m_telefono, e.nombre as especialidad " +
                     "FROM citas c " +
                     "JOIN pacientes p ON c.paciente_dni = p.dni " +
                     "JOIN medicos m ON c.medico_cmp = m.cmp " +
                     "JOIN especialidades e ON m.especialidad_id = e.id " +
                     "WHERE c.id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Construir Paciente
                Paciente paciente = new Paciente(
                    rs.getString("p_dni"),
                    rs.getString("p_nombre"),
                    rs.getString("p_apellido"),
                    rs.getDate("p_fecha_nac"),
                    rs.getString("p_telefono"),
                    rs.getString("genero"),
                    rs.getString("direccion"),
                    rs.getString("grupo_sanguineo")
                );
                
                // Construir Medico
                Medico medico = new Medico(
                    rs.getString("m_cmp"),
                    rs.getString("m_dni"),
                    rs.getString("m_nombre"),
                    rs.getString("m_apellido"),
                    rs.getDate("m_fecha_nac"),
                    rs.getString("m_telefono"),
                    rs.getString("especialidad"),
                    "8am-6pm"
                );
                
                // Construir Cita
                Cita cita = new Cita(
                    rs.getInt("id"),
                    paciente,
                    medico,
                    rs.getDate("fecha"),
                    rs.getString("hora"),
                    rs.getString("motivo"),
                    rs.getString("estado")
                );
                cita.setDiagnostico(rs.getString("diagnostico"));
                
                return cita;
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar: " + e.getMessage());
        }
        return null;
    }

    // READ - Listar todas
    public List<Cita> listarTodas() {
        List<Cita> citas = new ArrayList<>();
        String sql = "SELECT c.*, p.dni as p_dni, p.nombre as p_nombre, p.apellido as p_apellido, " +
                     "p.fecha_nacimiento as p_fecha_nac, p.telefono as p_telefono, p.genero, " +
                     "p.direccion, p.grupo_sanguineo, " +
                     "m.cmp as m_cmp, m.dni as m_dni, m.nombre as m_nombre, m.apellido as m_apellido, " +
                     "m.fecha_nacimiento as m_fecha_nac, m.telefono as m_telefono, e.nombre as especialidad " +
                     "FROM citas c " +
                     "JOIN pacientes p ON c.paciente_dni = p.dni " +
                     "JOIN medicos m ON c.medico_cmp = m.cmp " +
                     "JOIN especialidades e ON m.especialidad_id = e.id " +
                     "ORDER BY c.fecha, c.hora";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                // Construir Paciente
                Paciente paciente = new Paciente(
                    rs.getString("p_dni"),
                    rs.getString("p_nombre"),
                    rs.getString("p_apellido"),
                    rs.getDate("p_fecha_nac"),
                    rs.getString("p_telefono"),
                    rs.getString("genero"),
                    rs.getString("direccion"),
                    rs.getString("grupo_sanguineo")
                );
                
                // Construir Medico
                Medico medico = new Medico(
                    rs.getString("m_cmp"),
                    rs.getString("m_dni"),
                    rs.getString("m_nombre"),
                    rs.getString("m_apellido"),
                    rs.getDate("m_fecha_nac"),
                    rs.getString("m_telefono"),
                    rs.getString("especialidad"),
                    "8am-6pm"
                );
                
                // Construir Cita
                Cita cita = new Cita(
                    rs.getInt("id"),
                    paciente,
                    medico,
                    rs.getDate("fecha"),
                    rs.getString("hora"),
                    rs.getString("motivo"),
                    rs.getString("estado")
                );
                cita.setDiagnostico(rs.getString("diagnostico"));
                
                citas.add(cita);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar: " + e.getMessage());
        }
        return citas;
    }

    // UPDATE - Estado
    public boolean actualizarEstado(int id, String estado) {
        String sql = "UPDATE citas SET estado = ? WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, estado);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar: " + e.getMessage());
            return false;
        }
    }

    // UPDATE - Actualizar diagnóstico
    public boolean actualizarDiagnostico(int id, String diagnostico) {
        String sql = "UPDATE citas SET diagnostico = ?, estado = 'ATENDIDA' WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, diagnostico);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar diagnóstico: " + e.getMessage());
            return false;
        }
    }

    // READ - Listar citas por médico
    public List<Cita> listarPorMedico(String cmp) {
        List<Cita> citas = new ArrayList<>();
        String sql = "SELECT c.*, p.dni as p_dni, p.nombre as p_nombre, p.apellido as p_apellido, " +
                     "p.fecha_nacimiento as p_fecha_nac, p.telefono as p_telefono, p.genero, " +
                     "p.direccion, p.grupo_sanguineo, " +
                     "m.cmp as m_cmp, m.dni as m_dni, m.nombre as m_nombre, m.apellido as m_apellido, " +
                     "m.fecha_nacimiento as m_fecha_nac, m.telefono as m_telefono, e.nombre as especialidad " +
                     "FROM citas c " +
                     "JOIN pacientes p ON c.paciente_dni = p.dni " +
                     "JOIN medicos m ON c.medico_cmp = m.cmp " +
                     "JOIN especialidades e ON m.especialidad_id = e.id " +
                     "WHERE m.cmp = ? ORDER BY c.fecha, c.hora";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, cmp);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Paciente paciente = new Paciente(
                    rs.getString("p_dni"),
                    rs.getString("p_nombre"),
                    rs.getString("p_apellido"),
                    rs.getDate("p_fecha_nac"),
                    rs.getString("p_telefono"),
                    rs.getString("genero"),
                    rs.getString("direccion"),
                    rs.getString("grupo_sanguineo")
                );
                
                Medico medico = new Medico(
                    rs.getString("m_cmp"),
                    rs.getString("m_dni"),
                    rs.getString("m_nombre"),
                    rs.getString("m_apellido"),
                    rs.getDate("m_fecha_nac"),
                    rs.getString("m_telefono"),
                    rs.getString("especialidad"),
                    "8am-6pm"
                );
                
                Cita cita = new Cita(
                    rs.getInt("id"),
                    paciente,
                    medico,
                    rs.getDate("fecha"),
                    rs.getString("hora"),
                    rs.getString("motivo"),
                    rs.getString("estado")
                );
                cita.setDiagnostico(rs.getString("diagnostico"));
                
                citas.add(cita);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar por médico: " + e.getMessage());
        }
        return citas;
    }

    // READ - Listar citas por fecha
    public List<Cita> listarPorFecha(java.sql.Date fecha) {
        List<Cita> citas = new ArrayList<>();
        String sql = "SELECT c.*, p.dni as p_dni, p.nombre as p_nombre, p.apellido as p_apellido, " +
                     "p.fecha_nacimiento as p_fecha_nac, p.telefono as p_telefono, p.genero, " +
                     "p.direccion, p.grupo_sanguineo, " +
                     "m.cmp as m_cmp, m.dni as m_dni, m.nombre as m_nombre, m.apellido as m_apellido, " +
                     "m.fecha_nacimiento as m_fecha_nac, m.telefono as m_telefono, e.nombre as especialidad " +
                     "FROM citas c " +
                     "JOIN pacientes p ON c.paciente_dni = p.dni " +
                     "JOIN medicos m ON c.medico_cmp = m.cmp " +
                     "JOIN especialidades e ON m.especialidad_id = e.id " +
                     "WHERE c.fecha = ? ORDER BY c.hora";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setDate(1, fecha);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Paciente paciente = new Paciente(
                    rs.getString("p_dni"),
                    rs.getString("p_nombre"),
                    rs.getString("p_apellido"),
                    rs.getDate("p_fecha_nac"),
                    rs.getString("p_telefono"),
                    rs.getString("genero"),
                    rs.getString("direccion"),
                    rs.getString("grupo_sanguineo")
                );
                
                Medico medico = new Medico(
                    rs.getString("m_cmp"),
                    rs.getString("m_dni"),
                    rs.getString("m_nombre"),
                    rs.getString("m_apellido"),
                    rs.getDate("m_fecha_nac"),
                    rs.getString("m_telefono"),
                    rs.getString("especialidad"),
                    "8am-6pm"
                );
                
                Cita cita = new Cita(
                    rs.getInt("id"),
                    paciente,
                    medico,
                    rs.getDate("fecha"),
                    rs.getString("hora"),
                    rs.getString("motivo"),
                    rs.getString("estado")
                );
                cita.setDiagnostico(rs.getString("diagnostico"));
                
                citas.add(cita);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar por fecha: " + e.getMessage());
        }
        return citas;
    }

    // DELETE
    public boolean eliminar(int id) {
        String sql = "DELETE FROM citas WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar: " + e.getMessage());
            return false;
        }
    }
}