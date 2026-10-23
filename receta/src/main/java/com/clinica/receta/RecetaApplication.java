package com.clinica.receta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RecetaApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecetaApplication.class, args);
        System.out.println(">>> MicroReceta iniciado en puerto 8085");
    }
}
