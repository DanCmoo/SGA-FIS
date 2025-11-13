package com.academico.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO para respuesta de error
 * Diagram2-Activity: Mostrar mensaje de error con opción Aceptar
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDTO {
    
    // Diagram2-Decision: ¿Error en la base de datos? - código de error
    private String errorCode;
    
    // Diagram2-Activity: Mensaje de error a mostrar al usuario
    private String errorMessage;
    
    // Timestamp del error
    private LocalDateTime timestamp;
    
    // Path de la petición que generó el error
    private String path;
}
