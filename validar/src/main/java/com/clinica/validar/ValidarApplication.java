package com.clinica.validar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ValidarApplication {

    public static void main(String[] args) {
        SpringApplication.run(ValidarApplication.class, args);
        System.out.println(">>> MicroValidar iniciado en puerto 8081");
    }
    
}
