package com.clinica.atencion.dto;

public class LineaAnalisisDto {
    private String idLA;
    private String idTA;
    private String nombre;
    private String observaciones;

    public LineaAnalisisDto() {}

    public LineaAnalisisDto(String idLA, String idTA, String nombre, String observaciones) {
        this.idLA = idLA;
        this.idTA = idTA;
        this.nombre = nombre;
        this.observaciones = observaciones;
    }

    public String getIdLA() { return idLA; }
    public void setIdLA(String idLA) { this.idLA = idLA; }

    public String getIdTA() { return idTA; }
    public void setIdTA(String idTA) { this.idTA = idTA; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}
