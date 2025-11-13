package com.academico.domain.exception;

/**
 * Excepción cuando las credenciales son inválidas
 * Diagram2-Decision: ¿Credenciales válidas? [No] - lanza excepción
 */
public class InvalidCredentialsException extends RuntimeException {
    
    public InvalidCredentialsException(String message) {
        super(message);
    }
    
    public InvalidCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }
}
