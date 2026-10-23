package com.clinica.atencion.dto;

import com.clinica.atencion.model.AtencionMedica;

public class AtencionMedicaCompletaDto {

    private AtencionMedica atencion;
    private Object medico;
    private Object cita;
    private Object historia;
    private Object receta;
    private Object analisis;

    public AtencionMedicaCompletaDto() {}

    public AtencionMedicaCompletaDto(AtencionMedica atencion, Object medico, Object cita, Object historia, Object receta, Object analisis) {
        this.atencion = atencion;
        this.medico = medico;
        this.cita = cita;
        this.historia = historia;
        this.receta = receta;
        this.analisis = analisis;
    }

    public AtencionMedica getAtencion() { return atencion; }
    public void setAtencion(AtencionMedica atencion) { this.atencion = atencion; }

    public Object getMedico() { return medico; }
    public void setMedico(Object medico) { this.medico = medico; }

    public Object getCita() { return cita; }
    public void setCita(Object cita) { this.cita = cita; }

    public Object getHistoria() { return historia; }
    public void setHistoria(Object historia) { this.historia = historia; }

    public Object getReceta() { return receta; }
    public void setReceta(Object receta) { this.receta = receta; }

    public Object getAnalisis() { return analisis; }
    public void setAnalisis(Object analisis) { this.analisis = analisis; }
}
