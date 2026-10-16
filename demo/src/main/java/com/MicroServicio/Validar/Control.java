package com.MicroServicio.Validar;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicos")
public class Control {

    @Autowired
    private Servicio servicio;

    @PostMapping("/validar")
    public ResponseEntity<Medico> validar(@RequestBody Entrada e) {
        Medico m = servicio.validar(e);
        if (m == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(m);
    }

    @PostMapping
    public ResponseEntity<?> grabar(@RequestBody Medico m) {
    if (!servicio.grabar(m)) {
        return ResponseEntity.status(409).body("El usuario ya existe");
    }
    return ResponseEntity.ok().build();
}

    @GetMapping
    public List<Medico> listar() {
        return servicio.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medico> buscar(@PathVariable Long id) {
        Medico m = servicio.buscar(id);
        if (m == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(m);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medico> actualizar(@PathVariable Long id, @RequestBody Medico m) {
        Medico actualizado = servicio.actualizar(id, m);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
    }
}