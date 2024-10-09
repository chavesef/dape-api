package com.dape.api.usecase.service;

import com.dape.api.adapter.dto.request.BetStatusRequest;
import com.dape.api.stub.BetRequestStub;
import com.dape.api.stub.BetStatusRequestStub;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class BetServiceTest {

    private final BetRepository betRepository = Mockito.mock(BetRepository.class);
    private final BetService betService = new BetService(betRepository);

    @Test
    void registerBet() {
        final Bet expectedBet = BetStub.builder().build();

        when(betRepository.save(any(Bet.class))).thenReturn(expectedBet);

        final Bet actualBet = betService.registerBet(BetRequestStub.builder().build());

        assertThat(actualBet).usingRecursiveComparison().ignoringFieldsOfTypes(LocalDateTime.class).isEqualTo(expectedBet);
    }

    @Test
    void updateBet(){
        final BetRequest betRequest = BetRequestStub.builder().withDesBet("Vitória do Boca Juniors").withNumOdd(new BigDecimal("2.43")).build();

        final Bet expectedBet = BetStub.builder().withDesBet(betRequest.getDesBet()).withNumOdd(betRequest.getNumOdd()).build();

        final Long idtBet = 1L;
        when(betRepository.save(any(Bet.class))).thenReturn(expectedBet);
        when(betRepository.findById(idtBet)).thenReturn(Optional.of(BetStub.builder().build()));

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

        Bet existentBet = BetStub.builder().withFlgSelected(1).build();

        final Long idtBet = 1L;
        when(betRepository.findById(idtBet)).thenReturn(Optional.of(existentBet));

        final String expectedMessage = "Condições inválidas para atualização da aposta com id: " + existentBet.getIdtBet() + " - BetStatus=" + existentBet.getBetStatusEnum() + ", FlgSelected=" + existentBet.getFlgSelected();

        InvalidStatusForUpdateException invalidStatusForUpdateException =
                assertThrows(InvalidStatusForUpdateException.class, () -> betService.updateBet(idtBet, betRequest));
        assertEquals(expectedMessage, invalidStatusForUpdateException.getMessage());
    }

    @Test
    void updatedBetResolvedExpectException(){
        final BetRequest betRequest = BetRequestStub.builder().withDesBet("Vitória do Boca Juniors").withNumOdd(new BigDecimal("2.43")).build();

        Bet existentBet = BetStub.builder().withBetStatusEnum(BetStatusEnum.GREEN).build();

        final Long idtBet = 1L;
        when(betRepository.findById(idtBet)).thenReturn(Optional.of(existentBet));

        final String expectedMessage = "Condições inválidas para atualização da aposta com id: " + existentBet.getIdtBet() + " - BetStatus=" + existentBet.getBetStatusEnum() + ", FlgSelected=" + existentBet.getFlgSelected();

        InvalidStatusForUpdateException invalidStatusForUpdateException =
                assertThrows(InvalidStatusForUpdateException.class, () -> betService.updateBet(idtBet, betRequest));
        assertEquals(expectedMessage, invalidStatusForUpdateException.getMessage());
    }

    @Test
    void updateBetStatusToGreen(){
        final Bet expectedBet = BetStub.builder().withBetStatusEnum(BetStatusEnum.GREEN).build();

        when(betRepository.save(any(Bet.class))).thenReturn(expectedBet);
        when(betRepository.findById(anyLong())).thenReturn(Optional.of(BetStub.builder().build()));

        final Long idtBet = 1L;
        final Bet actualBet = betService.updateBetStatus(idtBet, BetStatusRequestStub.builder().withBetStatus(BetStatusEnum.GREEN).build());

        assertThat(actualBet).usingRecursiveComparison().ignoringFieldsOfTypes(LocalDateTime.class).isEqualTo(expectedBet);
    }

    @Test
    void updateBetStatusToRed(){
        final Bet expectedBet = BetStub.builder().withBetStatusEnum(BetStatusEnum.RED).build();

        when(betRepository.save(any(Bet.class))).thenReturn(expectedBet);
        when(betRepository.findById(anyLong())).thenReturn(Optional.of(BetStub.builder().build()));

        final Long idtBet = 1L;
        final Bet actualBet = betService.updateBetStatus(idtBet, BetStatusRequestStub.builder().withBetStatus(BetStatusEnum.RED).build());

        assertThat(actualBet).usingRecursiveComparison().ignoringFieldsOfTypes(LocalDateTime.class).isEqualTo(expectedBet);
    }

    @Test
    void updateBetStatusInexistentBetExpectException(){
        final BetStatusRequest betStatusRequest = BetStatusRequestStub.builder().withBetStatus(BetStatusEnum.GREEN).build();

        final Long idtBet = 2L;
        final String expectedMessage = "Aposta com id " + idtBet + " não existe no banco de dados";

        BetNotExistentException betNotExistentException =
                assertThrows(BetNotExistentException.class, () -> betService.updateBetStatus(idtBet, betStatusRequest));
        assertEquals(expectedMessage, betNotExistentException.getMessage());
    }

    @Test
    void updateWithInvalidStatusExpectException(){
        final BetStatusRequest betStatusRequest = BetStatusRequestStub.builder().build();

        when(betRepository.findById(anyLong())).thenReturn(Optional.of(BetStub.builder().build()));

        final String expectedMessage = "Não é permitido atualizar o status de uma aposta para PENDING";

        final Long idtBet = 1L;
        InvalidStatusForUpdateException invalidStatusForUpdateException =
                assertThrows(InvalidStatusForUpdateException.class, () -> betService.updateBetStatus(idtBet, betStatusRequest));
        assertEquals(expectedMessage, invalidStatusForUpdateException.getMessage());
    }

    @Test
    void updateWhenNotInStatusPendingExpectException(){
        final BetStatusRequest betStatusRequest = BetStatusRequestStub.builder().withBetStatus(BetStatusEnum.GREEN).build();

        when(betRepository.findById(anyLong())).thenReturn(Optional.of(BetStub.builder().withBetStatusEnum(BetStatusEnum.RED).build()));

        final Long idtBet = 1L;
        final String expectedMessage = "Condições inválidas para atualização do status da aposta com id: " +  idtBet + " - BetStatus=" + BetStatusEnum.RED;

        InvalidStatusForUpdateException invalidStatusForUpdateException =
                assertThrows(InvalidStatusForUpdateException.class, () -> betService.updateBetStatus(idtBet, betStatusRequest));
        assertEquals(expectedMessage, invalidStatusForUpdateException.getMessage());

    }
}
