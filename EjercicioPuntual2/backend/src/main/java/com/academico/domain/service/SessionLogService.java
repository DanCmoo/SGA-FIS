package com.academico.domain.service;

import com.academico.domain.exception.SessionLogException;
import com.academico.persistence.entity.SessionLogEntity;
import com.academico.persistence.repository.SessionLogRepository;
import com.academico.presentation.dto.SessionInitDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Servicio para gestión de logs de sesión
 * Diagram2-Activity: Registrar inicio de sesión - persistir información de sesión
 */
@Service
@RequiredArgsConstructor
public class SessionLogService {
    
    private final SessionLogRepository sessionLogRepository;
    
    /**
     * Registra el inicio de sesión de un usuario
     * Diagram2-Activity: Registrar inicio de sesión - crea registro en BD
     * 
     * @param userId ID del usuario
     * @param email Email del usuario
     * @param role Rol del usuario
     * @return DTO con información de la sesión creada
     */
    @Transactional
    public SessionInitDTO registerSessionStart(Long userId, String email, String role) {
        try {
            // Diagram2-Activity: Crear registro de sesión en session_logs
            SessionLogEntity sessionLog = SessionLogEntity.builder()
                    .userId(userId)
                    .loginTime(LocalDateTime.now())
                    .role(role)
                    .build();
            
            SessionLogEntity savedSession = sessionLogRepository.save(sessionLog);
            
            // Diagram2-Activity: Retornar información de sesión iniciada
            return SessionInitDTO.builder()
                    .sessionId(savedSession.getId())
                    .userId(savedSession.getUserId())
                    .email(email)
                    .role(savedSession.getRole())
                    .loginTime(savedSession.getLoginTime())
                    .build();
                    
        } catch (Exception e) {
            // Diagram2-Activity: Error al registrar sesión - lanza excepción
            throw new SessionLogException("Error al registrar inicio de sesión", e);
        }
    }
    
    /**
     * Registra el cierre de sesión de un usuario
     * 
     * @param sessionId ID de la sesión
     */
    @Transactional
    public void registerSessionEnd(Long sessionId) {
        sessionLogRepository.findById(sessionId).ifPresent(session -> {
            session.setLogoutTime(LocalDateTime.now());
            sessionLogRepository.save(session);
        });
    }
}
