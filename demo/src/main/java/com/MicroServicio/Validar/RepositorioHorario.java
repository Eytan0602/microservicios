package com.MicroServicio.Validar;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioHorario extends JpaRepository<HorarioMedico, String> {
    List<HorarioMedico> findByIdMedico(Long idMedico);
}