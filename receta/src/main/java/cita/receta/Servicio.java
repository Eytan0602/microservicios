package cita.receta;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Service
public class Servicio {

    private static final String URL_CATALOGO = "http://localhost:8084/catalogo/medicamentos/";
    private static final String URL_ATENCION = "http://localhost:8083/atenciones/";
    private final RestClient http = RestClient.create();

    private final Map<String, List<LineaReceta>> cestas = new ConcurrentHashMap<>();

    @Autowired
    private RepositorioReceta repoReceta;

    @Autowired
    private RepositorioLinea repoLinea;

    public LineaReceta agregar(String idAtencion, LineaReceta linea) {
        if (linea.getIdMedicamento() == null || linea.getCantidad() <= 0) {
            return null;
        }
        Map<?, ?> med;
        try {
            med = http.get().uri(URL_CATALOGO + "{id}", linea.getIdMedicamento())
                    .retrieve().body(Map.class);
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        }
        linea.setIdLR(UUID.randomUUID().toString());
        linea.setNombre(String.valueOf(med.get("nombre")));
        cestas.computeIfAbsent(idAtencion, k -> new ArrayList<>()).add(linea);
        return linea;
    }

    public List<LineaReceta> listar(String idAtencion) {
        return cestas.getOrDefault(idAtencion, new ArrayList<>());
    }

    public boolean quitar(String idAtencion, String idLR) {
        return listar(idAtencion).removeIf(l -> l.getIdLR().equals(idLR));
    }

    public void nuevo(String idAtencion) {
        cestas.remove(idAtencion);
    }

    public RecetaMedica grabar(String idAtencion, RecetaMedica r) {
        List<LineaReceta> cesta = cestas.get(idAtencion);
        if (cesta == null || cesta.isEmpty()) {
            throw new IllegalStateException("La cesta esta vacia");
        }
        try {
            http.get().uri(URL_ATENCION + "{id}", idAtencion)
                    .retrieve().body(Map.class);
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        }
        r.setIdReceta(UUID.randomUUID().toString());
        r.setIdAtencion(idAtencion);
        r.setFecha(LocalDate.now());
        RecetaMedica guardada = repoReceta.save(r);
        for (LineaReceta linea : cesta) {
            linea.setIdReceta(guardada.getIdReceta());
            repoLinea.save(linea);
        }
        cestas.remove(idAtencion);
        return guardada;
    }

    public List<RecetaMedica> buscarPorAtencion(String idAtencion) {
        return repoReceta.findByIdAtencion(idAtencion);
    }

    public List<LineaReceta> lineasDeReceta(String idReceta) {
        return repoLinea.findByIdReceta(idReceta);
    }
}