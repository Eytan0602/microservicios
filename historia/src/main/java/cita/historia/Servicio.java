package cita.historia;

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

    private static final String URL_ATENCION = "http://localhost:8083/atenciones/";
    private static final String URL_CITA = "http://localhost:8082/citas/";
    private static final String URL_RECETA = "http://localhost:8085/recetas/atencion/";
    private static final String URL_ANALISIS = "http://localhost:8086/analisis/atencion/";
    private final RestClient http = RestClient.create();

    @Autowired
    private RepositorioHistoria repoHistoria;

    @Autowired
    private RepositorioDetalle repoDetalle;

    public HistoriaClinica buscar(String idHC) {
        return repoHistoria.findById(idHC).orElse(null);
    }

    public HistoriaClinica actualizar(String idHC, HistoriaClinica h) {
        return repoHistoria.findById(idHC).map(x -> {
            x.setPaciente(h.getPaciente());
            x.setEstado(h.getEstado());
            return repoHistoria.save(x);
        }).orElse(null);
    }

    public List<HistoriaClinica> listar() {
        return repoHistoria.findAll();
    }

    public List<DetalleHistoria> atenciones(String idHC) {
        return repoDetalle.findByIdHC(idHC);
    }


    public DetalleHistoria registrarAtencion(String idAtencion) {
        if (repoDetalle.existsByIdAtencion(idAtencion)) {
            throw new IllegalStateException("La atencion ya esta registrada en la historia");
        }

        Map<?, ?> atencion;
        Map<?, ?> cita;
        try {
            atencion = http.get().uri(URL_ATENCION + "{id}", idAtencion)
                    .retrieve().body(Map.class);
            cita = http.get().uri(URL_CITA + "{id}", String.valueOf(atencion.get("idCita")))
                    .retrieve().body(Map.class);
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        }

        String paciente = String.valueOf(cita.get("paciente"));

        HistoriaClinica hc = repoHistoria.findByPaciente(paciente).orElseGet(() -> {
            HistoriaClinica nueva = new HistoriaClinica();
            nueva.setIdHC(UUID.randomUUID().toString());
            nueva.setIdAtencion(idAtencion);
            nueva.setPaciente(paciente);
            nueva.setFecha(LocalDate.now());
            nueva.setEstado("ACTIVA");
            return repoHistoria.save(nueva);
        });

        DetalleHistoria d = new DetalleHistoria();
        d.setIdDetalle(UUID.randomUUID().toString());
        d.setIdHC(hc.getIdHC());
        d.setIdAtencion(idAtencion);
        d.setIdCita(String.valueOf(atencion.get("idCita")));
        d.setIdPaciente(String.valueOf(atencion.getOrDefault("idPaciente", cita.getOrDefault("dniPaciente", paciente))));
        d.setIdMedico(String.valueOf(atencion.getOrDefault("idMedico", cita.getOrDefault("idMedico", ""))));
        d.setFecha(LocalDate.now());
        d.setDiagnostico(texto(atencion.get("diagnostico")));
        d.setTratamiento(texto(atencion.get("tratamiento")));
        d.setObservaciones(texto(atencion.get("observaciones")));
        d.setIdReceta(primerId(URL_RECETA, idAtencion, "idReceta"));
        d.setIdAnalisis(primerId(URL_ANALISIS, idAtencion, "idAnalisis"));
        return repoDetalle.save(d);
    }

    private String texto(Object o) {
        return o == null ? null : o.toString();
    }

    private String primerId(String url, String idAtencion, String campo) {
        try {
            List<?> lista = http.get().uri(url + "{id}", idAtencion)
                    .retrieve().body(List.class);
            if (lista != null && !lista.isEmpty()) {
                Object primero = lista.get(0);
                if (primero instanceof Map<?, ?> m && m.get(campo) != null) {
                    return m.get(campo).toString();
                }
            }
        } catch (Exception e) {
        }
        return null;
    }
}