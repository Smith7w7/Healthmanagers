package com.healthmanager.modelo;

public class Tratamiento {
    private int id;
    private int citaId;
    private String medicamento;
    private String dosis;
    private int duracionDias;
    private String indicaciones;

    public Tratamiento() {
    }

    public Tratamiento(int id, int citaId, String medicamento, String dosis, 
                      int duracionDias, String indicaciones) {
        this.id = id;
        this.citaId = citaId;
        this.medicamento = medicamento;
        this.dosis = dosis;
        this.duracionDias = duracionDias;
        this.indicaciones = indicaciones;
    }

    public void registrar() {
        System.out.println("Tratamiento registrado: " + medicamento + " - " + dosis);
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getCitaId() { return citaId; }
    public void setCitaId(int citaId) { this.citaId = citaId; }
    public String getMedicamento() { return medicamento; }
    public void setMedicamento(String medicamento) { this.medicamento = medicamento; }
    public String getDosis() { return dosis; }
    public void setDosis(String dosis) { this.dosis = dosis; }
    public int getDuracionDias() { return duracionDias; }
    public void setDuracionDias(int duracionDias) { this.duracionDias = duracionDias; }
    public String getIndicaciones() { return indicaciones; }
    public void setIndicaciones(String indicaciones) { this.indicaciones = indicaciones; }
}