package com.dape.api.adapter.controller;

import com.dape.api.adapter.dto.request.BetPostRequest;
import com.dape.api.adapter.dto.response.BetPostResponse;
import com.dape.api.domain.entity.Bet;
import com.dape.api.usecase.service.BetService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class BetOperationsControllerTest {

    private final BetService betService = Mockito.mock(BetService.class);
    private final BetOperationsController betOperationsController = new BetOperationsController(betService);

    @Test
    void registerBet(){
        final BetPostRequest betPostRequest = BetStub.createBetPostRequest();

        final BetPostResponse betEsperada = BetStub.createBetPostResponse();

        final Bet bet = BetStub.createBet();

        when(betService.registerBet(betPostRequest)).thenReturn(bet);

        ResponseEntity<BetPostResponse> betCriada = betOperationsController.registerBet(betPostRequest);

        assertEquals(betEsperada.getDesBet(), Objects.requireNonNull(betCriada.getBody()).getDesBet());
    }
}
