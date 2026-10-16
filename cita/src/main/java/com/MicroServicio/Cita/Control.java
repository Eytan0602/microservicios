package com.MicroServicio.Cita;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/citas")
public class Control {

    private final Servicio servicio;

    public Control(Servicio servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public List<CitaMedica> listar() {
        return servicio.listar();
    }

    @GetMapping("/{idCita}")
    public ResponseEntity<CitaMedica> buscar(@PathVariable String idCita) {
        CitaMedica c = servicio.buscar(idCita);
        if (c == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(c);
    }

    @GetMapping("/medico/{idMedico}")
    public List<CitaMedica> listarPorMedico(@PathVariable Long idMedico) {
        return servicio.listarPorMedico(idMedico);
    }
    @GetMapping("/medico/{idMedico}/pendientes")
    public List<CitaMedica> listarPendientes(@PathVariable Long idMedico) {
    return servicio.listarPendientesPorMedico(idMedico);
    }   
    @PutMapping("/{idCita}/atendida")
    public ResponseEntity<CitaMedica> marcarAtendida(@PathVariable String idCita) {
    CitaMedica c = servicio.marcarAtendida(idCita);
    if (c == null) return ResponseEntity.notFound().build();
    return ResponseEntity.ok(c);
}
    @PostMapping
    public CitaMedica grabar(@RequestBody CitaMedica c) {
        return servicio.grabar(c);
    }
}