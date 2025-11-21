package org.example.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
@Schema(description = "Status y timestamp")
public record HealthResponse(
        String status,
        LocalDateTime timestamp
) {}