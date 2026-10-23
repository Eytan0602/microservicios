package com.clinica.cita;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CitaApplication {

    public static void main(String[] args) {
        SpringApplication.run(CitaApplication.class, args);
        System.out.println(">>> MicroCita iniciado en puerto 8082");
    }
}
