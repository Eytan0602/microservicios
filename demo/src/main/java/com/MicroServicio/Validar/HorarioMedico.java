package com.MicroServicio.Validar;

import java.time.LocalTime;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class HorarioMedico {
    @Id
    private String idHorario;
    private Long idMedico;
    private String dias;
    private LocalTime horaInicio;
    private LocalTime horaFin;

    public HorarioMedico() {
    }

    public String getIdHorario() { return idHorario; }
    public void setIdHorario(String idHorario) { this.idHorario = idHorario; }

    public Long getIdMedico() { return idMedico; }
    public void setIdMedico(Long idMedico) { this.idMedico = idMedico; }

    public String getDias() { return dias; }
    public void setDias(String dias) { this.dias = dias; }

    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }

    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }
}