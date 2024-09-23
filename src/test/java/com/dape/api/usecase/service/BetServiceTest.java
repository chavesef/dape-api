package com.dape.api.usecase.service;

import com.dape.api.adapter.controller.stub.BetRequestStub;
import com.dape.api.adapter.controller.stub.BetStub;
import com.dape.api.adapter.dto.request.BetRequest;
import com.dape.api.adapter.repository.BetRepository;
import com.dape.api.domain.entity.Bet;
import com.dape.api.domain.enums.BetStatusEnum;
import com.dape.api.domain.exception.BetNotExistentException;
import com.dape.api.domain.exception.BetSelectedOrResolvedException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class BetServiceTest {

    private final BetRepository betRepository = Mockito.mock(BetRepository.class);
    private final BetService betService = new BetService(betRepository);

    @Test
    void registerBet() {
        final BetRequest betRequest = BetRequestStub.createBetPostRequest();

        final Bet expectedBet = BetStub.createBet();

        when(betRepository.save(Mockito.any(Bet.class))).thenReturn(expectedBet);

        final Bet actualBet = betService.registerBet(betRequest);

        assertEquals(expectedBet.getDesBet(), actualBet.getDesBet());
        assertEquals(expectedBet.getIdtBet(), actualBet.getIdtBet());
        assertEquals(expectedBet.getBetStatusEnum(), actualBet.getBetStatusEnum());
        assertEquals(expectedBet.getNumOdd(), actualBet.getNumOdd());
        assertEquals(expectedBet.getDatCreated().toLocalDate(), actualBet.getDatCreated().toLocalDate());
        assertEquals(expectedBet.getDatUpdated().toLocalDate(), actualBet.getDatUpdated().toLocalDate());
        assertEquals(expectedBet.getFlgSelected(), actualBet.getFlgSelected());
    }

    @Test
    void updateBet(){
        final Long idtBet = 1L;

        final BetRequest betRequest = BetRequestStub.createBetPatchRequest();

        final Bet existentBet = BetStub.createBet();
        final Bet expectedBet = BetStub.createUpdatedBet();

        when(betRepository.save(Mockito.any(Bet.class))).thenReturn(expectedBet);
        when(betRepository.findById(idtBet)).thenReturn(Optional.of(existentBet));

        final Bet actualBet = betService.updateBet(idtBet, betRequest);

        assertThat(actualBet).usingRecursiveComparison().isEqualTo(expectedBet);
    }

    @Test
    void updatedBetInexistent(){
        final Long idtBet = 2L;

        final BetRequest betRequest = BetRequestStub.createBetPatchRequest();

        assertThrows(BetNotExistentException.class, () -> betService.updateBet(idtBet, betRequest));

    }

    @Test
    void updatedBetSelected(){
        final Long idtBet = 1L;

        final BetRequest betRequest = BetRequestStub.createBetPatchRequest();

        Bet existentBet = BetStub.createBet();
        existentBet.setFlgSelected(1);

        when(betRepository.findById(idtBet)).thenReturn(Optional.of(existentBet));

        assertThrows(BetSelectedOrResolvedException.class, () -> betService.updateBet(idtBet, betRequest));
    }

    @Test
    void updatedBetResolved(){
        final Long idtBet = 1L;

        final BetRequest betRequest = BetRequestStub.createBetPatchRequest();

        Bet existentBet = BetStub.createBet();
        existentBet.setBetStatusEnum(BetStatusEnum.GREEN);

        when(betRepository.findById(idtBet)).thenReturn(Optional.of(existentBet));

        assertThrows(BetSelectedOrResolvedException.class, () -> betService.updateBet(idtBet, betRequest));
    }
}
