package com.academico.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO para response de login exitoso
 * Diagram2-Decision: ¿Credenciales válidas? [Sí] - retorna token y datos de sesión
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
    
    // Diagram2-Activity: Token JWT generado para autenticación
    private String token;
    
    // Diagram2-Activity: Registrar inicio de sesión - información del usuario
    private Long userId;
    private String email;
    private String role;
    
    // Diagram2-Activity: Información de sesión iniciada
    private Long sessionId;
    private LocalDateTime loginTime;
}
