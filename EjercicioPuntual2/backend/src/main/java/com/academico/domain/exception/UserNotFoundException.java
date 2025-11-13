package com.academico.domain.exception;

/**
 * Excepción cuando el usuario no se encuentra en la base de datos
 * Diagram2-SubActivity: Validar que esté registrado/USUARIO existe [No encontrado]
 */
public class UserNotFoundException extends RuntimeException {
    
    public UserNotFoundException(String message) {
        super(message);
    }
    
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
