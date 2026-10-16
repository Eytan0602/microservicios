package cita.receta;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recetas")
public class Control {

    @Autowired
    private Servicio servicio;

    @PostMapping("/{idAtencion}/lineas")
    public ResponseEntity<?> agregar(@PathVariable String idAtencion, @RequestBody LineaReceta linea) {
        LineaReceta l = servicio.agregar(idAtencion, linea);
        if (l == null) {
            return ResponseEntity.status(404).body("Medicamento invalido o no existe en el catalogo");
        }
        return ResponseEntity.ok(l);
    }

    @GetMapping("/{idAtencion}/lineas")
    public List<LineaReceta> listar(@PathVariable String idAtencion) {
        return servicio.listar(idAtencion);
    }

    @DeleteMapping("/{idAtencion}/lineas/{idLR}")
    public ResponseEntity<Void> quitar(@PathVariable String idAtencion, @PathVariable String idLR) {
        if (!servicio.quitar(idAtencion, idLR)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{idAtencion}/nuevo")
    public void nuevo(@PathVariable String idAtencion) {
        servicio.nuevo(idAtencion);
    }

    @PostMapping("/{idAtencion}")
    public ResponseEntity<?> grabar(@PathVariable String idAtencion, @RequestBody RecetaMedica r) {
        try {
            RecetaMedica g = servicio.grabar(idAtencion, r);
            if (g == null) {
                return ResponseEntity.status(404).body("La atencion no existe");
            }
            return ResponseEntity.ok(g);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    @GetMapping("/atencion/{idAtencion}")
    public List<RecetaMedica> porAtencion(@PathVariable String idAtencion) {
        return servicio.buscarPorAtencion(idAtencion);
    }

    @GetMapping("/{idReceta}/detalle")
    public List<LineaReceta> detalle(@PathVariable String idReceta) {
        return servicio.lineasDeReceta(idReceta);
    }
}