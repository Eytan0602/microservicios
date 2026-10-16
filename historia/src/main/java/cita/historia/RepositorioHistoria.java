package cita.historia;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioHistoria extends JpaRepository<HistoriaClinica, String> {
    Optional<HistoriaClinica> findByPaciente(String paciente);
    Optional<HistoriaClinica> findByDni(String dni);

}