package com.dape.api.usecase.service;

import com.dape.api.adapter.controller.BetStub;
import com.dape.api.adapter.dto.request.BetPostRequest;
import com.dape.api.adapter.repository.BetRepository;
import com.dape.api.domain.entity.Bet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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
        final BetPostRequest betPostRequest = BetStub.createBetPostRequest();

        final Bet betEsperada = BetStub.createBet();

        when(betRepository.save(Mockito.any(Bet.class))).thenReturn(betEsperada);

        betService = new BetService(betRepository);
        final Bet betCriada = betService.registerBet(betPostRequest);

        assertEquals(betEsperada.getDesBet(), betCriada.getDesBet());
    }
}
