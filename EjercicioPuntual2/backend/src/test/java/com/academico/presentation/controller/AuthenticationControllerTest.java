package com.academico.presentation.controller;

import com.academico.domain.exception.InvalidCredentialsException;
import com.academico.domain.exception.MaxAttemptsExceededException;
import com.academico.domain.exception.UserNotFoundException;
import com.academico.domain.service.AuthenticationService;
import com.academico.presentation.dto.LoginRequestDTO;
import com.academico.presentation.dto.LoginResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests para AuthenticationController
 * Diagram2-Activity: Validar flujos de autenticación
 */
@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private AuthenticationService authenticationService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    @DisplayName("Diagram2-Decision: ¿Credenciales válidas? [Sí] - Login exitoso")
    void testLoginSuccess() throws Exception {
        // Arrange
        LoginRequestDTO request = LoginRequestDTO.builder()
                .email("admin@academico.com")
                .password("password123")
                .build();
        
        LoginResponseDTO response = LoginResponseDTO.builder()
                .token("jwt-token-example")
                .userId(1L)
                .email("admin@academico.com")
                .role("ADMIN")
                .sessionId(1L)
                .loginTime(LocalDateTime.now())
                .build();
        
        when(authenticationService.authenticate(anyString(), anyString(), anyString()))
                .thenReturn(response);
        
        // Act & Assert
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.email").value("admin@academico.com"))
                .andExpect(jsonPath("$.role").value("ADMIN"));
    }
    
    @Test
    @DisplayName("Diagram2-Decision: ¿Credenciales válidas? [No] - Credenciales inválidas")
    void testLoginInvalidCredentials() throws Exception {
        // Arrange
        LoginRequestDTO request = LoginRequestDTO.builder()
                .email("admin@academico.com")
                .password("wrongpassword")
                .build();
        
        when(authenticationService.authenticate(anyString(), anyString(), anyString()))
                .thenThrow(new InvalidCredentialsException("Credenciales incorrectas"));
        
        // Act & Assert
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.errorCode").value("AUTH_INVALID"));
    }
    
    @Test
    @DisplayName("Diagram2-SubActivity: Usuario no encontrado")
    void testLoginUserNotFound() throws Exception {
        // Arrange
        LoginRequestDTO request = LoginRequestDTO.builder()
                .email("noexiste@academico.com")
                .password("password123")
                .build();
        
        when(authenticationService.authenticate(anyString(), anyString(), anyString()))
                .thenThrow(new UserNotFoundException("Usuario no encontrado"));
        
        // Act & Assert
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("USER_NOT_FOUND"));
    }
    
    @Test
    @DisplayName("Diagram2-SubActivity: Máximo de 3 intentos excedido")
    void testLoginMaxAttemptsExceeded() throws Exception {
        // Arrange
        LoginRequestDTO request = LoginRequestDTO.builder()
                .email("admin@academico.com")
                .password("wrongpassword")
                .build();
        
        when(authenticationService.authenticate(anyString(), anyString(), anyString()))
                .thenThrow(new MaxAttemptsExceededException("Máximo de intentos excedido"));
        
        // Act & Assert
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.errorCode").value("AUTH_MAX_ATTEMPTS"));
    }
    
    @Test
    @DisplayName("Diagram2-Activity: Validación de campos - Email requerido")
    void testLoginEmailRequired() throws Exception {
        // Arrange
        LoginRequestDTO request = LoginRequestDTO.builder()
                .password("password123")
                .build();
        
        // Act & Assert
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    @DisplayName("Diagram2-Activity: Validación de campos - Password mínimo 8 caracteres")
    void testLoginPasswordMinLength() throws Exception {
        // Arrange
        LoginRequestDTO request = LoginRequestDTO.builder()
                .email("admin@academico.com")
                .password("123")
                .build();
        
        // Act & Assert
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
