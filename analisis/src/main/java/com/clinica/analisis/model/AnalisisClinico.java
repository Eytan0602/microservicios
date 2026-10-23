package com.clinica.analisis.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "analisis_clinico")
public class AnalisisClinico {

    @Id
    @Column(name = "id_analisis", length = 30)
    private String idAnalisis;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(length = 500)
    private String observaciones;

    @Column(length = 30)
    private String estado;

    @Column(name = "id_atencion", length = 30)
    private String idAtencion;

    @OneToMany(mappedBy = "analisisClinico", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<AnalisisDetalle> detalles = new ArrayList<>();

    public AnalisisClinico() {}

    public AnalisisClinico(String idAnalisis, LocalDate fecha, String observaciones, String estado, String idAtencion) {
        this.idAnalisis = idAnalisis;
        this.fecha = fecha;
        this.observaciones = observaciones;
        this.estado = estado;
        this.idAtencion = idAtencion;
    }

    public void agregarDetalle(AnalisisDetalle detalle) {
        detalles.add(detalle);
        detalle.setAnalisisClinico(this);
    }

    public String getIdAnalisis() { return idAnalisis; }
    public void setIdAnalisis(String idAnalisis) { this.idAnalisis = idAnalisis; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getIdAtencion() { return idAtencion; }
    public void setIdAtencion(String idAtencion) { this.idAtencion = idAtencion; }

    public List<AnalisisDetalle> getDetalles() { return detalles; }
    public void setDetalles(List<AnalisisDetalle> detalles) { this.detalles = detalles; }
}
