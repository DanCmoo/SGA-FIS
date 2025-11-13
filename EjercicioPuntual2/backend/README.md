# Backend - Sistema de Gestión Académica

Backend implementado con Spring Boot siguiendo arquitectura N-Capas basado en diagramas de actividad UML.

## 🏗️ Arquitectura

```
backend/
├── src/main/java/com/academico/
│   ├── AcademicoApplication.java          # Clase principal
│   ├── config/                             # Configuraciones
│   │   ├── SecurityConfig.java            # Spring Security + JWT
│   │   ├── SwaggerConfig.java             # Documentación OpenAPI
│   │   └── JwtAuthenticationFilter.java   # Filtro JWT
│   ├── presentation/                       # Capa de Presentación
│   │   ├── controller/                     # REST Controllers
│   │   │   ├── HomeController.java        # Diagram1: Página principal
│   │   │   └── AuthenticationController.java # Diagram2: Login
│   │   └── dto/                            # Data Transfer Objects
│   │       ├── LoginRequestDTO.java
│   │       ├── LoginResponseDTO.java
│   │       ├── ErrorResponseDTO.java
│   │       ├── SessionInitDTO.java
│   │       └── HomePageContentDTO.java
│   ├── domain/                             # Capa de Dominio
│   │   ├── service/                        # Servicios de negocio
│   │   │   ├── HomeService.java
│   │   │   ├── AuthenticationService.java
│   │   │   ├── CredentialValidationService.java
│   │   │   ├── SessionLogService.java
│   │   │   └── LoginAttemptService.java
│   │   └── exception/                      # Excepciones personalizadas
│   │       ├── InvalidCredentialsException.java
│   │       ├── UserNotFoundException.java
│   │       ├── MaxAttemptsExceededException.java
│   │       ├── SessionLogException.java
│   │       └── GlobalExceptionHandler.java
│   ├── persistence/                        # Capa de Persistencia
│   │   ├── entity/                         # Entidades JPA
│   │   │   ├── UserEntity.java
│   │   │   ├── LoginAttemptEntity.java
│   │   │   └── SessionLogEntity.java
│   │   └── repository/                     # Repositorios JPA
│   │       ├── UserRepository.java
│   │       ├── LoginAttemptRepository.java
│   │       └── SessionLogRepository.java
│   └── util/                               # Utilidades
│       ├── JwtUtil.java                    # Generación/validación JWT
│       └── CredentialValidator.java        # Validación BCrypt
├── src/main/resources/
│   ├── application.properties              # Configuración aplicación
│   └── schema.sql                          # Schema de BD
└── src/test/java/                          # Tests unitarios
    └── com/academico/
        ├── presentation/controller/
        │   ├── AuthenticationControllerTest.java
        │   └── HomeControllerTest.java
        └── domain/service/
            ├── AuthenticationServiceTest.java
            └── LoginAttemptServiceTest.java
```

## 📋 Requisitos Previos

- Java 17 o superior
- Maven 3.6 o superior
- MySQL 8.0 o superior

## 🚀 Configuración

### 1. Configurar Base de Datos

Crear base de datos MySQL:

```sql
CREATE DATABASE academico_db;
```

### 2. Configurar Credenciales

Editar `src/main/resources/application.properties`:

```properties
spring.datasource.username=tu_usuario
spring.datasource.password=tu_password
```

### 3. Instalar Dependencias

```bash
cd backend
mvn clean install
```

## ▶️ Ejecutar Aplicación

```bash
mvn spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080/api`

## 📚 Documentación API (Swagger)

Una vez iniciada la aplicación, acceder a:

- **Swagger UI**: http://localhost:8080/api/swagger-ui.html
- **API Docs**: http://localhost:8080/api/api-docs

## 🔗 Endpoints Principales

### Diagram1: Página Principal

**GET** `/api/home`
- Descripción: Obtiene contenido de página principal (misión, visión, info general)
- Autenticación: No requerida
- Response: `HomePageContentDTO`

### Diagram2: Autenticación

**POST** `/api/auth/login`
- Descripción: Autentica usuario con email y password
- Autenticación: No requerida
- Request Body:
```json
{
  "email": "admin@academico.com",
  "password": "password123"
}
```
- Response Success (200):
```json
{
  "token": "jwt-token",
  "userId": 1,
  "email": "admin@academico.com",
  "role": "ADMIN",
  "sessionId": 1,
  "loginTime": "2025-11-13T10:30:00"
}
```
- Response Error (401):
```json
{
  "errorCode": "AUTH_INVALID",
  "errorMessage": "Credenciales incorrectas",
  "timestamp": "2025-11-13T10:30:00",
  "path": "/api/auth/login"
}
```

## 👥 Usuarios de Prueba

La base de datos incluye usuarios precargados (password: `password123`):

| Email | Password | Rol |
|-------|----------|-----|
| admin@academico.com | password123 | ADMIN |
| docente@academico.com | password123 | DOCENTE |
| estudiante@academico.com | password123 | ESTUDIANTE |

## 🧪 Ejecutar Tests

```bash
# Todos los tests
mvn test

# Tests específicos
mvn test -Dtest=AuthenticationControllerTest
mvn test -Dtest=AuthenticationServiceTest
```

## 🔒 Seguridad

- **Autenticación**: JWT (JSON Web Tokens)
- **Passwords**: BCrypt hashing
- **Control de Intentos**: Máximo 3 intentos por hora
- **CORS**: Configurado para frontend en localhost:3000 y localhost:5173
- **Session Management**: Stateless (JWT)

## 📊 Flujos Implementados

### Diagram1: Acceso Principal
1. Usuario accede a `/api/home`
2. Sistema retorna contenido de página principal
3. Usuario ve botones de "Iniciar Sesión" y "Preinscribirse"

### Diagram2: Autenticación con Validación
1. Usuario envía credenciales a `/api/auth/login`
2. Sistema valida que usuario exista en BD
3. Sistema valida que no haya excedido 3 intentos
4. Sistema valida password con hash BCrypt
5. Si válido: Registra sesión, genera JWT, retorna token
6. Si inválido: Registra intento fallido, retorna error 401

## 🔧 Tecnologías

- **Framework**: Spring Boot 3.2.0
- **Lenguaje**: Java 17
- **Base de Datos**: MySQL 8.0
- **ORM**: Spring Data JPA / Hibernate
- **Seguridad**: Spring Security + JWT
- **Documentación**: SpringDoc OpenAPI (Swagger)
- **Testing**: JUnit 5 + Mockito
- **Build Tool**: Maven

## 📝 Trazabilidad

Cada método incluye comentarios de trazabilidad al diagrama:

```java
// Diagram2-Activity: Validar Credenciales - orquesta flujo de autenticación
// Diagram2-Decision: ¿Credenciales válidas? [Sí] - genera token JWT
```

## ⚠️ Notas Importantes

1. El sistema crea las tablas automáticamente (`ddl-auto=create-drop`)
2. Los datos de prueba se insertan desde `schema.sql`
3. El JWT expira en 24 horas (configurable en `application.properties`)
4. Los intentos de login se resetean después de 1 hora
5. Por seguridad, los mensajes de error son genéricos para no revelar usuarios existentes

## 🐛 Troubleshooting

### Error: "Access denied for user"
- Verificar credenciales de MySQL en `application.properties`

### Error: "Port 8080 already in use"
- Cambiar puerto en `application.properties`: `server.port=8081`

### Error: "Table doesn't exist"
- Verificar que `spring.jpa.hibernate.ddl-auto=create-drop`
- Verificar que `schema.sql` esté en `src/main/resources`

## 📧 Soporte

Para problemas o dudas, contactar al equipo de desarrollo.
