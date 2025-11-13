package com.academico.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO para información de inicio de sesión
 * Diagram2-Activity: Registrar inicio de sesión - información de sesión creada
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionInitDTO {
    
    private Long sessionId;
    private Long userId;
    private String email;
    private String role;
    
    // Diagram2-Activity: Timestamp de inicio de sesión registrado
    private LocalDateTime loginTime;
}
