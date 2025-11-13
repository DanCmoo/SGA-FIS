package com.academico.presentation.controller;

import com.academico.domain.service.AuthenticationService;
import com.academico.presentation.dto.LoginRequestDTO;
import com.academico.presentation.dto.LoginResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador de autenticación
 * Diagram2-Activity: Dar clic en botón Ingresar - procesa credenciales
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Authentication", description = "Endpoints para autenticación de usuarios")
public class AuthenticationController {
    
    private final AuthenticationService authenticationService;
    
    /**
     * Endpoint de login
     * Diagram2-Activity: Dar clic en botón Ingresar - recibe y valida credenciales
     * 
     * @param request DTO con email y password
     * @param httpRequest Request HTTP para obtener IP
     * @return DTO con token JWT y datos de sesión
     */
    @PostMapping("/login")
    @Operation(
        summary = "Iniciar sesión",
        description = "Diagram2: Valida las credenciales del usuario (email y password), " +
                     "verifica que esté registrado, valida el límite de 3 intentos, " +
                     "registra el inicio de sesión y retorna un token JWT si las credenciales son válidas"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login exitoso - credenciales válidas"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "401", description = "Credenciales inválidas"),
        @ApiResponse(responseCode = "403", description = "Máximo de intentos excedido (3)"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<LoginResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO request,
            HttpServletRequest httpRequest) {
        
        // Diagram2-Controller: Dar clic en botón Ingresar - recibe credenciales del formulario
        log.info("Diagram2-Activity: Iniciando proceso de autenticación para email: {}", request.getEmail());
        
        // Obtener IP del cliente
        String ipAddress = getClientIpAddress(httpRequest);
        
        // Diagram2-SubActivity: Validar Credenciales - delega al servicio
        LoginResponseDTO response = authenticationService.authenticate(
                request.getEmail(), 
                request.getPassword(),
                ipAddress
        );
        
        // Diagram2-Decision: ¿Credenciales válidas? [Sí] - retorna respuesta exitosa
        log.info("Diagram2-Activity: Autenticación exitosa para usuario: {}", response.getEmail());
        return ResponseEntity.ok(response);
    }
    
    /**
     * Extrae la dirección IP del cliente
     */
    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}
