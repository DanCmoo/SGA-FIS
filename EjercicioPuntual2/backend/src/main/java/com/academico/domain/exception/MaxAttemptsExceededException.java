package com.academico.domain.exception;

/**
 * Excepción cuando se excede el máximo de intentos de login (3)
 * Diagram2-SubActivity: Validar límite de 3 intentos para ingresar credenciales válidas
 */
public class MaxAttemptsExceededException extends RuntimeException {
    
    public MaxAttemptsExceededException(String message) {
        super(message);
    }
    
    public MaxAttemptsExceededException(String message, Throwable cause) {
        super(message, cause);
    }
}
