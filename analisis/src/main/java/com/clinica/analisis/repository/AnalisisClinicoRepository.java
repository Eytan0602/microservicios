package com.clinica.analisis.repository;

import com.clinica.analisis.model.AnalisisClinico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnalisisClinicoRepository extends JpaRepository<AnalisisClinico, String> {
    Optional<AnalisisClinico> findByIdAtencion(String idAtencion);
}
