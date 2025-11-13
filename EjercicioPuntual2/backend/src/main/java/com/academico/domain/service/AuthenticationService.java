package com.academico.domain.service;

import com.academico.domain.exception.InvalidCredentialsException;
import com.academico.domain.exception.UserNotFoundException;
import com.academico.persistence.entity.UserEntity;
import com.academico.persistence.repository.UserRepository;
import com.academico.presentation.dto.LoginResponseDTO;
import com.academico.presentation.dto.SessionInitDTO;
import com.academico.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio de autenticación
 * Diagram2-Activity: Validar Credenciales - orquesta flujo completo de autenticación
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    
    private final UserRepository userRepository;
    private final CredentialValidationService credentialValidationService;
    private final LoginAttemptService loginAttemptService;
    private final SessionLogService sessionLogService;
    private final JwtUtil jwtUtil;
    
    /**
     * Autentica un usuario con email y password
     * Diagram2-Activity: Validar Credenciales - flujo completo de validación
     * 
     * @param email Email del usuario
     * @param password Password del usuario
     * @param ipAddress IP desde donde se hace el login
     * @return DTO con token JWT y datos de sesión
     */
    @Transactional
    public LoginResponseDTO authenticate(String email, String password, String ipAddress) {
        log.debug("Diagram2-Activity: Iniciando validación de credenciales para email: {}", email);
        
        try {
            // Diagram2-SubActivity: Validar que esté registrado/USUARIO existe
            UserEntity user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UserNotFoundException(
                            "Usuario no encontrado. Por seguridad, se muestra mensaje genérico."
                    ));
            
            log.debug("Diagram2-SubActivity: Usuario encontrado con ID: {}", user.getId());
            
            // Diagram2-Decision: ¿Error en la base de datos? [No] - usuario encontrado exitosamente
            
            // Diagram2-SubActivity: Validar si ha excedido intentos antes de validar password
            if (loginAttemptService.hasExceededAttempts(user.getId())) {
                throw new InvalidCredentialsException(
                        "Se permitieron hasta 3 intentos para ingresar credenciales válidas. " +
                        "Por seguridad, ante un fallo se muestra el mensaje genérico 'Credenciales incorrectas' " +
                        "para no revelar que usuarios están registrados."
                );
            }
            
            // Diagram2-SubActivity: Validar credenciales - compara password
            boolean isPasswordValid = credentialValidationService.validatePassword(
                    password, 
                    user.getHashedPassword()
            );
            
            if (!isPasswordValid) {
                // Diagram2-Decision: ¿Credenciales válidas? [No] - password incorrecto
                log.debug("Diagram2-Decision: Credenciales inválidas - registrando intento fallido");
                loginAttemptService.recordFailedAttempt(user.getId(), ipAddress);
                
                throw new InvalidCredentialsException(
                        "Credenciales incorrectas. Por seguridad, ante un fallo se muestra el mensaje genérico " +
                        "'Credenciales incorrectas' para no revelar que usuarios están registrados."
                );
            }
            
            // Diagram2-Decision: ¿Credenciales válidas? [Sí] - autenticación exitosa
            log.debug("Diagram2-Decision: Credenciales válidas - iniciando sesión");
            
            // Diagram2-Activity: Registrar intento exitoso
            loginAttemptService.recordSuccessfulAttempt(user.getId(), ipAddress);
            
            // Diagram2-Activity: Registrar inicio de sesión
            SessionInitDTO sessionInit = sessionLogService.registerSessionStart(
                    user.getId(), 
                    user.getEmail(), 
                    user.getRole()
            );
            
            // Diagram2-Activity: Generar token JWT para autenticación
            String token = jwtUtil.generateToken(user.getEmail(), user.getRole(), user.getId());
            
            // Diagram2-Activity: Retornar respuesta exitosa con token y datos de sesión
            return LoginResponseDTO.builder()
                    .token(token)
                    .userId(user.getId())
                    .email(user.getEmail())
                    .role(user.getRole())
                    .sessionId(sessionInit.getSessionId())
                    .loginTime(sessionInit.getLoginTime())
                    .build();
                    
        } catch (UserNotFoundException | InvalidCredentialsException e) {
            // Diagram2-Activity: Mostrar mensaje de error - propaga excepción al controller
            log.error("Diagram2-Activity: Error en autenticación: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            // Diagram2-Decision: ¿Error en la base de datos? [Sí] - error inesperado
            log.error("Diagram2-Decision: Error en la base de datos durante autenticación", e);
            throw new InvalidCredentialsException("Error en el sistema de autenticación", e);
        }
    }
}
