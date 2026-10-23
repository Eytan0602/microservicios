package com.clinica.analisis.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "analisis_detalle")
public class AnalisisDetalle {

    @Id
    @Column(name = "id_ad", length = 30)
    private String idAD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_analisis", nullable = false)
    @JsonBackReference
    private AnalisisClinico analisisClinico;

    @Column(name = "id_ta", length = 30)
    private String idTA;

    @Column(length = 150)
    private String nombre;

    @Column(length = 255)
    private String observaciones;

    public AnalisisDetalle() {}

    public AnalisisDetalle(String idAD, String idTA, String nombre, String observaciones) {
        this.idAD = idAD;
        this.idTA = idTA;
        this.nombre = nombre;
        this.observaciones = observaciones;
    }

    public String getIdAD() { return idAD; }
    public void setIdAD(String idAD) { this.idAD = idAD; }

    public AnalisisClinico getAnalisisClinico() { return analisisClinico; }
    public void setAnalisisClinico(AnalisisClinico analisisClinico) { this.analisisClinico = analisisClinico; }

    public String getIdTA() { return idTA; }
    public void setIdTA(String idTA) { this.idTA = idTA; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}
