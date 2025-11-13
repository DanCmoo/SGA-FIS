package com.academico.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Utilidad para generación y validación de tokens JWT
 * Diagram2-Decision: ¿Credenciales válidas? [Sí] - genera token de autenticación
 */
@Component
public class JwtUtil {
    
    @Value("${jwt.secret}")
    private String secret;
    
    @Value("${jwt.expiration}")
    private Long expiration;
    
    /**
     * Genera un token JWT para un usuario autenticado
     * Diagram2-Activity: Registrar inicio de sesión - token de autenticación
     */
    public String generateToken(String email, String role, Long userId) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        
        return JWT.create()
                .withSubject(email)
                .withClaim("role", role)
                .withClaim("userId", userId)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                .sign(algorithm);
    }
    
    /**
     * Valida y decodifica un token JWT
     */
    public DecodedJWT validateToken(String token) throws JWTVerificationException {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        
        return JWT.require(algorithm)
                .build()
                .verify(token);
    }
    
    /**
     * Extrae el email del token
     */
    public String getEmailFromToken(String token) {
        return validateToken(token).getSubject();
    }
    
    /**
     * Extrae el rol del token
     */
    public String getRoleFromToken(String token) {
        return validateToken(token).getClaim("role").asString();
    }
    
    /**
     * Extrae el userId del token
     */
    public Long getUserIdFromToken(String token) {
        return validateToken(token).getClaim("userId").asLong();
    }
}
