package com.MicroServicio.Validar;

import java.time.LocalTime;
import java.util.UUID;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatosIniciales implements CommandLineRunner {
    private final Repositorio medicos;
    private final RepositorioHorario horarios;

    public DatosIniciales(Repositorio medicos, RepositorioHorario horarios) {
        this.medicos = medicos;
        this.horarios = horarios;
    }

    @Override
    public void run(String... args) {
        if (medicos.count() == 0) {
            medicos.save(medico(1L, "Juan Pérez", "Medicina general", "jperez", "1234"));
            medicos.save(medico(2L, "Ana Torres", "Pediatría", "atorres", "1234"));
            medicos.save(medico(3L, "Carlos Rojas", "Cardiología", "crojas", "1234"));
        }

        if (horarios.count() == 0) {
            horarios.save(horario(1L, "Lunes a viernes", "09:00", "13:00"));
            horarios.save(horario(2L, "Lunes a viernes", "14:00", "18:00"));
            horarios.save(horario(3L, "Martes a sábado", "08:00", "12:00"));
        }
    }

    private Medico medico(Long id, String nombre, String especialidad, String usuario, String clave) {
        Medico medico = new Medico(id, nombre, especialidad, clave, usuario);
        return medico;
    }

    private HorarioMedico horario(Long idMedico, String dias, String inicio, String fin) {
        HorarioMedico horario = new HorarioMedico();
        horario.setIdHorario(UUID.randomUUID().toString());
        horario.setIdMedico(idMedico);
        horario.setDias(dias);
        horario.setHoraInicio(LocalTime.parse(inicio));
        horario.setHoraFin(LocalTime.parse(fin));
        return horario;
    }
}