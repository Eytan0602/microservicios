package com.clinica.atencion.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "atencion_medica")
public class AtencionMedica {

    @Id
    @Column(name = "id_atencion", length = 50)
    private String idAtencion;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "fecha")
    private LocalDateTime fecha;

    @Column(name = "id_cita", length = 50)
    private String idCita;

    @Column(name = "id_medico", length = 50)
    private String idMedico;

    @Column(name = "id_hc", length = 50)
    private String idHC;

    @Column(length = 150)
    private String paciente;

    @Column(length = 300)
    private String diagnostico;

    @Column(length = 300)
    private String tratamiento;

    @Column(length = 500)
    private String observaciones;

    @Column(length = 30)
    private String estado;

    @Column(name = "id_receta", length = 30)
    private String idReceta;

    @Column(name = "id_analisis", length = 30)
    private String idAnalisis;

    public AtencionMedica() {}

    public AtencionMedica(String idAtencion, LocalDateTime fecha, String idCita, String idMedico, String idHC, String paciente, String diagnostico, String tratamiento, String observaciones, String estado) {
        this.idAtencion = idAtencion;
        this.fecha = fecha;
        this.idCita = idCita;
        this.idMedico = idMedico;
        this.idHC = idHC;
        this.paciente = paciente;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.observaciones = observaciones;
        this.estado = estado;
    }

    public String getIdAtencion() { return idAtencion; }
    public void setIdAtencion(String idAtencion) { this.idAtencion = idAtencion; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

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

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getIdReceta() { return idReceta; }
    public void setIdReceta(String idReceta) { this.idReceta = idReceta; }
    
    public String getIdAnalisis() { return idAnalisis; }
    public void setIdAnalisis(String idAnalisis) { this.idAnalisis = idAnalisis; }
}
