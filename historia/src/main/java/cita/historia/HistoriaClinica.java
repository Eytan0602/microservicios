package cita.historia;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class HistoriaClinica {
    @Id
    private String idHC;
    private String idAtencion;
    private String paciente;
    private LocalDate fecha;
    private String estado;
    private String dni;

    public HistoriaClinica() {
    }

    public String getIdHC() { return idHC; }
    public void setIdHC(String idHC) { this.idHC = idHC; }

    public String getIdAtencion() { return idAtencion; }
    public void setIdAtencion(String idAtencion) { this.idAtencion = idAtencion; }

    public String getPaciente() { return paciente; }
    public void setPaciente(String paciente) { this.paciente = paciente; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
}