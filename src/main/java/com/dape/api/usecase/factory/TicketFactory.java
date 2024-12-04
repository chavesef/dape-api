package com.dape.api.usecase.factory;

import com.dape.api.adapter.dto.request.TicketRequest;
import com.dape.api.domain.entity.Client;
import com.dape.api.domain.entity.Ticket;
import com.dape.api.domain.enums.TicketStatusEnum;
import com.dape.api.domain.enums.TicketTypeEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TicketFactory {
    private TicketFactory() {}

    public static Ticket createTicket(TicketRequest ticketRequest, BigDecimal numFinalOdd, Client client) {
        Ticket ticket = new Ticket();
        ticket.setNumAmmount(ticketRequest.getNumAmount());
        ticket.setTicketStatusEnum(TicketStatusEnum.PENDING);
        ticket.setDatCreated(LocalDateTime.now());
        ticket.setDatUpdated(LocalDateTime.now());
        ticket.setNumFinalOdd(numFinalOdd);
        if(ticketRequest.getIdtBets().size() > 1)
            ticket.setTicketTypeEnum(TicketTypeEnum.MULTIPLE);
        else
            ticket.setTicketTypeEnum(TicketTypeEnum.SIMPLE);
        ticket.setClient(client);

        return ticket;
    }
}
