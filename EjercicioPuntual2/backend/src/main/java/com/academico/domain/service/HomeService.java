package com.academico.domain.service;

import com.academico.presentation.dto.HomePageContentDTO;
import org.springframework.stereotype.Service;

/**
 * Servicio para gestión de contenido de página principal
 * Diagram1-Activity: Muestra página principal - retorna contenido estático
 */
@Service
public class HomeService {
    
    /**
     * Obtiene el contenido de la página principal
     * Diagram1-Activity: Ingresa URL del sitio web - expone información general
     * 
     * @return DTO con misión, visión e información general
     */
    public HomePageContentDTO getHomePageContent() {
        // Diagram1-Activity: Muestra página principal con misión, visión e información general
        return HomePageContentDTO.builder()
                .mission("Formar profesionales integrales en el área de sistemas, con sólidos conocimientos " +
                        "técnicos y científicos, capacidad de innovación y sentido ético, que contribuyan al " +
                        "desarrollo tecnológico y social del país.")
                .vision("Ser reconocidos como líderes en educación superior en ciencias de la computación " +
                        "e ingeniería de sistemas, formando profesionales de clase mundial que impulsen la " +
                        "transformación digital de la sociedad.")
                .generalInfo("El Sistema de Gestión Académica le permite gestionar su información estudiantil, " +
                        "consultar calificaciones, inscribir materias, revisar horarios y mantenerse actualizado " +
                        "con las actividades académicas. Para acceder a todas las funcionalidades, inicie sesión " +
                        "con sus credenciales institucionales.")
                .showLoginButton(true)
                .showRegisterButton(true)
                .build();
    }
}
