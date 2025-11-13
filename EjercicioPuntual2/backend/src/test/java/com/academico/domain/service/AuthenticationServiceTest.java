package com.academico.domain.service;

import com.academico.domain.exception.InvalidCredentialsException;
import com.academico.domain.exception.UserNotFoundException;
import com.academico.persistence.entity.UserEntity;
import com.academico.persistence.repository.UserRepository;
import com.academico.presentation.dto.LoginResponseDTO;
import com.academico.presentation.dto.SessionInitDTO;
import com.academico.util.JwtUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Tests para AuthenticationService
 * Diagram2-Activity: Validar lógica de negocio de autenticación
 */
@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private CredentialValidationService credentialValidationService;
    
    @Mock
    private LoginAttemptService loginAttemptService;
    
    @Mock
    private SessionLogService sessionLogService;
    
    @Mock
    private JwtUtil jwtUtil;
    
    @InjectMocks
    private AuthenticationService authenticationService;
    
    @Test
    @DisplayName("Diagram2-Decision: ¿Credenciales válidas? [Sí] - Autenticación exitosa")
    void testAuthenticateSuccess() {
        // Arrange
        String email = "admin@academico.com";
        String password = "password123";
        String ipAddress = "127.0.0.1";
        
        UserEntity user = UserEntity.builder()
                .id(1L)
                .email(email)
                .hashedPassword("$2a$10$hashed")
                .role("ADMIN")
                .build();
        
        SessionInitDTO sessionInit = SessionInitDTO.builder()
                .sessionId(1L)
                .userId(1L)
                .email(email)
                .role("ADMIN")
                .loginTime(LocalDateTime.now())
                .build();
        
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(loginAttemptService.hasExceededAttempts(1L)).thenReturn(false);
        when(credentialValidationService.validatePassword(password, user.getHashedPassword())).thenReturn(true);
        when(sessionLogService.registerSessionStart(anyLong(), anyString(), anyString())).thenReturn(sessionInit);
        when(jwtUtil.generateToken(anyString(), anyString(), anyLong())).thenReturn("jwt-token");
        
        // Act
        LoginResponseDTO response = authenticationService.authenticate(email, password, ipAddress);
        
        // Assert
        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());
        assertEquals(email, response.getEmail());
        assertEquals("ADMIN", response.getRole());
        
        verify(loginAttemptService).recordSuccessfulAttempt(1L, ipAddress);
        verify(sessionLogService).registerSessionStart(1L, email, "ADMIN");
    }
    
    @Test
    @DisplayName("Diagram2-SubActivity: Usuario no encontrado - lanza UserNotFoundException")
    void testAuthenticateUserNotFound() {
        // Arrange
        String email = "noexiste@academico.com";
        String password = "password123";
        String ipAddress = "127.0.0.1";
        
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> 
            authenticationService.authenticate(email, password, ipAddress)
        );
    }
    
    @Test
    @DisplayName("Diagram2-Decision: ¿Credenciales válidas? [No] - Password incorrecto")
    void testAuthenticateInvalidPassword() {
        // Arrange
        String email = "admin@academico.com";
        String password = "wrongpassword";
        String ipAddress = "127.0.0.1";
        
        UserEntity user = UserEntity.builder()
                .id(1L)
                .email(email)
                .hashedPassword("$2a$10$hashed")
                .role("ADMIN")
                .build();
        
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(loginAttemptService.hasExceededAttempts(1L)).thenReturn(false);
        when(credentialValidationService.validatePassword(password, user.getHashedPassword())).thenReturn(false);
        
        // Act & Assert
        assertThrows(InvalidCredentialsException.class, () -> 
            authenticationService.authenticate(email, password, ipAddress)
        );
        
        verify(loginAttemptService).recordFailedAttempt(1L, ipAddress);
    }
}
