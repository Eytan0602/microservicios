package com.MicroServicio.Cita;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class Servicio {

    private final Repositorio repositorio;

    public Servicio(Repositorio repositorio) {
        this.repositorio = repositorio;
    }

    public CitaMedica buscar(String idCita) {
        return repositorio.findById(idCita).orElse(null);
    }

    public List<CitaMedica> listar() {
        return repositorio.findAll();
    }

    public List<CitaMedica> listarPorMedico(Long idMedico) {
        return repositorio.findByIdMedico(idMedico);
    }

    public CitaMedica grabar(CitaMedica c) {
        if (c.getIdCita() == null || c.getIdCita().isBlank()) {
            c.setIdCita(UUID.randomUUID().toString());
        }
        if (c.getEstado() == null) {
            c.setEstado("PENDIENTE");
        }
        return repositorio.save(c);
    }
    public CitaMedica marcarAtendida(String idCita) {
    CitaMedica c = buscar(idCita);
    if (c == null) return null;
    c.setEstado("ATENDIDA");
    return repositorio.save(c);
    }
    public List<CitaMedica> listarPendientesPorMedico(Long idMedico) {
    return repositorio.findByIdMedicoAndEstado(idMedico, "PENDIENTE");
}
}