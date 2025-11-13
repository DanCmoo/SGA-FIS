package com.academico.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entidad JPA para registrar intentos de login
 * Diagram2-SubActivity: Registrar intentos fallidos para validar límite de 3 intentos
 */
@Entity
@Table(name = "login_attempts", indexes = {
    @Index(name = "idx_user_attempt", columnList = "user_id, attempt_time")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginAttemptEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    @Column(name = "attempt_time", nullable = false)
    private LocalDateTime attemptTime;
    
    @Column(nullable = false)
    @Builder.Default
    private Boolean success = false;
    
    @Column(name = "ip_address", length = 50)
    private String ipAddress;
    
    @PrePersist
    protected void onCreate() {
        if (attemptTime == null) {
            attemptTime = LocalDateTime.now();
        }
    }
}
