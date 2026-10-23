package com.clinica.atencion;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class AtencionApplication {

    public static void main(String[] args) {
        SpringApplication.run(AtencionApplication.class, args);
        System.out.println("==========================================================");
        System.out.println(" MicroAtencion + Web Thymeleaf iniciado en puerto 8080!");
        System.out.println(" Web: http://localhost:8080");
        System.out.println(" Base de datos H2 en memoria");
        System.out.println("==========================================================");
    }

    @Bean
public RestTemplate restTemplate(@Value("${microservicio.internal-token}") String token) {
    RestTemplate rt = new RestTemplate();
    rt.getInterceptors().add((request, body, execution) -> {
        request.getHeaders().set("X-Internal-Token", token);
        return execution.execute(request, body);
    });
    return rt;
}}
