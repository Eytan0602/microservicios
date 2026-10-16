package cita.catalogo;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/catalogo")
public class Control {

    private final Servicio servicio;

    public Control(Servicio servicio) {
        this.servicio = servicio;
    }

    @GetMapping("/medicamentos")
    public List<Medicamento> listarMedicamentos() {
        return servicio.listarMedicamentos();
    }

    @GetMapping("/medicamentos/{id}")
    public ResponseEntity<Medicamento> buscarMedicamento(@PathVariable String id) {
        Medicamento m = servicio.buscarMedicamento(id);
        if (m == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(m);
    }

    @PostMapping("/medicamentos")
    public Medicamento grabarMedicamento(@RequestBody Medicamento m) {
        return servicio.grabarMedicamento(m);
    }

    @GetMapping("/tipos-analisis")
    public List<TipoAnalisis> listarTiposAnalisis() {
        return servicio.listarTiposAnalisis();
    }

    @GetMapping("/tipos-analisis/{id}")
    public ResponseEntity<TipoAnalisis> buscarTipoAnalisis(@PathVariable String id) {
        TipoAnalisis t = servicio.buscarTipoAnalisis(id);
        if (t == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(t);
    }

    @PostMapping("/tipos-analisis")
    public TipoAnalisis grabarTipoAnalisis(@RequestBody TipoAnalisis t) {
        return servicio.grabarTipoAnalisis(t);
    }
}