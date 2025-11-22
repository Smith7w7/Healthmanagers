package com.healthmanager.dao;

import com.healthmanager.modelo.Medico;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicoDAO {
    private Connection conexion;

    public MedicoDAO() {
        this.conexion = ConexionBD.getInstancia().getConexion();
    }

    // CREATE
    public boolean insertar(Medico medico) {
        String sql = "INSERT INTO medicos (cmp, dni, nombre, apellido, especialidad_id, telefono) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, medico.getCmp());
            stmt.setString(2, medico.getDni());
            stmt.setString(3, medico.getNombre());
            stmt.setString(4, medico.getApellido());
            stmt.setInt(5, 1); // Especialidad por defecto
            stmt.setString(6, medico.getTelefono());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar: " + e.getMessage());
            return false;
        }
    }

    // READ - Por CMP
    public Medico buscarPorCmp(String cmp) {
        String sql = "SELECT m.*, e.nombre AS especialidad_nombre FROM medicos m " +
                     "JOIN especialidades e ON m.especialidad_id = e.id WHERE m.cmp = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, cmp);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Medico(
                    rs.getString("cmp"),
                    rs.getString("dni"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getDate("fecha_nacimiento"),
                    rs.getString("telefono"),
                    rs.getString("especialidad_nombre"),
                    "8am-6pm"
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar: " + e.getMessage());
        }
        return null;
    }

    // READ - Listar todos
    public List<Medico> listarTodos() {
        List<Medico> medicos = new ArrayList<>();
        String sql = "SELECT m.*, e.nombre AS especialidad_nombre FROM medicos m " +
                     "JOIN especialidades e ON m.especialidad_id = e.id ORDER BY m.apellido, m.nombre";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Medico m = new Medico(
                    rs.getString("cmp"),
                    rs.getString("dni"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getDate("fecha_nacimiento"),
                    rs.getString("telefono"),
                    rs.getString("especialidad_nombre"),
                    "8am-6pm"
                );
                medicos.add(m);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar: " + e.getMessage());
        }
        return medicos;
    }

    // UPDATE
    public boolean actualizar(Medico medico) {
        String sql = "UPDATE medicos SET nombre = ?, apellido = ?, " +
                     "telefono = ? WHERE cmp = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, medico.getNombre());
            stmt.setString(2, medico.getApellido());
            stmt.setString(3, medico.getTelefono());
            stmt.setString(4, medico.getCmp());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar: " + e.getMessage());
            return false;
        }
    }

    // DELETE
    public boolean eliminar(String cmp) {
        String sql = "DELETE FROM medicos WHERE cmp = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, cmp);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar: " + e.getMessage());
            return false;
        }
    }
}