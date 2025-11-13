import React from 'react';
import { useNavigate } from 'react-router-dom';
import LoginForm from '../components/LoginForm';
import './LoginPage.css';

/**
 * Página de inicio de sesión
 * Diagram2-Activity: Seleccionar opción "Iniciar Sesión"
 */
function LoginPage() {
    const navigate = useNavigate();

    /**
     * Volver a la página principal
     */
    const handleBackToHome = () => {
        navigate('/');
    };

    return (
        <div className="login-page">
            <div className="login-background">
                <div className="gradient-circle circle-1"></div>
                <div className="gradient-circle circle-2"></div>
                <div className="gradient-circle circle-3"></div>
            </div>

            <div className="login-container">
                <button className="back-button" onClick={handleBackToHome}>
                    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                        <line x1="19" y1="12" x2="5" y2="12"></line>
                        <polyline points="12 19 5 12 12 5"></polyline>
                    </svg>
                    Volver al inicio
                </button>

                {/* Diagram2-Activity: Desplegar formulario de inicio de sesión */}
                <LoginForm />

                <div className="login-footer">
                    <p>¿Primera vez en el sistema?</p>
                    <p className="help-text">
                        Usa tus credenciales institucionales para acceder
                    </p>
                </div>
            </div>
        </div>
    );
}

export default LoginPage;
