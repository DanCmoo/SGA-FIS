import React from 'react';
import './HomePageContent.css';

/**
 * Componente para mostrar contenido de página principal
 * Diagram1-Activity: Muestra página principal con misión, visión e información general
 * Contenido estático del colegio - no requiere llamada al backend
 */
function HomePageContent({ content, onLoginClick, onRegisterClick }) {
    return (
        <div className="home-content">
            <div className="hero-section">
                <h1 className="hero-title">Sistema de Gestión Académica</h1>
                <p className="hero-subtitle">
                    Gestiona tu información académica de manera fácil y segura
                </p>
            </div>

            <div className="info-cards">
                {/* Diagram1-Activity: Muestra misión del sistema */}
                <div className="info-card">
                    <div className="card-icon mission-icon">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                            <path d="M12 2L2 7l10 5 10-5-10-5z"></path>
                            <path d="M2 17l10 5 10-5"></path>
                            <path d="M2 12l10 5 10-5"></path>
                        </svg>
                    </div>
                    <h2>Nuestra Misión</h2>
                    <p>{content.mission}</p>
                </div>

                {/* Diagram1-Activity: Muestra visión del sistema */}
                <div className="info-card">
                    <div className="card-icon vision-icon">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                            <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"></path>
                            <circle cx="12" cy="12" r="3"></circle>
                        </svg>
                    </div>
                    <h2>Nuestra Visión</h2>
                    <p>{content.vision}</p>
                </div>

                {/* Diagram1-Activity: Muestra información general */}
                <div className="info-card full-width">
                    <div className="card-icon info-icon">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                            <circle cx="12" cy="12" r="10"></circle>
                            <line x1="12" y1="16" x2="12" y2="12"></line>
                            <line x1="12" y1="8" x2="12.01" y2="8"></line>
                        </svg>
                    </div>
                    <h2>Información General</h2>
                    <p>{content.generalInfo}</p>
                </div>
            </div>

            {/* Diagram1-Activity: Botones de acción - Iniciar sesión y Preinscribirse */}
            <div className="action-buttons">
                {content.showLoginButton && (
                    <button 
                        className="btn btn-primary btn-large" 
                        onClick={onLoginClick}
                    >
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                            <path d="M15 3h4a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2h-4"></path>
                            <polyline points="10 17 15 12 10 7"></polyline>
                            <line x1="15" y1="12" x2="3" y2="12"></line>
                        </svg>
                        Iniciar Sesión
                    </button>
                )}
                {content.showRegisterButton && (
                    <button 
                        className="btn btn-secondary btn-large" 
                        onClick={onRegisterClick}
                    >
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                            <path d="M16 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path>
                            <circle cx="8.5" cy="7" r="4"></circle>
                            <line x1="20" y1="8" x2="20" y2="14"></line>
                            <line x1="23" y1="11" x2="17" y2="11"></line>
                        </svg>
                        Preinscribirse
                    </button>
                )}
            </div>
        </div>
    );
}

export default HomePageContent;
