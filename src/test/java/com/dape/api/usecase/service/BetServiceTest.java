package com.dape.api.usecase.service;

import com.dape.api.stub.BetRequestStub;
import com.dape.api.stub.BetStub;
import com.dape.api.adapter.dto.request.BetRequest;
import com.dape.api.adapter.repository.BetRepository;
import com.dape.api.domain.entity.Bet;
import com.dape.api.domain.enums.BetStatusEnum;
import com.dape.api.domain.exception.BetNotExistentException;
import com.dape.api.domain.exception.InvalidStatusForUpdateException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
        final BetRequest betRequest = BetRequestStub.builder().build();

        final Bet expectedBet = BetStub.builder().build();

        when(betRepository.save(Mockito.any(Bet.class))).thenReturn(expectedBet);

        final Bet actualBet = betService.registerBet(betRequest);

        assertThat(actualBet).usingRecursiveComparison().ignoringFieldsOfTypes(LocalDateTime.class).isEqualTo(expectedBet);
    }

    @Test
    void updateBet(){
        final BetRequest betRequest = BetRequestStub.builder().withDesBet("Vitória do Boca Juniors").withNumOdd(new BigDecimal("2.43")).build();

        final Bet existentBet = BetStub.builder().build();
        final Bet expectedBet = BetStub.builder().withDesBet(betRequest.getDesBet()).withNumOdd(betRequest.getNumOdd()).build();

        final Long idtBet = 1L;
        when(betRepository.save(Mockito.any(Bet.class))).thenReturn(expectedBet);
        when(betRepository.findById(idtBet)).thenReturn(Optional.of(existentBet));

        final Bet actualBet = betService.updateBet(idtBet, betRequest);

        assertThat(actualBet).usingRecursiveComparison().ignoringFieldsOfTypes(LocalDateTime.class).isEqualTo(expectedBet);
    }

    @Test
    void updatedBetInexistentExpectException(){
        final BetRequest betRequest = BetRequestStub.builder().withDesBet("Vitória do Boca Juniors").withNumOdd(new BigDecimal("2.43")).build();

        final Long idtBet = 2L;
        final String expectedMessage = "Aposta com id " + idtBet + " não existe no banco de dados";

        BetNotExistentException betNotExistentException =
                assertThrows(BetNotExistentException.class, () -> betService.updateBet(idtBet, betRequest));
        assertEquals(expectedMessage, betNotExistentException.getMessage());
    }

    @Test
    void updatedBetSelectedExpectException(){
        final BetRequest betRequest = BetRequestStub.builder().withDesBet("Vitória do Boca Juniors").withNumOdd(new BigDecimal("2.43")).build();

        Bet existentBet = BetStub.builder().build();
        existentBet.setFlgSelected(1);

        final Long idtBet = 1L;
        when(betRepository.findById(idtBet)).thenReturn(Optional.of(existentBet));

        final String expectedMessage = "Condições inválidas para atualização: BetStatus=" + existentBet.getBetStatusEnum() + ", FlgSelected=" + existentBet.getFlgSelected();

        InvalidStatusForUpdateException invalidStatusForUpdateException =
                assertThrows(InvalidStatusForUpdateException.class, () -> betService.updateBet(idtBet, betRequest));
        assertEquals(expectedMessage, invalidStatusForUpdateException.getMessage());
    }

    @Test
    void updatedBetResolvedExpectException(){
        final BetRequest betRequest = BetRequestStub.builder().withDesBet("Vitória do Boca Juniors").withNumOdd(new BigDecimal("2.43")).build();

        Bet existentBet = BetStub.builder().build();
        existentBet.setBetStatusEnum(BetStatusEnum.GREEN);

        final Long idtBet = 1L;
        when(betRepository.findById(idtBet)).thenReturn(Optional.of(existentBet));

        final String expectedMessage = "Condições inválidas para atualização: BetStatus=" + existentBet.getBetStatusEnum() + ", FlgSelected=" + existentBet.getFlgSelected();

        InvalidStatusForUpdateException invalidStatusForUpdateException =
                assertThrows(InvalidStatusForUpdateException.class, () -> betService.updateBet(idtBet, betRequest));
        assertEquals(expectedMessage, invalidStatusForUpdateException.getMessage());
    }
}
