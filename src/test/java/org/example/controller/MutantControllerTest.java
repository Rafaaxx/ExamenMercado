package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.DnaRequest;
import org.example.dto.StatsResponse;
import org.example.service.MutantService;
import org.example.service.StatsService;
import org.junit.jupiter.api.DisplayName;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MutantController.class)
public class MutantControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private MutantService mutantService;
    @MockBean
    private StatsService statsService;

    @Test
    @DisplayName("Post que debe retornar 200 para ADN mutante")
    void testCheckMutantReturns200ForMutant() throws Exception {
        String[] dna = {
                "ATGCGA", "CAGTGC", "TTATGT",
                "AGAAGG", "CCCCTA", "TCACTG"
        };
        DnaRequest dnaRequest = new DnaRequest(dna);
        when(mutantService.analizarYPersistir(any(String[].class))).thenReturn(true);
        mockMvc.perform(
                post("/api/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dnaRequest))
        ).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Post que debe retornar 403 cuando es humano")
    void testCheckMutantReturns403ForHuman() throws Exception {
        String[] dna = {
                "ATGCGA", "CAGTGC", "TTATTT",
                "AGACGG", "GCGTCA", "TCACTG"
        };
        DnaRequest dnaRequest = new DnaRequest(dna);
        when(mutantService.analizarYPersistir(any(String[].class))).thenReturn(false);
        mockMvc.perform(
                post("/api/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dnaRequest))
        ).andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Post que debe retornar 400 cuando es null")
    void testCheckMutantReturns400ForNullDna() throws Exception {
        DnaRequest dnaRequest = new DnaRequest(null);
        mockMvc.perform(
                post("/api/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dnaRequest))
        ).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Post que debe retornar 400 para dna vacio")
    void testCheckMutantReturns400ForEmptyDna() throws Exception {
        String[] dna = {};
        DnaRequest dnaRequest = new DnaRequest(dna);
        mockMvc.perform(
                post("/api/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dnaRequest))
        ).andExpect(status().isBadRequest());

    }
    @Test
    @DisplayName("Get que retorna estadisticas")
    void testGetStatsReturnsCorrectData() throws Exception{
        StatsResponse statsResponse=new StatsResponse(40,100,0.4);
        when(statsService.obtenerStats()).thenReturn(statsResponse);
        mockMvc.perform(
                get("/api/stats").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.count_mutant_dna").value(40))
                .andExpect(jsonPath("$.count_human_dna").value(100))
                .andExpect(jsonPath("$.ratio").value(0.4));

    }
    @Test
    @DisplayName("Get que retorna 200 incluso sin datos")
    void testGetStatsReturns200WithNoData() throws Exception{
        StatsResponse statsResponse=new StatsResponse(0,0,0.0);
        when(statsService.obtenerStats()).thenReturn(statsResponse);
        mockMvc.perform(
                        get("/api/stats").contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.count_mutant_dna").value(0))
                .andExpect(jsonPath("$.count_human_dna").value(0))
                .andExpect(jsonPath("$.ratio").value(0.0));

    }
    @Test
    @DisplayName("Post que rechaza request sin body")
    void testCheckMutantRejectsEmptyBody() throws Exception{
        mockMvc.perform(
                post("/api/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }
    @Test
    @DisplayName("El post debe aceptar Content-Type application/json")
    void testCheckMutantAcceptsJsonContentType() throws Exception{
        String[] dna = {
                "ATGCGA", "CAGTGC", "TTATGT",
                "AGAAGG", "CCCCTA", "TCACTG"
        };
        DnaRequest dnaRequest = new DnaRequest(dna);
        when(mutantService.analizarYPersistir(any(String[].class))).thenReturn(true);
        mockMvc.perform(
                post("/api/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dnaRequest))
        ).andExpect(status().isOk());
    }
    @Test
    @DisplayName("Post que retorna 400 para caracteres invalidos")
    void testCaracterIncorrecto()throws Exception{
        String[] dna = {
                "ATGCGX", "CAGTGC", "TTATGT",
                "AGAAGP", "CCCCTA", "TCACTG"
        };
        DnaRequest dnaRequest=new DnaRequest(dna);
        mockMvc.perform(
                post("/api/mutant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dnaRequest))
        ).andExpect(status().isBadRequest());
    }
}

