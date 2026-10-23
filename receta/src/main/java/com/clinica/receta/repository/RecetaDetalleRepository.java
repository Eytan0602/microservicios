package com.clinica.receta.repository;

import com.clinica.receta.model.RecetaDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecetaDetalleRepository extends JpaRepository<RecetaDetalle, String> {
    List<RecetaDetalle> findByRecetaMedicaIdReceta(String idReceta);
}
