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
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, cita.getPaciente().getDni());
            stmt.setString(2, cita.getMedico().getCmp());
            stmt.setDate(3, new java.sql.Date(cita.getFecha().getTime()));
            stmt.setString(4, cita.getHora());
            stmt.setString(5, cita.getMotivo());
            stmt.setString(6, cita.getEstado());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar: " + e.getMessage());
            return false;
        }
    }

    // READ - Por ID
    public Cita buscarPorId(int id) {
        String sql = "SELECT c.*, p.nombre, m.nombre FROM citas c " +
                     "JOIN pacientes p ON c.paciente_dni = p.dni " +
                     "JOIN medicos m ON c.medico_cmp = m.cmp WHERE c.id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Implementar retorno de Cita
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar: " + e.getMessage());
        }
        return null;
    }

    // READ - Listar todas
    public List<Cita> listarTodas() {
        List<Cita> citas = new ArrayList<>();
        String sql = "SELECT * FROM citas ORDER BY fecha, hora";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                // Implementar creación de Cita
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