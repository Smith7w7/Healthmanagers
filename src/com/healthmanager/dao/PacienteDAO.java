package com.healthmanager.dao;

import com.healthmanager.modelo.Paciente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {
    private Connection conexion;

    public PacienteDAO() {
        this.conexion = ConexionBD.getInstancia().getConexion();
    }

    // CREATE
    public boolean insertar(Paciente paciente) {
        String sql = "INSERT INTO pacientes (dni, nombre, apellido, fecha_nacimiento, " +
                     "genero, telefono, direccion, grupo_sanguineo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, paciente.getDni());
            stmt.setString(2, paciente.getNombre());
            stmt.setString(3, paciente.getApellido());
            stmt.setDate(4, new java.sql.Date(paciente.getFechaNacimiento().getTime()));
            stmt.setString(5, paciente.getGenero());
            stmt.setString(6, paciente.getTelefono());
            stmt.setString(7, paciente.getDireccion());
            stmt.setString(8, paciente.getGrupoSanguineo());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar: " + e.getMessage());
            return false;
        }
    }

    // READ - Por DNI
    public Paciente buscarPorDni(String dni) {
        String sql = "SELECT * FROM pacientes WHERE dni = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, dni);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Paciente(
                    rs.getString("dni"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getDate("fecha_nacimiento"),
                    rs.getString("telefono"),
                    rs.getString("genero"),
                    rs.getString("direccion"),
                    rs.getString("grupo_sanguineo")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar: " + e.getMessage());
        }
        return null;
    }

    // READ - Listar todos
    public List<Paciente> listarTodos() {
        List<Paciente> pacientes = new ArrayList<>();
        String sql = "SELECT * FROM pacientes ORDER BY apellido, nombre";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Paciente p = new Paciente(
                    rs.getString("dni"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getDate("fecha_nacimiento"),
                    rs.getString("telefono"),
                    rs.getString("genero"),
                    rs.getString("direccion"),
                    rs.getString("grupo_sanguineo")
                );
                pacientes.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar: " + e.getMessage());
        }
        return pacientes;
    }

    // UPDATE
    public boolean actualizar(Paciente paciente) {
        String sql = "UPDATE pacientes SET nombre = ?, apellido = ?, " +
                     "fecha_nacimiento = ?, genero = ?, telefono = ?, " +
                     "direccion = ?, grupo_sanguineo = ? WHERE dni = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, paciente.getNombre());
            stmt.setString(2, paciente.getApellido());
            stmt.setDate(3, new java.sql.Date(paciente.getFechaNacimiento().getTime()));
            stmt.setString(4, paciente.getGenero());
            stmt.setString(5, paciente.getTelefono());
            stmt.setString(6, paciente.getDireccion());
            stmt.setString(7, paciente.getGrupoSanguineo());
            stmt.setString(8, paciente.getDni());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar: " + e.getMessage());
            return false;
        }
    }

    // DELETE
    public boolean eliminar(String dni) {
        String sql = "DELETE FROM pacientes WHERE dni = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, dni);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar: " + e.getMessage());
            return false;
        }
    }
}