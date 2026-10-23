package com.clinica.atencion.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class DoctorSessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getRequestURI();
        if (path.equals("/login") || path.equals("/error")
    || path.equals("/medico/registro")
    || path.equals("/paciente/login") || path.equals("/paciente/registro")) return true;

        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("medicoId") != null
            && !path.startsWith("/paciente/")) return true;
        if (session != null && session.getAttribute("pacienteId") != null
            && path.startsWith("/paciente/") && !path.equals("/paciente/login")
            && !path.equals("/paciente/registro")) return true;
        if (session != null && session.getAttribute("medicoId") != null
            && (path.equals("/paciente/login") || path.equals("/paciente/registro"))) {
            response.sendRedirect("/" );
            return false;
        }
        if (path.startsWith("/paciente/")) {
            response.sendRedirect(request.getContextPath() + "/paciente/login");
            return false;
        }

        if (path.startsWith("/api/")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        response.sendRedirect(request.getContextPath() + "/login");
        return false;
    }
}
