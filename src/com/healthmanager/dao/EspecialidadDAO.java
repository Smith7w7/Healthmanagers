package com.healthmanager.dao;

import com.healthmanager.modelo.Especialidad;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EspecialidadDAO {
    private Connection conexion;

    public EspecialidadDAO() {
        this.conexion = ConexionBD.getInstancia().getConexion();
    }

    // CREATE
    public boolean insertar(Especialidad especialidad) {
        String sql = "INSERT INTO especialidades (nombre, descripcion) VALUES (?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, especialidad.getNombre());
            stmt.setString(2, especialidad.getDescripcion());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar: " + e.getMessage());
            return false;
        }
    }

    // READ - Por ID
    public Especialidad buscarPorId(int id) {
        String sql = "SELECT * FROM especialidades WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Especialidad(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("descripcion")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar: " + e.getMessage());
        }
        return null;
    }

    // READ - Por nombre
    public Especialidad buscarPorNombre(String nombre) {
        String sql = "SELECT * FROM especialidades WHERE nombre = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Especialidad(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("descripcion")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar: " + e.getMessage());
        }
        return null;
    }

    // READ - Listar todas
    public List<Especialidad> listarTodas() {
        List<Especialidad> especialidades = new ArrayList<>();
        String sql = "SELECT * FROM especialidades ORDER BY nombre";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Especialidad e = new Especialidad(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("descripcion")
                );
                especialidades.add(e);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar: " + e.getMessage());
        }
        return especialidades;
    }

    // UPDATE
    public boolean actualizar(Especialidad especialidad) {
        String sql = "UPDATE especialidades SET nombre = ?, descripcion = ? WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, especialidad.getNombre());
            stmt.setString(2, especialidad.getDescripcion());
            stmt.setInt(3, especialidad.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar: " + e.getMessage());
            return false;
        }
    }

    // DELETE
    public boolean eliminar(int id) {
        String sql = "DELETE FROM especialidades WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar: " + e.getMessage());
            return false;
        }
    }
}

