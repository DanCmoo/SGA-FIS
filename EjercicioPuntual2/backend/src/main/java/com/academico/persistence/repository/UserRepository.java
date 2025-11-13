package com.academico.persistence.repository;

import com.academico.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio JPA para entidad User
 * Diagram2-SubActivity: Validar Credenciales - búsqueda por email
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    
    /**
     * Busca usuario por email
     * Diagram2-SubActivity: Validar que esté registrado/USUARIO existe
     */
    Optional<UserEntity> findByEmail(String email);
    
    /**
     * Verifica si existe un usuario con el email dado
     */
    boolean existsByEmail(String email);
}
