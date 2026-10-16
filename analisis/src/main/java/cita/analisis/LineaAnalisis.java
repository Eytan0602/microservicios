package cita.analisis;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class LineaAnalisis {
    @Id
    private String idLA;
    private String idAnalisis;
    private String idTA;
    private String nombre;
    private String observaciones;

    public LineaAnalisis() {
    }

    public String getIdLA() { return idLA; }
    public void setIdLA(String idLA) { this.idLA = idLA; }

    public String getIdAnalisis() { return idAnalisis; }
    public void setIdAnalisis(String idAnalisis) { this.idAnalisis = idAnalisis; }

    public String getIdTA() { return idTA; }
    public void setIdTA(String idTA) { this.idTA = idTA; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}