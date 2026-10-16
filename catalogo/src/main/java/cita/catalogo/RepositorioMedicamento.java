package cita.catalogo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioMedicamento extends JpaRepository<Medicamento, String> {
}