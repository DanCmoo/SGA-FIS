package com.academico.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para contenido de página principal
 * Diagram1-Activity: Muestra página principal con misión, visión e información general
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HomePageContentDTO {
    
    // Diagram1-Activity: Misión del sistema académico
    private String mission;
    
    // Diagram1-Activity: Visión del sistema académico
    private String vision;
    
    // Diagram1-Activity: Información general del sistema
    private String generalInfo;
    
    // Diagram1-Activity: Botón para iniciar sesión
    @Builder.Default
    private Boolean showLoginButton = true;
    
    // Diagram1-Activity: Botón para preinscribirse
    @Builder.Default
    private Boolean showRegisterButton = true;
}
