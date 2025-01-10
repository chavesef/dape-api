package com.dape.api.stub;

import com.dape.api.adapter.dto.response.ClientResponse;
import com.dape.api.adapter.dto.response.TicketResponse;
import com.dape.api.domain.enums.TicketStatusEnum;
import com.dape.api.domain.enums.TicketTypeEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TicketResponseStub {
    private TicketStatusEnum ticketStatus;
    private Long idtTicket;
    private BigDecimal numAmount;
    private TicketTypeEnum ticketType;
    private BigDecimal numFinalOdd;
    private LocalDateTime datCreated;
    private ClientResponse clientResponse;

    private TicketResponseStub() {
        this.ticketStatus = TicketStatusEnum.PENDING;
        this.idtTicket = 1L;
        this.numAmount = BigDecimal.valueOf(10.00);
        this.ticketType = TicketTypeEnum.SIMPLE;
        this.numFinalOdd = BigDecimal.valueOf(3.1);
        this.datCreated = LocalDateTime.now();
        this.clientResponse = ClientResponseStub.builder().build();
    }

    public static TicketResponseStub builder() {
        return new TicketResponseStub();
    }

    public TicketResponse build() {
        TicketResponse ticketResponse = new TicketResponse();
        ticketResponse.setTicketStatus(this.ticketStatus);
        ticketResponse.setIdtTicket(this.idtTicket);
        ticketResponse.setNumAmount(this.numAmount);
        ticketResponse.setTicketType(this.ticketType);
        ticketResponse.setNumFinalOdd(this.numFinalOdd);
        ticketResponse.setDatCreated(this.datCreated);
        ticketResponse.setClientResponse(this.clientResponse);

        return ticketResponse;
    }

    public TicketResponseStub withTicketStatus(TicketStatusEnum ticketStatus) {
        this.ticketStatus = ticketStatus;
        return this;
    }

    public TicketResponseStub withIdtTicket(Long idtTicket) {
        this.idtTicket = idtTicket;
        return this;
    }

    public TicketResponseStub withNumAmount(BigDecimal numAmount) {
        this.numAmount = numAmount;
        return this;
    }

    public TicketResponseStub withTicketType(TicketTypeEnum ticketType) {
        this.ticketType = ticketType;
        return this;
    }

    public TicketResponseStub withNumFinalOdd(BigDecimal numFinalOdd) {
        this.numFinalOdd = numFinalOdd;
        return this;
    }

    public TicketResponseStub withDatCreated(LocalDateTime datCreated) {
        this.datCreated = datCreated;
        return this;
    }

    public TicketResponseStub withClientResponse(ClientResponse clientResponse) {
        this.clientResponse = clientResponse;
        return this;
    }
}
