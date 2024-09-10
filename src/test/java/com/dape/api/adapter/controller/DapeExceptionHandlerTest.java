package com.dape.api.adapter.controller;

import com.dape.api.adapter.dto.response.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DapeExceptionHandlerTest {

    private final DapeExceptionHandler exceptionHandler = new DapeExceptionHandler();

    @Test
    void handleInvalidRequestParameters() {
        BindingResult bindingResult = new BeanPropertyBindingResult("betPostRequest", "betPostRequest");
        bindingResult.addError(new FieldError("betPostRequest", "numOdd", "Valor da odd deve ser maior que 1"));
        bindingResult.addError(new FieldError("betPostRequest", "desBet", "Descrição da aposta não deve ser nula/vazia"));

        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(Mockito.mock(org.springframework.core.MethodParameter.class), bindingResult);

        ResponseEntity<List<ErrorResponse>> actualResponse = exceptionHandler.handleValidationException(exception);

        List<ErrorResponse> expectedErrors = new ArrayList<>();
        expectedErrors.add(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Valor da odd deve ser maior que 1"));
        expectedErrors.add(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Descrição da aposta não deve ser nula/vazia"));
        ResponseEntity<List<ErrorResponse>> expectedResponse =
                new ResponseEntity<>(expectedErrors, HttpStatus.BAD_REQUEST);

        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody().size(), actualResponse.getBody().size());
        for (int i = 0; i < expectedErrors.size(); i++) {
            assertEquals(expectedErrors.get(i).getMessage(), actualResponse.getBody().get(i).getMessage());
            assertEquals(expectedErrors.get(i).getErrorCode(), actualResponse.getBody().get(i).getErrorCode());
        }

    }
}
