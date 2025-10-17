package com.spring.llavepublicpriv.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception ex) {
        log.error("❌ EXCEPCIÓN CAPTURADA:", ex);

        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getClass().getSimpleName());
        error.put("mensaje", ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<Map<String, String>> handleAuthorizationDenied(AuthorizationDeniedException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("mensaje", "Acceso denegado");
        error.put("error", "Forbidden");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }
}