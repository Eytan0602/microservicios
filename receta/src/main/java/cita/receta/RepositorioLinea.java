package cita.receta;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioLinea extends JpaRepository<LineaReceta, String> {
    List<LineaReceta> findByIdReceta(String idReceta);
}