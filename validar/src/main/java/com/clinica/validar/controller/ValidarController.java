package com.clinica.validar.controller;

import com.clinica.validar.dto.Entrada;
import com.clinica.validar.model.Medico;
import com.clinica.validar.service.ValidarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/validar")
@CrossOrigin(origins = "*")
public class ValidarController {

    private final ValidarService validarService;

    public ValidarController(ValidarService validarService) {
        this.validarService = validarService;
    }

    @PostMapping
    public ResponseEntity<?> validar(@RequestBody Entrada entrada) {
        Medico medico = validarService.validar(entrada);
        if (medico != null) {
            return ResponseEntity.ok(medico);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Credenciales invalidas"));
    }

    @GetMapping("/medicos")
    public List<Medico> listarMedicos() {
        return validarService.listar();
    }
    @PostMapping("/medicos")
public ResponseEntity<?> crearMedico(@RequestBody Medico medico) {
    try {
        Medico creado = validarService.crear(medico);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    } catch (IllegalStateException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", e.getMessage()));
    }
}
    @GetMapping("/medicos/{id}")
    public ResponseEntity<Medico> buscarMedico(@PathVariable String id) {
        return validarService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
