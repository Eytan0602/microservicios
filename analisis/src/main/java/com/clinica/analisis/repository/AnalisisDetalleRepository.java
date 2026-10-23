package com.clinica.analisis.repository;

import com.clinica.analisis.model.AnalisisDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnalisisDetalleRepository extends JpaRepository<AnalisisDetalle, String> {
    List<AnalisisDetalle> findByAnalisisClinicoIdAnalisis(String idAnalisis);
}
