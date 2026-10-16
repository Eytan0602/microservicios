package cita.analisis;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/analisis")
public class Control {

    @Autowired
    private Servicio servicio;

    @PostMapping("/lineas")
    public ResponseEntity<?> agregar(@RequestBody LineaAnalisis lin) {
        LineaAnalisis l = servicio.agregar(lin);
        if (l == null) {
            return ResponseEntity.status(404).body("El tipo de analisis no existe en el catalogo");
        }
        return ResponseEntity.ok(l);
    }

    @GetMapping("/lineas")
    public List<LineaAnalisis> listar() {
        return servicio.listar();
    }

    @DeleteMapping("/lineas/{idLA}")
    public ResponseEntity<Void> quitar(@PathVariable String idLA) {
        if (!servicio.quitar(idLA)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/nuevo")
    public void nuevo() {
        servicio.nuevo();
    }

    @PostMapping
    public ResponseEntity<?> grabar(@RequestBody AnalisisClinico a) {
        try {
            AnalisisClinico g = servicio.grabar(a);
            if (g == null) {
                return ResponseEntity.status(404).body("La atencion no existe");
            }
            return ResponseEntity.ok(g);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    @GetMapping("/atencion/{idAtencion}")
    public List<AnalisisClinico> porAtencion(@PathVariable String idAtencion) {
        return servicio.buscarPorAtencion(idAtencion);
    }

    @GetMapping("/{idAnalisis}/lineas")
    public List<LineaAnalisis> lineas(@PathVariable String idAnalisis) {
        return servicio.lineasDeAnalisis(idAnalisis);
    }
}