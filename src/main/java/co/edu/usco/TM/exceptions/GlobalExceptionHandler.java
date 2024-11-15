package co.edu.usco.TM.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MessageResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().body(
                new MessageResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        "Argumentos no válidos para " + ex.getTarget().getClass().getSimpleName(),
                        "Errores: " + ex.getBindingResult().getFieldErrors().stream()
                                .collect(Collectors.toMap(
                                        FieldError::getField,
                                        FieldError::getDefaultMessage
                                )),
                        LocalDateTime.now()));
    }

}
