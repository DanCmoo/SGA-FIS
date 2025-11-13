package com.academico.persistence.repository;

import com.academico.persistence.entity.SessionLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para entidad SessionLog
 * Diagram2-Activity: Registrar inicio de sesión - persistir log de sesión
 */
@Repository
public interface SessionLogRepository extends JpaRepository<SessionLogEntity, Long> {
    
    /**
     * Obtiene todos los logs de sesión de un usuario
     * 
     * @param userId ID del usuario
     * @return Lista de sesiones del usuario
     */
    List<SessionLogEntity> findByUserIdOrderByLoginTimeDesc(Long userId);
}
