package com.clinica.cita.config;

import com.clinica.cita.model.CitaMedica;
import com.clinica.cita.repository.CitaMedicaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CitaMedicaRepository citaRepository;

    public DataInitializer(CitaMedicaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    @Override
    public void run(String... args) {
        if (citaRepository.count() == 0) {
            citaRepository.save(new CitaMedica("CIT-001", "Roberto Silva", LocalDateTime.now().withHour(9).withMinute(0), null, "MED-01", "HC-101", "PENDIENTE"));
            citaRepository.save(new CitaMedica("CIT-002", "Lucia Fernandez", LocalDateTime.now().withHour(10).withMinute(30), null, "MED-01", "HC-102", "PENDIENTE"));
            citaRepository.save(new CitaMedica("CIT-003", "Jorge Ramos", LocalDateTime.now().withHour(11).withMinute(45), null, "MED-02", "HC-103", "PENDIENTE"));
            System.out.println(">>> Citas medicas inicializadas en H2 memoria.");
        }
    }
}
