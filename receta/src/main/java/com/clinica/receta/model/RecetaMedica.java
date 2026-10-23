package com.clinica.receta.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "receta_medica")
public class RecetaMedica {

    @Id
    @Column(name = "id_receta", length = 30)
    private String idReceta;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(length = 500)
    private String indicaciones;

    @Column(name = "id_atencion", length = 30)
    private String idAtencion;

    @OneToMany(mappedBy = "recetaMedica", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<RecetaDetalle> detalles = new ArrayList<>();

    public RecetaMedica() {}

    public RecetaMedica(String idReceta, LocalDate fecha, String indicaciones, String idAtencion) {
        this.idReceta = idReceta;
        this.fecha = fecha;
        this.indicaciones = indicaciones;
        this.idAtencion = idAtencion;
    }

    public void agregarDetalle(RecetaDetalle detalle) {
        detalles.add(detalle);
        detalle.setRecetaMedica(this);
    }

    public String getIdReceta() { return idReceta; }
    public void setIdReceta(String idReceta) { this.idReceta = idReceta; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getIndicaciones() { return indicaciones; }
    public void setIndicaciones(String indicaciones) { this.indicaciones = indicaciones; }

    public String getIdAtencion() { return idAtencion; }
    public void setIdAtencion(String idAtencion) { this.idAtencion = idAtencion; }

    public List<RecetaDetalle> getDetalles() { return detalles; }
    public void setDetalles(List<RecetaDetalle> detalles) { this.detalles = detalles; }
}
