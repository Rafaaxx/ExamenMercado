package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.example.validation.ValidDnaSequence;
@Schema(description = "Este es el request para verificar si un ADN es mutante o no")
public record DnaRequest(
        @NotEmpty(message = "El dna no puede estar vacio")
        @NotNull(message = "El dna no puede ser nulo")
        @ValidDnaSequence
        @Schema(
                description = "Secuencia de ADN representada como matriz NxN",
                example = "[\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]",
                required = true
        )
        String[]dna
) {
}
