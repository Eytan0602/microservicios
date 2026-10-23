package com.clinica.atencion.controller;

import com.clinica.atencion.dto.AtencionMedicaCompletaDto;
import com.clinica.atencion.dto.EditarAtencionRequest;
import com.clinica.atencion.dto.RegistroAtencionRequest;
import com.clinica.atencion.model.AtencionMedica;
import com.clinica.atencion.service.AtencionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/atenciones")
public class MicroAtencionRestController {

    private final AtencionService atencionService;

    public MicroAtencionRestController(AtencionService atencionService) {
        this.atencionService = atencionService;
    }

    @GetMapping
    public List<AtencionMedica> listar(HttpSession session) {
        return atencionService.listar(idMedico(session));
    }

    @GetMapping("/{idAtencion}")
    public ResponseEntity<AtencionMedica> buscar(@PathVariable String idAtencion, HttpSession session) {
        AtencionMedica a = atencionService.buscar(idAtencion, idMedico(session));
        if (a != null) return ResponseEntity.ok(a);
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{idAtencion}/completa")
    public ResponseEntity<AtencionMedicaCompletaDto> buscarCompleta(@PathVariable String idAtencion, HttpSession session) {
        AtencionMedicaCompletaDto dto = atencionService.obtenerCompleta(idAtencion, idMedico(session));
        if (dto != null) return ResponseEntity.ok(dto);
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/buscar")
    public List<AtencionMedica> buscarPorFiltro(@RequestParam(required = false, defaultValue = "") String filtro,
                                                HttpSession session) {
        return atencionService.buscarPorFiltro(filtro, idMedico(session));
    }

    @PostMapping("/generar-numero")
    public Map<String, String> generarNumero() {
        return Map.of("numAtencion", atencionService.generarNumeroAtencion());
    }

    @PostMapping("/grabar")
    public ResponseEntity<AtencionMedicaCompletaDto> grabar(@RequestBody RegistroAtencionRequest request,
                                                            HttpSession session) {
        return ResponseEntity.ok(atencionService.grabar(request, idMedico(session)));
    }

    @PutMapping("/{idAtencion}")
    public ResponseEntity<?> editar(@PathVariable String idAtencion, @RequestBody EditarAtencionRequest request,
                                    HttpSession session) {
        AtencionMedica actualizada = atencionService.editar(idAtencion, request, idMedico(session));
        if (actualizada != null) return ResponseEntity.ok(actualizada);
        return ResponseEntity.notFound().build();
    }

    private String idMedico(HttpSession session) {
        Object id = session.getAttribute("medicoId");
        if (id == null) throw new IllegalStateException("No hay una sesión de médico autenticada.");
        return id.toString();
    }
}
