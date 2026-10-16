package com.MicroServicio.Atencion;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

@Service
public class Servicio {

    private static final String URL_CITA = "http://localhost:8082/citas";
    private final RestClient http = RestClient.create();

    private final Repositorio repositorio;
    private final TokenService tokenService;

    public Servicio(Repositorio repositorio, TokenService tokenService) {
        this.repositorio = repositorio;
        this.tokenService = tokenService;
    }

    public AtencionMedica generar(String idCita, String authorization) {
        Long medicoSolicitante = tokenService.validarBearer(authorization);
        if (idCita == null || medicoSolicitante == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Inicia sesión como médico para atender una cita");
        }
        Map<?, ?> cita;
        try {
            cita = http.get().uri(URL_CITA + "/mi-cita/{idCita}", idCita)
                    .header(HttpHeaders.AUTHORIZATION, authorization)
                    .retrieve().body(Map.class);
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        }
        if (cita == null) {
            return null;
        }
        if ("ATENDIDA".equals(cita.get("estado"))) {
            throw new IllegalStateException("La cita ya fue atendida");
        }

        AtencionMedica a = new AtencionMedica();
        a.setIdAtencion(UUID.randomUUID().toString());
        a.setIdCita(idCita);

        Object dniPaciente = cita.get("dniPaciente");
        Object paciente = cita.get("paciente");
        Object idMedicoCita = cita.get("idMedico");

        a.setIdPaciente(dniPaciente != null ? String.valueOf(dniPaciente) : (paciente != null ? String.valueOf(paciente) : ""));
        a.setIdMedico(idMedicoCita != null ? String.valueOf(idMedicoCita) : "");
        a.setFecha(LocalDate.now());
        AtencionMedica guardada = repositorio.save(a);

        http.put().uri(URL_CITA + "/mi-cita/{idCita}/atendida", idCita)
            .header(HttpHeaders.AUTHORIZATION, authorization)
                .retrieve().toBodilessEntity();
        return guardada;
    }

    public AtencionMedica registrar(Long idMedico, String idAtencion, AtencionMedica datos) {
        return buscarPropia(idMedico, idAtencion).map(a -> {
            a.setDiagnostico(datos.getDiagnostico());
            a.setTratamiento(datos.getTratamiento());
            a.setObservaciones(datos.getObservaciones());
            return repositorio.save(a);
        }).orElse(null);
    }

    public AtencionMedica buscar(Long idMedico, String idAtencion) {
        return buscarPropia(idMedico, idAtencion).orElse(null);
    }

    public List<AtencionMedica> listar(Long idMedico) {
        return repositorio.findByIdMedico(String.valueOf(idMedico));
    }

    private java.util.Optional<AtencionMedica> buscarPropia(Long idMedico, String idAtencion) {
        return repositorio.findById(idAtencion)
                .filter(a -> String.valueOf(idMedico).equals(a.getIdMedico()));
    }
}