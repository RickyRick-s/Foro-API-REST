package com.Rick.ForoAPI.infrastructure.Exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GestorDeErrores {


        @ExceptionHandler(MethodArgumentNotValidException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public Map<String, Object> handleValidationException(MethodArgumentNotValidException ex) {
            Map<String, Object> error = new HashMap<>();
            Map<String, String> erroresCampos = new HashMap<>();
            for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
                erroresCampos.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            error.put("timestamp", LocalDateTime.now());
            error.put("status", 400);
            error.put("error", "Error de validación");
            error.put("campos", erroresCampos);
            return error;
        }

        @ExceptionHandler(HttpMessageNotReadableException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public Map<String, Object> handleMalformedJson(HttpMessageNotReadableException ex) {
            Map<String, Object> error = new HashMap<>();
            error.put("timestamp", LocalDateTime.now());
            error.put("status", 400);
            error.put("error", "Solicitud malformada o valor inválido (posible enum inválido)");
            error.put("detalle", ex.getMostSpecificCause().getMessage());
            return error;
        }

        @ExceptionHandler(EntityNotFoundException.class)
        @ResponseStatus(HttpStatus.NOT_FOUND)
        public Map<String, Object> handleEntityNotFound(EntityNotFoundException ex) {
            Map<String, Object> error = new HashMap<>();
            error.put("timestamp", LocalDateTime.now());
            error.put("status", 404);
            error.put("error", "Recurso no encontrado");
            error.put("detalle", ex.getMessage());
            return error;
        }

        @ExceptionHandler(IllegalArgumentException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        public Map<String, Object> handleIllegalArgument(IllegalArgumentException ex) {
            Map<String, Object> error = new HashMap<>();
            error.put("timestamp", LocalDateTime.now());
            error.put("status", 400);
            error.put("error", "Argumento inválido");
            error.put("detalle", ex.getMessage());
            return error;
        }

        @ExceptionHandler(Exception.class)
        @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        public Map<String, Object> handleGeneric(Exception ex) {
            Map<String, Object> error = new HashMap<>();
            error.put("timestamp", LocalDateTime.now());
            error.put("status", 500);
            error.put("error", "Error interno del servidor");
            error.put("detalle", ex.getMessage());
            return error;
        }
    }


