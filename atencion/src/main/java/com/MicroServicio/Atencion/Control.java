package com.MicroServicio.Atencion;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/atenciones")
public class Control {

    private final Servicio servicio;
    private final TokenService tokenService;

    public Control(Servicio servicio, TokenService tokenService) {
        this.servicio = servicio;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<?> generar(@RequestBody AtencionMedica datos,
                                     @RequestHeader(value = "Authorization", required = false) String authorization) {
        try {
            AtencionMedica a = servicio.generar(datos.getIdCita(), authorization);
            if (a == null) {
                return ResponseEntity.status(404).body("La cita no existe o no está asignada a este médico");
            }
            return ResponseEntity.ok(a);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{idAtencion}")
    public ResponseEntity<AtencionMedica> registrar(@PathVariable String idAtencion,
                                                    @RequestBody AtencionMedica datos,
                                                    @RequestHeader(value = "Authorization", required = false) String authorization) {
        Long medico = medicoAutenticado(authorization);
        AtencionMedica a = servicio.registrar(medico, idAtencion, datos);
        if (a == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(a);
    }

    @GetMapping("/{idAtencion}")
    public ResponseEntity<AtencionMedica> buscar(@PathVariable String idAtencion,
                                                 @RequestHeader(value = "Authorization", required = false) String authorization) {
        Long medico = medicoAutenticado(authorization);
        AtencionMedica a = servicio.buscar(medico, idAtencion);
        if (a == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(a);
    }

    @GetMapping
    public List<AtencionMedica> listar(@RequestHeader(value = "Authorization", required = false) String authorization) {
        return servicio.listar(medicoAutenticado(authorization));
    }

    private Long medicoAutenticado(String authorization) {
        Long idMedico = tokenService.validarBearer(authorization);
        if (idMedico == null) {
            throw new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.UNAUTHORIZED, "Inicia sesión como médico");
        }
        return idMedico;
    }
}