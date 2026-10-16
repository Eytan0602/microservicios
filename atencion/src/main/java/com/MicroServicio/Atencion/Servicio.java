package com.MicroServicio.Atencion;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Service
public class Servicio {

    private static final String URL_CITA = "http://localhost:8082/citas";
    private final RestClient http = RestClient.create();

    @Autowired
    private Repositorio repositorio;

    public AtencionMedica generar(String idCita) {
        Map<?, ?> cita;
        try {
            cita = http.get().uri(URL_CITA + "/{id}", idCita)
                    .retrieve().body(Map.class);
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        }
        if (cita != null && "ATENDIDA".equals(cita.get("estado"))) {
            throw new IllegalStateException("La cita ya fue atendida");
        }

        AtencionMedica a = new AtencionMedica();
        a.setIdAtencion(UUID.randomUUID().toString());
        a.setIdCita(idCita);

        Object dniPaciente = cita.get("dniPaciente");
        Object paciente = cita.get("paciente");
        Object idMedico = cita.get("idMedico");

        a.setIdPaciente(dniPaciente != null ? String.valueOf(dniPaciente) : (paciente != null ? String.valueOf(paciente) : ""));
        a.setIdMedico(idMedico != null ? String.valueOf(idMedico) : "");
        a.setFecha(LocalDate.now());
        AtencionMedica guardada = repositorio.save(a);

        http.put().uri(URL_CITA + "/{id}/atendida", idCita)
                .retrieve().toBodilessEntity();
        return guardada;
    }

    public AtencionMedica registrar(String idAtencion, AtencionMedica datos) {
        return repositorio.findById(idAtencion).map(a -> {
            a.setDiagnostico(datos.getDiagnostico());
            a.setTratamiento(datos.getTratamiento());
            a.setObservaciones(datos.getObservaciones());
            return repositorio.save(a);
        }).orElse(null);
    }

    public AtencionMedica buscar(String idAtencion) {
        return repositorio.findById(idAtencion).orElse(null);
    }

    public List<AtencionMedica> listar() {
        return repositorio.findAll();
    }
}