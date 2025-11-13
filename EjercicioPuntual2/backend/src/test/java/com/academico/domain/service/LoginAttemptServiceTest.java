package com.academico.domain.service;

import com.academico.domain.exception.MaxAttemptsExceededException;
import com.academico.persistence.entity.LoginAttemptEntity;
import com.academico.persistence.repository.LoginAttemptRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Tests para LoginAttemptService
 * Diagram2-SubActivity: Validar límite de 3 intentos
 */
@ExtendWith(MockitoExtension.class)
class LoginAttemptServiceTest {
    
    @Mock
    private LoginAttemptRepository loginAttemptRepository;
    
    @InjectMocks
    private LoginAttemptService loginAttemptService;
    
    @Test
    @DisplayName("Diagram2-SubActivity: Registrar intento fallido exitosamente")
    void testRecordFailedAttempt() {
        // Arrange
        Long userId = 1L;
        String ipAddress = "127.0.0.1";
        
        when(loginAttemptRepository.countRecentFailedAttempts(eq(userId), any(LocalDateTime.class)))
                .thenReturn(0);
        when(loginAttemptRepository.save(any(LoginAttemptEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        
        // Act
        assertDoesNotThrow(() -> loginAttemptService.recordFailedAttempt(userId, ipAddress));
        
        // Assert
        ArgumentCaptor<LoginAttemptEntity> captor = ArgumentCaptor.forClass(LoginAttemptEntity.class);
        verify(loginAttemptRepository).save(captor.capture());
        
        LoginAttemptEntity savedAttempt = captor.getValue();
        assertEquals(userId, savedAttempt.getUserId());
        assertFalse(savedAttempt.getSuccess());
        assertEquals(ipAddress, savedAttempt.getIpAddress());
    }
    
    @Test
    @DisplayName("Diagram2-Decision: Máximo de 3 intentos excedido - lanza excepción")
    void testRecordFailedAttemptExceedsLimit() {
        // Arrange
        Long userId = 1L;
        String ipAddress = "127.0.0.1";
        
        when(loginAttemptRepository.countRecentFailedAttempts(eq(userId), any(LocalDateTime.class)))
                .thenReturn(3);
        
        // Act & Assert
        assertThrows(MaxAttemptsExceededException.class, () -> 
            loginAttemptService.recordFailedAttempt(userId, ipAddress)
        );
        
        verify(loginAttemptRepository, never()).save(any());
    }
    
    @Test
    @DisplayName("Diagram2-SubActivity: Registrar intento exitoso")
    void testRecordSuccessfulAttempt() {
        // Arrange
        Long userId = 1L;
        String ipAddress = "127.0.0.1";
        
        when(loginAttemptRepository.save(any(LoginAttemptEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        
        // Act
        loginAttemptService.recordSuccessfulAttempt(userId, ipAddress);
        
        // Assert
        ArgumentCaptor<LoginAttemptEntity> captor = ArgumentCaptor.forClass(LoginAttemptEntity.class);
        verify(loginAttemptRepository).save(captor.capture());
        
        LoginAttemptEntity savedAttempt = captor.getValue();
        assertEquals(userId, savedAttempt.getUserId());
        assertTrue(savedAttempt.getSuccess());
    }
    
    @Test
    @DisplayName("Diagram2-SubActivity: Verificar si ha excedido intentos")
    void testHasExceededAttempts() {
        // Arrange
        Long userId = 1L;
        
        when(loginAttemptRepository.countRecentFailedAttempts(eq(userId), any(LocalDateTime.class)))
                .thenReturn(3);
        
        // Act
        boolean result = loginAttemptService.hasExceededAttempts(userId);
        
        // Assert
        assertTrue(result);
    }
}
