package cita.analisis;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioLinea extends JpaRepository<LineaAnalisis, String> {
    List<LineaAnalisis> findByIdAnalisis(String idAnalisis);
}