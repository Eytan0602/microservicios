package cita.historia;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioDetalle extends JpaRepository<DetalleHistoria, String> {
    List<DetalleHistoria> findByIdHC(String idHC);
    boolean existsByIdAtencion(String idAtencion);
    Optional<DetalleHistoria> findByIdAtencion(String idAtencion);
}