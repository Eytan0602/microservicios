package com.clinica.historia.service;

import com.clinica.historia.model.HistoriaClinica;
import com.clinica.historia.repository.HistoriaClinicaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoriaService {

    private final HistoriaClinicaRepository historiaClinicaRepository;

    public HistoriaService(HistoriaClinicaRepository historiaClinicaRepository) {
        this.historiaClinicaRepository = historiaClinicaRepository;
    }

    public HistoriaClinica buscar(String idHC) {
        if (idHC == null) return null;
        return historiaClinicaRepository.findById(idHC).orElse(null);
    }

    public HistoriaClinica actualizar(HistoriaClinica h) {
        return historiaClinicaRepository.save(h);
    }

    public List<HistoriaClinica> listar() {
        return historiaClinicaRepository.findAll();
    }

    public HistoriaClinica guardar(HistoriaClinica h) {
        return historiaClinicaRepository.save(h);
    }
}
