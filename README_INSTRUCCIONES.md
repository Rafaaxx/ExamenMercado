# Mutant Detector API: Examen Final

Este proyecto consiste en la construcción de una API REST para detección de mutantes mediante análisis de secuencias de DNA (ADN), que permite determinar si una secuencia pertenece a un mutante o a un humano, siguiendo la consigna del examen integrador de Mercado Libre. El sistema también registra los DNA analizados en base de datos y es capaz de mostrar las estadísticas de uso.

La aplicación fue desarrollada en Java 21, Spring Boot 3, y está desplegada en Render.

### URL pública de la API:
https://examenmercado-h3go.onrender.com

### Enlace al Swagger de la API:
https://examenmercado-h3go.onrender.com/swagger-ui.html

## Características principales

-Validación completa del DNA (NxN, caracteres válidos, no vacío, etc.)

-Detección eficiente de secuencias mutantes (horizontal, vertical, diagonales en ambos sentidos).

-Persistencia automática y uso de hash SHA-256 para evitar DNA repetidos.

-Endpoints REST/documentación Swagger.

-Manejo de errores usando GlobalExceptionHandler.

-Tests unitarios y de integración.

-Estadísticas globales de cantidad de humanos, de mutantes y el ratio (cant. mutantes/ cant. humanos para casos donde no hay división por 0).
 

## Cómo ejecutar el proyecto
1. Clonar el repositorio
   git clone https://github.com/Rafaaxx/ExamenMercado
   cd ExamenMercado

2. Ejecutar con:

Maven : mvn spring-boot:run

Gradle: ./gradlew bootRun

4. Abrir la documentación

http://localhost:8080/swagger-ui.html

### Endpoints principales
- POST /api/mutant

Determina si un DNA es mutante o humano dependiendo de su secuencia y los patrones encontrados (2 o más patrones de 4 letras seguidas iguales).

El sistema ya viene con una cadena de DNA de ejemplo.
#### Respuestas

-200 OK: Es mutante.

-403 FORBIDDEN: No es mutante.

-400 BAD REQUEST: ADN inválido.

- GET /api/stats

Devuelve las estadísticas registradas (cant. mutantes, cant. humanos y ratio).

### Ejemplo de devolución:
-200 OK: devuelve correctamente las estadísticas.

Response
{
"count_mutant_dna": 10,
"count_human_dna": 5,
"ratio": 2.0
}


## Testing

El proyecto incluye:

- Test unitarios para la detección de ADN

- Test de validaciones

- Test de controlador

Como ejecutar:

Para Maven:
mvn test

Para Gradle:
./gradlew test
