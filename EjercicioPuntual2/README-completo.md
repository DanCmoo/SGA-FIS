# README: Generación de Sistema de Gestión Académica desde Diagramas de Actividad

## Propósito

Este documento detalla las **instrucciones paso a paso** y **reglas estrictas** para que un agente IA genere un módulo completo de backend (Spring, arquitectura n-capas) y frontend (React) basado en los diagramas de actividades UML del sistema de gestión académica.

---

## PARTE 1: Diagramas de Actividad Analizados

### Diagrama 1: Acceso Principal del Sistema

**Actores:** Usuario

**Flujo principal:**
- Usuario inicia en el sistema
- Usuario ingresa URL del sitio web
- Sistema muestra página principal con misión, visión, información general y botón para iniciar sesión o preinscribirse
- Usuario termina

**Elementos clave:**
- No hay bifurcaciones
- No hay manejo de errores
- Flujo lineal simple

### Diagrama 2: Autenticación y Registro con Validación

**Actores:** Usuario, Sistema

**Flujo principal:**
1. Usuario selecciona opción "Iniciar Sesión"
2. Usuario despliega formulario de sesión con botón "Ingresar"
3. Usuario ingresa correo y contraseña
4. Usuario da clic en botón "Ingresar"
5. Sistema valida credenciales (VALIDAR CREDENCIALES):
   - Validar que esté registrado/USUARIO existe
   - Si hay error en los datos → Mostrar mensaje de error con opción "Aceptar"
   - Si validación es exitosa → Continuar
6. Sistema valida credenciales (¿Credenciales válidas?):
   - Si No [s] → Registrar inicio de sesión
   - Si Sí [s:] → Mostrar interfaz inicial según rol
7. Sistema retorna a página principal según rol del usuario

**Caminos alternos:**
- Error en validación de datos → Mostrar mensaje de error
- Credenciales inválidas → Opción de aceptar y reintentar
- Credenciales válidas → Registrar inicio de sesión y mostrar interfaz

**Elementos de validación:**
- Existe campo de datos (formulario con campos específicos)
- Existe registro de entrada de datos (log de intentos)
- Se permite hasta 3 intentos para ingresar credenciales válidas

---

## PARTE 2: Estructura y Dependencias del Proyecto

### Backend (Spring Boot)

```
backend/
├── src/main/java/com/academico/
│   ├── config/
│   │   ├── SecurityConfig.java
│   │   └── SwaggerConfig.java
│   ├── presentation/
│   │   ├── controller/
│   │   │   ├── AuthenticationController.java
│   │   │   └── HomeController.java
│   │   └── dto/
│   │       ├── LoginRequestDTO.java
│   │       ├── LoginResponseDTO.java
│   │       ├── ErrorResponseDTO.java
│   │       └── SessionInitDTO.java
│   ├── domain/
│   │   ├── service/
│   │   │   ├── AuthenticationService.java
│   │   │   └── CredentialValidationService.java
│   │   ├── model/
│   │   │   ├── User.java
│   │   │   ├── LoginAttempt.java
│   │   │   └── SessionLog.java
│   │   └── exception/
│   │       ├── InvalidCredentialsException.java
│   │       ├── UserNotFoundException.java
│   │       ├── MaxAttemptsExceededException.java
│   │       └── GlobalExceptionHandler.java
│   ├── persistence/
│   │   ├── repository/
│   │   │   ├── UserRepository.java
│   │   │   ├── LoginAttemptRepository.java
│   │   │   └── SessionLogRepository.java
│   │   └── entity/
│   │       ├── UserEntity.java
│   │       ├── LoginAttemptEntity.java
│   │       └── SessionLogEntity.java
│   └── util/
│       └── CredentialValidator.java
├── src/main/resources/
│   ├── application.properties
│   └── schema.sql
└── pom.xml
```

### Frontend (React)

```
frontend/
├── src/
│   ├── components/
│   │   ├── LoginForm.jsx
│   │   ├── ErrorModal.jsx
│   │   └── HomePageContent.jsx
│   ├── views/
│   │   ├── HomePage.jsx
│   │   ├── LoginPage.jsx
│   │   └── DashboardPage.jsx
│   ├── services/
│   │   ├── authService.js
│   │   └── apiClient.js
│   ├── routes/
│   │   └── AppRoutes.jsx
│   ├── context/
│   │   └── AuthContext.jsx
│   ├── App.jsx
│   └── index.js
├── package.json
└── .env
```

---

## PARTE 3: Reglas Estrictas para el Agente IA

### 3.1 Reglas de Mapeo Diagrama → Código

#### Diagrama 1 (Acceso Principal)

- **Actividad "Ingresa URL del sitio web"** → Endpoint GET `/api/home`
  - Capa Controller: `HomeController.java` → método `getHomePage()`
  - Capa Service: `HomeService.java` → método `getHomePageContent()`
  - Retorna: `HomePageContentDTO` con misión, visión, información general, y botón de inicio de sesión/preinscripción
  - Documentación: `// Diagram1-Activity: Ingresa URL - expone contenido principal`

- **Actividad "Muestra página principal"** → Componente React `HomePage.jsx`
  - Debe renderizar el contenido retornado por `/api/home`
  - Botones: "Iniciar Sesión" (navega a `/login`) y "Preinscribirse" (navega a `/register`)
  - Documentación: `// Diagram1-Activity: Muestra página principal con botones`

#### Diagrama 2 (Autenticación y Validación)

- **Actividad "Desplegar formulario de sesión con botón de ingresar"** → Componente React `LoginForm.jsx`
  - Campos: email, password (ambos con validación básica HTML5)
  - Botón: "Ingresar" que dispara acción `handleLogin()`
  - Documentación: `// Diagram2-Activity: Desplegar formulario - renderiza campos según formulario definido`

- **Actividad "Ingresar correo y contraseña"** → Estado React en `LoginForm.jsx`
  - State: `{ email: "", password: "" }`
  - OnChange handlers para ambos campos
  - Documentación: `// Diagram2-Activity: Ingresar correo y contraseña - actualiza estado local`

- **Actividad "Dar clic en botón Ingresar"** → Manejador de evento en `LoginForm.jsx`
  - Valida que campos no estén vacíos
  - Llama a `authService.login(email, password)`
  - Documentación: `// Diagram2-Activity: Dar clic en botón - invoca servicio de autenticación`

- **Actividad "Validar Credenciales" (bloque de subproceso)** → Endpoint POST `/api/auth/login`
  - Capa Controller: `AuthenticationController.java` → método `login(LoginRequestDTO)`
    - DTO de entrada: `{ email, password }`
    - Documentación: `// Diagram2-SubActivity: Recibe credenciales en DTO`
  
  - Capa Service: `AuthenticationService.java` → método `authenticate(email, password)`
    - Llamada a `credentialValidationService.validateCredentials(email, password)`
    - Si validación falla (UserNotFoundException) → Lanza `UserNotFoundException`
    - Si validación pasa → Verifica campo `credentials_valid` (¿Credenciales válidas?)
      - Si es true → Registra sesión en `SessionLogService` con `registerSessionStart(userId)`
      - Si es false → Registra intento fallido en `LoginAttemptService` con `recordFailedAttempt(userId)`
        - Si intentos >= 3 → Lanza `MaxAttemptsExceededException`
    - Documentación: `// Diagram2-SubActivity: Validar Credenciales - orquesta validación y registro`
  
  - Capa Persistencia: Queries a `users`, `login_attempts`, `session_logs`
    - UserRepository: `findByEmail(email)`
    - LoginAttemptRepository: `countRecentAttempts(userId, lastHour)`
    - SessionLogRepository: `save(SessionLogEntity)`
    - Documentación: `// Diagram2-SubActivity: Persistencia - interactúa con BD`

- **Nodo de Decisión "¿Error en la base de datos?"** → Try-Catch en Controller
  - Si excepción de datos → Catch `InvalidCredentialsException` o `DataAccessException`
  - Retorna: `ErrorResponseDTO` con mensaje "Error en la base de datos" y opción "Aceptar"
  - HTTP Status: 401 (Unauthorized) o 400 (Bad Request)
  - Documentación: `// Diagram2-Decision: Error en BD - captura excepción y retorna error`

- **Nodo de Decisión "¿Credenciales válidas?"** → Lógica condicional en Service
  - Path [Sí] (credenciales válidas):
    - Llamar `SessionLogService.registerSessionStart(userId)`
    - Retorna: `LoginResponseDTO` con token JWT, rol de usuario, información de sesión
    - HTTP Status: 200 (OK)
    - Documentación: `// Diagram2-Decision: Credenciales válidas [Sí] - inicia sesión`
  
  - Path [No] (credenciales inválidas):
    - Llamar `LoginAttemptService.recordFailedAttempt(userId)`
    - Si intentos >= 3 → Lanza `MaxAttemptsExceededException`
    - Retorna: `ErrorResponseDTO` con mensaje "Credenciales inválidas" y opción "Aceptar"
    - HTTP Status: 401 (Unauthorized)
    - Documentación: `// Diagram2-Decision: Credenciales válidas [No] - rechaza intento`

- **Actividad "Mostrar mensaje de error con opción Aceptar"** → Componente React `ErrorModal.jsx`
  - Props: `{ message, onAccept }`
  - Aceptar: Limpia estado, permite reintentos
  - Documentación: `// Diagram2-Activity: Mostrar mensaje de error - renderiza modal`

- **Actividad "Registrar inicio de sesión"** → Método en `SessionLogService.java`
  - Crea registro en tabla `session_logs` con timestamp, user_id, rol
  - Retorna: `SessionInitDTO` con información de sesión iniciada
  - Documentación: `// Diagram2-Activity: Registrar inicio de sesión - persiste log`

- **Actividad "Mostrar interfaz inicial según rol"** → Componente React `DashboardPage.jsx`
  - Recibe rol del usuario desde contexto `AuthContext`
  - Renderiza vistas diferentes según rol (admin, docente, estudiante, etc.)
  - Documentación: `// Diagram2-Activity: Mostrar interfaz según rol - renderiza dashboard`

### 3.2 Reglas para DTOs

**LoginRequestDTO:**
```
- email: String (validado: no nulo, formato email)
- password: String (validado: no nulo, mínimo 8 caracteres)
```

**LoginResponseDTO:**
```
- token: String (JWT)
- userId: Long
- email: String
- role: String
- sessionId: Long
- loginTime: LocalDateTime
```

**ErrorResponseDTO:**
```
- errorCode: String
- errorMessage: String
- timestamp: LocalDateTime
- path: String
```

**SessionInitDTO:**
```
- sessionId: Long
- userId: Long
- email: String
- role: String
- loginTime: LocalDateTime
```

**HomePageContentDTO:**
```
- mission: String
- vision: String
- generalInfo: String
- showLoginButton: Boolean
- showRegisterButton: Boolean
```

### 3.3 Reglas para Excepciones Personalizadas

```
- InvalidCredentialsException: cuando email/password no cumple validación
- UserNotFoundException: cuando email no existe en BD
- MaxAttemptsExceededException: cuando intentos fallidos >= 3
- SessionLogException: cuando falla registro de sesión
```

### 3.4 Reglas para Componentes React

- **LoginForm.jsx:** Debe incluir validación de campos vacíos antes de enviar
- **ErrorModal.jsx:** Debe mostrar mensaje de error y permitir reintentos
- **HomePage.jsx:** Debe ser responsiva y mostrar botones de acción claramente
- **DashboardPage.jsx:** Debe renderizarse solo si usuario está autenticado (protegido por ruta privada)

### 3.5 Reglas para Persistencia

**Entidades:**
- `UserEntity`: id, email, hashedPassword, role, createdAt, updatedAt
- `LoginAttemptEntity`: id, userId, attemptTime, success, ipAddress
- `SessionLogEntity`: id, userId, loginTime, logoutTime, role

**Índices:**
- `users.email` (UNIQUE)
- `login_attempts.user_id, attempt_time`
- `session_logs.user_id, login_time`

### 3.6 Trazabilidad Obligatoria

Cada bloque de código generado debe incluir comentario:
```java
// Diagram{N}-{Type}: {ActivityName} - {Description}
```

Ejemplo:
```java
@PostMapping("/login")
public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
    // Diagram2-Controller: Dar clic en botón Ingresar - recibe credenciales y dispara validación
    try {
        LoginResponseDTO response = authenticationService.authenticate(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(response);
    } catch (InvalidCredentialsException e) {
        // Diagram2-Decision: ¿Credenciales válidas? [No] - retorna error
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
            new ErrorResponseDTO("AUTH_INVALID", "Credenciales inválidas", LocalDateTime.now(), request.toString())
        );
    }
}
```

---

## PARTE 4: Guía Paso a Paso para el Agente IA

### Paso 1: Configuración Inicial del Proyecto Backend

1. Crear proyecto Spring Boot con Maven
2. Agregar dependencias en `pom.xml`:
   - spring-boot-starter-web
   - spring-boot-starter-data-jpa
   - spring-boot-starter-security
   - springdoc-openapi-starter-webmvc-ui (Swagger)
   - mysql-connector-java (o postgresql)
   - lombok
   - java-jwt

3. Configurar `application.properties`:
   ```
   spring.datasource.url=jdbc:mysql://localhost:3306/academico_db
   spring.datasource.username=root
   spring.datasource.password=
   spring.jpa.hibernate.ddl-auto=create-drop
   spring.jpa.show-sql=true
   server.port=8080
   ```

### Paso 2: Crear Estructura de Capas Backend

1. **Capa de Presentación:**
   - Crear `HomeController.java` con método `getHomePage()` → GET `/api/home`
   - Crear `AuthenticationController.java` con método `login(LoginRequestDTO)` → POST `/api/auth/login`
   - Crear DTOs correspondientes en carpeta `dto/`
   - Crear `GlobalExceptionHandler.java` para manejo centralizado de excepciones

2. **Capa de Dominio/Servicios:**
   - Crear `HomeService.java` que retorne contenido de página principal
   - Crear `AuthenticationService.java` que orqueste el flujo de validación
   - Crear `CredentialValidationService.java` que valide email/password contra BD
   - Crear `SessionLogService.java` que registre inicio de sesiones
   - Crear `LoginAttemptService.java` que registre intentos fallidos
   - Crear excepciones personalizadas en `exception/`

3. **Capa de Persistencia:**
   - Crear entidades JPA: `UserEntity`, `LoginAttemptEntity`, `SessionLogEntity`
   - Crear repositorios: `UserRepository`, `LoginAttemptRepository`, `SessionLogRepository`
   - Crear schema SQL en `resources/schema.sql`

### Paso 3: Crear Estructura de Frontend

1. **Configuración Inicial:**
   - `npx create-react-app frontend`
   - Instalar dependencias: `axios`, `react-router-dom`, `react-context`
   - Crear archivo `.env` con `REACT_APP_API_BASE_URL=http://localhost:8080`

2. **Estructura de Carpetas:**
   - Crear carpeta `components/` con: `LoginForm.jsx`, `ErrorModal.jsx`, `HomePageContent.jsx`
   - Crear carpeta `views/` con: `HomePage.jsx`, `LoginPage.jsx`, `DashboardPage.jsx`
   - Crear carpeta `services/` con: `authService.js`, `apiClient.js`
   - Crear carpeta `routes/` con: `AppRoutes.jsx`
   - Crear carpeta `context/` con: `AuthContext.jsx`

### Paso 4: Implementar Flujo Diagrama 1 (Página Principal)

**Backend:**
```java
// HomeController.java
@RestController
@RequestMapping("/api/home")
public class HomeController {
    @Autowired
    private HomeService homeService;
    
    @GetMapping
    public ResponseEntity<HomePageContentDTO> getHomePage() {
        // Diagram1-Activity: Ingresa URL del sitio web - expone página principal
        HomePageContentDTO content = homeService.getHomePageContent();
        return ResponseEntity.ok(content);
    }
}

// HomeService.java
@Service
public class HomeService {
    public HomePageContentDTO getHomePageContent() {
        // Diagram1-Activity: Muestra página principal - retorna contenido
        return HomePageContentDTO.builder()
            .mission("Formar profesionales en sistemas...")
            .vision("Ser líderes en educación...")
            .generalInfo("Información general del sistema...")
            .showLoginButton(true)
            .showRegisterButton(true)
            .build();
    }
}
```

**Frontend:**
```jsx
// HomePage.jsx
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import authService from '../services/authService';

export default function HomePage() {
    // Diagram1-Activity: Ingresa URL del sitio web - fetch contenido
    const [content, setContent] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchHome = async () => {
            const data = await authService.getHomeContent();
            setContent(data);
        };
        fetchHome();
    }, []);

    return (
        <div>
            {/* Diagram1-Activity: Muestra página principal - renderiza contenido */}
            <h1>Bienvenido</h1>
            <p><strong>Misión:</strong> {content?.mission}</p>
            <p><strong>Visión:</strong> {content?.vision}</p>
            <p>{content?.generalInfo}</p>
            
            {content?.showLoginButton && (
                <button onClick={() => navigate('/login')}>Iniciar Sesión</button>
            )}
            {content?.showRegisterButton && (
                <button onClick={() => navigate('/register')}>Preinscribirse</button>
            )}
        </div>
    );
}
```

### Paso 5: Implementar Flujo Diagrama 2 (Autenticación)

**Backend - Validación y Almacenamiento:**

```java
// AuthenticationService.java
@Service
@Transactional
public class AuthenticationService {
    @Autowired private UserRepository userRepository;
    @Autowired private LoginAttemptRepository loginAttemptRepository;
    @Autowired private SessionLogRepository sessionLogRepository;
    @Autowired private CredentialValidationService credentialValidator;
    
    public LoginResponseDTO authenticate(String email, String password) {
        // Diagram2-Activity: Validar Credenciales - orquesta flujo
        
        try {
            // Diagram2-SubActivity: Validar que esté registrado/USUARIO existe
            UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));
            
            // Diagram2-Decision: ¿Error en la base de datos? [No] - usuario encontrado
            
            // Diagram2-SubActivity: Validar credenciales
            if (!credentialValidator.validatePassword(password, user.getHashedPassword())) {
                // Diagram2-Decision: ¿Credenciales válidas? [No] - registra intento fallido
                recordFailedAttempt(user.getId());
                throw new InvalidCredentialsException("Credenciales inválidas");
            }
            
            // Diagram2-Decision: ¿Credenciales válidas? [Sí] - inicia sesión
            SessionLogEntity sessionLog = registerSessionStart(user);
            
            return LoginResponseDTO.builder()
                .token(generateJWT(user))
                .userId(user.getId())
                .email(user.getEmail())
                .role(user.getRole())
                .sessionId(sessionLog.getId())
                .loginTime(sessionLog.getLoginTime())
                .build();
                
        } catch (UserNotFoundException | InvalidCredentialsException e) {
            // Diagram2-Activity: Mostrar mensaje de error - controlador retorna error
            throw e;
        }
    }
    
    private void recordFailedAttempt(Long userId) {
        // Diagram2-Activity: Registra intento fallido
        int recentAttempts = loginAttemptRepository.countRecentFailedAttempts(userId);
        
        if (recentAttempts >= 3) {
            throw new MaxAttemptsExceededException("Máximo de intentos excedido");
        }
        
        LoginAttemptEntity attempt = LoginAttemptEntity.builder()
            .userId(userId)
            .attemptTime(LocalDateTime.now())
            .success(false)
            .build();
        loginAttemptRepository.save(attempt);
    }
    
    private SessionLogEntity registerSessionStart(UserEntity user) {
        // Diagram2-Activity: Registrar inicio de sesión - persiste en BD
        SessionLogEntity log = SessionLogEntity.builder()
            .userId(user.getId())
            .loginTime(LocalDateTime.now())
            .role(user.getRole())
            .build();
        return sessionLogRepository.save(log);
    }
}

// GlobalExceptionHandler.java
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidCredentials(InvalidCredentialsException e) {
        // Diagram2-Decision: Error en datos - retorna respuesta de error
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
            ErrorResponseDTO.builder()
                .errorCode("AUTH_INVALID")
                .errorMessage(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build()
        );
    }
    
    @ExceptionHandler(MaxAttemptsExceededException.class)
    public ResponseEntity<ErrorResponseDTO> handleMaxAttempts(MaxAttemptsExceededException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
            ErrorResponseDTO.builder()
                .errorCode("AUTH_MAX_ATTEMPTS")
                .errorMessage("Has excedido el máximo de intentos. Intenta más tarde.")
                .timestamp(LocalDateTime.now())
                .build()
        );
    }
}
```

**Frontend - Formulario y Autenticación:**

```jsx
// LoginForm.jsx
import { useState } from 'react';
import ErrorModal from './ErrorModal';
import authService from '../services/authService';

export default function LoginForm({ onSuccess }) {
    // Diagram2-Activity: Ingresar correo y contraseña - estado local
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(false);

    const handleLogin = async () => {
        // Diagram2-Activity: Dar clic en botón Ingresar - valida y envía
        if (!email || !password) {
            setError('Todos los campos son requeridos');
            return;
        }

        setLoading(true);
        try {
            const response = await authService.login(email, password);
            // Diagram2-Decision: ¿Credenciales válidas? [Sí] - llama callback
            onSuccess(response);
        } catch (err) {
            // Diagram2-Decision: ¿Credenciales válidas? [No] - muestra error
            setError(err.response?.data?.errorMessage || 'Error en la autenticación');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div>
            {/* Diagram2-Activity: Desplegar formulario - renderiza campos */}
            <h2>Iniciar Sesión</h2>
            <input
                type="email"
                placeholder="Correo"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                disabled={loading}
            />
            <input
                type="password"
                placeholder="Contraseña"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                disabled={loading}
            />
            <button onClick={handleLogin} disabled={loading}>
                {loading ? 'Ingresando...' : 'Ingresar'}
            </button>

            {error && (
                // Diagram2-Activity: Mostrar mensaje de error con opción Aceptar
                <ErrorModal 
                    message={error} 
                    onAccept={() => setError(null)}
                />
            )}
        </div>
    );
}

// ErrorModal.jsx
export default function ErrorModal({ message, onAccept }) {
    return (
        <div className="modal-overlay">
            <div className="modal">
                <p>{message}</p>
                <button onClick={onAccept}>Aceptar</button>
            </div>
        </div>
    );
}

// authService.js
import apiClient from './apiClient';

const authService = {
    getHomeContent: async () => {
        const response = await apiClient.get('/home');
        return response.data;
    },
    
    login: async (email, password) => {
        const response = await apiClient.post('/auth/login', { email, password });
        return response.data;
    }
};

export default authService;

// apiClient.js
import axios from 'axios';

const apiClient = axios.create({
    baseURL: process.env.REACT_APP_API_BASE_URL || 'http://localhost:8080/api'
});

export default apiClient;
```

### Paso 6: Crear Rutas Protegidas

```jsx
// AppRoutes.jsx
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { useContext } from 'react';
import { AuthContext } from '../context/AuthContext';
import HomePage from '../views/HomePage';
import LoginPage from '../views/LoginPage';
import DashboardPage from '../views/DashboardPage';

const ProtectedRoute = ({ children }) => {
    const { user } = useContext(AuthContext);
    return user ? children : <Navigate to="/login" />;
};

export default function AppRoutes() {
    return (
        <BrowserRouter>
            <Routes>
                {/* Diagram1-Activity: Ingresa URL - Home sin autenticación */}
                <Route path="/" element={<HomePage />} />
                
                {/* Diagram2-Activity: Desplegar formulario - Login sin autenticación */}
                <Route path="/login" element={<LoginPage />} />
                
                {/* Diagram2-Activity: Mostrar interfaz inicial según rol - Dashboard protegido */}
                <Route 
                    path="/dashboard" 
                    element={
                        <ProtectedRoute>
                            <DashboardPage />
                        </ProtectedRoute>
                    } 
                />
            </Routes>
        </BrowserRouter>
    );
}
```

### Paso 7: Crear Contexto de Autenticación

```jsx
// AuthContext.jsx
import { createContext, useState, useCallback } from 'react';
import authService from '../services/authService';

export const AuthContext = createContext();

export function AuthProvider({ children }) {
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(false);

    const login = useCallback(async (email, password) => {
        setLoading(true);
        try {
            const response = await authService.login(email, password);
            // Diagram2-Activity: Registrar inicio de sesión - almacena usuario
            setUser({
                id: response.userId,
                email: response.email,
                role: response.role,
                token: response.token,
                sessionId: response.sessionId
            });
            return response;
        } finally {
            setLoading(false);
        }
    }, []);

    const logout = useCallback(() => {
        setUser(null);
    }, []);

    return (
        <AuthContext.Provider value={{ user, login, logout, loading }}>
            {children}
        </AuthContext.Provider>
    );
}
```

---

## PARTE 5: Reglas Estrictas de Cumplimiento

### Prohibiciones del Agente IA

1. **Prohibido inferir operaciones no explicitadas en los diagramas.** Solo mapear flujos visibles.
2. **Prohibido generar código sin comentarios de trazabilidad.** Todo comentario debe incluir `// Diagram{N}-{Tipo}: {Actividad}`.
3. **Prohibido omitir manejo de excepciones.** Todo camino alterno debe capturarse.
4. **Prohibido crear namespaces o entidades no mencionadas.** Solo lo requerido por los diagramas.
5. **Prohibido mezclar lógica entre capas.** Controller ↔ Service ↔ Repository deben estar separadas.
6. **Prohibido omitir DTOs.** Cada transacción significativa requiere DTO asociado.
7. **Prohibido renombrar conceptos del diagrama.** Respetar nombres y descripciones exactamente.

### Validaciones Obligatorias

- [ ] Todos los endpoints tienen documentación Swagger auto-generada
- [ ] Todos los DTOs tienen validaciones @NotNull, @Email, etc.
- [ ] Todas las excepciones están capturadas en GlobalExceptionHandler
- [ ] Todas las funciones React tienen comentarios de origen en diagrama
- [ ] Todos los flujos alternos están implementados (caminos de error)
- [ ] La persistencia refleja exactamente las entidades mencionadas en diagramas
- [ ] Las rutas REST siguen estructura REST estándar: GET, POST, PUT, DELETE

---

## PARTE 6: Pruebas Automáticas Requeridas

### Backend (JUnit 5 + Mockito)

```java
@SpringBootTest
public class AuthenticationControllerTest {
    @Autowired private MockMvc mockMvc;
    @MockBean private AuthenticationService authService;

    @Test
    public void testLoginSuccess() throws Exception {
        // Diagram2-Decision: ¿Credenciales válidas? [Sí]
        LoginResponseDTO response = LoginResponseDTO.builder()
            .token("jwt-token").userId(1L).email("user@test.com").build();
        
        when(authService.authenticate("user@test.com", "password123"))
            .thenReturn(response);

        mockMvc.perform(post("/api/auth/login")
            .contentType(APPLICATION_JSON)
            .content("{\"email\":\"user@test.com\",\"password\":\"password123\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").exists());
    }

    @Test
    public void testLoginFailure() throws Exception {
        // Diagram2-Decision: ¿Credenciales válidas? [No]
        when(authService.authenticate(anyString(), anyString()))
            .thenThrow(new InvalidCredentialsException("Credenciales inválidas"));

        mockMvc.perform(post("/api/auth/login")
            .contentType(APPLICATION_JSON)
            .content("{\"email\":\"user@test.com\",\"password\":\"wrong\"}"))
            .andExpect(status().isUnauthorized());
    }
}
```

### Frontend (Jest + React Testing Library)

```javascript
import { render, screen, fireEvent } from '@testing-library/react';
import LoginForm from '../components/LoginForm';

describe('LoginForm', () => {
    it('should display login form', () => {
        // Diagram2-Activity: Desplegar formulario - renderiza componente
        render(<LoginForm onSuccess={jest.fn()} />);
        expect(screen.getByPlaceholderText('Correo')).toBeInTheDocument();
    });

    it('should show error on invalid credentials', async () => {
        // Diagram2-Decision: ¿Credenciales válidas? [No] - muestra error
        const mockOnSuccess = jest.fn();
        render(<LoginForm onSuccess={mockOnSuccess} />);
        
        fireEvent.change(screen.getByPlaceholderText('Correo'), { target: { value: 'test@test.com' } });
        fireEvent.change(screen.getByPlaceholderText('Contraseña'), { target: { value: 'wrong' } });
        fireEvent.click(screen.getByText('Ingresar'));
        
        await screen.findByText(/credenciales inválidas/i);
    });
});
```

---

## Resumen de Entregables

Una vez ejecutados todos los pasos, el agente IA debe haber generado:

1. **Backend:** 8 Controllers/Services, 5 Repositories, 3 Entities, 5 DTOs, 4 Exceptions, tests
2. **Frontend:** 3 Views, 5 Components, 2 Services, 1 Context, routes configuradas
3. **Base de Datos:** 3 tablas con índices y relaciones
4. **Documentación:** Swagger auto-generado, comentarios en código
5. **Tests:** Mínimo 10 tests backend, 5 tests frontend

Este README es el contrato de cumplimiento. Cualquier desviación debe ser reportada y justificada.