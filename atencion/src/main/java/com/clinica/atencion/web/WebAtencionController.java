package com.clinica.atencion.web;

import com.clinica.atencion.dto.AtencionMedicaCompletaDto;
import com.clinica.atencion.dto.EditarAtencionRequest;
import com.clinica.atencion.dto.RegistroAtencionRequest;
import com.clinica.atencion.model.AtencionMedica;
import com.clinica.atencion.service.AtencionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping
public class WebAtencionController {

    private final AtencionService atencionService;
    private final ObjectMapper objectMapper;

    public WebAtencionController(AtencionService atencionService, ObjectMapper objectMapper) {
        this.atencionService = atencionService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        String idMedico = idMedico(session);
        List<AtencionMedica> atenciones = atencionService.listar(idMedico);
        model.addAttribute("totalAtenciones", atenciones.size());
        model.addAttribute("citasPendientes", atencionService.listarCitas(idMedico).size());
        model.addAttribute("totalMedicamentos", atencionService.listarMedicamentosCatalogo().size());
        model.addAttribute("totalAnalisis", atencionService.listarTiposAnalisisCatalogo().size());
        model.addAttribute("ultimasAtenciones", atenciones);
        return "index";
    }

    @GetMapping("/atencion/nueva")
    public String nuevaAtencion(@RequestParam(required = false) String idCita, HttpSession session, Model model) {
        String idMedico = idMedico(session);
        List<?> citas = atencionService.listarCitas(idMedico);
        model.addAttribute("citas", citas);
        model.addAttribute("medicos", atencionService.listarMedicos());
        model.addAttribute("medicamentos", atencionService.listarMedicamentosCatalogo());
        model.addAttribute("tiposAnalisis", atencionService.listarTiposAnalisisCatalogo());
        model.addAttribute("numeroGenerado", atencionService.generarNumeroAtencion());

        Object citaSeleccionada = null;
        if (idCita != null && !idCita.isEmpty()) {
            citaSeleccionada = atencionService.consultarCita(idCita);
            if (!(citaSeleccionada instanceof Map<?, ?> datos)
                    || !idMedico.equals(String.valueOf(datos.get("idMedico")))) {
                citaSeleccionada = null;
            }
        }
        model.addAttribute("citaSeleccionada", citaSeleccionada);

        return "atencion-nueva";
    }

    @PostMapping("/atencion/grabar")
    public String procesarGrabar(@ModelAttribute RegistroAtencionRequest request,
                                 HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            AtencionMedicaCompletaDto dto = atencionService.grabar(request, idMedico(session));
            redirectAttributes.addFlashAttribute("mensajeExito",
                    "Atención Médica " + dto.getAtencion().getIdAtencion() + " registrada y almacenada en la historia médica con éxito.");
            return "redirect:/atencion/detalle/" + dto.getAtencion().getIdAtencion();
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("mensajeError", "Error al registrar la atención: " + ex.getMessage());
            return "redirect:/atencion/nueva";
        }
    }

    @GetMapping("/atencion/listar")
    public String listarAtenciones(HttpSession session, Model model) {
        model.addAttribute("atenciones", atencionService.listar(idMedico(session)));
        return "atencion-listar";
    }

    @GetMapping("/atencion/buscar")
    public String buscarAtenciones(@RequestParam(required = false, defaultValue = "") String filtro,
                                   HttpSession session, Model model) {
        model.addAttribute("filtro", filtro);
        model.addAttribute("resultados", atencionService.buscarPorFiltro(filtro, idMedico(session)));
        return "atencion-buscar";
    }

    @GetMapping("/atencion/editar/{idAtencion}")
    public String editarAtencionForm(@PathVariable String idAtencion, HttpSession session, Model model) {
        AtencionMedica atencion = atencionService.buscar(idAtencion, idMedico(session));
        if (atencion == null) return "redirect:/atencion/listar";
        model.addAttribute("atencion", atencion);
        return "atencion-editar";
    }

    @PostMapping("/atencion/editar/{idAtencion}")
    public String procesarEditar(@PathVariable String idAtencion,
                                 @ModelAttribute EditarAtencionRequest request,
                                 HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            AtencionMedica actualizada = atencionService.editar(idAtencion, request, idMedico(session));
            if (actualizada != null) {
                redirectAttributes.addFlashAttribute("mensajeExito",
                        "Atención Médica " + idAtencion + " actualizada correctamente.");
                return "redirect:/atencion/detalle/" + idAtencion;
            }
            redirectAttributes.addFlashAttribute("mensajeError", "No se encontró la atención.");
            return "redirect:/atencion/listar";
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("mensajeError", "Error al editar: " + ex.getMessage());
            return "redirect:/atencion/editar/" + idAtencion;
        }
    }

    @GetMapping("/atencion/detalle/{idAtencion}")
    public String detalleAtencion(@PathVariable String idAtencion, HttpSession session, Model model) {
        String medicoActual = idMedico(session);
        AtencionMedicaCompletaDto dto = atencionService.obtenerCompletaCompartida(idAtencion);
        if (dto == null) return "redirect:/atencion/listar";
        model.addAttribute("dto", dto);
        model.addAttribute("medicoActual", medicoActual);

        try {
            String jsonFormat = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dto);
            model.addAttribute("jsonString", jsonFormat);
        } catch (Exception e) {
            model.addAttribute("jsonString", "{}");
        }

        return "atencion-detalle";
    }

    @GetMapping("/historias")
    public String listarHistorias(@RequestParam(required = false, defaultValue = "") String filtro,
                                  HttpSession session, Model model) {
        idMedico(session);
        model.addAttribute("filtro", filtro);
        model.addAttribute("historias", atencionService.buscarHistorias(filtro));
        return "historia-listar";
    }

    @GetMapping("/historias/{idHC}")
    public String detalleHistoria(@PathVariable String idHC, HttpSession session, Model model) {
        idMedico(session);
        Object historia = atencionService.buscarHistoria(idHC);
        if (historia == null) return "redirect:/historias";
        model.addAttribute("historia", historia);
        model.addAttribute("atenciones", atencionService.listarPorHistoria(idHC).stream()
                .map(a -> atencionService.obtenerCompletaCompartida(a.getIdAtencion()))
                .filter(Objects::nonNull)
                .toList());
        return "historia-detalle";
    }

    @GetMapping("/api-visor")
    public String apiVisor() {
        return "api-json";
    }

    @GetMapping("/solicitudes")
    public String solicitudes(HttpSession session, Model model) {
        String idMedico = idMedico(session);
        model.addAttribute("solicitudes", atencionService.listarSolicitudes(idMedico));
        model.addAttribute("pacientes", atencionService.listarPacientesMedico(idMedico));
        return "solicitudes";
    }

    @PostMapping("/solicitudes/{idCita}/{decision}")
    public String resolverSolicitud(@PathVariable String idCita, @PathVariable String decision,
                                    HttpSession session, RedirectAttributes redirectAttributes) {
        String accion = "aceptar".equalsIgnoreCase(decision) ? "ACEPTAR"
                : "rechazar".equalsIgnoreCase(decision) ? "RECHAZAR" : "";
        try {
            if (accion.isEmpty() || !atencionService.resolverSolicitud(idCita, idMedico(session), accion)) {
                redirectAttributes.addFlashAttribute("error", "La solicitud no existe o no pertenece a tu cuenta.");
            } else {
                redirectAttributes.addFlashAttribute("mensaje", "La solicitud fue procesada.");
            }
        } catch (Exception ex) {
            System.err.println("ERROR AL RESOLVER SOLICITUD:");
            ex.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "No se pudo procesar la solicitud: " + ex.getMessage());
        }
        return "redirect:/solicitudes";
    }

    @GetMapping("/paciente/inicio")
    public String inicioPaciente(HttpSession session, Model model) {
        model.addAttribute("solicitudes", atencionService.listarSolicitudesPaciente(
                session.getAttribute("pacienteId").toString()));
        model.addAttribute("medicos", atencionService.listarMedicos());
        return "paciente-inicio";
    }

    @PostMapping("/paciente/solicitudes")
    public String crearSolicitudPaciente(@RequestParam String idMedico,
                                         @RequestParam(required = false) String horario,
                                         HttpSession session, RedirectAttributes redirectAttributes) {
        try {
            boolean medicoExiste = atencionService.listarMedicos().stream()
                    .filter(Map.class::isInstance)
                    .map(Map.class::cast)
                    .anyMatch(medico -> idMedico.equals(String.valueOf(medico.get("idMedico"))));
            if (!medicoExiste) throw new IllegalArgumentException("Selecciona un médico válido.");
            atencionService.solicitarCita(session.getAttribute("pacienteId").toString(), idMedico, horario);
            redirectAttributes.addFlashAttribute("mensaje", "Solicitud enviada; espera la respuesta del médico.");
        } catch (Exception ex) {
            System.err.println("ERROR AL SOLICITAR CITA:");
            ex.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "No se pudo enviar la solicitud: " + ex.getMessage());
        }
        return "redirect:/paciente/inicio";
    }

    private String idMedico(HttpSession session) {
        Object id = session.getAttribute("medicoId");
        if (id == null) throw new IllegalStateException("No hay una sesión de médico autenticada.");
        return id.toString();
    }
}