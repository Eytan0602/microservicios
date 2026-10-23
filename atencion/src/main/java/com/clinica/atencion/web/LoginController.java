package com.clinica.atencion.web;

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {

    private final RestTemplate restTemplate;

    @Value("${microservicio.validar.url:http://localhost:8081/validar}")
    private String urlValidar;

    @Value("${microservicio.cita.url:http://localhost:8082/citas}")
    private String urlCita;

    public LoginController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String autenticar(@RequestParam String usuario, @RequestParam String clave,
                             HttpServletRequest request, Model model) {
        try {
            ResponseEntity<Map> respuesta = restTemplate.postForEntity(
                    urlValidar, Map.of("usuario", usuario, "clave", clave), Map.class);
            Map medico = respuesta.getBody();
            if (medico == null || medico.get("idMedico") == null) {
                model.addAttribute("error", "Usuario o clave incorrectos.");
                return "login";
            }
            HttpSession session = request.getSession(false);
            if (session != null) session.invalidate();
            session = request.getSession(true);
            session.setAttribute("medicoId", medico.get("idMedico").toString());
            session.setAttribute("medicoNombre", medico.get("nombre").toString());
            return "redirect:/";
        } catch (RestClientResponseException ex) {
            model.addAttribute("error", "Usuario o clave incorrectos.");
            return "login";
        } catch (Exception ex) {
            model.addAttribute("error", "No se pudo conectar con el servicio de validación.");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/paciente/logout")
    public String logoutPaciente(HttpSession session) {
        session.invalidate();
        return "redirect:/paciente/login";
    }

    @GetMapping("/paciente/login")
    public String loginPaciente() {
        return "paciente-login";
    }

    @PostMapping("/paciente/login")
    public String autenticarPaciente(@RequestParam String usuario, @RequestParam String clave,
                                    HttpServletRequest request, Model model) {
        try {
            Object paciente = restTemplate.postForObject(
                urlCita + "/pacientes/login", Map.of("usuario", usuario, "clave", clave), Object.class);
            iniciarSesionPaciente(paciente, request);
            return "redirect:/paciente/inicio";
        } catch (Exception ex) {
            model.addAttribute("error", "Usuario o clave incorrectos.");
            return "paciente-login";
        }
    }

    @GetMapping("/paciente/registro")
    public String registroPaciente() {
        return "paciente-registro";
    }

    @PostMapping("/paciente/registro")
    public String registrarPaciente(@RequestParam String nombre, @RequestParam String usuario,
                                    @RequestParam String clave, @RequestParam(required = false) String telefono,
                                    HttpServletRequest request, Model model) {
        try {
                Object paciente = restTemplate.postForObject(urlCita + "/pacientes", Map.of(
                    "nombre", nombre, "usuario", usuario, "clave", clave,
                    "telefono", telefono == null ? "" : telefono), Object.class);
                iniciarSesionPaciente(paciente, request);
            return "redirect:/paciente/inicio";
        } catch (RestClientResponseException ex) {
            model.addAttribute("error", ex.getResponseBodyAsString());
            return "paciente-registro";
        } catch (Exception ex) {
            model.addAttribute("error", "No se pudo completar el registro.");
            return "paciente-registro";
        }
    }
@GetMapping("/medico/registro")
public String registroMedico() {
    return "registro-medico";
}

@PostMapping("/medico/registro")
public String registrarMedico(
        @RequestParam String nombre,
        @RequestParam(required = false) String especialidad,
        @RequestParam String usuario,
        @RequestParam String clave,
        Model model) {

    try {
        Map<String, String> body = new HashMap<>();
        body.put("nombre", nombre.trim());
        body.put("especialidad",
                especialidad == null ? "" : especialidad.trim());
        body.put("usuario", usuario.trim());
        body.put("clave", clave);

        restTemplate.postForEntity(
                urlValidar + "/medicos", body, Map.class);

        return "redirect:/login";

    } catch (RestClientResponseException ex) {
        String msg = ex.getStatusCode().value() == 409
                ? "Ya existe ese usuario."
                : "No se pudo crear la cuenta ("
                    + ex.getStatusCode().value() + ").";

        model.addAttribute("error", msg);
        return "registro-medico";

    } catch (Exception ex) {
        model.addAttribute("error",
                "No se pudo conectar con el servicio de validación.");
        return "registro-medico";
    }
}
    private void iniciarSesionPaciente(Object respuesta, HttpServletRequest request) {
    if (!(respuesta instanceof Map<?, ?> paciente)) {
        throw new IllegalStateException("Respuesta de paciente inválida.");
    }

    if (paciente.get("idPaciente") == null) {
        throw new IllegalStateException("Respuesta de paciente inválida.");
    }

    System.out.println("ID PACIENTE DEVUELTO POR CITA: "
            + paciente.get("idPaciente"));

    HttpSession actual = request.getSession(false);
    if (actual != null) actual.invalidate();

    HttpSession nueva = request.getSession(true);
    nueva.setAttribute("pacienteId", paciente.get("idPaciente").toString());
    nueva.setAttribute("pacienteNombre", paciente.get("nombre").toString());
}
}
