package com.MicroServicio.Validar;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Servicio {

    @Autowired
    private Repositorio repositorio;

       public void eliminar(Long id) {
        repositorio.deleteById(id);
    }

    public Medico buscar(Long id) {
        return repositorio.findById(id).orElse(null);
    }

    public List<Medico> listar() {
        return repositorio.findAll();
    }

    public Medico actualizar(Long id, Medico m) {
       return repositorio.findById(id).map(medico -> {
            medico.setNombre(m.getNombre());
            medico.setEspecialidad(m.getEspecialidad());
            medico.setClave(m.getClave());
            medico.setUsuario(m.getUsuario()); 
                       return repositorio.save(medico);
        }).orElse(null);
    }

    public boolean grabar(Medico m) {
    if (repositorio.existsByUsuario(m.getUsuario())) {
        return false;
    }
    repositorio.save(m);
    return true;
}

public Medico validar(Entrada e) {
    return repositorio.findFirstByUsuarioAndClave(e.getUsuario(), e.getClave());
}
}