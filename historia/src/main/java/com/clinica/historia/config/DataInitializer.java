package com.clinica.historia.config;

import com.clinica.historia.model.HistoriaClinica;
import com.clinica.historia.repository.HistoriaClinicaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    private final HistoriaClinicaRepository historiaRepository;

    public DataInitializer(HistoriaClinicaRepository historiaRepository) {
        this.historiaRepository = historiaRepository;
    }

    @Override
    public void run(String... args) {
        if (historiaRepository.count() == 0) {
            historiaRepository.save(new HistoriaClinica("HC-100", "Mario Vargas", LocalDate.of(2026, 1, 5), "ACTIVA", "Sin antecedentes previos"));
            historiaRepository.save(new HistoriaClinica("HC-101", "Roberto Silva", LocalDate.of(2026, 1, 10), "ACTIVA", "Hipertension arterial controlada con Losartan."));
            historiaRepository.save(new HistoriaClinica("HC-102", "Lucia Fernandez", LocalDate.of(2026, 2, 15), "ACTIVA", "Alergia confirmada a la penicilina."));
            historiaRepository.save(new HistoriaClinica("HC-103", "Jorge Ramos", LocalDate.of(2026, 3, 1), "ACTIVA", "Sin antecedentes patologicos relevantes."));
            System.out.println(">>> Historias clinicas inicializadas en H2 memoria.");
        }
    }
}
