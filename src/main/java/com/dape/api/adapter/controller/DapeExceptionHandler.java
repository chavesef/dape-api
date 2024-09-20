package com.dape.api.adapter.controller;

import com.dape.api.adapter.dto.response.ErrorResponse;
import com.dape.api.domain.exception.BetNotExistent;
import com.dape.api.domain.exception.BetSelectedOrResolvedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
        return ResponseEntity.status(400).body(new ErrorResponse(400, e.getMessage()));
    }

    @ExceptionHandler(BetNotExistent.class)
    public ResponseEntity<ErrorResponse> handleBetNotExistException(BetNotExistent e){
        return ResponseEntity.status(404).body(new ErrorResponse(404, e.getMessage()));
    }

    @ExceptionHandler(BetSelectedOrResolvedException.class)
    public ResponseEntity<ErrorResponse> handleBetSelectedException(BetSelectedOrResolvedException e){
        return ResponseEntity.status(400).body(new ErrorResponse(400, e.getMessage()));
    }
}
