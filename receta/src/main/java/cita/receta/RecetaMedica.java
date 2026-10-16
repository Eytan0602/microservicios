package cita.receta;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class RecetaMedica {
    @Id
    private String idReceta;
    private String idAtencion;
    private LocalDate fecha;
    private String indicaciones;

    public RecetaMedica() {
    }

    public String getIdReceta() { return idReceta; }
    public void setIdReceta(String idReceta) { this.idReceta = idReceta; }

    public String getIdAtencion() { return idAtencion; }
    public void setIdAtencion(String idAtencion) { this.idAtencion = idAtencion; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getIndicaciones() { return indicaciones; }
    public void setIndicaciones(String indicaciones) { this.indicaciones = indicaciones; }
}