package com.dape.api.usecase.service;

import com.dape.api.adapter.dto.request.TicketRequest;
import com.dape.api.adapter.repository.BetRepository;
import com.dape.api.adapter.repository.ClientRepository;
import com.dape.api.adapter.repository.TicketBetRepository;
import com.dape.api.adapter.repository.TicketRepository;
import com.dape.api.domain.entity.Bet;
import com.dape.api.domain.entity.Client;
import com.dape.api.domain.entity.Ticket;
import com.dape.api.domain.enums.BetStatusEnum;
import com.dape.api.domain.enums.TicketTypeEnum;
import com.dape.api.domain.exception.BetNotExistentException;
import com.dape.api.domain.exception.ClientNotExistentException;
import com.dape.api.domain.exception.InvalidBetStatusException;
import com.dape.api.domain.exception.UnavailableBalanceException;
import com.dape.api.stub.BetStub;
import com.dape.api.stub.ClientStub;
import com.dape.api.stub.TicketRequestStub;
import com.dape.api.stub.TicketStub;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class TicketServiceTest {
    private final TicketRepository ticketRepository  = Mockito.mock(TicketRepository.class);
    private final BetRepository betRepository  = Mockito.mock(BetRepository.class);
    private final TicketBetRepository ticketBetRepository  = Mockito.mock(TicketBetRepository.class);
    private final ClientRepository clientRepository  = Mockito.mock(ClientRepository.class);

    private final TicketService ticketService = new TicketService(ticketRepository, betRepository, clientRepository, ticketBetRepository);

    @Test
    void registerTicketSimple() {
        final Ticket expectedTicket = TicketStub.builder().build();
        final Bet bet = BetStub.builder().build();
        final Client client = ClientStub.builder().build();

        when(ticketRepository.save(any(Ticket.class))).thenReturn(expectedTicket);
        when(betRepository.findById(anyLong())).thenReturn(Optional.ofNullable(bet));
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));

        final Ticket actualTicket = ticketService.registerTicket(TicketRequestStub.builder().build());

        verify(ticketRepository).save(any(Ticket.class));
        verify(betRepository, times(3)).findById(anyLong());
        verify(clientRepository).findById(anyLong());

        assertThat(actualTicket).usingRecursiveComparison().ignoringFieldsOfTypes(LocalDateTime.class).isEqualTo(expectedTicket);
    }

    @Test
    void registerTicketMultiple() {
        final Ticket expectedTicket = TicketStub.builder().withTicketType(TicketTypeEnum.MULTIPLE).build();
        final Bet firstBet = BetStub.builder().build();
        final Bet secondBet = BetStub.builder().withIdtBet(2L).withBetStatusEnum(BetStatusEnum.PENDING).withNumOdd(BigDecimal.valueOf(1.03)).build();
        final Client client = ClientStub.builder().build();

        when(ticketRepository.save(any(Ticket.class))).thenReturn(expectedTicket);
        when(betRepository.findById(1L)).thenReturn(Optional.ofNullable(firstBet));
        when(betRepository.findById(2L)).thenReturn(Optional.ofNullable(secondBet));
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));

        final Ticket actualTicket = ticketService.registerTicket(TicketRequestStub.builder().withIdtBets(List.of(1L, 2L)).build());

        verify(ticketRepository).save(any(Ticket.class));
        verify(betRepository, times(6)).findById(anyLong());
        verify(clientRepository).findById(anyLong());

        assertThat(actualTicket).usingRecursiveComparison().ignoringFieldsOfTypes(LocalDateTime.class).isEqualTo(expectedTicket);
    }

    @Test
    void selectingBetInexistentExpectException() {
        when(betRepository.findById(anyLong())).thenReturn(Optional.empty());

        final TicketRequest ticketRequest = TicketRequestStub.builder().build();

        final BetNotExistentException betNotExistentException =
                assertThrows(BetNotExistentException.class, () -> ticketService.registerTicket(ticketRequest));

        final String expectedMessage = "Aposta com id " + ticketRequest.getIdtBets().get(0) + " não existe no banco de dados";
        assertEquals(expectedMessage, betNotExistentException.getMessage());
    }

    @Test
    void selectBetResolvedExpectException() {
        final Bet bet = BetStub.builder().withBetStatusEnum(BetStatusEnum.GREEN).build();

        when(betRepository.findById(anyLong())).thenReturn(Optional.ofNullable(bet));

        final TicketRequest ticketRequest = TicketRequestStub.builder().build();
        final InvalidBetStatusException invalidBetStatusException =
                assertThrows(InvalidBetStatusException.class, () -> ticketService.registerTicket(ticketRequest));

        final String expectedMessage = "Não é permitido criar bilhetes com apostas resolvidas";
        assertEquals(expectedMessage, invalidBetStatusException.getMessage());
    }

    @Test
    void clientInexistentExpectException() {
        final Bet bet = BetStub.builder().build();

        when(betRepository.findById(anyLong())).thenReturn(Optional.ofNullable(bet));
        when(clientRepository.findById(anyLong())).thenReturn(Optional.empty());

        final TicketRequest ticketRequest = TicketRequestStub.builder().build();

        final ClientNotExistentException clientNotExistentException =
                assertThrows(ClientNotExistentException.class, () -> ticketService.registerTicket(ticketRequest));

        final String expectedMessage = "O cliente com id " + ticketRequest.getIdtClient() + " não existe";
        assertEquals(expectedMessage, clientNotExistentException.getMessage());
    }

    @Test
    void insufficientBalanceExpectException() {
        final Bet bet = BetStub.builder().build();
        final Client client = ClientStub.builder().build();

        when(betRepository.findById(anyLong())).thenReturn(Optional.ofNullable(bet));
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));

        final TicketRequest ticketRequest = TicketRequestStub.builder().withNumAmount(BigDecimal.valueOf(2000)).build();

        final UnavailableBalanceException unavailableBalanceException =
                assertThrows(UnavailableBalanceException.class, () -> ticketService.registerTicket(ticketRequest));

        final String expectedMessage =
                "O saldo da conta (" + client.getNumBalance() + ") é menor do que o valor a ser apostado (" + ticketRequest.getNumAmount() + ")";
        assertEquals(expectedMessage, unavailableBalanceException.getMessage());
    }
}
