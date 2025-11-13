# Frontend - Sistema de Gestión Académica

Frontend React implementado con diseño moderno e intuitivo basado en diagramas de actividad UML.

## 🎨 Características de Diseño

- **Interfaz Amigable**: Diseño moderno con gradientes y animaciones suaves
- **Responsive**: Adaptable a dispositivos móviles, tablets y desktop
- **Intuitivo**: Navegación clara con iconos SVG y retroalimentación visual
- **Accesible**: Colores contrastantes y textos legibles

## 🏗️ Estructura del Proyecto

```
frontend/
├── public/
│   └── index.html                    # HTML principal
├── src/
│   ├── components/                   # Componentes reutilizables
│   │   ├── ErrorModal.jsx           # Modal de errores
│   │   ├── ErrorModal.css
│   │   ├── LoginForm.jsx            # Formulario de login
│   │   ├── LoginForm.css
│   │   ├── HomePageContent.jsx      # Contenido de home
│   │   └── HomePageContent.css
│   ├── views/                        # Páginas principales
│   │   ├── HomePage.jsx             # Diagram1: Página principal
│   │   ├── HomePage.css
│   │   ├── LoginPage.jsx            # Diagram2: Página de login
│   │   ├── LoginPage.css
│   │   ├── DashboardPage.jsx        # Diagram2: Dashboard por rol
│   │   └── DashboardPage.css
│   ├── services/                     # Servicios de API
│   │   ├── apiClient.js             # Cliente Axios configurado
│   │   └── authService.js           # Servicio de autenticación
│   ├── context/                      # Context API
│   │   └── AuthContext.jsx          # Estado global de autenticación
│   ├── routes/                       # Configuración de rutas
│   │   └── AppRoutes.jsx            # Router con rutas protegidas
│   ├── App.jsx                       # Componente principal
│   ├── App.css                       # Estilos globales
│   └── index.js                      # Punto de entrada
├── .env                              # Variables de entorno
└── package.json                      # Dependencias
```

## 📋 Requisitos Previos

- Node.js 16 o superior
- npm 8 o superior
- Backend corriendo en `http://localhost:8080`

## 🚀 Instalación y Ejecución

### 1. Instalar Dependencias

```bash
cd frontend
npm install
```

### 2. Configurar Variables de Entorno

El archivo `.env` ya está configurado:
```
REACT_APP_API_BASE_URL=http://localhost:8080/api
```

### 3. Ejecutar en Modo Desarrollo

```bash
npm start
```

La aplicación estará disponible en: `http://localhost:3000`

### 4. Compilar para Producción

```bash
npm run build
```

## 🎯 Flujos Implementados

### Diagram1: Página Principal

**Ruta**: `/`

1. Usuario accede a la URL
2. Sistema muestra página principal con:
   - **Misión**: Card con gradiente morado
   - **Visión**: Card con gradiente rosa
   - **Información General**: Card con gradiente azul
3. Botones de acción:
   - **Iniciar Sesión**: Navega a `/login`
   - **Preinscribirse**: Muestra alerta (funcionalidad futura)

### Diagram2: Autenticación Completa

**Ruta**: `/login`

1. Usuario ve formulario con diseño amigable:
   - Campo de email con validación
   - Campo de password con toggle de visibilidad
   - Información de límite de 3 intentos
2. Usuario ingresa credenciales
3. Al hacer clic en "Ingresar":
   - Validación frontend (email válido, password mínimo 8 caracteres)
   - Request a backend `/api/auth/login`
   - Si exitoso: Navega a `/dashboard`
   - Si error: Muestra modal de error con opción "Aceptar"
4. Dashboard muestra interfaz según rol:
   - **ADMIN**: Panel de administración
   - **DOCENTE**: Portal del docente
   - **ESTUDIANTE**: Portal del estudiante

## 🎨 Paleta de Colores

```css
/* Primarios */
--primary-gradient: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
--secondary-gradient: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
--info-gradient: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);

/* Neutrales */
--text-primary: #2c3e50;
--text-secondary: #7f8c8d;
--background: #f5f7fa;
--border: #e1e8ed;
```

## 🔒 Seguridad Frontend

- **Token Storage**: JWT almacenado en localStorage
- **Rutas Protegidas**: ProtectedRoute valida autenticación
- **Auto-logout**: Interceptor redirige a login si token expira
- **Validación de Formularios**: Validación antes de enviar
- **Manejo de Errores**: Mensajes de error amigables

## 📱 Características Responsivas

| Breakpoint | Cambios |
|------------|---------|
| < 640px | Formularios a ancho completo, texto reducido |
| < 768px | Sidebar oculto, grid de 1 columna |
| < 1024px | Sidebar reducido a 240px |
| > 1024px | Experiencia completa desktop |

## 🎭 Componentes Principales

### LoginForm
- Iconos SVG inline
- Toggle de visibilidad de password
- Spinner de carga durante autenticación
- Validación en tiempo real

### ErrorModal
- Overlay con backdrop blur
- Animaciones de entrada (fadeIn + slideUp)
- Cierre por clic en overlay o botón
- Icono de error con círculo rojo

### HomePageContent
- Cards con hover effects (elevación)
- Iconos según tipo de información
- Gradientes únicos por card
- Grid responsivo

### DashboardPage
- Sidebar fijo con navegación
- Menú contextual según rol
- Cards de acciones rápidas
- Badge de información de sesión

## 🔄 Estado Global (AuthContext)

```javascript
{
  user: {
    userId: Number,
    email: String,
    role: String,
    sessionId: Number
  },
  login: Function,
  logout: Function,
  loading: Boolean,
  isAuthenticated: Boolean
}
```

## 🌐 Endpoints Consumidos

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/home` | Obtener contenido de página principal |
| POST | `/api/auth/login` | Autenticar usuario |

## 🧪 Testing

```bash
# Ejecutar tests (si se implementan)
npm test
```

## 🎨 Personalización

### Cambiar Colores

Edita `App.css` y modifica las variables CSS en los gradientes.

### Agregar Nuevas Rutas

1. Crear componente de vista en `src/views/`
2. Agregar ruta en `src/routes/AppRoutes.jsx`
3. Opcional: Agregar al menú del Dashboard

### Personalizar Componentes

Todos los componentes tienen su propio archivo CSS para facilitar la personalización.

## 📊 Métricas del Proyecto

- **Total Archivos**: 23
- **Componentes React**: 8
- **Rutas**: 3 (1 pública home, 1 pública login, 1 protegida dashboard)
- **Servicios**: 2 (apiClient, authService)
- **Líneas de Código**: ~1,800+

## 🐛 Troubleshooting

### Error: "Cannot connect to backend"
- Verificar que el backend esté corriendo en puerto 8080
- Verificar URL en `.env`

### Error: "Token expired"
- El token JWT expira en 24 horas
- El sistema redirige automáticamente a login

### Estilos no se aplican
- Ejecutar `npm install` nuevamente
- Limpiar cache: `npm start -- --reset-cache`

## 📚 Tecnologías

- **React 18**: Framework UI
- **React Router DOM 6**: Enrutamiento SPA
- **Axios**: Cliente HTTP
- **Context API**: Estado global
- **CSS3**: Estilos con gradientes y animaciones
- **SVG**: Iconos vectoriales inline

## 📝 Trazabilidad

Cada componente incluye comentarios de trazabilidad:

```jsx
// Diagram1-Activity: Ingresa URL del sitio web
// Diagram2-Decision: ¿Credenciales válidas? [Sí]
```

## 🎉 Características Destacadas

✅ Diseño moderno con gradientes
✅ Animaciones suaves y fluidas
✅ Iconos SVG personalizados
✅ Formularios con validación
✅ Modal de errores elegante
✅ Dashboard dinámico por rol
✅ Responsive design completo
✅ Loading states en todas las acciones
✅ Toggle de visibilidad de password
✅ Rutas protegidas con autenticación

## 📧 Soporte

Para problemas o dudas, contactar al equipo de desarrollo.
