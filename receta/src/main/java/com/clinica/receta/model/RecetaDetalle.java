package com.clinica.receta.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "receta_detalle")
public class RecetaDetalle {

    @Id
    @Column(name = "id_rd", length = 30)
    private String idRD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_receta", nullable = false)
    @JsonBackReference
    private RecetaMedica recetaMedica;

    @Column(name = "id_medicamento", length = 30)
    private String idMedicamento;

    @Column(length = 150)
    private String nombre;

    @Column(length = 255)
    private String indicaciones;

    @Column(nullable = false)
    private int cantidad;

    public RecetaDetalle() {}

    public RecetaDetalle(String idRD, String idMedicamento, String nombre, String indicaciones, int cantidad) {
        this.idRD = idRD;
        this.idMedicamento = idMedicamento;
        this.nombre = nombre;
        this.indicaciones = indicaciones;
        this.cantidad = cantidad;
    }

    public String getIdRD() { return idRD; }
    public void setIdRD(String idRD) { this.idRD = idRD; }

    public RecetaMedica getRecetaMedica() { return recetaMedica; }
    public void setRecetaMedica(RecetaMedica recetaMedica) { this.recetaMedica = recetaMedica; }

    public String getIdMedicamento() { return idMedicamento; }
    public void setIdMedicamento(String idMedicamento) { this.idMedicamento = idMedicamento; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getIndicaciones() { return indicaciones; }
    public void setIndicaciones(String indicaciones) { this.indicaciones = indicaciones; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
}
