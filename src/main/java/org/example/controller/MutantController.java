package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.DnaRequest;
import org.example.dto.HealthResponse;
import org.example.dto.StatsResponse;
import org.example.service.MutantService;
import org.example.service.StatsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
 public ResponseEntity<Void>verificarMutante(@Valid @RequestBody DnaRequest dnaRequest)throws Exception{
   boolean esMutante=mutantService.analizarYPersistirAsync(dnaRequest.dna()).get();
   if(esMutante){
       return ResponseEntity.status(HttpStatus.OK).build();
   }else{
       return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
   }
 }
 @GetMapping("/stats")
 @Operation(summary = "Mostrar estadisticas",description = "Muestra la cantidad de dna registrados que pertenecen a humanos, a mutantes, y el ratio (mutantes/humanos). Puede calcular entre fechas",responses = {
         @ApiResponse(responseCode ="200",description = "Estadisticas obtenidas exitosamente.")
 })
    public ResponseEntity<StatsResponse>obtenerEstadisticas(@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate){
     if (startDate == null || endDate == null) {
         StatsResponse statsResponseSinTiempo=statsService.obtenerStats();
         return ResponseEntity.status(HttpStatus.OK).body(statsResponseSinTiempo);
     }else {
         LocalDate inicio = LocalDate.parse(startDate);
         LocalDate fin = LocalDate.parse(endDate);

         if (fin.isBefore(inicio)) {
             throw new IllegalArgumentException("El fin no puede ser menor que el inicio");
         }
         LocalDateTime startDateTime = inicio.atStartOfDay();
         LocalDateTime endDateTime = fin.atTime(23, 59, 59);

         StatsResponse statsResponseConTiempo=statsService.obtenerStatsFiltrado(startDateTime, endDateTime);

         return ResponseEntity.status(HttpStatus.OK).body(statsResponseConTiempo);
     }

 }
    @GetMapping("/health")
    @Operation(summary = "Health",description = "Muestra el status y el timestamp",responses = {
            @ApiResponse(responseCode ="200",description = "Health obtenido exitosamente.")
    })
    public ResponseEntity<HealthResponse>obtenerHealth(){
     HealthResponse healthResponse=new HealthResponse("UP", LocalDateTime.now());
     return ResponseEntity.status(HttpStatus.OK).body(healthResponse);
 }
    @DeleteMapping("/mutant/{hash}")
    @Operation(summary = "Borrar registro",description = "Borra un registro (dna) de la base de datos usando como parametro el hash recibido en la URL",responses = {
            @ApiResponse(responseCode ="200",description = "Eliminación exitosa."),
            @ApiResponse(responseCode = "404",description = "El hash ingresado no se encuentra en la base de datos")
    })
    public ResponseEntity<String>borrarRegistro(@PathVariable String hash){
     mutantService.eliminarRegistroPorHash(hash);
     String mensaje="El registro con el hash "+ hash +" fue eliminado";
     return ResponseEntity.status(HttpStatus.OK).body(mensaje);
 }
}


