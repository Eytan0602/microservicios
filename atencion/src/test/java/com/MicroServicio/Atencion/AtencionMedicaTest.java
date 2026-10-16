package com.MicroServicio.Atencion;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class AtencionMedicaTest {

    @Test
    void debeRegistrarDatosDelPacienteYMedicoEnLaAtencion() {
        AtencionMedica atencion = new AtencionMedica();
        atencion.setIdPaciente("P-1001");
        atencion.setIdMedico("M-2002");

        assertEquals("P-1001", atencion.getIdPaciente());
        assertEquals("M-2002", atencion.getIdMedico());
    }
}
