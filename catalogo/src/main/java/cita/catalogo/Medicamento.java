package cita.catalogo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Medicamento {
    @Id
    private String idMedicamento;
    private String nombre;
    private String marca;
    private String descripcion;
    private int miligramos;
    private String presentacion;
    private int stock;

    public Medicamento() {
    }

    public String getIdMedicamento() { return idMedicamento; }
    public void setIdMedicamento(String idMedicamento) { this.idMedicamento = idMedicamento; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public int getMiligramos() { return miligramos; }
    public void setMiligramos(int miligramos) { this.miligramos = miligramos; }

    public String getPresentacion() { return presentacion; }
    public void setPresentacion(String presentacion) { this.presentacion = presentacion; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}