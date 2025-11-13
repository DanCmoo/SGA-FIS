import React, { createContext, useState, useEffect, useCallback } from 'react';
import authService from '../services/authService';

/**
 * Context para gestión de autenticación global
 * Diagram2-Activity: Mantener estado de autenticación en toda la aplicación
 */
export const AuthContext = createContext();

export function AuthProvider({ children }) {
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);

    // Cargar usuario desde localStorage al iniciar
    useEffect(() => {
        const currentUser = authService.getCurrentUser();
        if (currentUser) {
            setUser(currentUser);
        }
        setLoading(false);
    }, []);

    /**
     * Login de usuario
     * Diagram2-Activity: Autenticar usuario y almacenar estado
     */
    const login = useCallback(async (email, password) => {
        setLoading(true);
        try {
            const response = await authService.login(email, password);
            // Diagram2-Activity: Registrar inicio de sesión - actualizar contexto
            setUser({
                userId: response.userId,
                email: response.email,
                role: response.role,
                sessionId: response.sessionId,
            });
            return response;
        } finally {
            setLoading(false);
        }
    }, []);

    /**
     * Logout de usuario
     */
    const logout = useCallback(() => {
        authService.logout();
        setUser(null);
    }, []);

    const value = {
        user,
        login,
        logout,
        loading,
        isAuthenticated: !!user,
    };

    return (
        <AuthContext.Provider value={value}>
            {children}
        </AuthContext.Provider>
    );
}
