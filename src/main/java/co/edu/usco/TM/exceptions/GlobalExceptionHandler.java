package co.edu.usco.TM.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    MessageSource messageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MessageResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest webRequest) {

        Locale locale = webRequest.getLocale();

        Map<String, String> errors = getValidationErrors(ex.getBindingResult(), locale);


        return ResponseEntity.badRequest().body(
                new MessageResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        "Argumentos no válidos para " + ex.getTarget().getClass().getSimpleName(),
                        "Errores en los campos: " + errors,
                        LocalDateTime.now()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<MessageResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, WebRequest webRequest) {

        Locale locale = webRequest.getLocale();

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
                new MessageResponse(
                        HttpStatus.UNPROCESSABLE_ENTITY.value(),
                        getMessage("NotReadable", locale),
                        ex.getCause().getLocalizedMessage(),
                        LocalDateTime.now()
                )
        );
    }

    public Map<String, String> getValidationErrors(BindingResult errors, Locale locale) {
        return errors.getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        fieldError -> getMessage(fieldError.getCode(), locale))
                );
    }

    public String getMessage(String key, Locale locale) {
        return messageSource.getMessage(key, null, locale);
    }

}
