package com.healthmanager.modelo;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class Cita {
    private int id;
    private Paciente paciente;
    private Medico medico;
    private Date fecha;
    private String hora;
    private String motivo;
    private String diagnostico;
    private String estado; // PENDIENTE, ATENDIDA, CANCELADA
    private List<Tratamiento> tratamientos;

    public Cita() {
        this.tratamientos = new ArrayList<>();
    }

    public Cita(int id, Paciente paciente, Medico medico, Date fecha, String hora, 
                String motivo, String estado) {
        this.id = id;
        this.paciente = paciente;
        this.medico = medico;
        this.fecha = fecha;
        this.hora = hora;
        this.motivo = motivo;
        this.estado = estado;
        this.tratamientos = new ArrayList<>();
    }

    public void agendar() {
        this.estado = "PENDIENTE";
        System.out.println("Cita agendada para: " + paciente.getNombre() + 
                         " con Dr. " + medico.getNombre());
    }

    public void atender(String diagnostico) {
        this.estado = "ATENDIDA";
        this.diagnostico = diagnostico;
        System.out.println("Cita atendida. Diagnóstico registrado.");
    }

    public void cancelar() {
        this.estado = "CANCELADA";
        System.out.println("Cita cancelada.");
    }

    public void agregarTratamiento(Tratamiento tratamiento) {
        this.tratamientos.add(tratamiento);
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }
    public Medico getMedico() { return medico; }
    public void setMedico(Medico medico) { this.medico = medico; }
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public List<Tratamiento> getTratamientos() { return tratamientos; }
}