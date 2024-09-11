package com.dape.api.usecase.service;

import com.dape.api.adapter.controller.BetStub;
import com.dape.api.adapter.dto.request.BetPostRequest;
import com.dape.api.adapter.repository.BetRepository;
import com.dape.api.domain.entity.Bet;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class BetServiceTest {

    private final BetRepository betRepository = Mockito.mock(BetRepository.class);
    private BetService betService;

    @Test
    void registerBet() {
        final BetPostRequest betPostRequest = BetStub.createBetPostRequest();

        final Bet betEsperada = BetStub.createBet();

        when(betRepository.save(Mockito.any(Bet.class))).thenReturn(betEsperada);

        betService = new BetService(betRepository);
        final Bet betCriada = betService.registerBet(betPostRequest);

        assertEquals(betEsperada.getDesBet(), betCriada.getDesBet());
        assertEquals(betEsperada.getIdtBet(), betCriada.getIdtBet());
        assertEquals(betEsperada.getBetStatusEnum(), betCriada.getBetStatusEnum());
        assertEquals(betEsperada.getNumOdd(), betCriada.getNumOdd());
        assertEquals(betEsperada.getDatCreated(), betCriada.getDatCreated());
        assertEquals(betEsperada.getDatUpdated(), betCriada.getDatUpdated());
        assertEquals(betEsperada.getFlgSelected(), betCriada.getFlgSelected());
    }
}
