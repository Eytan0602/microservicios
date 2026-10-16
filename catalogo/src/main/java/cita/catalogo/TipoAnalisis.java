package cita.catalogo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class TipoAnalisis {
    @Id
    private String idTA;
    private String nombre;
    private String descripcion;
    private String estado;

    public TipoAnalisis() {
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