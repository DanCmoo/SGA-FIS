package com.academico.config;

import com.academico.persistence.entity.UserEntity;
import com.academico.persistence.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Inicializador de datos de prueba
 * Carga usuarios de prueba al iniciar la aplicación
 */
@Component
@RequiredArgsConstructor
public class DataInitializer {
    
    private final UserRepository userRepository;
    
    @PostConstruct
    public void init() {
        if (userRepository.count() == 0) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String hashedPassword = encoder.encode("password123");

            UserEntity admin = new UserEntity();
            admin.setEmail("admin@academico.com");
            admin.setHashedPassword(hashedPassword);
            admin.setRole("ADMIN");
            admin.setFirstName("Admin");
            admin.setLastName("Sistema");
            admin.setCreatedAt(LocalDateTime.now());
            admin.setUpdatedAt(LocalDateTime.now());
            userRepository.save(admin);

            UserEntity docente = new UserEntity();
            docente.setEmail("docente@academico.com");
            docente.setHashedPassword(hashedPassword);
            docente.setRole("DOCENTE");
            docente.setFirstName("Juan");
            docente.setLastName("Profesor");
            docente.setCreatedAt(LocalDateTime.now());
            docente.setUpdatedAt(LocalDateTime.now());
            userRepository.save(docente);

            UserEntity estudiante = new UserEntity();
            estudiante.setEmail("estudiante@academico.com");
            estudiante.setHashedPassword(hashedPassword);
            estudiante.setRole("ESTUDIANTE");
            estudiante.setFirstName("Maria");
            estudiante.setLastName("Estudiante");
            estudiante.setCreatedAt(LocalDateTime.now());
            estudiante.setUpdatedAt(LocalDateTime.now());
            userRepository.save(estudiante);

            System.out.println("✅ Datos de prueba cargados exitosamente");
            System.out.println("   - admin@academico.com / password123 (ADMIN)");
            System.out.println("   - docente@academico.com / password123 (DOCENTE)");
            System.out.println("   - estudiante@academico.com / password123 (ESTUDIANTE)");
        }
    }
}
