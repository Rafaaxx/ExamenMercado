package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.DnaRequest;
import org.example.dto.StatsResponse;
import org.example.service.MutantService;
import org.example.service.StatsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Detector de mutantes", description = "Operaciones relacionadas con el manejo de cadenas de dna (adn en español), diferenciando entre mutante y humano.")
public class MutantController {
 private final MutantService mutantService;
 private final StatsService statsService;
 @PostMapping("/mutant")
 @Operation(summary = "Verificar si es mutante o humano",description = "Verifica que el dna ingresado pertenezca a un humano o un mutante",responses = {
         @ApiResponse(responseCode ="200", description = "El dna pertenece a un mutante."),
         @ApiResponse(responseCode = "403", description = "El dna no pertenece a un mutante."),
         @ApiResponse(responseCode ="400", description = "Dna invalido.")
 })
 public ResponseEntity<Void>verificarMutante(@Valid @RequestBody DnaRequest dnaRequest){
   boolean esMutante=mutantService.analizarYPersistir(dnaRequest.dna());
   if(esMutante){
       return ResponseEntity.status(HttpStatus.OK).build();
   }else{
       return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
   }
 }
 @GetMapping("/stats")
 @Operation(summary = "Mostrar estadisticas",description = "Muestra la cantidad de dna registrados que pertenecen a humanos, a mutantes, y el ratio (mutantes/humanos)",responses = {
         @ApiResponse(responseCode ="200",description = "Estadisticas obtenidas exitosamente.")
 })
    public ResponseEntity<StatsResponse>obtenerEstadisticas(){
     StatsResponse statsResponse=statsService.obtenerStats();
     return ResponseEntity.status(HttpStatus.OK).body(statsResponse);
 }
}
