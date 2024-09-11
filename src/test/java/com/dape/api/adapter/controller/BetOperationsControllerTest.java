package com.dape.api.adapter.controller;

import com.dape.api.adapter.controller.stub.BetPostRequestStub;
import com.dape.api.adapter.controller.stub.BetPostResponseStub;
import com.dape.api.adapter.controller.stub.BetStub;
import com.dape.api.adapter.dto.request.BetPostRequest;
import com.dape.api.adapter.dto.response.BetPostResponse;
import com.dape.api.domain.entity.Bet;
import com.dape.api.usecase.service.BetService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BetOperationsControllerTest {

    private final BetService betService = Mockito.mock(BetService.class);
    private final BetOperationsController betOperationsController = new BetOperationsController(betService);

    @Test
    void registerBet(){
        final BetPostRequest betPostRequest = BetPostRequestStub.createBetPostRequest();

        final BetPostResponse betPostResponse = BetPostResponseStub.createBetPostResponse();

        final Bet bet = BetStub.createBet();

        when(betService.registerBet(betPostRequest)).thenReturn(bet);

        ResponseEntity<BetPostResponse> betCriada = betOperationsController.registerBet(betPostRequest);
        ResponseEntity<BetPostResponse> betEsperada =
                ResponseEntity.status(HttpStatusCode.valueOf(201)).body(betPostResponse);

        verify(betService).registerBet(betPostRequest);
        assertEquals(betEsperada.getStatusCode(), betCriada.getStatusCode());
        assertEquals(betEsperada.getBody().getDesBet(), betCriada.getBody().getDesBet());
        assertEquals(betEsperada.getBody().getBetStatusEnum(), betCriada.getBody().getBetStatusEnum());
        assertEquals(betEsperada.getBody().getIdtBet(), betCriada.getBody().getIdtBet());
        assertEquals(betEsperada.getBody().getDatCreated().toLocalDate(), betCriada.getBody().getDatCreated().toLocalDate());
        assertEquals(betEsperada.getBody().getDatUpdated().toLocalDate(), betCriada.getBody().getDatUpdated().toLocalDate());
        assertEquals(betEsperada.getBody().getNumOdd(), betCriada.getBody().getNumOdd());
        assertEquals(betEsperada.getBody().getFlgSelected(), betCriada.getBody().getFlgSelected());
    }
}
