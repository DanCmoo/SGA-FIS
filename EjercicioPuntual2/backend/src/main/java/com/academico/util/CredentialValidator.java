package com.academico.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Validador de credenciales
 * Diagram2-SubActivity: Validar Credenciales - validación de password con hash
 */
@Component
public class CredentialValidator {
    
    private final BCryptPasswordEncoder passwordEncoder;
    
    public CredentialValidator() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
    
    /**
     * Valida si el password coincide con el hash almacenado
     * Diagram2-SubActivity: Validar password contra hash en BD
     * 
     * @param rawPassword Password en texto plano
     * @param hashedPassword Password hasheado almacenado
     * @return true si coinciden, false en caso contrario
     */
    public boolean validatePassword(String rawPassword, String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }
    
    /**
     * Genera hash de password para almacenamiento
     * 
     * @param rawPassword Password en texto plano
     * @return Password hasheado
     */
    public String hashPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}
