package com.rest.payments.exceptions;

import javax.validation.ValidationException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;

@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<Object> handleCustomServiceException(final ServiceException serviceException) {
        return new ResponseEntity<>(serviceException.getMessage(), serviceException.getResponseCode());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> handleNullPointerException(final NullPointerException nullPointerException) {
        return new ResponseEntity<>(nullPointerException.getMessage(), BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> handleHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException) {
        return new ResponseEntity<>(httpRequestMethodNotSupportedException.getMessage(), METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationExceptionException(final ValidationException validationException) {
        return new ResponseEntity<>(validationException.getMessage(), BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {
        MethodArgumentInvalidException methodArgumentInvalidException = new MethodArgumentInvalidException(exception.getParameter(), exception.getBindingResult());
        return new ResponseEntity<>(methodArgumentInvalidException.getMessage(), BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUnknownException(final Exception exception) {
        return new ResponseEntity<>("Unable o process your request", INTERNAL_SERVER_ERROR);
    }
}
