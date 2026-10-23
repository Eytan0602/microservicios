package com.clinica.atencion.dto;

import java.util.ArrayList;
import java.util.List;

public class RegistroAtencionRequest {

    private String idAtencion;
    private String idCita;
    private String idMedico;
    private String idHC;
    private String paciente;
    private String diagnostico;
    private String tratamiento;
    private String observaciones;

    private boolean requiereReceta;
    private String indicacionesReceta;
    private List<LineaRecetaDto> itemsReceta = new ArrayList<>();

    private boolean requiereAnalisis;
    private String observacionesAnalisis;
    private List<LineaAnalisisDto> itemsAnalisis = new ArrayList<>();

    public RegistroAtencionRequest() {}

    public String getIdAtencion() { return idAtencion; }
    public void setIdAtencion(String idAtencion) { this.idAtencion = idAtencion; }

    public String getIdCita() { return idCita; }
    public void setIdCita(String idCita) { this.idCita = idCita; }

    public String getIdMedico() { return idMedico; }
    public void setIdMedico(String idMedico) { this.idMedico = idMedico; }

    public String getIdHC() { return idHC; }
    public void setIdHC(String idHC) { this.idHC = idHC; }

    public String getPaciente() { return paciente; }
    public void setPaciente(String paciente) { this.paciente = paciente; }

    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }

    public String getTratamiento() { return tratamiento; }
    public void setTratamiento(String tratamiento) { this.tratamiento = tratamiento; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public boolean isRequiereReceta() { return requiereReceta; }
    public void setRequiereReceta(boolean requiereReceta) { this.requiereReceta = requiereReceta; }

    public String getIndicacionesReceta() { return indicacionesReceta; }
    public void setIndicacionesReceta(String indicacionesReceta) { this.indicacionesReceta = indicacionesReceta; }

    public List<LineaRecetaDto> getItemsReceta() { return itemsReceta; }
    public void setItemsReceta(List<LineaRecetaDto> itemsReceta) { this.itemsReceta = itemsReceta; }

    public boolean isRequiereAnalisis() { return requiereAnalisis; }
    public void setRequiereAnalisis(boolean requiereAnalisis) { this.requiereAnalisis = requiereAnalisis; }

    public String getObservacionesAnalisis() { return observacionesAnalisis; }
    public void setObservacionesAnalisis(String observacionesAnalisis) { this.observacionesAnalisis = observacionesAnalisis; }

    public List<LineaAnalisisDto> getItemsAnalisis() { return itemsAnalisis; }
    public void setItemsAnalisis(List<LineaAnalisisDto> itemsAnalisis) { this.itemsAnalisis = itemsAnalisis; }
}
