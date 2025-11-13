import React, { useState, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../context/AuthContext';
import ErrorModal from './ErrorModal';
import './LoginForm.css';

/**
 * Formulario de login
 * Diagram2-Activity: Desplegar formulario de inicio de sesión con botón de ingresar
 */
function LoginForm() {
    // Diagram2-Activity: Ingresar correo y contraseña - estado local
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState(null);
    const [showPassword, setShowPassword] = useState(false);
    
    const { login, loading } = useContext(AuthContext);
    const navigate = useNavigate();

    /**
     * Maneja el envío del formulario
     * Diagram2-Activity: Dar clic en botón Ingresar - valida y envía credenciales
     */
    const handleSubmit = async (e) => {
        e.preventDefault();
        
        // Validación básica de campos
        if (!email || !password) {
            setError('Por favor, completa todos los campos');
            return;
        }

        if (!email.includes('@')) {
            setError('Por favor, ingresa un email válido');
            return;
        }

        if (password.length < 8) {
            setError('La contraseña debe tener al menos 8 caracteres');
            return;
        }

        try {
            // Diagram2-SubActivity: Validar Credenciales - enviar al backend
            await login(email, password);
            
            // Diagram2-Decision: ¿Credenciales válidas? [Sí] - navegar a dashboard
            navigate('/dashboard');
        } catch (err) {
            // Diagram2-Decision: ¿Credenciales válidas? [No] - mostrar error
            const errorMessage = err.response?.data?.errorMessage || 
                               'Error al iniciar sesión. Por favor, verifica tus credenciales.';
            setError(errorMessage);
        }
    };

    /**
     * Cierra el modal de error
     * Diagram2-Activity: Aceptar mensaje de error - permite reintentar
     */
    const handleCloseError = () => {
        setError(null);
    };

    return (
        <div className="login-form-container">
            {/* Diagram2-Activity: Desplegar formulario - renderiza campos */}
            <form onSubmit={handleSubmit} className="login-form">
                <div className="form-header">
                    <div className="form-icon">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                            <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
                            <circle cx="12" cy="7" r="4"></circle>
                        </svg>
                    </div>
                    <h2>Iniciar Sesión</h2>
                    <p>Ingresa tus credenciales institucionales</p>
                </div>

                <div className="form-group">
                    <label htmlFor="email">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                            <path d="M4 4h16c1.1 0 2 .9 2 2v12c0 1.1-.9 2-2 2H4c-1.1 0-2-.9-2-2V6c0-1.1.9-2 2-2z"></path>
                            <polyline points="22,6 12,13 2,6"></polyline>
                        </svg>
                        Correo Electrónico
                    </label>
                    <input
                        type="email"
                        id="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        placeholder="ejemplo@academico.com"
                        disabled={loading}
                        required
                    />
                </div>

                <div className="form-group">
                    <label htmlFor="password">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                            <rect x="3" y="11" width="18" height="11" rx="2" ry="2"></rect>
                            <path d="M7 11V7a5 5 0 0 1 10 0v4"></path>
                        </svg>
                        Contraseña
                    </label>
                    <div className="password-input">
                        <input
                            type={showPassword ? 'text' : 'password'}
                            id="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            placeholder="••••••••"
                            disabled={loading}
                            required
                        />
                        <button
                            type="button"
                            className="toggle-password"
                            onClick={() => setShowPassword(!showPassword)}
                            tabIndex="-1"
                        >
                            {showPassword ? (
                                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                                    <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"></path>
                                    <circle cx="12" cy="12" r="3"></circle>
                                </svg>
                            ) : (
                                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                                    <path d="M17.94 17.94A10.07 10.07 0 0 1 12 20c-7 0-11-8-11-8a18.45 18.45 0 0 1 5.06-5.94M9.9 4.24A9.12 9.12 0 0 1 12 4c7 0 11 8 11 8a18.5 18.5 0 0 1-2.16 3.19m-6.72-1.07a3 3 0 1 1-4.24-4.24"></path>
                                    <line x1="1" y1="1" x2="23" y2="23"></line>
                                </svg>
                            )}
                        </button>
                    </div>
                </div>

                <div className="form-info">
                    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                        <circle cx="12" cy="12" r="10"></circle>
                        <line x1="12" y1="16" x2="12" y2="12"></line>
                        <line x1="12" y1="8" x2="12.01" y2="8"></line>
                    </svg>
                    <span>Se permiten hasta 3 intentos por hora</span>
                </div>

                {/* Diagram2-Activity: Dar clic en botón Ingresar */}
                <button 
                    type="submit" 
                    className="btn btn-primary btn-full"
                    disabled={loading}
                >
                    {loading ? (
                        <>
                            <span className="spinner"></span>
                            Iniciando sesión...
                        </>
                    ) : (
                        'Ingresar'
                    )}
                </button>

                <div className="form-footer">
                    <p>¿Problemas para acceder? Contacta a soporte@academico.com</p>
                </div>
            </form>

            {/* Diagram2-Activity: Mostrar mensaje de error con opción Aceptar */}
            <ErrorModal message={error} onAccept={handleCloseError} />
        </div>
    );
}

export default LoginForm;
