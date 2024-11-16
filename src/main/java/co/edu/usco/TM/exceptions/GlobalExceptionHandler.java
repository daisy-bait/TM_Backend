package co.edu.usco.TM.exceptions;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.persistence.EntityNotFoundException;
import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageResponse> handleGeneralException(Exception ex, WebRequest request) {
        Locale locale = request.getLocale();
        Map<String, String> details = doDetails(ex, locale);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new MessageResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        getMessage("no.specified.error", locale),
                        details,
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<MessageResponse> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
        Locale locale = request.getLocale();

        Map<String, String> details = doDetails(ex, locale);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new MessageResponse(
                        HttpStatus.UNAUTHORIZED.value(),
                        getMessage("unauthorized", locale),
                        details,
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<MessageResponse> handleJWTVerificationException(JWTVerificationException ex, WebRequest request) {
        Locale locale = request.getLocale();

        Map<String, String> details = doDetails(ex, locale);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new MessageResponse(
                        HttpStatus.UNAUTHORIZED.value(),
                        getMessage("unauthorized.jwt.invalid", locale),
                        details,
                        LocalDateTime.now()
                ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MessageResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest webRequest) {

        Locale locale = webRequest.getLocale();

        Map<String, String> errors = getValidationErrors(ex.getBindingResult(), locale);

        return ResponseEntity.badRequest().body(
                new MessageResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        getMessage("invalid.arguments", locale) +
                                ex.getTarget().getClass().getSimpleName(),
                        errors,
                        LocalDateTime.now()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<MessageResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, WebRequest webRequest) {

        Locale locale = webRequest.getLocale();
        Map<String, String> details = doDetails(ex, locale);

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
                new MessageResponse(
                        HttpStatus.UNPROCESSABLE_ENTITY.value(),
                        getMessage("NotReadable", locale),
                        details,
                        LocalDateTime.now()
                )
        );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<MessageResponse> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest webRequest) {

        Locale locale = webRequest.getLocale();
        Map<String, String> details = doDetails(ex, locale);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new MessageResponse(
                        HttpStatus.NOT_FOUND.value(),
                        getMessage("EntityNotFound", locale),
                        details,
                        LocalDateTime.now()
                )
        );
    }

    public Map<String, String> getValidationErrors(BindingResult errors, Locale locale) {
        return errors.getFieldErrors().stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(
                                fieldError -> getMessage(fieldError.getCode(), locale),
                                Collectors.joining(", ")
                        )
                ));
    }

    public String getMessage(String key, Locale locale) {
        return messageSource.getMessage(key, null, locale);
    }

    public Map<String, String> doDetails(Exception ex, Locale locale) {
        Map<String, String> details = new HashMap<>();
        details.put(getMessage("message", locale), ex.getLocalizedMessage());
        details.put(getMessage("cause", locale), ex.getCause() != null ? ex.getCause().toString() : getMessage("no.info", locale));

        return details;
    }

}
