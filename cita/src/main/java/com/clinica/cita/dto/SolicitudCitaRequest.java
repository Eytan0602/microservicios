package com.clinica.cita.dto;

import java.time.LocalDateTime;

public class SolicitudCitaRequest {
    private String idPaciente;
    private String idMedico;
    private LocalDateTime horario;

    public String getIdPaciente() { return idPaciente; }
    public void setIdPaciente(String idPaciente) { this.idPaciente = idPaciente; }

    public String getIdMedico() { return idMedico; }
    public void setIdMedico(String idMedico) { this.idMedico = idMedico; }

    public LocalDateTime getHorario() { return horario; }
    public void setHorario(LocalDateTime horario) { this.horario = horario; }
}
