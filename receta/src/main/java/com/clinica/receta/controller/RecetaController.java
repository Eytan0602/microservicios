package com.clinica.receta.controller;

import com.clinica.receta.model.LineaReceta;
import com.clinica.receta.model.RecetaMedica;
import com.clinica.receta.service.RecetaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/recetas")
@CrossOrigin(origins = "*")
public class RecetaController {

    private final RecetaService recetaService;

    public RecetaController(RecetaService recetaService) {
        this.recetaService = recetaService;
    }

    @GetMapping
    public List<RecetaMedica> listarTodas() {
        return recetaService.listarTodas();
    }

    @GetMapping("/cesta")
    public List<LineaReceta> listarCesta() {
        return recetaService.listar();
    }

    @PostMapping("/cesta")
    public ResponseEntity<LineaReceta> agregarACesta(@RequestBody LineaReceta linea) {
        return ResponseEntity.ok(recetaService.agregar(linea));
    }

    @DeleteMapping("/cesta/{idLR}")
    public ResponseEntity<?> quitarDeCesta(@PathVariable String idLR) {
        recetaService.quitar(idLR);
        return ResponseEntity.ok(Map.of("mensaje", "Item removido de la cesta", "idLR", idLR));
    }

    @DeleteMapping("/cesta")
    public ResponseEntity<?> limpiarCesta() {
        recetaService.nuevo();
        return ResponseEntity.ok(Map.of("mensaje", "Cesta de recetas reiniciada"));
    }

    @PostMapping("/grabar")
    public ResponseEntity<RecetaMedica> grabar(@RequestBody RecetaMedica receta) {
        return ResponseEntity.ok(recetaService.grabar(receta));
    }

    @GetMapping("/atencion/{idAtencion}")
    public ResponseEntity<RecetaMedica> buscarPorAtencion(@PathVariable String idAtencion) {
        RecetaMedica r = recetaService.buscarPorAtencion(idAtencion);
        if (r != null) return ResponseEntity.ok(r);
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecetaMedica> buscarPorId(@PathVariable String id) {
        RecetaMedica r = recetaService.buscarPorId(id);
        if (r != null) return ResponseEntity.ok(r);
        return ResponseEntity.notFound().build();
    }
}
