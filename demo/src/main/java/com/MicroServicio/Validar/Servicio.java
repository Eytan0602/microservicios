package com.MicroServicio.Validar;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class Servicio {

    private final Repositorio repositorio;
    private final RepositorioHorario repositorioHorario;

    public Servicio(Repositorio repositorio, RepositorioHorario repositorioHorario) {
        this.repositorio = repositorio;
        this.repositorioHorario = repositorioHorario;
    }

    public List<HorarioMedico> listarHorarios(Long idMedico) {
        return repositorioHorario.findByIdMedico(idMedico);
    }

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
    if (m.getId() == null) {
        Medico ultimo = repositorio.findFirstByOrderByIdDesc();
        m.setId(ultimo == null ? 1L : ultimo.getId() + 1L);
    }
    repositorio.save(m);
    return true;
}

public Medico validar(Entrada e) {
    return repositorio.findFirstByUsuarioAndClave(e.getUsuario(), e.getClave());
}
}