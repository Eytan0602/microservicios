package cita.historia;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/historias")
public class Control {

    @Autowired
    private Servicio servicio;

    @GetMapping
    public List<HistoriaClinica> listar() {
        return servicio.listar();
    }

    @GetMapping("/{idHC}")
    public ResponseEntity<HistoriaClinica> buscar(@PathVariable String idHC) {
        HistoriaClinica h = servicio.buscar(idHC);
        if (h == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(h);
    }

    @PutMapping("/{idHC}")
    public ResponseEntity<HistoriaClinica> actualizar(@PathVariable String idHC,
                                                      @RequestBody HistoriaClinica h) {
        HistoriaClinica a = servicio.actualizar(idHC, h);
        if (a == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(a);
    }

    @GetMapping("/{idHC}/atenciones")
    public List<DetalleHistoria> atenciones(@PathVariable String idHC) {
        return servicio.atenciones(idHC);
    }

    @PostMapping("/atencion/{idAtencion}")
    public ResponseEntity<?> registrarAtencion(@PathVariable String idAtencion) {
        try {
            DetalleHistoria d = servicio.registrarAtencion(idAtencion);
            if (d == null) {
                return ResponseEntity.status(404).body("La atencion o su cita no existen");
            }
            return ResponseEntity.ok(d);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }
}