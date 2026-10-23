package com.clinica.catalogo.config;

import com.clinica.catalogo.model.Medicamento;
import com.clinica.catalogo.model.TipoAnalisis;
import com.clinica.catalogo.repository.MedicamentoRepository;
import com.clinica.catalogo.repository.TipoAnalisisRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final MedicamentoRepository medicamentoRepository;
    private final TipoAnalisisRepository tipoAnalisisRepository;

    public DataInitializer(MedicamentoRepository medicamentoRepository, TipoAnalisisRepository tipoAnalisisRepository) {
        this.medicamentoRepository = medicamentoRepository;
        this.tipoAnalisisRepository = tipoAnalisisRepository;
    }

    @Override
    public void run(String... args) {
        if (medicamentoRepository.count() == 0) {
            medicamentoRepository.save(new Medicamento("MED-M01", "Paracetamol 500mg", "Caja x 20 tabletas", 150));
            medicamentoRepository.save(new Medicamento("MED-M02", "Amoxicilina 500mg", "Caja x 30 capsulas", 80));
            medicamentoRepository.save(new Medicamento("MED-M03", "Ibuprofeno 400mg", "Caja x 20 tabletas", 120));
            medicamentoRepository.save(new Medicamento("MED-M04", "Omeprazol 20mg", "Frasco x 30 capsulas", 95));
            medicamentoRepository.save(new Medicamento("MED-M05", "Losartan 50mg", "Caja x 30 tabletas", 110));
            medicamentoRepository.save(new Medicamento("MED-M06", "Cetirizina 10mg", "Caja x 10 tabletas", 65));
            medicamentoRepository.save(new Medicamento("MED-M07", "Azitromicina 500mg", "Caja x 3 tabletas", 50));
            System.out.println(">>> Medicamentos inicializados en H2 memoria.");
        }

        if (tipoAnalisisRepository.count() == 0) {
            tipoAnalisisRepository.save(new TipoAnalisis("TA-01", "Hemograma Completo", "Conteo sanguineo completo y plaquetas", "ACTIVO"));
            tipoAnalisisRepository.save(new TipoAnalisis("TA-02", "Glucosa en Sangre (Basal)", "Medicion de nivel de glucosa en ayunas", "ACTIVO"));
            tipoAnalisisRepository.save(new TipoAnalisis("TA-03", "Perfil Lipidico", "Colesterol total, HDL, LDL y trigliceridos", "ACTIVO"));
            tipoAnalisisRepository.save(new TipoAnalisis("TA-04", "Examen Completo de Orina", "Analisis fisico, quimico y microscopico de orina", "ACTIVO"));
            tipoAnalisisRepository.save(new TipoAnalisis("TA-05", "Radiografia de Torax", "Radiografia anteroposterior pulmonar", "ACTIVO"));
            tipoAnalisisRepository.save(new TipoAnalisis("TA-06", "Electrocardiograma (ECG)", "Evaluacion de actividad electrica cardiaca", "ACTIVO"));
            System.out.println(">>> Tipos de analisis inicializados en H2 memoria.");
        }
    }
}
