package com.ensiklopediaulos.ditenun.exceptions.handlers;

import com.ensiklopediaulos.ditenun.dtos.response.ErrorBaseResponse;
import com.ensiklopediaulos.ditenun.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class Handler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = ResourceNotFoundException.class )
    public ResponseEntity<ErrorBaseResponse<String>> handleResourceNotFoundException(
            ResourceNotFoundException e) {
        ErrorBaseResponse<String> response = new ErrorBaseResponse<>();
        response.setCode(HttpStatus.NOT_FOUND.value());
        response.setStatus("error");
        response.setMessage(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        ErrorBaseResponse<Map<String, String>> response = new ErrorBaseResponse<>();
        response.setCode(HttpStatus.NOT_FOUND.value());
        response.setStatus("error");
        response.setMessage(errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
