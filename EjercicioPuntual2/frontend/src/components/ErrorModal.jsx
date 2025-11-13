import React from 'react';
import './ErrorModal.css';

/**
 * Modal para mostrar errores
 * Diagram2-Activity: Mostrar mensaje de error con opción Aceptar
 */
function ErrorModal({ message, onAccept }) {
    if (!message) return null;

    return (
        <div className="modal-overlay" onClick={onAccept}>
            <div className="modal-content" onClick={(e) => e.stopPropagation()}>
                <div className="modal-icon error-icon">
                    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2">
                        <circle cx="12" cy="12" r="10"></circle>
                        <line x1="15" y1="9" x2="9" y2="15"></line>
                        <line x1="9" y1="9" x2="15" y2="15"></line>
                    </svg>
                </div>
                <h3 className="modal-title">Error de Autenticación</h3>
                <p className="modal-message">{message}</p>
                <button className="btn btn-primary btn-full" onClick={onAccept}>
                    Aceptar
                </button>
            </div>
        </div>
    );
}

export default ErrorModal;
