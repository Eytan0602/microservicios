package com.MicroServicio.Cita;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Repositorio extends JpaRepository<CitaMedica, String> {
    List<CitaMedica> findByIdMedico(Long idMedico);
    List<CitaMedica> findByIdMedicoAndEstado(Long idMedico, String estado);
}