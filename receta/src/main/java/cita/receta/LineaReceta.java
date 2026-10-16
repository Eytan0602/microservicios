package cita.receta;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class LineaReceta {
    @Id
    private String idLR;
    private String idReceta;
    private String idMedicamento;
    private String nombre;
    private String indicaciones;
    private int cantidad;

    public LineaReceta() {
    }

    public String getIdLR() { return idLR; }
    public void setIdLR(String idLR) { this.idLR = idLR; }

    public String getIdReceta() { return idReceta; }
    public void setIdReceta(String idReceta) { this.idReceta = idReceta; }

    public String getIdMedicamento() { return idMedicamento; }
    public void setIdMedicamento(String idMedicamento) { this.idMedicamento = idMedicamento; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getIndicaciones() { return indicaciones; }
    public void setIndicaciones(String indicaciones) { this.indicaciones = indicaciones; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
}