package com.clinica.historia.repository;

import com.clinica.historia.model.HistoriaClinica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HistoriaClinicaRepository extends JpaRepository<HistoriaClinica, String> {
    Optional<HistoriaClinica> findByPacienteIgnoreCase(String paciente);
}
