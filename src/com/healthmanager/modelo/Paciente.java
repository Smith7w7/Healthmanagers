package com.healthmanager.modelo;

import java.util.Date;

public class Paciente extends Persona {
    private String genero;
    private String direccion;
    private String grupoSanguineo;

    public Paciente() {
        super();
    }

    public Paciente(String dni, String nombre, String apellido, Date fechaNacimiento, 
                   String telefono, String genero, String direccion, String grupoSanguineo) {
        super(dni, nombre, apellido, fechaNacimiento, telefono);
        this.genero = genero;
        this.direccion = direccion;
        this.grupoSanguineo = grupoSanguineo;
    }

    @Override
    public void mostrarInfo() {
        System.out.println("=== INFORMACIÓN DEL PACIENTE ===");
        System.out.println("DNI: " + dni);
        System.out.println("Nombre: " + nombre + " " + apellido);
        System.out.println("Edad: " + calcularEdad() + " años");
        System.out.println("Género: " + genero);
        System.out.println("Teléfono: " + telefono);
        System.out.println("Dirección: " + direccion);
        System.out.println("Grupo Sanguíneo: " + grupoSanguineo);
        System.out.println("================================");
    }

    // Getters y Setters
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public String getGrupoSanguineo() { return grupoSanguineo; }
    public void setGrupoSanguineo(String grupoSanguineo) { this.grupoSanguineo = grupoSanguineo; }
}