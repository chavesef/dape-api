package com.dape.api.usecase.service;

import com.dape.api.adapter.dto.request.BetPostRequest;
import com.dape.api.adapter.repository.BetRepository;
import com.dape.api.domain.entity.Bet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class BetServiceTest {

    private BetRepository betRepository;
    private BetService betService;

    @BeforeEach
    public void setUp() {
        betRepository = Mockito.mock(BetRepository.class);
    }

    @Test
    void registerBet() {
        BetPostRequest betPostRequest = new BetPostRequest(
                new BigDecimal("2.12"), "Vitória do River Plate");

        final Bet betEsperada = Mockito.mock(Bet.class);
        when(betEsperada.getDesBet()).thenReturn("Vitória do River Plate");
        when(betEsperada.getNumOdd()).thenReturn(new BigDecimal("2.12"));

        when(betRepository.save(Mockito.any(Bet.class))).thenReturn(betEsperada);

        betService = new BetService(betRepository);
        final Bet betCriada = betService.registerBet(betPostRequest);

        assertEquals(betEsperada.getDesBet(), betCriada.getDesBet());
    }
}
