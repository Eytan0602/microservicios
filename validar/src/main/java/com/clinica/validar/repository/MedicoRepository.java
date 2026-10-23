package com.clinica.validar.repository;

import com.clinica.validar.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, String> {
    Optional<Medico> findByUsuarioAndClave(String usuario, String clave);
    Optional<Medico> findByUsuario(String usuario);
}
