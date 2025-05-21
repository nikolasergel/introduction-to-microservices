package com.epam.resourceservice.controller;

import com.epam.resourceservice.dto.ErrorResponse;
import com.epam.resourceservice.exception.EntityAlreadyExistsException;
import com.epam.resourceservice.exception.InvalidNumberException;
import com.epam.resourceservice.exception.NotFoundException;
import com.fasterxml.jackson.databind.JsonMappingException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.method.MethodValidationException;
import org.springframework.validation.method.MethodValidationResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({ConstraintViolationException.class})
    protected ResponseEntity<ErrorResponse> handleValidationException(
            ConstraintViolationException ex, WebRequest request) {
        String message = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(
                        Collectors.joining());
        ErrorResponse errorResponse = new ErrorResponse(message, Collections.emptyMap(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler({InvalidNumberException.class})
    protected ResponseEntity<ErrorResponse> handleValidationException(
            Exception ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), Collections.emptyMap(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler({HandlerMethodValidationException.class, MethodValidationException.class})
    protected ResponseEntity<ErrorResponse> handleValidationException(

            MethodValidationResult ex, WebRequest request) {
        Map<String, String> details = ex.getParameterValidationResults().stream()
                .collect(
                        Collectors.toMap((k) -> Objects.requireNonNull(k.getContainerIndex()).toString(),
                                (v) -> v.getResolvableErrors().stream().map(MessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(","))));
        ErrorResponse errorResponse = new ErrorResponse("Validation Error", details, HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    protected ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> details = ex.getBindingResult().getFieldErrors().stream()
                .collect(
                        Collectors.toMap(FieldError::getField,
                                DefaultMessageSourceResolvable::getDefaultMessage));
        ErrorResponse errorResponse = new ErrorResponse("Validation error", details, HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    protected ResponseEntity<ErrorResponse> handleValidationException(
            HttpMessageNotReadableException ex, WebRequest request) {
        Map<String, String> details;
        if (ex.getCause() instanceof JsonMappingException jsonEx) {
            details = jsonEx.getPath().stream().collect(Collectors.toMap(
                            JsonMappingException.Reference::getFieldName,
                            (v) -> "Invalid value"));
        } else {
            details = Map.of("error", ex.getMessage());
        }
        ErrorResponse errorResponse = new ErrorResponse("Json parse error", details, HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler({EntityAlreadyExistsException.class})
    protected ResponseEntity<ErrorResponse> handleException(
            EntityAlreadyExistsException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), Collections.emptyMap(),
                HttpStatus.CONFLICT.value());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler({NotFoundException.class})
    protected ResponseEntity<ErrorResponse> handleException(
            NotFoundException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), Collections.emptyMap(),
                HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<ErrorResponse> handleException(
            Exception ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse("Something went wrong on the server side", Collections.emptyMap(),
                HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
