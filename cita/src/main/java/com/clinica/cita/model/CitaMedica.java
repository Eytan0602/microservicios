package com.clinica.cita.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "cita_medica")
public class CitaMedica {

    @Id
    @Column(name = "id_cita", length = 30)
    private String idCita;

    @Column(nullable = false, length = 150)
    private String paciente;

    @Column(name = "id_paciente", length = 30)
    private String idPaciente;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "horario")
    private LocalDateTime horario;

    @Column(name = "num_atencion", length = 30)
    private String numAtencion;

    @Column(name = "id_medico", length = 36)
    private String idMedico;

    @Column(name = "id_hc", length = 30)
    private String idHC;

    @Column(length = 30)
    private String estado;

    public CitaMedica() {}

    public CitaMedica(String idCita, String paciente, LocalDateTime horario, String numAtencion, String idMedico, String idHC, String estado) {
        this.idCita = idCita;
        this.paciente = paciente;
        this.horario = horario;
        this.numAtencion = numAtencion;
        this.idMedico = idMedico;
        this.idHC = idHC;
        this.estado = estado;
    }

    public String getIdCita() { return idCita; }
    public void setIdCita(String idCita) { this.idCita = idCita; }

    public String getPaciente() { return paciente; }
    public void setPaciente(String paciente) { this.paciente = paciente; }

    public String getIdPaciente() { return idPaciente; }
    public void setIdPaciente(String idPaciente) { this.idPaciente = idPaciente; }

    public LocalDateTime getHorario() { return horario; }
    public void setHorario(LocalDateTime horario) { this.horario = horario; }

    public String getNumAtencion() { return numAtencion; }
    public void setNumAtencion(String numAtencion) { this.numAtencion = numAtencion; }

    public String getIdMedico() { return idMedico; }
    public void setIdMedico(String idMedico) { this.idMedico = idMedico; }

    public String getIdHC() { return idHC; }
    public void setIdHC(String idHC) { this.idHC = idHC; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
