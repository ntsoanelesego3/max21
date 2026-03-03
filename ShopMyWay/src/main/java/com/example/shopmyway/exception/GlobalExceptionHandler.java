package com.example.shopmyway.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

// ✅ This is like a "catch-all safety net" for your whole app.
// Without this, when something goes wrong, Spring sends a big ugly error page.
// With this, your API sends a clean, readable JSON error — like a professional app.

// Example of BEFORE (ugly):
// "There was an unexpected error (type=Internal Server Error, status=500)"

// Example of AFTER (clean):
// { "error": "User not found with ID: 5", "status": 404, "timestamp": "2024-01-15T10:30:00" }

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handles "not found" errors (like looking up a user that doesn't exist)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(EntityNotFoundException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // Handles runtime errors (something unexpected crashed)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntime(RuntimeException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Handles any other error we didn't plan for
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneral(Exception ex) {
        return buildResponse("Something went wrong: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Map<String, Object>> buildResponse(String message, HttpStatus status) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", message);
        body.put("status", status.value());
        body.put("timestamp", LocalDateTime.now());
        return new ResponseEntity<>(body, status);
    }
}