package com.MicroServicio.Atencion;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Repositorio extends JpaRepository<AtencionMedica, String> {
}