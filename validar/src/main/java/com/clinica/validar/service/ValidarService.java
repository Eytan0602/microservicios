package com.clinica.validar.service;

import com.clinica.validar.dto.Entrada;
import com.clinica.validar.model.Medico;
import com.clinica.validar.repository.MedicoRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
public class ValidarService {

    private final MedicoRepository medicoRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public ValidarService(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    public Medico validar(Entrada e) {
        if (e == null || e.getUsuario() == null || e.getClave() == null) {
            return null;
        }
        Medico medico = medicoRepository.findByUsuario(e.getUsuario().trim()).orElse(null);
        return medico != null && passwordEncoder.matches(e.getClave().trim(), medico.getClave()) ? medico : null;
    }

    public List<Medico> listar() {
        return medicoRepository.findAll();
    }
    
public Medico crear(Medico medico) {

    if (medico == null) {
        throw new IllegalArgumentException("Los datos del médico son obligatorios");
    }

    if (medico.getNombre() == null || medico.getNombre().isBlank()) {
        throw new IllegalArgumentException("El nombre es obligatorio");
    }

    if (medico.getUsuario() == null || medico.getUsuario().isBlank()) {
        throw new IllegalArgumentException("El usuario es obligatorio");
    }

    if (medico.getClave() == null || medico.getClave().isBlank()) {
        throw new IllegalArgumentException("La clave es obligatoria");
    }

    if (medicoRepository.findByUsuario(medico.getUsuario().trim()).isPresent()) {
        throw new IllegalStateException("El usuario ya está en uso");
    }

    medico.setIdMedico(java.util.UUID.randomUUID().toString());

    medico.setNombre(medico.getNombre().trim());
    medico.setUsuario(medico.getUsuario().trim());

    medico.setClave(passwordEncoder.encode(medico.getClave().trim()));

    return medicoRepository.save(medico);
}

    public Optional<Medico> buscarPorId(String idMedico) {
        return medicoRepository.findById(idMedico);
    }
}
