package cita.analisis;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Service
public class Servicio {

    private static final String URL_CATALOGO = "http://localhost:8084/catalogo/tipos-analisis/";
    private static final String URL_ATENCION = "http://localhost:8083/atenciones/";
    private final RestClient http = RestClient.create();

    private final List<LineaAnalisis> cesta = new ArrayList<>();

    @Autowired
    private RepositorioAnalisis repoAnalisis;

    @Autowired
    private RepositorioLinea repoLinea;

    public LineaAnalisis agregar(LineaAnalisis lin) {
        if (lin.getIdTA() == null) {
            return null;
        }
        Map<?, ?> tipo;
        try {
            tipo = http.get().uri(URL_CATALOGO + "{id}", lin.getIdTA())
                    .retrieve().body(Map.class);
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        }
        lin.setIdLA(UUID.randomUUID().toString());
        lin.setNombre(String.valueOf(tipo.get("nombre")));
        cesta.add(lin);
        return lin;
    }

    public List<LineaAnalisis> listar() {
        return cesta;
    }

    public boolean quitar(String idLA) {
        return cesta.removeIf(l -> l.getIdLA().equals(idLA));
    }

    public void nuevo() {
        cesta.clear();
    }

    public AnalisisClinico grabar(AnalisisClinico a) {
        if (cesta.isEmpty()) {
            throw new IllegalStateException("La cesta esta vacia");
        }
        try {
            http.get().uri(URL_ATENCION + "{id}", a.getIdAtencion())
                    .retrieve().body(Map.class);
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        }
        a.setIdAnalisis(UUID.randomUUID().toString());
        a.setFecha(LocalDate.now());
        a.setEstado("PENDIENTE");
        AnalisisClinico guardado = repoAnalisis.save(a);
        for (LineaAnalisis lin : cesta) {
            lin.setIdAnalisis(guardado.getIdAnalisis());
            repoLinea.save(lin);
        }
        cesta.clear();
        return guardado;
    }

    public List<AnalisisClinico> buscarPorAtencion(String idAtencion) {
        return repoAnalisis.findByIdAtencion(idAtencion);
    }

    public List<LineaAnalisis> lineasDeAnalisis(String idAnalisis) {
        return repoLinea.findByIdAnalisis(idAnalisis);
    }
}