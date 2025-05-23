package com.dape.api.adapter.controller;

import com.dape.api.adapter.dto.response.ErrorResponse;
import com.dape.api.domain.exception.BetNotExistentException;
import com.dape.api.domain.exception.ClientNotExistentException;
import com.dape.api.domain.exception.InvalidStatusException;
import com.dape.api.domain.exception.InvalidStatusForUpdateException;
import com.dape.api.domain.exception.UnavailableBalanceException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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
        final BindingResult bindingResult = new BeanPropertyBindingResult("betPostRequest", "betPostRequest");
        bindingResult.addError(new FieldError("betPostRequest", "numOdd", "Valor da odd deve ser maior que 1"));
        bindingResult.addError(new FieldError("betPostRequest", "desBet", "Descrição da aposta não deve ser nula/vazia"));

        final MethodArgumentNotValidException exception = new MethodArgumentNotValidException(Mockito.mock(org.springframework.core.MethodParameter.class), bindingResult);

        final ResponseEntity<List<ErrorResponse>> actualResponse = exceptionHandler.handleValidationException(exception);

        final List<ErrorResponse> expectedErrors = new ArrayList<>();
        expectedErrors.add(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Valor da odd deve ser maior que 1"));
        expectedErrors.add(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Descrição da aposta não deve ser nula/vazia"));

        final ResponseEntity<List<ErrorResponse>> expectedResponse =
                new ResponseEntity<>(expectedErrors, HttpStatus.BAD_REQUEST);

        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody().size(), actualResponse.getBody().size());
        for (int i = 0; i < expectedErrors.size(); i++) {
            assertEquals(expectedErrors.get(i).getMessage(), actualResponse.getBody().get(i).getMessage());
            assertEquals(expectedErrors.get(i).getErrorCode(), actualResponse.getBody().get(i).getErrorCode());
        }

    }

    @Test
    void handleInvalidTypeOdd(){
        HttpInputMessage httpInputMessage = Mockito.mock(HttpInputMessage.class);
        final HttpMessageNotReadableException exception = new HttpMessageNotReadableException("Valor de odd inválido", httpInputMessage);

        final ResponseEntity<ErrorResponse> actualResponse = exceptionHandler.handleInvalidRequestDataExceptions(exception);

        final ResponseEntity<ErrorResponse> expectedResponse = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage()));

        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody().getMessage(), actualResponse.getBody().getMessage());
    }

    @Test
    void handleBetInexistent(){
        final BetNotExistentException exception = new BetNotExistentException("Aposta com id 4 não existe no banco de dados");

        final ResponseEntity<ErrorResponse> actualResponse = exceptionHandler.handleBetNotExistException(exception);

        final ResponseEntity<ErrorResponse> expectedResponse = ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage()));

        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody().getMessage(), actualResponse.getBody().getMessage());
    }

    @Test
    void handleBetSelectedOrResolved(){
        final InvalidStatusForUpdateException exception = new InvalidStatusForUpdateException("Condições inválidas para atualização: BetStatus=RED, FlgSelected=0");

        final ResponseEntity<ErrorResponse> actualResponse = exceptionHandler.handleInvalidRequestDataExceptions(exception);

        final ResponseEntity<ErrorResponse> expectedResponse = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage()));

        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody().getMessage(), actualResponse.getBody().getMessage());
    }

    @Test
    void handleInvalidStatusForGetBets(){
        final InvalidStatusException exception = new InvalidStatusException("Status não existente: VENCIDA" );

        final ResponseEntity<ErrorResponse> actualResponse = exceptionHandler.handleInvalidRequestDataExceptions(exception);

        final ResponseEntity<ErrorResponse> expectedResponse = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage()));

        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody().getMessage(), actualResponse.getBody().getMessage());
    }

    @Test
    void handleUnavailableBalance(){
        final UnavailableBalanceException exception = new UnavailableBalanceException("O saldo da conta (10) é menor do que o valor a ser apostado (100)");

        final ResponseEntity<ErrorResponse> actualResponse = exceptionHandler.handleInvalidRequestDataExceptions(exception);

        final ResponseEntity<ErrorResponse> expectedResponse = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage()));

        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody().getMessage(), actualResponse.getBody().getMessage());
    }

    @Test
    void handleClientInexistent(){
        final ClientNotExistentException exception = new ClientNotExistentException("O cliente com id 6 não existe");

        final ResponseEntity<ErrorResponse> actualResponse = exceptionHandler.handleBetNotExistException(exception);

        final ResponseEntity<ErrorResponse> expectedResponse = ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage()));

        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody().getMessage(), actualResponse.getBody().getMessage());
    }
}
