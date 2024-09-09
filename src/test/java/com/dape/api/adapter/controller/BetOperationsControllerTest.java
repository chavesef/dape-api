package com.dape.api.adapter.controller;

import com.dape.api.adapter.dto.request.BetPostRequest;
import com.dape.api.adapter.dto.response.BetPostResponse;
import com.dape.api.domain.entity.Bet;
import com.dape.api.domain.enums.BetStatusEnum;
import com.dape.api.usecase.service.BetService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class BetOperationsControllerTest {

    private final BetService betService = Mockito.mock(BetService.class);
    private final BetOperationsController betOperationsController = new BetOperationsController(betService);

    @Test
    void registerBet(){
        BetPostRequest betPostRequest = new BetPostRequest(
                new BigDecimal("2.12"), "Vitória do River Plate");

        BetPostResponse betEsperada = Mockito.mock(BetPostResponse.class);
        when(betEsperada.getDesBet()).thenReturn("Vitória do River Plate");
        when(betEsperada.getNumOdd()).thenReturn(new BigDecimal("2.12"));

        Bet bet = Mockito.mock(Bet.class);
        when(bet.getDesBet()).thenReturn(betPostRequest.getDesBet());
        when(bet.getNumOdd()).thenReturn(betPostRequest.getNumOdd());
        when(bet.getDatCreated()).thenReturn(LocalDateTime.now());
        when(bet.getBetStatusEnum()).thenReturn(BetStatusEnum.PENDING);

        when(betService.registerBet(betPostRequest)).thenReturn(bet);

        ResponseEntity<BetPostResponse> betCriada = betOperationsController.registerBet(betPostRequest);

        assertEquals(betEsperada.getDesBet(), Objects.requireNonNull(betCriada.getBody()).getDesBet());
    }
}
