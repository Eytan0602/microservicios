package cita.receta;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioReceta extends JpaRepository<RecetaMedica, String> {
    List<RecetaMedica> findByIdAtencion(String idAtencion);
}