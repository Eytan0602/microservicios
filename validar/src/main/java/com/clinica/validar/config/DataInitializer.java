package com.clinica.validar.config;

import com.clinica.validar.model.Medico;
import com.clinica.validar.repository.MedicoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Component
public class DataInitializer implements CommandLineRunner {

    private final MedicoRepository medicoRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public DataInitializer(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    @Override
    public void run(String... args) {
        if (medicoRepository.count() == 0) {
            medicoRepository.save(new Medico("MED-01", "Dr. Carlos Mendoza", "Medicina General", "cmendoza", passwordEncoder.encode("1234")));
            medicoRepository.save(new Medico("MED-02", "Dra. Ana Gómez", "Cardiología", "agomez", passwordEncoder.encode("1234")));
            medicoRepository.save(new Medico("MED-03", "Dr. Juan Pérez", "Pediatría", "jperez", passwordEncoder.encode("1234")));
            System.out.println(">>> Medicos inicializados en H2 memoria.");
        }
    }
}
