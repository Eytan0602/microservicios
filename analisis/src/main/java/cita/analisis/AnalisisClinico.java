package cita.analisis;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class AnalisisClinico {
    @Id
    private String idAnalisis;
    private String idAtencion;
    private LocalDate fecha;
    private String observaciones;
    private String estado;

    public AnalisisClinico() {
    }

    public String getIdAnalisis() { return idAnalisis; }
    public void setIdAnalisis(String idAnalisis) { this.idAnalisis = idAnalisis; }

    public String getIdAtencion() { return idAtencion; }
    public void setIdAtencion(String idAtencion) { this.idAtencion = idAtencion; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}