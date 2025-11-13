import React from 'react';
import { useNavigate } from 'react-router-dom';
import HomePageContent from '../components/HomePageContent';
import './HomePage.css';

/**
 * Página principal del sistema
 * Diagram1-Activity: Ingresa URL del sitio web - muestra página principal
 */
function HomePage() {
    const navigate = useNavigate();

    // Contenido estático del colegio - no requiere base de datos
    const content = {
        mission: 'Formar estudiantes integrales con excelencia académica, promoviendo valores éticos, pensamiento crítico y compromiso social para contribuir al desarrollo de la sociedad.',
        vision: 'Ser reconocidos como una institución líder en educación de calidad, innovación pedagógica y formación de ciudadanos competentes que transformen positivamente su entorno.',
        generalInfo: 'Nuestra institución cuenta con más de 30 años de experiencia en educación de calidad. Ofrecemos programas académicos desde educación básica hasta bachillerato, con énfasis en ciencias, humanidades y tecnología. Contamos con instalaciones modernas, laboratorios equipados, biblioteca digital y un equipo docente altamente calificado comprometido con la excelencia educativa.',
        showLoginButton: true,
        showRegisterButton: true
    };

    /**
     * Maneja clic en botón Iniciar Sesión
     * Diagram1-Activity: Navegar a página de login
     */
    const handleLoginClick = () => {
        navigate('/login');
    };

    /**
     * Maneja clic en botón Preinscribirse
     */
    const handleRegisterClick = () => {
        alert('La funcionalidad de preinscripción estará disponible próximamente.');
    };

    return (
        <div className="home-page">
            <header className="home-header">
                <div className="logo">
                    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                        <path d="M12 2L2 7l10 5 10-5-10-5z"></path>
                        <path d="M2 17l10 5 10-5"></path>
                        <path d="M2 12l10 5 10-5"></path>
                    </svg>
                    <span>SGA</span>
                </div>
            </header>

            <main className="home-main">
                <HomePageContent 
                    content={content}
                    onLoginClick={handleLoginClick}
                    onRegisterClick={handleRegisterClick}
                />
            </main>

            <footer className="home-footer">
                <p>© 2025 Sistema de Gestión Académica. Todos los derechos reservados.</p>
            </footer>
        </div>
    );
}

export default HomePage;
