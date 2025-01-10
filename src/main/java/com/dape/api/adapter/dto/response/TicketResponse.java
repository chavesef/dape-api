package com.dape.api.adapter.dto.response;

import com.dape.api.domain.enums.TicketStatusEnum;
import com.dape.api.domain.enums.TicketTypeEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TicketResponse {
    private TicketStatusEnum ticketStatus;
    private Long idtTicket;
    private BigDecimal numAmount;
    private TicketTypeEnum ticketType;
    private BigDecimal numFinalOdd;
    private LocalDateTime datCreated;
    private ClientResponse clientResponse;

    public TicketStatusEnum getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(TicketStatusEnum ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public Long getIdtTicket() {
        return idtTicket;
    }

    public void setIdtTicket(Long idtTicket) {
        this.idtTicket = idtTicket;
    }

    public BigDecimal getNumAmount() {
        return numAmount;
    }

    public void setNumAmount(BigDecimal numAmount) {
        this.numAmount = numAmount;
    }

    public TicketTypeEnum getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketTypeEnum ticketType) {
        this.ticketType = ticketType;
    }

    public BigDecimal getNumFinalOdd() {
        return numFinalOdd;
    }

    public void setNumFinalOdd(BigDecimal numFinalOdd) {
        this.numFinalOdd = numFinalOdd;
    }

    public ClientResponse getClientResponse() {
        return clientResponse;
    }

    public void setClientResponse(ClientResponse clientResponse) {
        this.clientResponse = clientResponse;
    }

    public LocalDateTime getDatCreated() {
        return datCreated;
    }

    public void setDatCreated(LocalDateTime datCreated) {
        this.datCreated = datCreated;
    }
}
