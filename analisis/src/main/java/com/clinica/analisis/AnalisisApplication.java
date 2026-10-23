package com.clinica.analisis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AnalisisApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnalisisApplication.class, args);
        System.out.println(">>> MicroAnalisis iniciado en puerto 8086");
    }
}
