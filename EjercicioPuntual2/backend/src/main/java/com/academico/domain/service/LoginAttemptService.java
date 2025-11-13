package com.academico.domain.service;

import com.academico.domain.exception.MaxAttemptsExceededException;
import com.academico.persistence.entity.LoginAttemptEntity;
import com.academico.persistence.repository.LoginAttemptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Servicio para gestión de intentos de login
 * Diagram2-SubActivity: Validar límite de 3 intentos para ingresar credenciales válidas
 */
@Service
@RequiredArgsConstructor
public class LoginAttemptService {
    
    private final LoginAttemptRepository loginAttemptRepository;
    
    /**
     * Registra un intento de login fallido
     * Diagram2-Decision: ¿Credenciales válidas? [No] - registra intento fallido
     * 
     * @param userId ID del usuario
     * @param ipAddress Dirección IP del intento
     */
    @Transactional
    public void recordFailedAttempt(Long userId, String ipAddress) {
        // Diagram2-SubActivity: Verificar intentos recientes (última hora)
        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);
        int recentAttempts = loginAttemptRepository.countRecentFailedAttempts(userId, oneHourAgo);
        
        // Diagram2-Decision: ¿Intentos >= 3? - lanza excepción de máximo intentos
        if (recentAttempts >= 3) {
            throw new MaxAttemptsExceededException(
                    "Se permitieron hasta 3 intentos para ingresar credenciales válidas. " +
                    "Por seguridad, ante un fallo se muestra el mensaje genérico 'Credenciales incorrectas' " +
                    "para no revelar que usuarios están registrados. Ha excedido el límite de intentos."
            );
        }
        
        // Registrar el intento fallido
        LoginAttemptEntity attempt = LoginAttemptEntity.builder()
                .userId(userId)
                .attemptTime(LocalDateTime.now())
                .success(false)
                .ipAddress(ipAddress)
                .build();
        
        loginAttemptRepository.save(attempt);
    }
    
    /**
     * Registra un intento de login exitoso
     * Diagram2-Decision: ¿Credenciales válidas? [Sí] - registra intento exitoso
     * 
     * @param userId ID del usuario
     * @param ipAddress Dirección IP del intento
     */
    @Transactional
    public void recordSuccessfulAttempt(Long userId, String ipAddress) {
        LoginAttemptEntity attempt = LoginAttemptEntity.builder()
                .userId(userId)
                .attemptTime(LocalDateTime.now())
                .success(true)
                .ipAddress(ipAddress)
                .build();
        
        loginAttemptRepository.save(attempt);
    }
    
    /**
     * Verifica si el usuario ha excedido el límite de intentos
     * 
     * @param userId ID del usuario
     * @return true si ha excedido el límite
     */
    public boolean hasExceededAttempts(Long userId) {
        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);
        int recentAttempts = loginAttemptRepository.countRecentFailedAttempts(userId, oneHourAgo);
        return recentAttempts >= 3;
    }
}
