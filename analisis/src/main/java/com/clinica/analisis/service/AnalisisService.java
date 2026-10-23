package com.clinica.analisis.service;

import com.clinica.analisis.model.AnalisisClinico;
import com.clinica.analisis.model.AnalisisDetalle;
import com.clinica.analisis.model.LineaAnalisis;
import com.clinica.analisis.repository.AnalisisClinicoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class AnalisisService {

    private final AnalisisClinicoRepository analisisClinicoRepository;
    private final List<LineaAnalisis> cesta = new CopyOnWriteArrayList<>();

    public AnalisisService(AnalisisClinicoRepository analisisClinicoRepository) {
        this.analisisClinicoRepository = analisisClinicoRepository;
    }

    public LineaAnalisis agregar(LineaAnalisis lin) {
        if (lin == null) return null;
        if (lin.getIdLA() == null || lin.getIdLA().trim().isEmpty()) {
            lin.setIdLA(String.valueOf(System.currentTimeMillis() % 1000000));
        }
        cesta.add(lin);
        return lin;
    }

    public void quitar(String idLA) {
        cesta.removeIf(item -> item.getIdLA() != null && item.getIdLA().equals(idLA));
    }

    public void quitar(Long idLA) {
        if (idLA != null) quitar(String.valueOf(idLA));
    }

    public List<LineaAnalisis> listar() {
        return new ArrayList<>(cesta);
    }

    public void nuevo() {
        cesta.clear();
    }

    public AnalisisClinico grabar(AnalisisClinico a) {
        if (a.getIdAnalisis() == null || a.getIdAnalisis().trim().isEmpty()) {
            a.setIdAnalisis("ANA-" + System.currentTimeMillis() % 100000);
        }
        if (a.getFecha() == null) {
            a.setFecha(LocalDate.now());
        }
        if (a.getEstado() == null || a.getEstado().trim().isEmpty()) {
            a.setEstado("SOLICITADO");
        }

        if (a.getDetalles() == null || a.getDetalles().isEmpty()) {
            for (LineaAnalisis item : cesta) {
                String idAD = "AD-" + UUID.randomUUID().toString().substring(0, 8);
                AnalisisDetalle det = new AnalisisDetalle(
                        idAD,
                        item.getIdTA(),
                        item.getNombre(),
                        item.getObservaciones()
                );
                a.agregarDetalle(det);
            }
        } else {
            for (AnalisisDetalle det : a.getDetalles()) {
                if (det.getIdAD() == null || det.getIdAD().trim().isEmpty()) {
                    det.setIdAD("AD-" + UUID.randomUUID().toString().substring(0, 8));
                }
                det.setAnalisisClinico(a);
            }
        }

        AnalisisClinico guardado = analisisClinicoRepository.save(a);
        nuevo();
        return guardado;
    }

    public AnalisisClinico buscarPorAtencion(String idAtencion) {
        return analisisClinicoRepository.findByIdAtencion(idAtencion).orElse(null);
    }

    public AnalisisClinico buscarPorId(String idAnalisis) {
        return analisisClinicoRepository.findById(idAnalisis).orElse(null);
    }

    public List<AnalisisClinico> listarTodos() {
        return analisisClinicoRepository.findAll();
    }
}
