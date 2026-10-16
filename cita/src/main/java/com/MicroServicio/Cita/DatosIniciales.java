package com.MicroServicio.Cita;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatosIniciales implements CommandLineRunner {
    private final Repositorio citas;

    public DatosIniciales(Repositorio citas) {
        this.citas = citas;
    }

    @Override
    public void run(String... args) {
        if (citas.count() > 0) {
            return;
        }

        citas.save(cita("María López", "12345678", 1L,
                proximoDiaLaboral(LocalDate.now().plusDays(1), false), "09:30"));
        citas.save(cita("Pedro Sánchez", "23456789", 2L,
                proximoDiaLaboral(LocalDate.now().plusDays(1), false), "14:30"));
        citas.save(cita("Lucía Herrera", "34567890", 3L,
                proximoDiaLaboral(LocalDate.now().plusDays(1), true), "08:30"));
    }

    private CitaMedica cita(String paciente, String dni, Long idMedico, LocalDate fecha, String hora) {
        CitaMedica cita = new CitaMedica();
        LocalDateTime inicio = LocalDateTime.of(fecha, LocalTime.parse(hora));
        cita.setIdCita(UUID.randomUUID().toString());
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