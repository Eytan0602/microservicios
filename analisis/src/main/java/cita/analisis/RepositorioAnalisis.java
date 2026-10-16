package cita.analisis;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioAnalisis extends JpaRepository<AnalisisClinico, String> {
    List<AnalisisClinico> findByIdAtencion(String idAtencion);
}