package com.MicroServicio.Atencion;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/atenciones")
public class Control {

    @Autowired
    private Servicio servicio;

    @PostMapping
    public ResponseEntity<?> generar(@RequestBody AtencionMedica datos) {
        try {
            AtencionMedica a = servicio.generar(datos.getIdCita());
            if (a == null) {
                return ResponseEntity.status(404).body("La cita no existe");
            }
            return ResponseEntity.ok(a);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    @PutMapping("/{idAtencion}")
    public ResponseEntity<AtencionMedica> registrar(@PathVariable String idAtencion,
                                                    @RequestBody AtencionMedica datos) {
        AtencionMedica a = servicio.registrar(idAtencion, datos);
        if (a == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(a);
    }

    @GetMapping("/{idAtencion}")
    public ResponseEntity<AtencionMedica> buscar(@PathVariable String idAtencion) {
        AtencionMedica a = servicio.buscar(idAtencion);
        if (a == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(a);
    }

    @GetMapping
    public List<AtencionMedica> listar() {
        return servicio.listar();
    }
}