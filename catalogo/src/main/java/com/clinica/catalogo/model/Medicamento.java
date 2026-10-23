package com.clinica.catalogo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "medicamento")
public class Medicamento {

    @Id
    @Column(name = "id_medicamento", length = 30)
    private String idMedicamento;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(length = 100)
    private String presentacion;

    @Column(nullable = false)
    private int stock;

    public Medicamento() {}

    public Medicamento(String idMedicamento, String nombre, String presentacion, int stock) {
        this.idMedicamento = idMedicamento;
        this.nombre = nombre;
        this.presentacion = presentacion;
        this.stock = stock;
    }

    public String getIdMedicamento() { return idMedicamento; }
    public void setIdMedicamento(String idMedicamento) { this.idMedicamento = idMedicamento; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getPresentacion() { return presentacion; }
    public void setPresentacion(String presentacion) { this.presentacion = presentacion; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}
