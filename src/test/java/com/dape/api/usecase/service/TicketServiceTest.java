package com.dape.api.usecase.service;

import com.dape.api.adapter.dto.request.GetTicketsRequest;
import com.dape.api.adapter.dto.request.TicketRequest;
import com.dape.api.adapter.repository.BetRepository;
import com.dape.api.adapter.repository.ClientRepository;
import com.dape.api.adapter.repository.TicketBetRepository;
import com.dape.api.adapter.repository.TicketRepository;
import com.dape.api.domain.entity.Bet;
import com.dape.api.domain.entity.Client;
import com.dape.api.domain.entity.Ticket;
import com.dape.api.domain.enums.BetStatusEnum;
import com.dape.api.domain.enums.TicketStatusEnum;
import com.dape.api.domain.enums.TicketTypeEnum;
import com.dape.api.domain.exception.BetNotExistentException;
import com.dape.api.domain.exception.ClientNotExistentException;
import com.dape.api.domain.exception.InvalidStatusException;
import com.dape.api.domain.exception.UnavailableBalanceException;
import com.dape.api.stub.BetStub;
import com.dape.api.stub.ClientStub;
import com.dape.api.stub.GetTicketsRequestStub;
import com.dape.api.stub.TicketRequestStub;
import com.dape.api.stub.TicketStub;
import com.querydsl.core.types.dsl.BooleanExpression;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TicketServiceTest {
    private final TicketRepository ticketRepository = Mockito.mock(TicketRepository.class);
    private final BetRepository betRepository = Mockito.mock(BetRepository.class);
    private final TicketBetRepository ticketBetRepository = Mockito.mock(TicketBetRepository.class);
    private final ClientRepository clientRepository = Mockito.mock(ClientRepository.class);

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
        final InvalidStatusException invalidStatusException =
                assertThrows(InvalidStatusException.class, () -> ticketService.registerTicket(ticketRequest));

        final String expectedMessage = "Não é permitido criar bilhetes com apostas resolvidas";
        assertEquals(expectedMessage, invalidStatusException.getMessage());
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

    @Test
    void updateTicketStatusToRedWhenBetIsUpdatedToRed() {
        final Ticket expectedTicket = TicketStub.builder().withTicketStatus(TicketStatusEnum.RED).build();

        when(ticketRepository.getTicketByBetId(anyLong())).thenReturn(List.of(1L));
        when(ticketRepository.findById(anyLong())).thenReturn(Optional.of(TicketStub.builder().build()));

        ticketService.updateTicketStatus(1L, BetStatusEnum.RED.getCodBetStatus());

        ArgumentCaptor<Ticket> ticketCaptor = ArgumentCaptor.forClass(Ticket.class);
        verify(ticketRepository, times(1)).save(ticketCaptor.capture());

        Ticket actualTicket = ticketCaptor.getValue();
        assertThat(actualTicket).usingRecursiveComparison().ignoringFieldsOfTypes(LocalDateTime.class).isEqualTo(expectedTicket);
    }

    @Test
    void updateSimpleTicketStatusToGreenWhenBetIsUpdatedToGreen() {
        final Client client = ClientStub.builder().withNumBalance(BigDecimal.valueOf(1031)).build();
        final Ticket expectedTicket = TicketStub.builder().withTicketStatus(TicketStatusEnum.GREEN).withClient(client).build();

        when(ticketRepository.getTicketByBetId(anyLong())).thenReturn(List.of(1L));
        when(ticketRepository.findById(anyLong())).thenReturn(Optional.of(TicketStub.builder().build()));

        ticketService.updateTicketStatus(1L, BetStatusEnum.GREEN.getCodBetStatus());

        ArgumentCaptor<Ticket> ticketCaptor = ArgumentCaptor.forClass(Ticket.class);
        verify(ticketRepository, times(1)).save(ticketCaptor.capture());

        Ticket actualTicket = ticketCaptor.getValue();
        assertThat(actualTicket).usingRecursiveComparison().withComparatorForType(BigDecimal::compareTo, BigDecimal.class)
                .ignoringFieldsOfTypes(LocalDateTime.class).isEqualTo(expectedTicket);
    }

    @Test
    void updateMultipleTicketStatusToGreenWhenBetIsUpdatedToGreen() {
        final Client client = ClientStub.builder().withNumBalance(BigDecimal.valueOf(1031)).build();
        final Ticket expectedTicket = TicketStub.builder().withTicketType(TicketTypeEnum.MULTIPLE).
                withTicketStatus(TicketStatusEnum.GREEN).withClient(client).build();

        when(ticketRepository.getTicketByBetId(anyLong())).thenReturn(List.of(1L));
        when(ticketRepository.findById(anyLong())).thenReturn(Optional.of(TicketStub.builder().withTicketType(TicketTypeEnum.MULTIPLE).build()));
        when(ticketBetRepository.findBetsByTicketId(anyLong())).thenReturn(List.of(1L, 2L));
        when(betRepository.findById(anyLong())).thenReturn(Optional.of(BetStub.builder().withIdtBet(2L).withBetStatusEnum(BetStatusEnum.GREEN).build()));

        ticketService.updateTicketStatus(1L, BetStatusEnum.GREEN.getCodBetStatus());

        ArgumentCaptor<Ticket> ticketCaptor = ArgumentCaptor.forClass(Ticket.class);
        verify(ticketRepository, times(1)).save(ticketCaptor.capture());

        Ticket actualTicket = ticketCaptor.getValue();
        assertThat(actualTicket).usingRecursiveComparison().withComparatorForType(BigDecimal::compareTo, BigDecimal.class)
                .ignoringFieldsOfTypes(LocalDateTime.class).isEqualTo(expectedTicket);
    }

    @Test
    void shouldNotUpdateTicketResolvedWhenUpdatingBetStatus() {
        when(ticketRepository.getTicketByBetId(anyLong())).thenReturn(List.of(1L));
        when(ticketRepository.findById(anyLong())).thenReturn(Optional.of(TicketStub.builder().withTicketType(TicketTypeEnum.MULTIPLE).withTicketStatus(TicketStatusEnum.GREEN).build()));

        ticketService.updateTicketStatus(1L, BetStatusEnum.RED.getCodBetStatus());

        verify(ticketRepository, never()).save(any(Ticket.class));
    }

    @Test
    void shouldNotUpdateMultipleTicketWhenUpdatingBetStatusToGreenButThereIsPendingBet() {
        final Ticket expectedTicket = TicketStub.builder().withTicketType(TicketTypeEnum.MULTIPLE).build();

        when(ticketRepository.getTicketByBetId(anyLong())).thenReturn(List.of(1L));
        when(ticketRepository.findById(anyLong())).thenReturn(Optional.of(TicketStub.builder().withTicketType(TicketTypeEnum.MULTIPLE).build()));
        when(ticketBetRepository.findBetsByTicketId(anyLong())).thenReturn(List.of(1L, 2L));
        when(betRepository.findById(anyLong())).thenReturn(Optional.of(BetStub.builder().withIdtBet(2L).withBetStatusEnum(BetStatusEnum.PENDING).build()));

        ticketService.updateTicketStatus(1L, BetStatusEnum.GREEN.getCodBetStatus());

        ArgumentCaptor<Ticket> ticketCaptor = ArgumentCaptor.forClass(Ticket.class);
        verify(ticketRepository, times(1)).save(ticketCaptor.capture());

        Ticket actualTicket = ticketCaptor.getValue();
        assertThat(actualTicket).usingRecursiveComparison().withComparatorForType(BigDecimal::compareTo, BigDecimal.class)
                .ignoringFieldsOfTypes(LocalDateTime.class).isEqualTo(expectedTicket);
    }

    @Test
    void getTickets() {
        final Page<Ticket> expectedTicketsPage = createPage();

        when(ticketRepository.findAll(any(BooleanExpression.class), any(Pageable.class))).thenReturn(expectedTicketsPage);

        final Page<Ticket> actualTicketsPage = ticketService.getTicketList(GetTicketsRequestStub.builder().build());

        assertThat(actualTicketsPage).usingRecursiveComparison().ignoringFieldsOfTypes(LocalDateTime.class).isEqualTo(expectedTicketsPage);
    }

    @Test
    void getTicketsWithNullTicketStatus() {
        final Page<Ticket> expectedTicketsPage = createPage();

        when(ticketRepository.findAll(any(BooleanExpression.class), any(Pageable.class))).thenReturn(expectedTicketsPage);

        final Page<Ticket> actualTicketsPage = ticketService.getTicketList(GetTicketsRequestStub.builder().withTicketStatus(null).build());

        assertThat(actualTicketsPage).usingRecursiveComparison().ignoringFieldsOfTypes(LocalDateTime.class).isEqualTo(expectedTicketsPage);
    }

    @Test
    void getTicketsWithInvalidStatusExpectException() {
        when(ticketRepository.findAll(any(BooleanExpression.class), any(Pageable.class))).thenReturn(createPage());


        final String ticketStatus = "VENCIDA";
        final String expectedMessage = "Status não existente: " + ticketStatus;
        final GetTicketsRequest getTicketsRequest = GetTicketsRequestStub.builder().withTicketStatus(ticketStatus).build();

        final InvalidStatusException invalidStatusException =
                assertThrows(InvalidStatusException.class, () -> ticketService.getTicketList(getTicketsRequest));
        assertEquals(expectedMessage, invalidStatusException.getMessage());
    }

    @Test
    void getTicketsWithInvalidDatCreatedExpectException(){
        when(ticketRepository.findAll(any(BooleanExpression.class), any(Pageable.class))).thenReturn(createPage());

        final String expectedMessage = "Formato de data inválido.";
        final GetTicketsRequest getTicketsRequest = GetTicketsRequestStub.builder().withDatCreated("A2024-10-21").build();

        final IllegalArgumentException illegalArgumentException =
                assertThrows(IllegalArgumentException.class, () -> ticketService.getTicketList(getTicketsRequest));
        assertEquals(expectedMessage, illegalArgumentException.getMessage());
    }

    @Test
    void getTicketsWithInvalidDatUpdatedExpectException(){
        when(ticketRepository.findAll(any(BooleanExpression.class), any(Pageable.class))).thenReturn(createPage());

        final String expectedMessage = "Formato de data inválido.";
        final GetTicketsRequest getTicketsRequest = GetTicketsRequestStub.builder().withDatUpdated("A2024-10-21").build();

        final IllegalArgumentException illegalArgumentException =
                assertThrows(IllegalArgumentException.class, () -> ticketService.getTicketList(getTicketsRequest));
        assertEquals(expectedMessage, illegalArgumentException.getMessage());
    }

    private Page<Ticket> createPage() {
        final Pageable pageable = PageRequest.of(0, 10);
        return new PageImpl<>(Collections.singletonList(TicketStub.builder().build()), pageable, 1);
    }
}
