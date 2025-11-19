package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Estadísticas de las verificaciones hechas al DNA (ADN en español)")
public record StatsResponse(
        @Schema(description = "Cantidad de mutantes confirmados")
         long count_mutant_dna,
        @Schema(description = "Cantidad de humanos confirmados")
         long count_human_dna,
        @Schema(description = "Ratio de mutantes/humanos")
         double ratio

) {
}
