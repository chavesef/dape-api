package com.dape.api.adapter.controller;

import com.dape.api.adapter.dto.BetPostRequest;
import com.dape.api.adapter.dto.BetPostResponse;
import com.dape.api.domain.entity.Bet;
import com.dape.api.domain.enums.BetStatusEnum;
import com.dape.api.usecase.service.BetService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class DapeControllerTest {

    private final BetService betService = Mockito.mock(BetService.class);
    private final DapeController dapeController = new DapeController(betService);

    @Test
    void helloWorld(){
        final String result = dapeController.helloWorld();
        final String expected = "Hello World!";
        assertNotNull(result);
        assertEquals(expected, result);
    }

    @Test
    void cadastrarAposta(){
        BetPostRequest betPostRequest = new BetPostRequest(
                new BigDecimal("2.12"), "Vitória do River Plate");
        BetPostResponse betEsperada = apostaEsperada(betPostRequest);
        when(betService.cadastrarAposta(betPostRequest)).thenReturn(betEsperada);
        BetPostResponse betCriada = dapeController.cadastrarAposta(betPostRequest);

        assertEquals(betEsperada.getDesBet(), betCriada.getDesBet());
    }

    private BetPostResponse apostaEsperada(BetPostRequest betPostRequest) {
        Bet bet = new Bet();
        bet.setDesBet(betPostRequest.getDesBet());
        bet.setNumOdd(betPostRequest.getNumOdd());
        bet.setBetStatusEnum(BetStatusEnum.PENDING);
        bet.setDatCreated(LocalDateTime.now());
        bet.setDatUpdated(LocalDateTime.now());
        bet.setFlgSelected(0);

        return new BetPostResponse(bet.getIdtBet(), bet.getDesBet(), bet.getNumOdd(),
                bet.getDatCreated().toLocalDate(), bet.getBetStatusEnum());
    }
}
