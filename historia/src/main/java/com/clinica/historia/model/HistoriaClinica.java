package com.clinica.historia.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "historia_clinica")
public class HistoriaClinica {

    @Id
    @Column(name = "id_hc", length = 30)
    private String idHC;

    @Column(nullable = false, length = 150)
    private String paciente;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(length = 30)
    private String estado;

    @Column(length = 500)
    private String antecedentes;

    public HistoriaClinica() {}

    public HistoriaClinica(String idHC, String paciente, LocalDate fecha, String estado, String antecedentes) {
        this.idHC = idHC;
        this.paciente = paciente;
        this.fecha = fecha;
        this.estado = estado;
        this.antecedentes = antecedentes;
    }

    public String getIdHC() { return idHC; }
    public void setIdHC(String idHC) { this.idHC = idHC; }

    public String getPaciente() { return paciente; }
    public void setPaciente(String paciente) { this.paciente = paciente; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getAntecedentes() { return antecedentes; }
    public void setAntecedentes(String antecedentes) { this.antecedentes = antecedentes; }
}
