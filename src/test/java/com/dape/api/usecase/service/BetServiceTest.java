package com.dape.api.usecase.service;

import com.dape.api.adapter.dto.request.BetStatusRequest;
import com.dape.api.adapter.dto.request.GetBetsRequest;
import com.dape.api.domain.exception.GetBetsInvalidStatusException;
import com.dape.api.stub.BetRequestStub;
import com.dape.api.stub.BetStatusRequestStub;
import com.dape.api.stub.BetStub;
import com.dape.api.adapter.dto.request.BetRequest;
import com.dape.api.adapter.repository.BetRepository;
import com.dape.api.domain.entity.Bet;
import com.dape.api.domain.enums.BetStatusEnum;
import com.dape.api.domain.exception.BetNotExistentException;
import com.dape.api.domain.exception.InvalidStatusForUpdateException;
import com.dape.api.stub.GetBetsRequestStub;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
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
        final Bet actualBet = betService.updateBetStatus(idtBet, BetStatusRequestStub.builder().withBetStatus("GREEN").build());

        assertThat(actualBet).usingRecursiveComparison().ignoringFieldsOfTypes(LocalDateTime.class).isEqualTo(expectedBet);
    }

    @Test
    void updateBetStatusToRed(){
        final Bet expectedBet = BetStub.builder().withBetStatusEnum(BetStatusEnum.RED).build();

        when(betRepository.save(any(Bet.class))).thenReturn(expectedBet);
        when(betRepository.findById(anyLong())).thenReturn(Optional.of(BetStub.builder().build()));

        final Long idtBet = 1L;
        final Bet actualBet = betService.updateBetStatus(idtBet, BetStatusRequestStub.builder().withBetStatus("RED").build());

        assertThat(actualBet).usingRecursiveComparison().ignoringFieldsOfTypes(LocalDateTime.class).isEqualTo(expectedBet);
    }

    @Test
    void updateBetStatusInexistentBetExpectException(){
        final BetStatusRequest betStatusRequest = BetStatusRequestStub.builder().withBetStatus("GREEN").build();

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
        final BetStatusRequest betStatusRequest = BetStatusRequestStub.builder().withBetStatus("GREEN").build();

        when(betRepository.findById(anyLong())).thenReturn(Optional.of(BetStub.builder().withBetStatusEnum(BetStatusEnum.RED).build()));

        final Long idtBet = 1L;
        final String expectedMessage = "Condições inválidas para atualização do status da aposta com id: " +  idtBet + " - BetStatus=" + BetStatusEnum.RED;

        InvalidStatusForUpdateException invalidStatusForUpdateException =
                assertThrows(InvalidStatusForUpdateException.class, () -> betService.updateBetStatus(idtBet, betStatusRequest));
        assertEquals(expectedMessage, invalidStatusForUpdateException.getMessage());

    }

    @Test
    void getBets(){
        final Pageable pageable = PageRequest.of(0, 10);
        final Page<Bet> expectedBetPage = new PageImpl<>(Collections.singletonList(BetStub.builder().build()), pageable, 1);

        when(betRepository.findAllWithFilters(any(), any(), any(), any())).thenReturn(expectedBetPage);

        final Page<Bet> actualBetPage = betService.getBetList(GetBetsRequestStub.builder().build());

        assertThat(actualBetPage).usingRecursiveComparison().ignoringFieldsOfTypes(LocalDateTime.class).isEqualTo(expectedBetPage);
    }

    @Test
    void getBetsWithNullBetStatus(){
        final Pageable pageable = PageRequest.of(0, 10);
        final Page<Bet> expectedBetPage = new PageImpl<>(Collections.singletonList(BetStub.builder().build()), pageable, 1);

        when(betRepository.findAllWithFilters(any(), any(), any(), any())).thenReturn(expectedBetPage);

        final Page<Bet> actualBetPage = betService.getBetList(GetBetsRequestStub.builder().withBetStatus(null).build());

        assertThat(actualBetPage).usingRecursiveComparison().ignoringFieldsOfTypes(LocalDateTime.class).isEqualTo(expectedBetPage);
    }

    @Test
    void getBetsWithInvalidStatusExpectException(){
        when(betRepository.findAllWithFilters(any(), any(), any(), any()))
                .thenReturn(new PageImpl<>(Collections.singletonList(BetStub.builder().build()), PageRequest.of(0, 10), 1));

        final String betStatus = "VENCIDA";
        final String expectedMessage = "Status não existente: " + betStatus;
        final GetBetsRequest getBetsRequest = GetBetsRequestStub.builder().withBetStatus(betStatus).build();

        GetBetsInvalidStatusException getBetsInvalidStatusException =
                assertThrows(GetBetsInvalidStatusException.class, () -> betService.getBetList(getBetsRequest));
        assertEquals(expectedMessage, getBetsInvalidStatusException.getMessage());
    }

    @Test
    void getBetsWithInvalidDatCreatedExpectException(){
        when(betRepository.findAllWithFilters(any(), any(), any(), any()))
                .thenReturn(new PageImpl<>(Collections.singletonList(BetStub.builder().build()), PageRequest.of(0, 10), 1));

        final String expectedMessage = "Formato de data inválido.";
        final GetBetsRequest getBetsRequest = GetBetsRequestStub.builder().withDatCreated("A2024-10-21").build();

        IllegalArgumentException illegalArgumentException =
                assertThrows(IllegalArgumentException.class, () -> betService.getBetList(getBetsRequest));
        assertEquals(expectedMessage, illegalArgumentException.getMessage());
    }

    @Test
    void getBetsWithInvalidDatUpdatedExpectException(){
        when(betRepository.findAllWithFilters(any(), any(), any(), any()))
                .thenReturn(new PageImpl<>(Collections.singletonList(BetStub.builder().build()), PageRequest.of(0, 10), 1));

        final String expectedMessage = "Formato de data inválido.";
        final GetBetsRequest getBetsRequest = GetBetsRequestStub.builder().withDatUpdated("A2024-10-21").build();

        IllegalArgumentException illegalArgumentException =
                assertThrows(IllegalArgumentException.class, () -> betService.getBetList(getBetsRequest));
        assertEquals(expectedMessage, illegalArgumentException.getMessage());
    }
}
