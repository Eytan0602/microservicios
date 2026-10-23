package com.clinica.atencion.dto;

public class LineaRecetaDto {
    private String idLR;
    private String idMedicamento;
    private String nombre;
    private String indicaciones;
    private int cantidad;

    public LineaRecetaDto() {}

    public LineaRecetaDto(String idLR, String idMedicamento, String nombre, String indicaciones, int cantidad) {
        this.idLR = idLR;
        this.idMedicamento = idMedicamento;
        this.nombre = nombre;
        this.indicaciones = indicaciones;
        this.cantidad = cantidad;
    }

    public String getIdLR() { return idLR; }
    public void setIdLR(String idLR) { this.idLR = idLR; }

    public String getIdMedicamento() { return idMedicamento; }
    public void setIdMedicamento(String idMedicamento) { this.idMedicamento = idMedicamento; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getIndicaciones() { return indicaciones; }
    public void setIndicaciones(String indicaciones) { this.indicaciones = indicaciones; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
}
