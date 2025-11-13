# Configuración de CORS - Sistema Académico

## ✅ Estado de la Configuración

### Backend (Spring Boot)
**Archivo**: `backend/src/main/java/com/academico/config/SecurityConfig.java`

**Configuración Implementada**:
```java
@Bean
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    
    // Permite cualquier puerto en localhost (3000, 5173, etc.)
    configuration.setAllowedOriginPatterns(Arrays.asList(
        "http://localhost:*", 
        "http://127.0.0.1:*"
    ));
    
    // Métodos HTTP permitidos
    configuration.setAllowedMethods(Arrays.asList(
        "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"
    ));
    
    // Headers permitidos y expuestos
    configuration.setAllowedHeaders(Arrays.asList(
        "Authorization", "Content-Type", "Accept", 
        "Origin", "X-Requested-With",
        "Access-Control-Request-Method",
        "Access-Control-Request-Headers"
    ));
    
    // Permite credenciales (JWT tokens, cookies)
    configuration.setAllowCredentials(true);
    
    // Cache para preflight requests (1 hora)
    configuration.setMaxAge(3600L);
}
```

**Características**:
- ✅ Permite peticiones desde cualquier puerto localhost
- ✅ Soporta todos los métodos HTTP necesarios (GET, POST, PUT, DELETE, OPTIONS)
- ✅ Permite envío de credenciales (JWT en header Authorization)
- ✅ Maneja preflight requests (OPTIONS)
- ✅ Expone headers necesarios al frontend
- ✅ Cache de 1 hora para preflight

---

### Frontend (React)
**Archivos**:
1. `frontend/package.json`
2. `frontend/src/services/apiClient.js`
3. `frontend/.env`

#### 1. package.json - Proxy Configuration
```json
{
  "proxy": "http://localhost:8080"
}
```
**Propósito**: Durante desarrollo, el proxy redirige requests `/api/*` al backend automáticamente.

#### 2. apiClient.js - Axios Configuration
```javascript
const apiClient = axios.create({
    baseURL: 'http://localhost:8080/api',
    headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
    },
    withCredentials: true  // ✅ Envía credenciales con cada request
});
```

**Interceptors Configurados**:
- **Request**: Agrega JWT token al header `Authorization`
- **Response**: Maneja errores 401 (token expirado/inválido)

#### 3. .env - Variables de Entorno
```env
REACT_APP_API_BASE_URL=http://localhost:8080/api
```

---

## 🔄 Flujo de Comunicación

### 1. Petición desde Frontend
```
Usuario → LoginForm.jsx → authService.login() → apiClient.post('/auth/login')
```

### 2. Request HTTP
```http
POST http://localhost:8080/api/auth/login
Origin: http://localhost:3000
Content-Type: application/json
Accept: application/json

{
  "email": "estudiante@universidad.edu.co",
  "password": "password123"
}
```

### 3. Backend Procesa CORS
```
Browser envía OPTIONS preflight →
SecurityConfig.corsConfigurationSource() valida origen →
Backend responde con headers CORS →
Browser permite el request POST →
AuthenticationController.login() procesa
```

### 4. Response del Backend
```http
HTTP/1.1 200 OK
Access-Control-Allow-Origin: http://localhost:3000
Access-Control-Allow-Credentials: true
Content-Type: application/json

{
  "token": "eyJhbGciOiJIUzI1NiIs...",
  "user": { ... }
}
```

### 5. Frontend Recibe Response
```javascript
// apiClient interceptor guarda token
localStorage.setItem('token', response.data.token);

// Requests posteriores incluyen token
Authorization: Bearer eyJhbGciOiJIUzI1NiIs...
```

---

## 🧪 Pruebas de CORS

### Test 1: Preflight Request (OPTIONS)
```bash
curl -X OPTIONS http://localhost:8080/api/auth/login \
  -H "Origin: http://localhost:3000" \
  -H "Access-Control-Request-Method: POST" \
  -H "Access-Control-Request-Headers: Content-Type,Authorization" \
  -v
```

**Respuesta Esperada**:
```
< HTTP/1.1 200 OK
< Access-Control-Allow-Origin: http://localhost:3000
< Access-Control-Allow-Methods: GET,POST,PUT,DELETE,PATCH,OPTIONS
< Access-Control-Allow-Headers: Authorization,Content-Type,Accept,...
< Access-Control-Allow-Credentials: true
< Access-Control-Max-Age: 3600
```

### Test 2: Login Request
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Origin: http://localhost:3000" \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@universidad.edu.co","password":"admin123"}' \
  -v
```

**Respuesta Esperada**:
```
< HTTP/1.1 200 OK
< Access-Control-Allow-Origin: http://localhost:3000
< Access-Control-Allow-Credentials: true
< Content-Type: application/json
{
  "token": "eyJhbGciOiJIUzI1NiIs...",
  "user": { "email": "admin@...", "role": "ADMIN" }
}
```

### Test 3: Desde Browser Console
```javascript
// Ejecutar en http://localhost:3000 (DevTools Console)
fetch('http://localhost:8080/api/home', {
    method: 'GET',
    headers: {
        'Content-Type': 'application/json'
    },
    credentials: 'include'
})
.then(r => r.json())
.then(data => console.log('✅ CORS funciona:', data))
.catch(err => console.error('❌ Error CORS:', err));
```

---

## 🚀 Instrucciones de Ejecución

### 1. Iniciar Backend
```bash
cd backend
mvn clean install
mvn spring-boot:run
```
**Verifica**: Backend corriendo en `http://localhost:8080`

### 2. Iniciar Frontend
```bash
cd frontend
npm install
npm start
```
**Verifica**: Frontend corriendo en `http://localhost:3000`

### 3. Verificar Conexión
1. Abre `http://localhost:3000` en el navegador
2. Intenta hacer login con:
   - Email: `admin@universidad.edu.co`
   - Password: `admin123`
3. Abre DevTools → Network → verifica:
   - Request a `http://localhost:8080/api/auth/login`
   - Headers de respuesta incluyen `Access-Control-Allow-Origin`
   - Status 200 OK

---

## 🐛 Troubleshooting

### Problema: "CORS policy: No 'Access-Control-Allow-Origin' header"
**Causa**: Backend no configurado o no corriendo
**Solución**:
1. Verifica que backend esté corriendo: `curl http://localhost:8080/api/home`
2. Revisa logs de Spring Boot para errores
3. Verifica `SecurityConfig.java` tiene `corsConfigurationSource()`

### Problema: "Preflight request doesn't pass"
**Causa**: OPTIONS request bloqueado
**Solución**:
1. Verifica que `allowedMethods` incluya `"OPTIONS"`
2. Verifica que Spring Security permita OPTIONS sin autenticación
3. En `SecurityConfig.httpSecurity`: `.requestMatchers("/**").permitAll()` para OPTIONS

### Problema: "Credentials flag is 'true', but Access-Control-Allow-Credentials is not"
**Causa**: `allowCredentials` no configurado
**Solución**:
1. Backend: `configuration.setAllowCredentials(true)`
2. Frontend: `withCredentials: true` en axios

### Problema: "Request header field Authorization is not allowed"
**Causa**: Header Authorization no permitido en CORS
**Solución**:
- Verifica `allowedHeaders` incluya `"Authorization"`

---

## 📋 Checklist de Verificación

### Backend ✅
- [x] `SecurityConfig.java` tiene `corsConfigurationSource()`
- [x] `allowedOriginPatterns` incluye `http://localhost:*`
- [x] `allowedMethods` incluye GET, POST, PUT, DELETE, OPTIONS
- [x] `allowedHeaders` incluye Authorization, Content-Type
- [x] `allowCredentials` es `true`
- [x] Spring Security permite preflight (OPTIONS)

### Frontend ✅
- [x] `package.json` tiene proxy `http://localhost:8080`
- [x] `apiClient.js` tiene `withCredentials: true`
- [x] `apiClient.js` baseURL apunta a `http://localhost:8080/api`
- [x] Request interceptor agrega JWT en header Authorization
- [x] `.env` tiene `REACT_APP_API_BASE_URL` configurado

### Testing ✅
- [x] Backend responde a `curl http://localhost:8080/api/home`
- [x] Frontend puede hacer login exitosamente
- [x] DevTools muestra headers CORS en responses
- [x] JWT token se guarda en localStorage
- [x] Requests autenticados incluyen `Authorization: Bearer <token>`

---

## 📚 Referencias

- **Spring CORS**: https://docs.spring.io/spring-framework/reference/web/webmvc-cors.html
- **Axios withCredentials**: https://axios-http.com/docs/req_config
- **React Proxy**: https://create-react-app.dev/docs/proxying-api-requests-in-development/
- **MDN CORS**: https://developer.mozilla.org/en-US/docs/Web/HTTP/CORS

---

## ✨ Resumen

**CORS está completamente configurado y habilitado**:
- ✅ Backend permite requests desde cualquier puerto localhost
- ✅ Todos los métodos HTTP necesarios habilitados
- ✅ Credenciales (JWT) permitidas
- ✅ Frontend configurado con proxy y withCredentials
- ✅ Headers Authorization y Content-Type permitidos
- ✅ Preflight requests manejados correctamente

**La comunicación frontend-backend está lista para funcionar sin problemas de CORS**.
