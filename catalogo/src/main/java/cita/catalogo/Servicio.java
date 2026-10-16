package cita.catalogo;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Servicio {

    @Autowired
    private RepositorioMedicamento repoMedicamento;

    @Autowired
    private RepositorioTipoAnalisis repoTipo;

    public List<Medicamento> listarMedicamentos() {
        return repoMedicamento.findAll();
    }

    public List<TipoAnalisis> listarTiposAnalisis() {
        return repoTipo.findAll();
    }

    public Medicamento buscarMedicamento(String id) {
        return repoMedicamento.findById(id).orElse(null);
    }

    public TipoAnalisis buscarTipoAnalisis(String id) {
        return repoTipo.findById(id).orElse(null);
    }

    public Medicamento grabarMedicamento(Medicamento m) {
        if (m.getIdMedicamento() == null || m.getIdMedicamento().isBlank()) {
            m.setIdMedicamento(UUID.randomUUID().toString());
        }
        return repoMedicamento.save(m);
    }

    public TipoAnalisis grabarTipoAnalisis(TipoAnalisis t) {
        if (t.getIdTA() == null || t.getIdTA().isBlank()) {
            t.setIdTA(UUID.randomUUID().toString());
        }
        if (t.getEstado() == null) {
            t.setEstado("ACTIVO");
        }
        return repoTipo.save(t);
    }
}