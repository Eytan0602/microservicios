package com.clinica.catalogo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tipo_analisis")
public class TipoAnalisis {

    @Id
    @Column(name = "id_ta", length = 30)
    private String idTA;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(length = 255)
    private String descripcion;

    @Column(length = 30)
    private String estado;

    public TipoAnalisis() {}

    public TipoAnalisis(String idTA, String nombre, String descripcion, String estado) {
        this.idTA = idTA;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public String getIdTA() { return idTA; }
    public void setIdTA(String idTA) { this.idTA = idTA; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
