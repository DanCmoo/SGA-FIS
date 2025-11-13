# Instrucciones para Capturas de Pantalla del Documento LaTeX

## Organización de Imágenes

Crea una carpeta `imagenes/` dentro de `documento/` y guarda todas las capturas ahí.

## Lista de Capturas Necesarias

### Sección 1: Descripción de Plataformas de IA

1. **perplexity_prompt.png**
   - Qué: Captura de Perplexity Pro mostrando el prompt inicial
   - Cómo: Screenshot de la interfaz de Perplexity con el prompt completo visible

2. **vscode_copilot.png**
   - Qué: VS Code con GitHub Copilot mostrando el chat
   - Cómo: Captura de VS Code con el panel de Copilot Chat abierto

### Sección 2: Prompts Utilizados

3. **diagrama1_home.png**
   - Qué: Diagrama de actividad 1 (Acceso Principal)
   - Cómo: Imagen clara del diagrama UML completo

4. **diagrama2_auth.png**
   - Qué: Diagrama de actividad 2 (Autenticación)
   - Cómo: Imagen clara del diagrama UML completo

5. **copilot_prompt_inicial.png**
   - Qué: Primer mensaje enviado a Copilot con los diagramas
   - Cómo: Screenshot del chat mostrando el prompt inicial

### Sección 3: Código Fuente

6. **estructura_backend.png**
   - Qué: Árbol de directorios del backend en VS Code
   - Cómo: Expandir carpeta backend/ en el explorador de VS Code y capturar

7. **estructura_frontend.png**
   - Qué: Árbol de directorios del frontend en VS Code
   - Cómo: Expandir carpeta frontend/src/ en el explorador de VS Code

8. **datainitializer_code.png**
   - Qué: Código de DataInitializer.java
   - Cómo: Abrir el archivo en VS Code y capturar el código completo

9. **authenticationservice_code.png**
   - Qué: Código de AuthenticationService.java (método authenticate)
   - Cómo: Capturar el método principal con los comentarios de trazabilidad visibles

10. **securityconfig_code.png**
    - Qué: Código de SecurityConfig.java
    - Cómo: Mostrar el método filterChain con configuración CORS

11. **loginform_code.png**
    - Qué: Código de LoginForm.jsx
    - Cómo: Capturar el componente completo o al menos el handleSubmit

12. **backend_logs.png**
    - Qué: Terminal mostrando backend iniciando con usuarios cargados
    - Cómo: Captura del terminal con los logs mostrando "✅ Datos de prueba cargados exitosamente"

### Interfaz de Usuario

13. **homepage_ui.png**
    - Qué: Página de inicio funcionando en el navegador
    - Cómo: Abrir http://localhost:3000 y capturar pantalla completa

14. **login_form.png**
    - Qué: Formulario de login
    - Cómo: Navegar a /login y capturar el formulario con campos vacíos

15. **error_modal.png**
    - Qué: Modal de error al fallar login
    - Cómo: Ingresar credenciales incorrectas y capturar el modal de error

16. **dashboard.png**
    - Qué: Dashboard después de login exitoso
    - Cómo: Hacer login con admin@academico.com / password123 y capturar

### Opcionales (si hay tiempo)

17. **swagger_ui.png**
    - Qué: Documentación Swagger del backend
    - Cómo: Abrir http://localhost:8080/api/swagger-ui/index.html

18. **network_tab.png**
    - Qué: Consola del navegador mostrando petición de login
    - Cómo: F12 → Network tab → hacer login → capturar la petición POST /api/auth/login

19. **h2_console.png**
    - Qué: Consola H2 mostrando tabla users
    - Cómo: Abrir http://localhost:8080/api/h2-console → conectar → SELECT * FROM USERS

## Cómo Usar las Imágenes en LaTeX

Una vez tengas las capturas, descomenta los bloques de figura en el documento LaTeX que están marcados con `% NOTA:`.

Ejemplo:
```latex
% Cambiar de:
% \begin{figure}[H]
% \centering
% \includegraphics[width=0.9\textwidth]{imagenes/homepage_ui.png}
% \caption{Interfaz de la Página de Inicio}
% \end{figure}

% A:
\begin{figure}[H]
\centering
\includegraphics[width=0.9\textwidth]{imagenes/homepage_ui.png}
\caption{Interfaz de la Página de Inicio}
\end{figure}
```

## Notas Importantes

- Asegúrate de que las imágenes sean de buena calidad (al menos 1920x1080 para capturas completas)
- Los nombres de archivo deben coincidir exactamente con los especificados en el LaTeX
- Usa formato PNG para mejor calidad en documentos
- Para capturas de código, asegúrate de que la fuente sea legible

## Orden Sugerido de Capturas

1. Primero ejecuta el backend y frontend
2. Captura las interfaces funcionando (13-16)
3. Captura el código en VS Code (6-11)
4. Captura los diagramas originales (3-4)
5. Busca en el historial del chat las capturas de Perplexity y Copilot (1-2, 5)
