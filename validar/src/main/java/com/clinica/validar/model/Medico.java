package com.clinica.validar.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "medico")
public class Medico {

    @Id
@Column(name = "id_medico", length = 50)
    private String idMedico;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(length = 100)
    private String especialidad;

    @Column(unique = true, length = 50)
    private String usuario;

    @Column(length = 100)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String clave;

    public Medico() {}

    public Medico(String idMedico, String nombre, String especialidad, String usuario, String clave) {
        this.idMedico = idMedico;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.usuario = usuario;
        this.clave = clave;
    }

    public String getIdMedico() { return idMedico; }
    public void setIdMedico(String idMedico) { this.idMedico = idMedico; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }
}
