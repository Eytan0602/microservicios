package com.MicroServicio.Validar;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Medico{
    @Id
    private Long id;
    private String nombre;
    private String especialidad;
    private String clave;
    private String usuario;

    public Medico(){
}

public  Medico (Long id, String nombre, String especialidad, String clave, String usuario){
    this.id=id;
    this.nombre=nombre;
    this.especialidad=especialidad;
    this.clave=clave;
    this.usuario=usuario;
}

public void setId(Long id) {
    this.id = id;
}

public void setNombre(String nombre) {
    this.nombre = nombre;
}

public void setEspecialidad(String especialidad) {
    this.especialidad = especialidad;
}

public void setClave(String clave) {
    this.clave = clave;
}

public void setUsuario(String usuario) {
    this.usuario = usuario;
}

public Long getId() {
    return id;
}

public String getNombre() {
    return nombre;
}

public String getEspecialidad() {
    return especialidad;
}

@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
public String getClave() {
    return clave;
}

public String getUsuario() {
    return usuario;
}
}

