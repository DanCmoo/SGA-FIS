import React, { useContext } from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { AuthContext } from '../context/AuthContext';
import HomePage from '../views/HomePage';
import LoginPage from '../views/LoginPage';
import DashboardPage from '../views/DashboardPage';

/**
 * Componente para rutas protegidas
 * Diagram2-Activity: Proteger rutas que requieren autenticación
 */
function ProtectedRoute({ children }) {
    const { user, loading } = useContext(AuthContext);

    if (loading) {
        return (
            <div style={{
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                minHeight: '100vh'
            }}>
                <div className="spinner-large"></div>
            </div>
        );
    }

    // Diagram2-Decision: ¿Usuario autenticado? - redirigir si no
    return user ? children : <Navigate to="/login" replace />;
}

/**
 * Componente de enrutamiento principal
 * Diagram1 & Diagram2: Define rutas públicas y protegidas
 */
function AppRoutes() {
    return (
        <BrowserRouter>
            <Routes>
                {/* Diagram1-Activity: Ruta pública - Página principal */}
                <Route path="/" element={<HomePage />} />
                
                {/* Diagram2-Activity: Ruta pública - Login */}
                <Route path="/login" element={<LoginPage />} />
                
                {/* Diagram2-Activity: Ruta protegida - Dashboard según rol */}
                <Route 
                    path="/dashboard" 
                    element={
                        <ProtectedRoute>
                            <DashboardPage />
                        </ProtectedRoute>
                    } 
                />

                {/* Ruta por defecto - redirigir a home */}
                <Route path="*" element={<Navigate to="/" replace />} />
            </Routes>
        </BrowserRouter>
    );
}

export default AppRoutes;
