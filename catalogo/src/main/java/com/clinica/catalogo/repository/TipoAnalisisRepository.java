package com.clinica.catalogo.repository;

import com.clinica.catalogo.model.TipoAnalisis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoAnalisisRepository extends JpaRepository<TipoAnalisis, String> {
}
