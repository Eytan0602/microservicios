package com.clinica.historia.controller;

import com.clinica.historia.model.HistoriaClinica;
import com.clinica.historia.service.HistoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/historias")
@CrossOrigin(origins = "*")
public class HistoriaController {

    private final HistoriaService historiaService;

    public HistoriaController(HistoriaService historiaService) {
        this.historiaService = historiaService;
    }

    @GetMapping
    public List<HistoriaClinica> listar() {
        return historiaService.listar();
    }

    @GetMapping("/{idHC}")
    public ResponseEntity<HistoriaClinica> buscar(@PathVariable String idHC) {
        HistoriaClinica h = historiaService.buscar(idHC);
        if (h != null) {
            return ResponseEntity.ok(h);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{idHC}")
    public ResponseEntity<HistoriaClinica> actualizar(@PathVariable String idHC, @RequestBody HistoriaClinica h) {
        h.setIdHC(idHC);
        return ResponseEntity.ok(historiaService.actualizar(h));
    }

    @PostMapping
    public ResponseEntity<HistoriaClinica> crear(@RequestBody HistoriaClinica h) {
        return ResponseEntity.ok(historiaService.guardar(h));
    }
}
