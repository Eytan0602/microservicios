package com.MicroServicio.Atencion;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class AtencionMedica {
    @Id
    private String idAtencion;
    private String idCita;
    private String idPaciente;
    private String idMedico;
    private LocalDate fecha;
    private String diagnostico;
    private String tratamiento;
    private String observaciones;

    public AtencionMedica() {
    }

    public String getIdAtencion() { return idAtencion; }
    public void setIdAtencion(String idAtencion) { this.idAtencion = idAtencion; }

    public String getIdCita() { return idCita; }
    public void setIdCita(String idCita) { this.idCita = idCita; }

    public String getIdPaciente() { return idPaciente; }
    public void setIdPaciente(String idPaciente) { this.idPaciente = idPaciente; }

    public String getIdMedico() { return idMedico; }
    public void setIdMedico(String idMedico) { this.idMedico = idMedico; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }

    public String getTratamiento() { return tratamiento; }
    public void setTratamiento(String tratamiento) { this.tratamiento = tratamiento; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}