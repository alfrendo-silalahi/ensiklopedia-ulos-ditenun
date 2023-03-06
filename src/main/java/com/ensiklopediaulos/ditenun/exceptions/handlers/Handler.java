package com.ensiklopediaulos.ditenun.exceptions.handlers;

import com.ensiklopediaulos.ditenun.dtos.response.ErrorBaseResponse;
import com.ensiklopediaulos.ditenun.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class Handler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { ResourceNotFoundException.class })
    protected ResponseEntity<ErrorBaseResponse> handleResourceNotFoundException(ResourceNotFoundException e) {
        ErrorBaseResponse response = new ErrorBaseResponse();
        response.setCode(HttpStatus.NOT_FOUND.value());
        response.setStatus("error");
        response.setMessage(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
