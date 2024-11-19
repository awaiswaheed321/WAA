package com.waa.marketplace.exceptions;

import com.waa.marketplace.dtos.responses.ErrorDto;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex, WebRequest request) {
        // Extract the default message from the first field error
        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst() // Get the first field error
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("Validation error occurred");
        LOGGER.error("Validation failed: {}", errorMessage, ex);
        ErrorDto errorResponse = new ErrorDto(
                HttpStatus.BAD_REQUEST.value(),
                errorMessage
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDto> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        LOGGER.error(GlobalExceptionHandler.class.getName(), ex.getMessage(), ex);
        ErrorDto errorResponse = new ErrorDto(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorDto> handleIllegalStateException(IllegalStateException ex, WebRequest request) {
        LOGGER.error(GlobalExceptionHandler.class.getName(), ex.getMessage(), ex);
        ErrorDto errorResponse = new ErrorDto(
                HttpStatus.BAD_REQUEST.value(),
                "Invalid state: " + ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDto> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
        LOGGER.error(GlobalExceptionHandler.class.getName(), ex.getMessage(), ex);
        ErrorDto errorResponse = new ErrorDto(
                HttpStatus.UNAUTHORIZED.value(),
                "Invalid username or password"
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDto> handleResourceNotFoundException(EntityNotFoundException ex, WebRequest request) {
        LOGGER.error(GlobalExceptionHandler.class.getName(), ex.getMessage(), ex);
        ErrorDto errorResponse = new ErrorDto(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorDto> handleNoResourceFoundException(Exception ex, WebRequest request) {
        LOGGER.error(GlobalExceptionHandler.class.getName(), ex.getMessage(), ex);
        ErrorDto errorResponse = new ErrorDto(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorDto> handleHandlerMethodValidationException(Exception ex, WebRequest request) {
        LOGGER.error(GlobalExceptionHandler.class.getName(), ex.getMessage(), ex);
        ErrorDto errorResponse = new ErrorDto(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // Handle generic exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleGlobalException(Exception ex, WebRequest request) {
        LOGGER.error(GlobalExceptionHandler.class.getName(), ex.getMessage(), ex);
        ErrorDto errorResponse = new ErrorDto(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
