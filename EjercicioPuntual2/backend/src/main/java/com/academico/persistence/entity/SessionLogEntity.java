package com.academico.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entidad JPA para registrar sesiones de usuario
 * Diagram2-Activity: Registrar inicio de sesión - persistir información de sesión
 */
@Entity
@Table(name = "session_logs", indexes = {
    @Index(name = "idx_user_login", columnList = "user_id, login_time")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionLogEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    @Column(name = "login_time", nullable = false)
    private LocalDateTime loginTime;
    
    @Column(name = "logout_time")
    private LocalDateTime logoutTime;
    
    @Column(nullable = false, length = 50)
    private String role;
    
    @PrePersist
    protected void onCreate() {
        if (loginTime == null) {
            loginTime = LocalDateTime.now();
        }
    }
}
