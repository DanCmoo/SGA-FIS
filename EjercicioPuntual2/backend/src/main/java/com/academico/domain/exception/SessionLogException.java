package com.academico.domain.exception;

/**
 * Excepción cuando falla el registro de sesión
 * Diagram2-Activity: Registrar inicio de sesión - error al persistir
 */
public class SessionLogException extends RuntimeException {
    
    public SessionLogException(String message) {
        super(message);
    }
    
    public SessionLogException(String message, Throwable cause) {
        super(message, cause);
    }
}
