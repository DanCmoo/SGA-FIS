package com.academico.presentation.controller;

import com.academico.domain.service.HomeService;
import com.academico.presentation.dto.HomePageContentDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador para página principal
 * Diagram1-Activity: Ingresa URL del sitio web - expone endpoint de home
 */
@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
@Tag(name = "Home", description = "Endpoints para página principal del sistema")
public class HomeController {
    
    private final HomeService homeService;
    
    /**
     * Obtiene el contenido de la página principal
     * Diagram1-Activity: Muestra página principal del sistema con botón para iniciar sesión o preinscribirse
     * 
     * @return DTO con misión, visión, información general y botones de acción
     */
    @GetMapping
    @Operation(
        summary = "Obtener contenido de página principal",
        description = "Diagram1: Retorna la información de la página principal incluyendo misión, visión, " +
                     "información general y botones para iniciar sesión o preinscribirse"
    )
    public ResponseEntity<HomePageContentDTO> getHomePage() {
        // Diagram1-Activity: Ingresa URL del sitio web - retorna contenido
        HomePageContentDTO content = homeService.getHomePageContent();
        return ResponseEntity.ok(content);
    }
}
