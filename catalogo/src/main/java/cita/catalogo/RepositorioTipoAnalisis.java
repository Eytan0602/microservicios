package cita.catalogo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioTipoAnalisis extends JpaRepository<TipoAnalisis, String> {
}