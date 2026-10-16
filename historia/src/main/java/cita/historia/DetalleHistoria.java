package cita.historia;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class DetalleHistoria {
    @Id
    private String idDetalle;
    private String idHC;
    private String idAtencion;
    private String idCita;
    private String idPaciente;
    private String idMedico;
    private LocalDate fecha;
    private String diagnostico;
    private String tratamiento;
    private String observaciones;
    private String idReceta;
    private String idAnalisis;

    public DetalleHistoria() {
    }

    public String getIdDetalle() { return idDetalle; }
    public void setIdDetalle(String idDetalle) { this.idDetalle = idDetalle; }

    public String getIdHC() { return idHC; }
    public void setIdHC(String idHC) { this.idHC = idHC; }

    public String getIdAtencion() { return idAtencion; }
    public void setIdAtencion(String idAtencion) { this.idAtencion = idAtencion; }

    public String getIdCita() { return idCita; }
    public void setIdCita(String idCita) { this.idCita = idCita; }

    public String getIdPaciente() { return idPaciente; }
    public void setIdPaciente(String idPaciente) { this.idPaciente = idPaciente; }

    public String getIdMedico() { return idMedico; }
    public void setIdMedico(String idMedico) { this.idMedico = idMedico; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }

    public String getTratamiento() { return tratamiento; }
    public void setTratamiento(String tratamiento) { this.tratamiento = tratamiento; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public String getIdReceta() { return idReceta; }
    public void setIdReceta(String idReceta) { this.idReceta = idReceta; }

    public String getIdAnalisis() { return idAnalisis; }
    public void setIdAnalisis(String idAnalisis) { this.idAnalisis = idAnalisis; }
}