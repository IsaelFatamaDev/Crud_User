package pe.edu.vallegrande.quality.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pe.edu.vallegrande.quality.dto.ApiResponse;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

     @ExceptionHandler(UserNotFoundException.class)
     public ResponseEntity<ApiResponse<Object>> handleUserNotFoundException(UserNotFoundException ex) {
          log.error("Usuario no encontrado: {}", ex.getMessage());
          return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(ex.getMessage()));
     }

     @ExceptionHandler(UserValidationException.class)
     public ResponseEntity<ApiResponse<Object>> handleUserValidationException(UserValidationException ex) {
          log.error("Error de validación: {}", ex.getMessage());
          return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(ex.getMessage()));
     }

     @ExceptionHandler(MethodArgumentNotValidException.class)
     public ResponseEntity<ApiResponse<Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
          Map<String, String> errors = new HashMap<>();
          ex.getBindingResult().getFieldErrors()
                    .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

          log.error("Errores de validación: {}", errors);
          return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, "Errores de validación", errors));
     }

     @ExceptionHandler(Exception.class)
     public ResponseEntity<ApiResponse<Object>> handleGenericException(Exception ex) {
          log.error("Error interno del servidor: ", ex);
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error interno del servidor"));
     }
}
