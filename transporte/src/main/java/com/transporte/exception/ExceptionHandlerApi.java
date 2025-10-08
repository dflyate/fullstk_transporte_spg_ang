package com.transporte.exception;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ExceptionHandlerApi {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerApi.class);

    private ResponseEntity<Map<String, String>> buildErrorResponse(HttpStatus status, String logMessage, Exception ex) {
        logger.warn("{}: {}", logMessage, ex.getMessage());
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());
        return ResponseEntity.status(status).body(error);
    }

    private ResponseEntity<Map<String, String>> buildStaticMessageResponse(HttpStatus status, String logMessage, String userMessage, Exception ex) {
        logger.warn("{}: {}", logMessage, ex.getMessage());
        Map<String, String> error = new HashMap<>();
        error.put("message", userMessage);
        return ResponseEntity.status(status).body(error);
    }

    // Validaciones de campos
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        logger.warn("Validation error: {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String field = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(field, message);
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler({
        NoSuchElementException.class,
        IllegalArgumentException.class
    })
    public ResponseEntity<Map<String, String>> handleNotFoundAndBadRequest(RuntimeException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Client error", ex);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String message = String.format("El parámetro '%s' debe ser del tipo '%s'",
                ex.getName(),
                ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "desconocido");
        return buildStaticMessageResponse(HttpStatus.BAD_REQUEST, "Type mismatch", message, ex);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String, String>> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        String message = String.format("El método %s no está permitido. Métodos soportados: %s",
                ex.getMethod(),
                String.join(", ", ex.getSupportedHttpMethods().stream().map(method -> method.name()).toList()));

        Map<String, String> errors = new HashMap<>();
        errors.put("message", message);

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        return buildStaticMessageResponse(HttpStatus.BAD_REQUEST, "Malformed JSON", "El formato de la solicitud es inválido o contiene tipos incorrectos", ex);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, String>> handleIllegalState(IllegalStateException ex) {
        return buildErrorResponse(HttpStatus.CONFLICT, "Illegal state", ex);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Map<String, String>> handleDataAccess(DataAccessException ex) {
        return buildStaticMessageResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Database error", "Error interno al gestionar los datos", ex);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUsernameNotFound(UsernameNotFoundException ex) {
        return buildStaticMessageResponse(HttpStatus.NOT_FOUND, "User not found", "Usuario no encontrado", ex);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleBadCredentials(BadCredentialsException ex) {
        return buildStaticMessageResponse(HttpStatus.UNAUTHORIZED, "Bad credentials", "Credenciales erradas", ex);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoHandlerFound(NoHandlerFoundException ex) {
        return buildStaticMessageResponse(HttpStatus.NOT_FOUND, "URL not found", "La URL de la petición no existe", ex);
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<Map<String, String>> handleMissingPathVariable(MissingPathVariableException ex) {
        return buildStaticMessageResponse(HttpStatus.BAD_REQUEST, "Missing URI variable", "Parámetros no presentes en la URI", ex);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleAllOtherExceptions(Exception ex) {
        logger.error("Unhandled exception", ex);
        return buildStaticMessageResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Unhandled exception", "Error interno del servidor, por favor comuníquese con el administrador.", ex);
    }
    
    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleUsernameAlreadyExists(UsernameAlreadyExistsException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "USERNAME_ALREADY_EXISTS");
        error.put("message", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT); // 409
    }
}