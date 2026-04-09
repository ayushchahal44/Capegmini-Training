/* Build by: Ayush chahal | ayushchahal44@gmail.com */
package com.capg.ayush.finflow.common.web.exception;
import com.capg.ayush.finflow.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for REST controllers.
 * Provides a unified way to handle exceptions and return standard API responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles all generic exceptions and returns a bad request response.
     * @param ex The exception that occurred
     * @return A ResponseEntity containing the error details
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handle(Exception ex) {
        return ResponseEntity.badRequest()
                .body(new ApiResponse<>(false, ex.getMessage(), null));
    }
}