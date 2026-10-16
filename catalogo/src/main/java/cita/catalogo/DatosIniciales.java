package cita.catalogo;

import java.util.UUID;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatosIniciales implements CommandLineRunner {
    private final RepositorioMedicamento medicamentos;
    private final RepositorioTipoAnalisis tiposAnalisis;

    public DatosIniciales(RepositorioMedicamento medicamentos, RepositorioTipoAnalisis tiposAnalisis) {
        this.medicamentos = medicamentos;
        this.tiposAnalisis = tiposAnalisis;
    }

    @Override
    public void run(String... args) {
        if (medicamentos.count() == 0) {
            medicamentos.save(medicamento("Paracetamol", "Genérico", "Analgésico y antipirético", 500, "Tableta"));
            medicamentos.save(medicamento("Ibuprofeno", "Genérico", "Antiinflamatorio no esteroideo", 400, "Tableta"));
            medicamentos.save(medicamento("Amoxicilina", "Genérico", "Antibiótico de uso bajo indicación médica", 500, "Cápsula"));
            medicamentos.save(medicamento("Loratadina", "Genérico", "Antihistamínico", 10, "Tableta"));
        }

        if (tiposAnalisis.count() == 0) {
            tiposAnalisis.save(tipo("Hemograma completo", "Evalúa células sanguíneas como glóbulos rojos, blancos y plaquetas."));
            tiposAnalisis.save(tipo("Glucosa en sangre", "Mide la concentración de glucosa en una muestra de sangre."));
            tiposAnalisis.save(tipo("Perfil lipídico", "Incluye mediciones de colesterol y triglicéridos."));
            tiposAnalisis.save(tipo("Examen general de orina", "Evalúa características físicas, químicas y microscópicas de la orina."));
        }
    }

    private Medicamento medicamento(String nombre, String marca, String descripcion, int miligramos, String presentacion) {
        Medicamento medicamento = new Medicamento();
        medicamento.setIdMedicamento(UUID.randomUUID().toString());
        medicamento.setNombre(nombre);
        medicamento.setMarca(marca);
        medicamento.setDescripcion(descripcion);
        medicamento.setMiligramos(miligramos);
        medicamento.setPresentacion(presentacion);
        medicamento.setStock(100);
        return medicamento;
    }

    private TipoAnalisis tipo(String nombre, String descripcion) {
        TipoAnalisis tipo = new TipoAnalisis();
        tipo.setIdTA(UUID.randomUUID().toString());
        tipo.setNombre(nombre);
        tipo.setDescripcion(descripcion);
        tipo.setEstado("ACTIVO");
        return tipo;
    }
}