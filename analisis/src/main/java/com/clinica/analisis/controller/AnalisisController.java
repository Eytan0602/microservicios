package com.clinica.analisis.controller;

import com.clinica.analisis.model.AnalisisClinico;
import com.clinica.analisis.model.LineaAnalisis;
import com.clinica.analisis.service.AnalisisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/analisis")
@CrossOrigin(origins = "*")
public class AnalisisController {

    private final AnalisisService analisisService;

    public AnalisisController(AnalisisService analisisService) {
        this.analisisService = analisisService;
    }

    @GetMapping
    public List<AnalisisClinico> listarTodos() {
        return analisisService.listarTodos();
    }

    @GetMapping("/cesta")
    public List<LineaAnalisis> listarCesta() {
        return analisisService.listar();
    }

    @PostMapping("/cesta")
    public ResponseEntity<LineaAnalisis> agregarACesta(@RequestBody LineaAnalisis linea) {
        return ResponseEntity.ok(analisisService.agregar(linea));
    }

    @DeleteMapping("/cesta/{idLA}")
    public ResponseEntity<?> quitarDeCesta(@PathVariable String idLA) {
        analisisService.quitar(idLA);
        return ResponseEntity.ok(Map.of("mensaje", "Item removido de la cesta", "idLA", idLA));
    }

    @DeleteMapping("/cesta")
    public ResponseEntity<?> limpiarCesta() {
        analisisService.nuevo();
        return ResponseEntity.ok(Map.of("mensaje", "Cesta de analisis reiniciada"));
    }

    @PostMapping("/grabar")
    public ResponseEntity<AnalisisClinico> grabar(@RequestBody AnalisisClinico analisis) {
        return ResponseEntity.ok(analisisService.grabar(analisis));
    }

    @GetMapping("/atencion/{idAtencion}")
    public ResponseEntity<AnalisisClinico> buscarPorAtencion(@PathVariable String idAtencion) {
        AnalisisClinico a = analisisService.buscarPorAtencion(idAtencion);
        if (a != null) return ResponseEntity.ok(a);
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnalisisClinico> buscarPorId(@PathVariable String id) {
        AnalisisClinico a = analisisService.buscarPorId(id);
        if (a != null) return ResponseEntity.ok(a);
        return ResponseEntity.notFound().build();
    }
}
