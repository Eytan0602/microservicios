package com.clinica.receta.service;

import com.clinica.receta.model.LineaReceta;
import com.clinica.receta.model.RecetaDetalle;
import com.clinica.receta.model.RecetaMedica;
import com.clinica.receta.repository.RecetaMedicaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class RecetaService {

    private final RecetaMedicaRepository recetaMedicaRepository;
    private final List<LineaReceta> cesta = new CopyOnWriteArrayList<>();

    public RecetaService(RecetaMedicaRepository recetaMedicaRepository) {
        this.recetaMedicaRepository = recetaMedicaRepository;
    }

    public LineaReceta agregar(LineaReceta lin) {
        if (lin == null) return null;
        if (lin.getIdLR() == null || lin.getIdLR().trim().isEmpty()) {
            lin.setIdLR(String.valueOf(System.currentTimeMillis() % 1000000));
        }
        cesta.add(lin);
        return lin;
    }

    public List<LineaReceta> listar() {
        return new ArrayList<>(cesta);
    }

    public void quitar(String idLR) {
        cesta.removeIf(item -> item.getIdLR() != null && item.getIdLR().equals(idLR));
    }

    public void quitar(Long idLR) {
        if (idLR != null) quitar(String.valueOf(idLR));
    }

    public void nuevo() {
        cesta.clear();
    }

    public RecetaMedica grabar(RecetaMedica r) {
        if (r.getIdReceta() == null || r.getIdReceta().trim().isEmpty()) {
            r.setIdReceta("REC-" + System.currentTimeMillis() % 100000);
        }
        if (r.getFecha() == null) {
            r.setFecha(LocalDate.now());
        }

        if (r.getDetalles() == null || r.getDetalles().isEmpty()) {
            for (LineaReceta item : cesta) {
                String idRD = "RD-" + UUID.randomUUID().toString().substring(0, 8);
                RecetaDetalle det = new RecetaDetalle(
                        idRD,
                        item.getIdMedicamento(),
                        item.getNombre(),
                        item.getIndicaciones(),
                        item.getCantidad() > 0 ? item.getCantidad() : 1
                );
                r.agregarDetalle(det);
            }
        } else {
            for (RecetaDetalle det : r.getDetalles()) {
                if (det.getIdRD() == null || det.getIdRD().trim().isEmpty()) {
                    det.setIdRD("RD-" + UUID.randomUUID().toString().substring(0, 8));
                }
                det.setRecetaMedica(r);
            }
        }

        RecetaMedica guardada = recetaMedicaRepository.save(r);
        nuevo();
        return guardada;
    }

    public RecetaMedica buscarPorAtencion(String idAtencion) {
        return recetaMedicaRepository.findByIdAtencion(idAtencion).orElse(null);
    }

    public RecetaMedica buscarPorId(String idReceta) {
        return recetaMedicaRepository.findById(idReceta).orElse(null);
    }

    public List<RecetaMedica> listarTodas() {
        return recetaMedicaRepository.findAll();
    }
}
