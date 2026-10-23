package com.clinica.cita.service;

import com.clinica.cita.model.CitaMedica;
import com.clinica.cita.dto.RegistroPacienteRequest;
import com.clinica.cita.dto.SolicitudCitaRequest;
import com.clinica.cita.model.Paciente;
import com.clinica.cita.repository.CitaMedicaRepository;
import com.clinica.cita.repository.PacienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Service
public class CitaService {

    private final CitaMedicaRepository citaMedicaRepository;
    private final PacienteRepository pacienteRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public CitaService(CitaMedicaRepository citaMedicaRepository, PacienteRepository pacienteRepository) {
        this.citaMedicaRepository = citaMedicaRepository;
        this.pacienteRepository = pacienteRepository;
    }

    public CitaMedica buscar(String idCita) {
        if (idCita == null) return null;
        return citaMedicaRepository.findById(idCita).orElse(null);
    }

    public List<CitaMedica> listar() {
        return citaMedicaRepository.findAll();
    }

    public List<CitaMedica> listarPendientes() {
        return citaMedicaRepository.findByEstado("PENDIENTE");
    }

    public List<CitaMedica> listarPorMedico(String idMedico) {
        return citaMedicaRepository.findByIdMedico(idMedico);
    }

    public List<CitaMedica> listarSolicitudesPorMedico(String idMedico) {
        return citaMedicaRepository.findByIdMedicoAndEstado(idMedico, "SOLICITADA");
    }

    public List<CitaMedica> listarPorPaciente(String idPaciente) {
        return citaMedicaRepository.findByIdPaciente(idPaciente);
    }

    public Paciente registrarPaciente(RegistroPacienteRequest request) {
        if (request.getNombre() == null || request.getNombre().isBlank()
                || request.getUsuario() == null || request.getUsuario().isBlank()
                || request.getClave() == null || request.getClave().isBlank()) {
            throw new IllegalArgumentException("Nombre, usuario y clave son obligatorios.");
        }
        String usuario = request.getUsuario().trim().toLowerCase();
        if (pacienteRepository.findByUsuario(usuario).isPresent()) {
            throw new IllegalArgumentException("Ese usuario ya está registrado.");
        }
        Paciente paciente = new Paciente();
        paciente.setIdPaciente("PAC-" + java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        paciente.setNombre(request.getNombre().trim());
        paciente.setUsuario(usuario);
        paciente.setClave(passwordEncoder.encode(request.getClave()));
        paciente.setTelefono(request.getTelefono());
        return pacienteRepository.save(paciente);
    }

    public Paciente autenticarPaciente(String usuario, String clave) {
        Paciente paciente = pacienteRepository.findByUsuario(usuario.trim().toLowerCase()).orElse(null);
        return paciente != null && passwordEncoder.matches(clave, paciente.getClave()) ? paciente : null;
    }

    public List<Paciente> listarPacientes() {
        return pacienteRepository.findAll();
    }

    public List<Paciente> listarPacientesPorMedico(String idMedico) {
        return citaMedicaRepository.findByIdMedicoAndEstadoIn(idMedico, List.of("PENDIENTE", "ATENDIDA")).stream()
                .map(CitaMedica::getIdPaciente)
                .filter(java.util.Objects::nonNull)
                .distinct()
                .map(idPaciente -> pacienteRepository.findById(idPaciente).orElse(null))
                .filter(java.util.Objects::nonNull)
                .toList();
    }

   public CitaMedica solicitarCita(SolicitudCitaRequest request) {
    Paciente paciente = pacienteRepository.findById(request.getIdPaciente())
            .orElseThrow(() -> new IllegalArgumentException("Paciente no registrado."));
    if (request.getIdMedico() == null || request.getIdMedico().isBlank()) {
        throw new IllegalArgumentException("Selecciona un médico.");
    }
    CitaMedica cita = new CitaMedica();
    cita.setIdCita("SOL-" + java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase());
    cita.setIdPaciente(paciente.getIdPaciente());
    cita.setPaciente(paciente.getNombre());
    cita.setIdMedico(request.getIdMedico());
    cita.setHorario(request.getHorario());
    cita.setEstado("SOLICITADA");
    return citaMedicaRepository.save(cita);
}
public CitaMedica resolverSolicitud(String idCita, String idMedico, String decision) {
    CitaMedica cita = citaMedicaRepository.findById(idCita).orElse(null);
    if (cita == null || !idMedico.equals(cita.getIdMedico()) || !"SOLICITADA".equals(cita.getEstado())) {
        return null;
    }
    if ("ACEPTAR".equals(decision)) {
        cita.setEstado("PENDIENTE");
        cita.setIdHC("HC-" + cita.getIdPaciente().replace("PAC-", ""));
    } else if ("RECHAZAR".equals(decision)) {
        cita.setEstado("RECHAZADA");
    } else {
        throw new IllegalArgumentException("Decisión inválida.");
    }
    return citaMedicaRepository.save(cita);
}

    public CitaMedica actualizar(CitaMedica cita) {
        return citaMedicaRepository.save(cita);
    }

    public CitaMedica guardar(CitaMedica cita) {
        return citaMedicaRepository.save(cita);
    }
}
