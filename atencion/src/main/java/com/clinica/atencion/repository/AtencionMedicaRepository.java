package com.clinica.atencion.repository;

import com.clinica.atencion.model.AtencionMedica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AtencionMedicaRepository extends JpaRepository<AtencionMedica, String> {
    List<AtencionMedica> findAllByIdMedicoOrderByFechaDesc(String idMedico);
    Optional<AtencionMedica> findByIdAtencionAndIdMedico(String idAtencion, String idMedico);
    List<AtencionMedica> findByIdMedicoAndIdHC(String idMedico, String idHC);
    List<AtencionMedica> findByIdHCOrderByFechaDesc(String idHC);
    @Query("SELECT a FROM AtencionMedica a WHERE " +
           "a.idMedico = :idMedico AND (" +
           "LOWER(a.idAtencion) LIKE LOWER(CONCAT('%', :filtro, '%')) OR " +
           "LOWER(a.idCita) LIKE LOWER(CONCAT('%', :filtro, '%')) OR " +
           "LOWER(a.paciente) LIKE LOWER(CONCAT('%', :filtro, '%')) OR " +
           "LOWER(a.idHC) LIKE LOWER(CONCAT('%', :filtro, '%'))) " +
           "ORDER BY a.fecha DESC")
    List<AtencionMedica> buscarPorFiltro(@Param("filtro") String filtro, @Param("idMedico") String idMedico);

}
