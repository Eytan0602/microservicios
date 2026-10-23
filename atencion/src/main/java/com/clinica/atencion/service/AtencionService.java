package com.clinica.atencion.service;

import com.clinica.atencion.dto.AtencionMedicaCompletaDto;
import com.clinica.atencion.dto.EditarAtencionRequest;
import com.clinica.atencion.dto.RegistroAtencionRequest;
import com.clinica.atencion.model.AtencionMedica;
import com.clinica.atencion.repository.AtencionMedicaRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class AtencionService {

    private final AtencionMedicaRepository atencionMedicaRepository;
    private final RestTemplate restTemplate;

    @Value("${microservicio.validar.url:http://localhost:8081/validar}")
    private String urlValidar;

    @Value("${microservicio.cita.url:http://localhost:8082/citas}")
    private String urlCita;

    @Value("${microservicio.historia.url:http://localhost:8083/historias}")
    private String urlHistoria;

    @Value("${microservicio.catalogo.url:http://localhost:8084/catalogo}")
    private String urlCatalogo;

    @Value("${microservicio.receta.url:http://localhost:8085/recetas}")
    private String urlReceta;

    @Value("${microservicio.analisis.url:http://localhost:8086/analisis}")
    private String urlAnalisis;

    private static final AtomicInteger contador = new AtomicInteger(100);

    public AtencionService(AtencionMedicaRepository atencionMedicaRepository, RestTemplate restTemplate) {
        this.atencionMedicaRepository = atencionMedicaRepository;
        this.restTemplate = restTemplate;
    }

    public synchronized String generarNumeroAtencion() {
        return "AT-" + LocalDate.now().getYear() + "-" + contador.incrementAndGet();
    }

    public Object consultarCita(String idCita) {
        try {
            return restTemplate.getForObject(urlCita + "/" + idCita, Object.class);
        } catch (Exception e) {
            return null;
        }
    }

    public List<?> listarCitas(String idMedico) {
        try {
            return restTemplate.getForObject(urlCita + "/medico/" + idMedico, List.class);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public List<?> listarSolicitudes(String idMedico) {
        try {
            return restTemplate.getForObject(urlCita + "/medico/" + idMedico + "/solicitudes", List.class);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public List<?> listarSolicitudesPaciente(String idPaciente) {
        try {
            return restTemplate.getForObject(urlCita + "/pacientes/" + idPaciente + "/solicitudes", List.class);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public List<?> listarPacientesMedico(String idMedico) {
        try {
            return restTemplate.getForObject(urlCita + "/medico/" + idMedico + "/pacientes", List.class);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public void solicitarCita(String idPaciente, String idMedico, String horario) {
        Map<String, Object> solicitud = new HashMap<>();
        solicitud.put("idPaciente", idPaciente);
        solicitud.put("idMedico", idMedico);
        solicitud.put("horario", horario == null || horario.isBlank() ? null : horario);
        restTemplate.postForObject(urlCita + "/solicitudes", solicitud, Object.class);
    }

    public boolean resolverSolicitud(String idCita, String idMedico, String decision) {
        String url = urlCita + "/" + idCita + "/solicitud?idMedico=" + idMedico + "&decision=" + decision;
        Map<?, ?> cita = restTemplate.exchange(url, org.springframework.http.HttpMethod.PUT,
                org.springframework.http.HttpEntity.EMPTY, Map.class).getBody();
        if (cita == null) return false;
       if ("ACEPTAR".equals(decision) && cita.get("idHC") != null) {
    String idHC = String.valueOf(cita.get("idHC"));
    if (buscarHistoria(idHC) == null) {
        Map<String, Object> historia = new HashMap<>();
        historia.put("idHC", idHC);
        historia.put("paciente", cita.get("paciente"));
        historia.put("fecha", LocalDate.now().toString());
        historia.put("estado", "ACTIVA");
        historia.put("antecedentes", "Sin antecedentes registrados");
        restTemplate.postForObject(urlHistoria, historia, Object.class);
    }
}
        return true;
    }

    public List<?> listarMedicamentosCatalogo() {
        try {
            return restTemplate.getForObject(urlCatalogo + "/medicamentos", List.class);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public List<?> listarTiposAnalisisCatalogo() {
        try {
            return restTemplate.getForObject(urlCatalogo + "/analisis", List.class);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public List<?> listarMedicos() {
        try {
            return restTemplate.getForObject(urlValidar + "/medicos", List.class);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public List<?> listarHistorias() {
    try {
        List<?> historias = restTemplate.getForObject(urlHistoria, List.class);
        return historias == null ? Collections.emptyList() : historias;
    } catch (Exception e) {
        return Collections.emptyList();
    }
}

public Object buscarHistoria(String idHC) {
    if (idHC == null || idHC.isBlank()) return null;
    try {
        return restTemplate.getForObject(urlHistoria + "/" + idHC.trim(), Object.class);
    } catch (Exception e) {
        return null;
    }
}

public List<?> buscarHistorias(String filtro) {
    List<?> todas = listarHistorias();
    if (filtro == null || filtro.isBlank()) return todas;
    String f = filtro.trim().toLowerCase();
    return todas.stream()
            .filter(Map.class::isInstance)
            .map(h -> (Map<?, ?>) h)
            .filter(h -> h.values().stream()
                    .anyMatch(v -> v != null && String.valueOf(v).toLowerCase().contains(f)))
            .toList();
}

public List<AtencionMedica> listarPorHistoria(String idHC) {
    return atencionMedicaRepository.findByIdHCOrderByFechaDesc(idHC);
}

    @Transactional
    public AtencionMedicaCompletaDto grabar(RegistroAtencionRequest req, String idMedico) {
        if (idMedico == null || idMedico.isBlank()) {
            throw new IllegalArgumentException("La sesión no contiene un médico válido.");
        }
        if (req.getIdCita() == null || req.getIdCita().isBlank()) {
            throw new IllegalArgumentException("Selecciona una cita asignada a tu cuenta.");
        }
        String idCita = req.getIdCita().trim();
        Object citaSeleccionada = consultarCita(idCita);
        if (!(citaSeleccionada instanceof Map<?, ?> datosCita)
                || !idMedico.equals(String.valueOf(datosCita.get("idMedico")))) {
            throw new IllegalArgumentException("La cita no pertenece al médico autenticado.");
        }

        String pacienteCita = datosCita.get("paciente") == null ? null : String.valueOf(datosCita.get("paciente"));
        String idHCCita = datosCita.get("idHC") == null ? null : String.valueOf(datosCita.get("idHC"));
        if (idHCCita == null || idHCCita.isBlank()) {
            throw new IllegalArgumentException("La cita no tiene historia clínica asignada.");
        }

        String idAtencion = (req.getIdAtencion() != null && !req.getIdAtencion().trim().isEmpty())
                ? req.getIdAtencion().trim()
                : generarNumeroAtencion();

        AtencionMedica atencion = new AtencionMedica();
        atencion.setIdAtencion(idAtencion);
        atencion.setFecha(LocalDateTime.now());
        atencion.setIdCita(idCita);
        atencion.setIdMedico(idMedico);
        atencion.setIdHC(idHCCita);
        atencion.setPaciente(pacienteCita);
        atencion.setDiagnostico(req.getDiagnostico());
        atencion.setTratamiento(req.getTratamiento());
        atencion.setObservaciones(req.getObservaciones());
        atencion.setEstado("REGISTRADA");

        if (req.isRequiereReceta() && req.getItemsReceta() != null && !req.getItemsReceta().isEmpty()) {
            try {
                String idReceta = "REC-" + idAtencion.replace("AT-", "");
                Map<String, Object> recetaPayload = new HashMap<>();
                recetaPayload.put("idReceta", idReceta);
                recetaPayload.put("idAtencion", idAtencion);
                recetaPayload.put("fecha", LocalDate.now().toString());
                recetaPayload.put("indicaciones", req.getIndicacionesReceta() != null ? req.getIndicacionesReceta() : "Tomar según indicaciones");

                List<Map<String, Object>> detalles = new ArrayList<>();
                req.getItemsReceta().forEach(item -> {
                    Map<String, Object> det = new HashMap<>();
                    det.put("idMedicamento", item.getIdMedicamento());
                    det.put("nombre", item.getNombre());
                    det.put("indicaciones", item.getIndicaciones());
                    det.put("cantidad", item.getCantidad() > 0 ? item.getCantidad() : 1);
                    detalles.add(det);
                });
                recetaPayload.put("detalles", detalles);

                restTemplate.postForObject(urlReceta + "/grabar", recetaPayload, Object.class);
                atencion.setIdReceta(idReceta);
            } catch (Exception ex) {
                System.err.println("Aviso al conectar con MicroReceta: " + ex.getMessage());
            }
        }

        if (req.isRequiereAnalisis() && req.getItemsAnalisis() != null && !req.getItemsAnalisis().isEmpty()) {
            try {
                String idAnalisis = "ANA-" + idAtencion.replace("AT-", "");
                Map<String, Object> analisisPayload = new HashMap<>();
                analisisPayload.put("idAnalisis", idAnalisis);
                analisisPayload.put("idAtencion", idAtencion);
                analisisPayload.put("fecha", LocalDate.now().toString());
                analisisPayload.put("observaciones", req.getObservacionesAnalisis() != null ? req.getObservacionesAnalisis() : "Análisis clínico");
                analisisPayload.put("estado", "SOLICITADO");

                List<Map<String, Object>> detalles = new ArrayList<>();
                req.getItemsAnalisis().forEach(item -> {
                    Map<String, Object> det = new HashMap<>();
                    det.put("idTA", item.getIdTA());
                    det.put("nombre", item.getNombre());
                    det.put("observaciones", item.getObservaciones());
                    detalles.add(det);
                });
                analisisPayload.put("detalles", detalles);

                restTemplate.postForObject(urlAnalisis + "/grabar", analisisPayload, Object.class);
                atencion.setIdAnalisis(idAnalisis);
            } catch (Exception ex) {
                System.err.println("Aviso al conectar con MicroAnalisis: " + ex.getMessage());
            }
        }

        AtencionMedica guardada = atencionMedicaRepository.save(atencion);

        try {
            Map<String, Object> citaData = new HashMap<>();
            citaData.put("idCita", idCita);
            citaData.put("paciente", pacienteCita);
            citaData.put("numAtencion", idAtencion);
            citaData.put("idMedico", idMedico);
            citaData.put("idHC", idHCCita);
            citaData.put("idPaciente", datosCita.get("idPaciente"));
            citaData.put("horario", datosCita.get("horario"));
            citaData.put("estado", "ATENDIDA");
            restTemplate.put(urlCita + "/" + idCita, citaData);
        } catch (Exception ex) {
            System.err.println("Aviso al actualizar MicroCita: " + ex.getMessage());
        }

        try {
            Object historia = restTemplate.getForObject(urlHistoria + "/" + idHCCita, Object.class);
            if (historia instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> hcMap = (Map<String, Object>) historia;
                String ant = (String) hcMap.get("antecedentes");
                String nuevoAnt = (ant != null ? ant : "") + " [Atención: " + idAtencion + " | Diag: " + guardada.getDiagnostico() + "]";
                hcMap.put("antecedentes", nuevoAnt);
                restTemplate.put(urlHistoria + "/" + idHCCita, hcMap);
            }
        } catch (Exception ex) {
            System.err.println("Aviso al actualizar MicroHistoria: " + ex.getMessage());
        }

        return obtenerCompleta(idAtencion, idMedico);
    }

    public List<AtencionMedica> listar(String idMedico) {
        return atencionMedicaRepository.findAllByIdMedicoOrderByFechaDesc(idMedico);
    }

    public AtencionMedica buscar(String idAtencion, String idMedico) {
        if (idAtencion == null) return null;
        return atencionMedicaRepository.findByIdAtencionAndIdMedico(idAtencion.trim(), idMedico).orElse(null);
    }

    public List<AtencionMedica> buscarPorFiltro(String filtro, String idMedico) {
        if (filtro == null || filtro.trim().isEmpty()) return listar(idMedico);
        return atencionMedicaRepository.buscarPorFiltro(filtro.trim(), idMedico);
    }

    @Transactional
    public AtencionMedica editar(String idAtencion, EditarAtencionRequest req, String idMedico) {
        AtencionMedica a = buscar(idAtencion, idMedico);
        if (a == null) return null;

        if (req.getDiagnostico() != null) a.setDiagnostico(req.getDiagnostico());
        if (req.getTratamiento() != null) a.setTratamiento(req.getTratamiento());
        if (req.getObservaciones() != null) a.setObservaciones(req.getObservaciones());
        if (req.getEstado() != null && !req.getEstado().isEmpty()) a.setEstado(req.getEstado());
        else a.setEstado("EDITADA");

        return atencionMedicaRepository.save(a);
    }

    public AtencionMedicaCompletaDto obtenerCompleta(String idAtencion, String idMedico) {
    AtencionMedica a = buscar(idAtencion, idMedico);
    return a == null ? null : armarCompleta(a);
}

public AtencionMedicaCompletaDto obtenerCompletaCompartida(String idAtencion) {
    if (idAtencion == null || idAtencion.isBlank()) return null;
    return atencionMedicaRepository.findById(idAtencion.trim())
            .map(this::armarCompleta)
            .orElse(null);
}

private AtencionMedicaCompletaDto armarCompleta(AtencionMedica a) {
    String idAtencion = a.getIdAtencion();

    Object medico = null;
    if (a.getIdMedico() != null) {
        try {
            medico = restTemplate.getForObject(urlValidar + "/medicos/" + a.getIdMedico(), Object.class);
        } catch (Exception ignored) {}
    }

    Object cita = null;
    if (a.getIdCita() != null) {
        try {
            cita = restTemplate.getForObject(urlCita + "/" + a.getIdCita(), Object.class);
        } catch (Exception ignored) {}
    }

    Object historia = null;
    if (a.getIdHC() != null) {
        try {
            historia = restTemplate.getForObject(urlHistoria + "/" + a.getIdHC(), Object.class);
        } catch (Exception ignored) {}
    }

    Object receta = null;
    try {
        receta = restTemplate.getForObject(urlReceta + "/atencion/" + idAtencion, Object.class);
    } catch (Exception ignored) {}

    Object analisis = null;
    try {
        analisis = restTemplate.getForObject(urlAnalisis + "/atencion/" + idAtencion, Object.class);
    } catch (Exception ignored) {}

    return new AtencionMedicaCompletaDto(a, medico, cita, historia, receta, analisis);
}
}