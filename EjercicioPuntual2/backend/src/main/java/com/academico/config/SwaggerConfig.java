package com.academico.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de Swagger/OpenAPI para documentación de API
 * Generación automática de documentación según especificación del README
 */
@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sistema de Gestión Académica API")
                        .version("1.0.0")
                        .description("API REST para Sistema de Gestión Académica implementado desde diagramas de actividad UML. " +
                                   "Este sistema maneja autenticación de usuarios, registro de sesiones y control de acceso basado en roles. " +
                                   "\n\n**Diagramas implementados:**\n" +
                                   "- Diagram1: Acceso Principal del Sistema\n" +
                                   "- Diagram2: Autenticación y Registro con Validación (máximo 3 intentos)")
                        .contact(new Contact()
                                .name("Equipo de Desarrollo")
                                .email("soporte@academico.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html")))
                .components(new Components()
                        .addSecuritySchemes("bearer-jwt", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("Token JWT obtenido del endpoint /api/auth/login")))
                .addSecurityItem(new SecurityRequirement().addList("bearer-jwt"));
    }
}
