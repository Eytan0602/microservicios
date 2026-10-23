package com.clinica.receta.repository;

import com.clinica.receta.model.RecetaMedica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecetaMedicaRepository extends JpaRepository<RecetaMedica, String> {
    Optional<RecetaMedica> findByIdAtencion(String idAtencion);
}
