package com.clinica.cita.controller;

import com.clinica.cita.model.CitaMedica;
import com.clinica.cita.model.Paciente;
import com.clinica.cita.dto.RegistroPacienteRequest;
import com.clinica.cita.dto.SolicitudCitaRequest;
import com.clinica.cita.service.CitaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/citas")
@CrossOrigin(origins = "*")
public class CitaController {

    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    @GetMapping
    public List<CitaMedica> listar() {
        return citaService.listar();
    }

    @GetMapping("/pendientes")
    public List<CitaMedica> listarPendientes() {
        return citaService.listarPendientes();
    }

    @GetMapping("/medico/{idMedico}")
    public List<CitaMedica> listarPorMedico(@PathVariable String idMedico) {
        return citaService.listarPorMedico(idMedico);
    }

    @GetMapping("/medico/{idMedico}/solicitudes")
    public List<CitaMedica> listarSolicitudesPorMedico(@PathVariable String idMedico) {
        return citaService.listarSolicitudesPorMedico(idMedico);
    }

    @PostMapping("/pacientes")
    public ResponseEntity<Paciente> registrarPaciente(@RequestBody RegistroPacienteRequest request) {
        return ResponseEntity.ok(citaService.registrarPaciente(request));
    }

    @PostMapping("/pacientes/login")
    public ResponseEntity<Paciente> autenticarPaciente(@RequestBody RegistroPacienteRequest request) {
        Paciente paciente = citaService.autenticarPaciente(request.getUsuario(), request.getClave());
        return paciente != null ? ResponseEntity.ok(paciente) : ResponseEntity.status(401).build();
    }

    @PostMapping("/solicitudes")
    public ResponseEntity<CitaMedica> solicitarCita(@RequestBody SolicitudCitaRequest request) {
        return ResponseEntity.ok(citaService.solicitarCita(request));
    }

    @GetMapping("/pacientes/{idPaciente}/solicitudes")
    public List<CitaMedica> listarSolicitudesPaciente(@PathVariable String idPaciente) {
        return citaService.listarPorPaciente(idPaciente);
    }

    @GetMapping("/medico/{idMedico}/pacientes")
    public List<Paciente> listarPacientesPorMedico(@PathVariable String idMedico) {
        return citaService.listarPacientesPorMedico(idMedico);
    }

    @PutMapping("/{idCita}/solicitud")
    public ResponseEntity<CitaMedica> resolverSolicitud(@PathVariable String idCita,
                                                         @RequestParam String idMedico,
                                                         @RequestParam String decision) {
        CitaMedica cita = citaService.resolverSolicitud(idCita, idMedico, decision);
        return cita != null ? ResponseEntity.ok(cita) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{idCita}")
    public ResponseEntity<CitaMedica> buscar(@PathVariable String idCita) {
        CitaMedica cita = citaService.buscar(idCita);
        if (cita != null) {
            return ResponseEntity.ok(cita);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<CitaMedica> crear(@RequestBody CitaMedica cita) {
        return ResponseEntity.ok(citaService.guardar(cita));
    }

    @PutMapping("/{idCita}")
    public ResponseEntity<CitaMedica> actualizar(@PathVariable String idCita, @RequestBody CitaMedica cita) {
        cita.setIdCita(idCita);
        return ResponseEntity.ok(citaService.actualizar(cita));
    }
}
