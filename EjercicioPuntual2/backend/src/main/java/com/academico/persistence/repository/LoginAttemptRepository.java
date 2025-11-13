package com.academico.persistence.repository;

import com.academico.persistence.entity.LoginAttemptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * Repositorio JPA para entidad LoginAttempt
 * Diagram2-SubActivity: Controlar intentos de login (máximo 3)
 */
@Repository
public interface LoginAttemptRepository extends JpaRepository<LoginAttemptEntity, Long> {
    
    /**
     * Cuenta intentos fallidos recientes de un usuario
     * Diagram2-Decision: Validar límite de 3 intentos por hora
     * 
     * @param userId ID del usuario
     * @param since Tiempo desde el cual contar intentos
     * @return Número de intentos fallidos
     */
    @Query("SELECT COUNT(la) FROM LoginAttemptEntity la WHERE la.userId = :userId AND la.success = false AND la.attemptTime >= :since")
    int countRecentFailedAttempts(@Param("userId") Long userId, @Param("since") LocalDateTime since);
}
