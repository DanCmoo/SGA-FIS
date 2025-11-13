import React, { useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../context/AuthContext';
import './DashboardPage.css';

/**
 * Dashboard del usuario autenticado
 * Diagram2-Activity: Mostrar interfaz inicial según rol
 */
function DashboardPage() {
    const { user, logout } = useContext(AuthContext);
    const navigate = useNavigate();

    /**
     * Maneja el cierre de sesión
     */
    const handleLogout = () => {
        logout();
        navigate('/');
    };

    /**
     * Obtiene el saludo según el rol
     */
    const getRoleGreeting = () => {
        switch (user?.role) {
            case 'ADMIN':
                return 'Panel de Administración';
            case 'DOCENTE':
                return 'Portal del Docente';
            case 'ESTUDIANTE':
                return 'Portal del Estudiante';
            default:
                return 'Panel de Usuario';
        }
    };

    /**
     * Obtiene el ícono según el rol
     */
    const getRoleIcon = () => {
        switch (user?.role) {
            case 'ADMIN':
                return (
                    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                        <path d="M12 2L2 7l10 5 10-5-10-5z"></path>
                        <path d="M2 17l10 5 10-5"></path>
                        <path d="M2 12l10 5 10-5"></path>
                    </svg>
                );
            case 'DOCENTE':
                return (
                    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                        <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path>
                        <circle cx="9" cy="7" r="4"></circle>
                        <path d="M23 21v-2a4 4 0 0 0-3-3.87"></path>
                        <path d="M16 3.13a4 4 0 0 1 0 7.75"></path>
                    </svg>
                );
            case 'ESTUDIANTE':
                return (
                    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                        <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
                        <circle cx="12" cy="7" r="4"></circle>
                    </svg>
                );
            default:
                return null;
        }
    };

    /**
     * Obtiene las opciones del menú según el rol
     */
    const getMenuOptions = () => {
        const commonOptions = [
            { id: 'profile', label: 'Mi Perfil', icon: 'user' },
            { id: 'settings', label: 'Configuración', icon: 'settings' },
        ];

        switch (user?.role) {
            case 'ADMIN':
                return [
                    { id: 'users', label: 'Gestión de Usuarios', icon: 'users' },
                    { id: 'reports', label: 'Reportes', icon: 'chart' },
                    { id: 'system', label: 'Configuración del Sistema', icon: 'tool' },
                    ...commonOptions,
                ];
            case 'DOCENTE':
                return [
                    { id: 'courses', label: 'Mis Cursos', icon: 'book' },
                    { id: 'students', label: 'Estudiantes', icon: 'users' },
                    { id: 'grades', label: 'Calificaciones', icon: 'clipboard' },
                    { id: 'schedule', label: 'Horario', icon: 'calendar' },
                    ...commonOptions,
                ];
            case 'ESTUDIANTE':
                return [
                    { id: 'courses', label: 'Mis Materias', icon: 'book' },
                    { id: 'grades', label: 'Mis Calificaciones', icon: 'clipboard' },
                    { id: 'schedule', label: 'Mi Horario', icon: 'calendar' },
                    { id: 'enrollment', label: 'Inscripción', icon: 'edit' },
                    ...commonOptions,
                ];
            default:
                return commonOptions;
        }
    };

    const getIcon = (iconType) => {
        const icons = {
            users: (
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                    <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path>
                    <circle cx="9" cy="7" r="4"></circle>
                    <path d="M23 21v-2a4 4 0 0 0-3-3.87"></path>
                    <path d="M16 3.13a4 4 0 0 1 0 7.75"></path>
                </svg>
            ),
            chart: (
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                    <line x1="12" y1="20" x2="12" y2="10"></line>
                    <line x1="18" y1="20" x2="18" y2="4"></line>
                    <line x1="6" y1="20" x2="6" y2="16"></line>
                </svg>
            ),
            tool: (
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                    <path d="M14.7 6.3a1 1 0 0 0 0 1.4l1.6 1.6a1 1 0 0 0 1.4 0l3.77-3.77a6 6 0 0 1-7.94 7.94l-6.91 6.91a2.12 2.12 0 0 1-3-3l6.91-6.91a6 6 0 0 1 7.94-7.94l-3.76 3.76z"></path>
                </svg>
            ),
            book: (
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                    <path d="M4 19.5A2.5 2.5 0 0 1 6.5 17H20"></path>
                    <path d="M6.5 2H20v20H6.5A2.5 2.5 0 0 1 4 19.5v-15A2.5 2.5 0 0 1 6.5 2z"></path>
                </svg>
            ),
            clipboard: (
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                    <path d="M16 4h2a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2H6a2 2 0 0 1-2-2V6a2 2 0 0 1 2-2h2"></path>
                    <rect x="8" y="2" width="8" height="4" rx="1" ry="1"></rect>
                </svg>
            ),
            calendar: (
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                    <rect x="3" y="4" width="18" height="18" rx="2" ry="2"></rect>
                    <line x1="16" y1="2" x2="16" y2="6"></line>
                    <line x1="8" y1="2" x2="8" y2="6"></line>
                    <line x1="3" y1="10" x2="21" y2="10"></line>
                </svg>
            ),
            edit: (
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                    <path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"></path>
                    <path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"></path>
                </svg>
            ),
            user: (
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                    <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
                    <circle cx="12" cy="7" r="4"></circle>
                </svg>
            ),
            settings: (
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                    <circle cx="12" cy="12" r="3"></circle>
                    <path d="M12 1v6m0 6v6m9-9h-6m-6 0H3"></path>
                </svg>
            ),
        };
        return icons[iconType] || null;
    };

    return (
        <div className="dashboard-page">
            <aside className="dashboard-sidebar">
                <div className="sidebar-header">
                    <div className="logo">
                        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                            <path d="M12 2L2 7l10 5 10-5-10-5z"></path>
                            <path d="M2 17l10 5 10-5"></path>
                            <path d="M2 12l10 5 10-5"></path>
                        </svg>
                        <span>SGA</span>
                    </div>
                </div>

                <nav className="sidebar-nav">
                    {getMenuOptions().map((option) => (
                        <button 
                            key={option.id} 
                            className="nav-item"
                            onClick={() => alert(`Navegando a: ${option.label}`)}
                        >
                            {getIcon(option.icon)}
                            <span>{option.label}</span>
                        </button>
                    ))}
                </nav>

                <button className="logout-button" onClick={handleLogout}>
                    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                        <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"></path>
                        <polyline points="16 17 21 12 16 7"></polyline>
                        <line x1="21" y1="12" x2="9" y2="12"></line>
                    </svg>
                    Cerrar Sesión
                </button>
            </aside>

            <main className="dashboard-main">
                <header className="dashboard-header">
                    <div>
                        <h1>{getRoleGreeting()}</h1>
                        <p>Bienvenido, {user?.email}</p>
                    </div>
                    <div className="user-badge">
                        <div className="user-avatar">
                            {getRoleIcon()}
                        </div>
                        <div className="user-info">
                            <span className="user-role">{user?.role}</span>
                            <span className="user-email">{user?.email}</span>
                        </div>
                    </div>
                </header>

                <div className="dashboard-content">
                    <div className="welcome-card">
                        <div className="welcome-icon">
                            {getRoleIcon()}
                        </div>
                        <h2>¡Bienvenido al Sistema!</h2>
                        <p>Has iniciado sesión exitosamente.</p>
                        <div className="info-badges">
                            <span className="badge">Sesión ID: {user?.sessionId}</span>
                            <span className="badge">Usuario ID: {user?.userId}</span>
                        </div>
                        <p className="info-text">
                            Diagram2-Activity: Interfaz mostrada según rol <strong>{user?.role}</strong>
                        </p>
                    </div>

                    <div className="quick-actions">
                        <h3>Acciones Rápidas</h3>
                        <div className="actions-grid">
                            {getMenuOptions().slice(0, 4).map((option) => (
                                <button 
                                    key={option.id} 
                                    className="action-card"
                                    onClick={() => alert(`Accediendo a: ${option.label}`)}
                                >
                                    {getIcon(option.icon)}
                                    <span>{option.label}</span>
                                </button>
                            ))}
                        </div>
                    </div>
                </div>
            </main>
        </div>
    );
}

export default DashboardPage;
