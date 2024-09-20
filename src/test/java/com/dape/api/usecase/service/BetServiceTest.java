package com.dape.api.usecase.service;

import com.dape.api.adapter.controller.stub.BetPostRequestStub;
import com.dape.api.adapter.controller.stub.BetStub;
import com.dape.api.adapter.dto.request.BetRequest;
import com.dape.api.adapter.repository.BetRepository;
import com.dape.api.domain.entity.Bet;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class BetServiceTest {

    private final BetRepository betRepository = Mockito.mock(BetRepository.class);
    private final BetService betService = new BetService(betRepository);

    @Test
    void registerBet() {
        final BetRequest betRequest = BetPostRequestStub.createBetPostRequest();

        final Bet betEsperada = BetStub.createBet();

        when(betRepository.save(Mockito.any(Bet.class))).thenReturn(betEsperada);

        final Bet betCriada = betService.registerBet(betRequest);

        assertEquals(betEsperada.getDesBet(), betCriada.getDesBet());
        assertEquals(betEsperada.getIdtBet(), betCriada.getIdtBet());
        assertEquals(betEsperada.getBetStatusEnum(), betCriada.getBetStatusEnum());
        assertEquals(betEsperada.getNumOdd(), betCriada.getNumOdd());
        assertEquals(betEsperada.getDatCreated().toLocalDate(), betCriada.getDatCreated().toLocalDate());
        assertEquals(betEsperada.getDatUpdated().toLocalDate(), betCriada.getDatUpdated().toLocalDate());
        assertEquals(betEsperada.getFlgSelected(), betCriada.getFlgSelected());
    }
}
