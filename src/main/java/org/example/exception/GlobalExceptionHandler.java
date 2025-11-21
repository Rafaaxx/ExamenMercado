package org.example.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.example.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.List;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex, WebRequest request) {
        List<String> mensajeDeError=ex.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField()+": "+fieldError.getDefaultMessage())
                .toList();
        ErrorResponse errorResponse=ErrorResponse.of(HttpStatus.BAD_REQUEST.value(), "Error de validacion.", request.getDescription(false), mensajeDeError);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(DnaHashCalculationException.class)
    public ResponseEntity<ErrorResponse> handleDnaHashError(DnaHashCalculationException ex, WebRequest request) {
       ErrorResponse errorResponse=ErrorResponse.simple(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Error interno del servidor al calcular el hash", request.getDescription(false), ex.getMessage());
       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse>handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpServletRequest servletRequest){
        ErrorResponse errorResponse=ErrorResponse.simple(HttpStatus.BAD_REQUEST.value(),"El cuerpo debe tener contenido",servletRequest.getRequestURI(),ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    @ExceptionHandler(DnaNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDnaNotFound(DnaNotFoundException ex, WebRequest request) {
        ErrorResponse error = ErrorResponse.simple(
                HttpStatus.NOT_FOUND.value(),
                "DNA no encontrado",
                request.getDescription(false),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex, WebRequest req) {
        ErrorResponse errorResponse = ErrorResponse.simple(
                HttpStatus.BAD_REQUEST.value(),
                "Parámetros inválidos",
                req.getDescription(false),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse>handleGenericException(Exception ex, WebRequest request){
        ErrorResponse errorResponse=ErrorResponse.simple(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error interno del servidor", request.getDescription(false),ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
