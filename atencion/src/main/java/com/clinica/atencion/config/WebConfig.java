package com.clinica.atencion.config;

import com.clinica.atencion.web.DoctorSessionInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final DoctorSessionInterceptor doctorSessionInterceptor;

    public WebConfig(DoctorSessionInterceptor doctorSessionInterceptor) {
        this.doctorSessionInterceptor = doctorSessionInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(doctorSessionInterceptor);
    }
}
