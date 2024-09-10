package com.dape.api.adapter.controller;

import com.dape.api.adapter.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class DapeExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<List<ErrorResponse>> handleValidationException(MethodArgumentNotValidException e) {
        ArrayList<ErrorResponse> errors = new ArrayList<>();
        for (ObjectError error : e.getAllErrors()) {
            errors.add(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), error.getDefaultMessage()));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

}
