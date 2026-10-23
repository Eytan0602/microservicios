package com.clinica.catalogo.service;

import com.clinica.catalogo.model.Medicamento;
import com.clinica.catalogo.model.TipoAnalisis;
import com.clinica.catalogo.repository.MedicamentoRepository;
import com.clinica.catalogo.repository.TipoAnalisisRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogoService {

    private final MedicamentoRepository medicamentoRepository;
    private final TipoAnalisisRepository tipoAnalisisRepository;

    public CatalogoService(MedicamentoRepository medicamentoRepository, TipoAnalisisRepository tipoAnalisisRepository) {
        this.medicamentoRepository = medicamentoRepository;
        this.tipoAnalisisRepository = tipoAnalisisRepository;
    }

    public List<Medicamento> listarMedicamentos() {
        return medicamentoRepository.findAll();
    }

    public List<TipoAnalisis> listarTiposAnalisis() {
        return tipoAnalisisRepository.findAll();
    }

    public Medicamento buscarMedicamento(String idMedicamento) {
        if (idMedicamento == null) return null;
        return medicamentoRepository.findById(idMedicamento).orElse(null);
    }

    public TipoAnalisis buscarTipoAnalisis(String idTA) {
        if (idTA == null) return null;
        return tipoAnalisisRepository.findById(idTA).orElse(null);
    }
}
