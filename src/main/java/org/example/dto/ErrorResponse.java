package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;
@Schema(description = "Esquema con la estructura predefinida de los errores, que se vera por pantalla")
public record ErrorResponse(
        @Schema(description = "Tiempo exacto en el que sucedio el error")
        LocalDateTime timestamp,
        @Schema(description = "Status del error")
        int status,
        @Schema(description = "Mensaje para identificar el tipo de error")
        String mensajeError,
        @Schema(description = "Lista de mensajes especificos del error que ocurrio")
        List<String> messages,
        @Schema(description = "Ruta donde ocurrio el error")
        String path

) {
    public static ErrorResponse of(int status,String mensajeError, String path, List<String>detalles){
        return new ErrorResponse(LocalDateTime.now(),status,mensajeError,detalles,path);
    }
    public static ErrorResponse simple(int status,String mensajeError, String path, String detalle){
       return new ErrorResponse(LocalDateTime.now(),status,mensajeError,List.of(detalle),path);
    }
}
