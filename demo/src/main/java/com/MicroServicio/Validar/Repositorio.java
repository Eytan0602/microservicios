package com.MicroServicio.Validar;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Repositorio extends JpaRepository<Medico, Long> {
    Medico findFirstByUsuarioAndClave(String usuario, String clave);
    boolean existsByUsuario(String usuario);
}