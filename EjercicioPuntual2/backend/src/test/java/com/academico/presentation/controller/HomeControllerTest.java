package com.academico.presentation.controller;

import com.academico.domain.service.HomeService;
import com.academico.presentation.dto.HomePageContentDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests para HomeController
 * Diagram1-Activity: Validar página principal
 */
@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private HomeService homeService;
    
    @Test
    @DisplayName("Diagram1-Activity: Muestra página principal con misión, visión y botones")
    void testGetHomePage() throws Exception {
        // Arrange
        HomePageContentDTO content = HomePageContentDTO.builder()
                .mission("Formar profesionales en sistemas")
                .vision("Ser líderes en educación")
                .generalInfo("Sistema de gestión académica")
                .showLoginButton(true)
                .showRegisterButton(true)
                .build();
        
        when(homeService.getHomePageContent()).thenReturn(content);
        
        // Act & Assert
        mockMvc.perform(get("/api/home"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mission").exists())
                .andExpect(jsonPath("$.vision").exists())
                .andExpect(jsonPath("$.generalInfo").exists())
                .andExpect(jsonPath("$.showLoginButton").value(true))
                .andExpect(jsonPath("$.showRegisterButton").value(true));
    }
}
