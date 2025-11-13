package com.academico.domain.service;

import com.academico.util.CredentialValidator;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

/**
 * Servicio para validación de credenciales
 * Diagram2-SubActivity: Validar Credenciales - verifica password contra hash
 */
@Service
@RequiredArgsConstructor
public class CredentialValidationService {
    
    private final CredentialValidator credentialValidator;
    
    /**
     * Valida si el password proporcionado coincide con el hash almacenado
     * Diagram2-SubActivity: Validar credenciales - comparación de password
     * 
     * @param rawPassword Password en texto plano
     * @param hashedPassword Password hasheado de la BD
     * @return true si el password es válido, false en caso contrario
     */
    public boolean validatePassword(String rawPassword, String hashedPassword) {
        // Diagram2-SubActivity: Comparar password con hash usando BCrypt
        return credentialValidator.validatePassword(rawPassword, hashedPassword);
    }
}
