package com.healthmanager.modelo;

import java.util.Date;

public class Medico extends Persona {
    private String cmp; // Código Médico Particular
    private String especialidad;
    private String horario;

    public Medico() {
        super();
    }

    public Medico(String cmp, String dni, String nombre, String apellido, 
                 Date fechaNacimiento, String telefono, String especialidad, String horario) {
        super(dni, nombre, apellido, fechaNacimiento, telefono);
        this.cmp = cmp;
        this.especialidad = especialidad;
        this.horario = horario;
    }

    @Override
    public void mostrarInfo() {
        System.out.println("=== INFORMACIÓN DEL MÉDICO ===");
        System.out.println("CMP: " + cmp);
        System.out.println("DNI: " + dni);
        System.out.println("Nombre: " + nombre + " " + apellido);
        System.out.println("Especialidad: " + especialidad);
        System.out.println("Teléfono: " + telefono);
        System.out.println("Horario: " + horario);
        System.out.println("==============================");
    }

    // Getters y Setters
    public String getCmp() { return cmp; }
    public void setCmp(String cmp) { this.cmp = cmp; }
    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
    public String getHorario() { return horario; }
    public void setHorario(String horario) { this.horario = horario; }
}