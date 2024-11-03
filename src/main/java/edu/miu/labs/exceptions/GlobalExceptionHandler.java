package edu.miu.labs.exceptions;

import edu.miu.labs.entities.dtos.ErrorDto;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

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
