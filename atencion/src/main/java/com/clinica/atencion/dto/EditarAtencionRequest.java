package com.clinica.atencion.dto;

public class EditarAtencionRequest {

    private String diagnostico;
    private String tratamiento;
    private String observaciones;
    private String estado;

    public EditarAtencionRequest() {}

    public EditarAtencionRequest(String diagnostico, String tratamiento, String observaciones, String estado) {
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.observaciones = observaciones;
        this.estado = estado;
    }

    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }

    public String getTratamiento() { return tratamiento; }
    public void setTratamiento(String tratamiento) { this.tratamiento = tratamiento; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
