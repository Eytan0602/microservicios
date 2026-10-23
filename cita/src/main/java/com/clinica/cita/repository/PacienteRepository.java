package com.clinica.cita.repository;

import com.clinica.cita.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, String> {
    Optional<Paciente> findByUsuario(String usuario);
    Optional<Paciente> findByUsuarioAndClave(String usuario, String clave);
}
