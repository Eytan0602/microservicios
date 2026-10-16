package com.MicroServicio.Cita;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/citas")
public class Control {

    private final Servicio servicio;
    private final TokenService tokenService;

    public Control(Servicio servicio, TokenService tokenService) {
        this.servicio = servicio;
        this.tokenService = tokenService;
    }

    @GetMapping("/mi-cita/{idCita}")
    public ResponseEntity<CitaMedica> buscarMiCita(@RequestHeader(value = "Authorization", required = false) String authorization,
                                                   @PathVariable String idCita) {
        Long idMedico = medicoAutenticado(authorization);
        CitaMedica c = servicio.buscarCitaDeMedico(idCita, idMedico);
        if (c == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(c);
    }
    @GetMapping("/mis-pendientes")
    public List<CitaMedica> listarMisPendientes(@RequestHeader(value = "Authorization", required = false) String authorization) {
        return servicio.listarPendientesPorMedico(medicoAutenticado(authorization));
    }

    @PutMapping("/mi-cita/{idCita}/atendida")
    public ResponseEntity<CitaMedica> marcarMiCitaAtendida(
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @PathVariable String idCita) {
    Long idMedico = medicoAutenticado(authorization);
    CitaMedica c = servicio.marcarAtendida(idCita, idMedico);
    if (c == null) return ResponseEntity.notFound().build();
    return ResponseEntity.ok(c);
}
    private Long medicoAutenticado(String authorization) {
        Long idMedico = tokenService.validarBearer(authorization);
        if (idMedico == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Inicia sesión como médico para consultar sus citas");
        }
        return idMedico;
    }
}