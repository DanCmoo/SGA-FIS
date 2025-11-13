import axios from 'axios';

/**
 * Cliente API configurado para comunicarse con el backend
 * Diagram1 & Diagram2: Cliente HTTP para consumir endpoints REST
 */
const apiClient = axios.create({
    baseURL: process.env.REACT_APP_API_BASE_URL || 'http://localhost:8080/api',
    headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
    },
    withCredentials: true, // Habilitar envío de credenciales (cookies, auth headers)
});

/**
 * Interceptor para agregar token JWT a las peticiones
 * Diagram2-Activity: Incluir token de autenticación en headers
 */
apiClient.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('token');
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

/**
 * Interceptor para manejo de errores globales
 */
apiClient.interceptors.response.use(
    (response) => response,
    (error) => {
        if (error.response?.status === 401) {
            // Token expirado o inválido
            localStorage.removeItem('token');
            localStorage.removeItem('user');
            window.location.href = '/login';
        }
        return Promise.reject(error);
    }
);

export default apiClient;
