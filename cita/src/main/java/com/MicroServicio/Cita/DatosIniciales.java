package com.MicroServicio.Cita;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatosIniciales implements CommandLineRunner {
    private final Repositorio citas;
    private final java.time.Clock reloj = java.time.Clock.systemDefaultZone();

    public DatosIniciales(Repositorio citas) {
        this.citas = citas;
    }

    @Override
    public void run(String... args) {
        if (citas.findById("demo-clinica-1").isPresent()) {
            return;
        }

        boolean demoAlreadyPresent = citas.findAll().stream()
            .anyMatch(existing -> existing.getDniPaciente() != null
                && existing.getDniPaciente().startsWith("DEMO-CLINICA-"));
        if (demoAlreadyPresent) {
            return;
        }

        LocalDate hoy = LocalDate.now(reloj);
        citas.save(cita("demo-clinica-1", "María López", "DEMO-CLINICA-1001", 1L,
            proximoDiaLaboral(hoy.plusDays(1), false), "09:30"));
        citas.save(cita("demo-clinica-2", "Pedro Sánchez", "DEMO-CLINICA-1002", 2L,
            proximoDiaLaboral(hoy.plusDays(1), false), "14:30"));
        citas.save(cita("demo-clinica-3", "Lucía Herrera", "DEMO-CLINICA-1003", 3L,
            proximoDiaLaboral(hoy.plusDays(1), true), "08:30"));
    }

        private CitaMedica cita(String idCita, String paciente, String dni, Long idMedico, LocalDate fecha, String hora) {
        CitaMedica cita = new CitaMedica();
        LocalDateTime inicio = LocalDateTime.of(fecha, LocalTime.parse(hora));
        cita.setIdCita(idCita);
        cita.setPaciente(paciente);
        cita.setDniPaciente(dni);
        cita.setIdMedico(idMedico);
        cita.setHoraInicio(inicio);
        cita.setHoraFin(inicio.plusMinutes(30));
        cita.setEstado("PENDIENTE");
        return cita;
    }

    private LocalDate proximoDiaLaboral(LocalDate fecha, boolean incluyeSabado) {
        while (fecha.getDayOfWeek() == DayOfWeek.SUNDAY
                || (!incluyeSabado && fecha.getDayOfWeek() == DayOfWeek.SATURDAY)) {
            fecha = fecha.plusDays(1);
        }
        return fecha;
    }
}