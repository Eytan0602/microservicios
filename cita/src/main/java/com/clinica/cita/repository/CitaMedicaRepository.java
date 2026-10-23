package com.clinica.cita.repository;

import com.clinica.cita.model.CitaMedica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CitaMedicaRepository extends JpaRepository<CitaMedica, String> {
    List<CitaMedica> findByEstado(String estado);
    List<CitaMedica> findByIdMedico(String idMedico);
    List<CitaMedica> findByIdPaciente(String idPaciente);
    List<CitaMedica> findByIdMedicoAndEstado(String idMedico, String estado);
    List<CitaMedica> findByIdMedicoAndEstadoIn(String idMedico, List<String> estados);
}
