package com.dape.api.stub;

import com.dape.api.domain.entity.Client;
import com.dape.api.domain.entity.Ticket;
import com.dape.api.domain.enums.TicketStatusEnum;
import com.dape.api.domain.enums.TicketTypeEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TicketStub {
    private Long idtTicket;
    private BigDecimal numAmount;
    private LocalDateTime datCreated;
    private LocalDateTime datUpdated;
    private TicketStatusEnum ticketStatus;
    private TicketTypeEnum ticketType;
    private BigDecimal finalOdd;
    private Client client;

    private TicketStub(){
        this.idtTicket = 1L;
        this.numAmount = BigDecimal.valueOf(10.00);
        this.datCreated = LocalDateTime.now();
        this.datUpdated = LocalDateTime.now();
        this.ticketStatus = TicketStatusEnum.PENDING;
        this.ticketType = TicketTypeEnum.SIMPLE;
        this.finalOdd = BigDecimal.valueOf(3.10);
        this.client = ClientStub.builder().build();
    }

    public static TicketStub builder(){
        return new TicketStub();
    }

    public Ticket build(){
        Ticket ticket = new Ticket();
        ticket.setIdtTicket(idtTicket);
        ticket.setNumAmount(numAmount);
        ticket.setDatCreated(datCreated);
        ticket.setDatUpdated(datUpdated);
        ticket.setTicketTypeEnum(ticketType);
        ticket.setTicketStatusEnum(ticketStatus);
        ticket.setNumFinalOdd(finalOdd);
        ticket.setClient(client);

        return ticket;
    }

    public TicketStub withIdtTicket(Long idtTicket){
        this.idtTicket = idtTicket;
        return this;
    }

    public TicketStub withNumAmount(BigDecimal numAmount){
        this.numAmount = numAmount;
        return this;
    }

    public TicketStub withDatCreated(LocalDateTime datCreated){
        this.datCreated = datCreated;
        return this;
    }

    public TicketStub withDatUpdated(LocalDateTime datUpdated){
        this.datUpdated = datUpdated;
        return this;
    }

    public TicketStub withTicketStatus(TicketStatusEnum ticketStatus){
        this.ticketStatus = ticketStatus;
        return this;
    }

    public TicketStub withTicketType(TicketTypeEnum ticketType){
        this.ticketType = ticketType;
        return this;
    }

    public TicketStub withFinalOdd(BigDecimal finalOdd){
        this.finalOdd = finalOdd;
        return this;
    }

    public TicketStub withClient(Client client){
        this.client = client;
        return this;
    }
}
