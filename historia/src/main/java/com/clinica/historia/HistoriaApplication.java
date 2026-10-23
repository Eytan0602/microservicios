package com.clinica.historia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HistoriaApplication {

    public static void main(String[] args) {
        SpringApplication.run(HistoriaApplication.class, args);
        System.out.println(">>> MicroHistoria iniciado en puerto 8083");
    }
}
