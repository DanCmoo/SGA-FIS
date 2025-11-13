package com.academico.domain.exception;

import com.academico.presentation.dto.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Manejador global de excepciones
 * Diagram2-Activity: Mostrar mensaje de error con opción Aceptar - captura todas las excepciones
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    /**
     * Maneja excepciones de credenciales inválidas
     * Diagram2-Decision: ¿Credenciales válidas? [No] - retorna error 401
     */
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidCredentials(
            InvalidCredentialsException ex, 
            HttpServletRequest request) {
        
        ErrorResponseDTO errorResponse = ErrorResponseDTO.builder()
                .errorCode("AUTH_INVALID")
                .errorMessage(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }
    
    /**
     * Maneja excepciones de usuario no encontrado
     * Diagram2-SubActivity: Validar que esté registrado/USUARIO existe [No encontrado]
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserNotFound(
            UserNotFoundException ex, 
            HttpServletRequest request) {
        
        ErrorResponseDTO errorResponse = ErrorResponseDTO.builder()
                .errorCode("USER_NOT_FOUND")
                .errorMessage(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
    
    /**
     * Maneja excepciones de máximos intentos excedidos
     * Diagram2-SubActivity: Validar límite de 3 intentos - bloquea después de 3 intentos
     */
    @ExceptionHandler(MaxAttemptsExceededException.class)
    public ResponseEntity<ErrorResponseDTO> handleMaxAttempts(
            MaxAttemptsExceededException ex, 
            HttpServletRequest request) {
        
        ErrorResponseDTO errorResponse = ErrorResponseDTO.builder()
                .errorCode("AUTH_MAX_ATTEMPTS")
                .errorMessage(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();
        
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }
    
    /**
     * Maneja excepciones de log de sesión
     * Diagram2-Activity: Registrar inicio de sesión - error al guardar
     */
    @ExceptionHandler(SessionLogException.class)
    public ResponseEntity<ErrorResponseDTO> handleSessionLog(
            SessionLogException ex, 
            HttpServletRequest request) {
        
        ErrorResponseDTO errorResponse = ErrorResponseDTO.builder()
                .errorCode("SESSION_LOG_ERROR")
                .errorMessage(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
    
    /**
     * Maneja errores de validación en DTOs
     * Diagram2-Activity: Validar campos del formulario - errores de formato
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {
        
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        Map<String, Object> response = new HashMap<>();
        response.put("errorCode", "VALIDATION_ERROR");
        response.put("errorMessage", "Error en la validación de datos");
        response.put("timestamp", LocalDateTime.now());
        response.put("path", request.getRequestURI());
        response.put("errors", errors);
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    
    /**
     * Maneja excepciones genéricas no capturadas
     * Diagram2-Decision: ¿Error en la base de datos? - error general del sistema
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(
            Exception ex, 
            HttpServletRequest request) {
        
        ErrorResponseDTO errorResponse = ErrorResponseDTO.builder()
                .errorCode("INTERNAL_ERROR")
                .errorMessage("Error interno del servidor: " + ex.getMessage())
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
