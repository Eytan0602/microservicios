package com.MicroServicio.Cita;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class CitaMedica {
    @Id
    private String idCita;
    private String paciente;
    private LocalDateTime horaInicio;
    private LocalDateTime horaFin;
    private Long idMedico;
    private String estado;
    
    public CitaMedica() {
    }

    public String getIdCita() { return idCita; }
    public void setIdCita(String idCita) { this.idCita = idCita; }

    public String getPaciente() { return paciente; }
    public void setPaciente(String paciente) { this.paciente = paciente; }

    public LocalDateTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalDateTime horaInicio) { this.horaInicio = horaInicio; }

    public LocalDateTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalDateTime horaFin) { this.horaFin = horaFin; }

    public Long getIdMedico() { return idMedico; }
    public void setIdMedico(Long idMedico) { this.idMedico = idMedico; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    private String dniPaciente;
    public String getDniPaciente() {
        return dniPaciente;
    }
    public void setDniPaciente(String dniPaciente) {
        this.dniPaciente = dniPaciente;
    }
}