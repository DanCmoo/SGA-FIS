import apiClient from './apiClient';

/**
 * Servicio de autenticación
 * Diagram1 & Diagram2: Métodos para interactuar con endpoints de autenticación
 */
const authService = {
    /**
     * Obtiene el contenido de la página principal
     * Diagram1-Activity: Ingresa URL del sitio web - fetch contenido home
     */
    getHomeContent: async () => {
        try {
            const response = await apiClient.get('/home');
            return response.data;
        } catch (error) {
            console.error('Error al obtener contenido home:', error);
            throw error;
        }
    },

    /**
     * Autentica un usuario
     * Diagram2-Activity: Dar clic en botón Ingresar - envía credenciales al backend
     * 
     * @param {string} email - Email del usuario
     * @param {string} password - Password del usuario
     * @returns {Promise} Response con token y datos de usuario
     */
    login: async (email, password) => {
        try {
            const response = await apiClient.post('/auth/login', {
                email,
                password,
            });
            
            // Diagram2-Decision: ¿Credenciales válidas? [Sí] - almacenar token
            if (response.data.token) {
                localStorage.setItem('token', response.data.token);
                localStorage.setItem('user', JSON.stringify({
                    userId: response.data.userId,
                    email: response.data.email,
                    role: response.data.role,
                    sessionId: response.data.sessionId,
                }));
            }
            
            return response.data;
        } catch (error) {
            // Diagram2-Decision: ¿Credenciales válidas? [No] - propagar error
            console.error('Error en login:', error);
            throw error;
        }
    },

    /**
     * Cierra sesión del usuario
     */
    logout: () => {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
    },

    /**
     * Obtiene el usuario actual desde localStorage
     */
    getCurrentUser: () => {
        const userStr = localStorage.getItem('user');
        return userStr ? JSON.parse(userStr) : null;
    },

    /**
     * Verifica si hay un usuario autenticado
     */
    isAuthenticated: () => {
        return !!localStorage.getItem('token');
    },
};

export default authService;
