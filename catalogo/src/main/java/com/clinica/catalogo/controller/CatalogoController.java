package com.clinica.catalogo.controller;

import com.clinica.catalogo.model.Medicamento;
import com.clinica.catalogo.model.TipoAnalisis;
import com.clinica.catalogo.service.CatalogoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalogo")
@CrossOrigin(origins = "*")
public class CatalogoController {

    private final CatalogoService catalogoService;

    public CatalogoController(CatalogoService catalogoService) {
        this.catalogoService = catalogoService;
    }

    @GetMapping("/medicamentos")
    public List<Medicamento> listarMedicamentos() {
        return catalogoService.listarMedicamentos();
    }

    @GetMapping("/medicamentos/{id}")
    public ResponseEntity<Medicamento> buscarMedicamento(@PathVariable String id) {
        Medicamento m = catalogoService.buscarMedicamento(id);
        if (m != null) return ResponseEntity.ok(m);
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/analisis")
    public List<TipoAnalisis> listarTiposAnalisis() {
        return catalogoService.listarTiposAnalisis();
    }

    @GetMapping("/analisis/{id}")
    public ResponseEntity<TipoAnalisis> buscarTipoAnalisis(@PathVariable String id) {
        TipoAnalisis ta = catalogoService.buscarTipoAnalisis(id);
        if (ta != null) return ResponseEntity.ok(ta);
        return ResponseEntity.notFound().build();
    }
}
