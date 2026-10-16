package com.MicroServicio.Validar;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicos")
public class Control {

    private final Servicio servicio;
    private final TokenService tokenService;

    public Control(Servicio servicio, TokenService tokenService) {
        this.servicio = servicio;
        this.tokenService = tokenService;
    }

    @PostMapping("/validar")
    public ResponseEntity<RespuestaLogin> validar(@RequestBody Entrada e) {
        Medico m = servicio.validar(e);
        if (m == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(new RespuestaLogin(m, tokenService.emitir(m.getId())));
    }

    @PostMapping
    public ResponseEntity<?> grabar(@RequestBody Medico m) {
    if (!servicio.grabar(m)) {
        return ResponseEntity.status(409).body("El usuario ya existe");
    }
    return ResponseEntity.ok().build();
}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
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

    @GetMapping("/{id}/horarios")
    public List<HorarioMedico> listarHorarios(@PathVariable Long id) {
        return servicio.listarHorarios(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medico> actualizar(@PathVariable Long id, @RequestBody Medico m) {
        Medico actualizado = servicio.actualizar(id, m);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizado);
    }

}