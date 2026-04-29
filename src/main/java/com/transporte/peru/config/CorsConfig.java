package com.transporte.peru.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/api/**") // Aplica a todas las rutas que empiecen con /api/
						.allowedOrigins("http://localhost:4200") // Solo tu frontend Angular
						.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS").allowedHeaders("*")
						.allowCredentials(true)
						.maxAge(3600); // El navegador puede cachear esta respuesta por 1 hora
			}
		};
	}
}
