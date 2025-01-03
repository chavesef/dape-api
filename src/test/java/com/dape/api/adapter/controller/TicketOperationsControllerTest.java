package com.dape.api.adapter.controller;

import com.dape.api.adapter.dto.request.TicketRequest;
import com.dape.api.adapter.dto.response.TicketResponse;
import com.dape.api.domain.enums.TicketTypeEnum;
import com.dape.api.stub.TicketRequestStub;
import com.dape.api.stub.TicketResponseStub;
import com.dape.api.stub.TicketStub;
import com.dape.api.usecase.service.TicketService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TicketOperationsControllerTest {
    private final TicketService ticketService = Mockito.mock(TicketService.class);
    private final TicketOperationsController ticketOperationsController = new TicketOperationsController(ticketService);

    @Test
    void registerTicketSimple() {
        when(ticketService.registerTicket(any(TicketRequest.class))).thenReturn(TicketStub.builder().build());

        final TicketRequest ticketRequest = TicketRequestStub.builder().build();
        final ResponseEntity<TicketResponse> actualTicket = ticketOperationsController.registerTicket(ticketRequest);
        final ResponseEntity<TicketResponse> expectedTicket =
                ResponseEntity.status(HttpStatusCode.valueOf(201)).body(TicketResponseStub.builder().build());

        verify(ticketService).registerTicket(ticketRequest);
        assertThat(actualTicket).usingRecursiveComparison().ignoringFieldsOfTypes(LocalDateTime.class).isEqualTo(expectedTicket);
    }

    @Test
    void registerTicketMultiple(){
        when(ticketService.registerTicket(any(TicketRequest.class))).thenReturn(TicketStub.builder().withTicketType(TicketTypeEnum.MULTIPLE).build());

        final TicketRequest ticketRequest = TicketRequestStub.builder().withIdtBets(List.of(1L, 2L)).build();
        final ResponseEntity<TicketResponse> actualTicket = ticketOperationsController.registerTicket(ticketRequest);
        final ResponseEntity<TicketResponse> expectedTicket =
                ResponseEntity.status(HttpStatusCode.valueOf(201)).body(TicketResponseStub.builder().withTicketType(TicketTypeEnum.MULTIPLE).build());

        verify(ticketService).registerTicket(ticketRequest);
        assertThat(actualTicket).usingRecursiveComparison().ignoringFieldsOfTypes(LocalDateTime.class).isEqualTo(expectedTicket);
    }
}
