package com.dape.api.usecase.factory;

import com.dape.api.adapter.dto.response.ClientResponse;
import com.dape.api.adapter.dto.response.TicketResponse;
import com.dape.api.domain.entity.Ticket;

public class TicketResponseFactory {

    private TicketResponseFactory() {
    }

    public static TicketResponse createTicketResponse(Ticket ticket) {

        final ClientResponse clientResponse = new ClientResponse();
        clientResponse.setIdtClient(ticket.getClient().getIdtClient());
        clientResponse.setNamClient(ticket.getClient().getNamClient());
        clientResponse.setNumBalance(ticket.getClient().getNumBalance());
        clientResponse.setNumDocument(ticket.getClient().getNumDocument());

        final TicketResponse ticketResponse = new TicketResponse();
        ticketResponse.setIdtTicket(ticket.getIdtTicket());
        ticketResponse.setTicketStatus(ticket.getTicketStatusEnum());
        ticketResponse.setTicketType(ticket.getTicketTypeEnum());
        ticketResponse.setNumAmount(ticket.getNumAmmount());
        ticketResponse.setDatCreated(ticket.getDatCreated());
        ticketResponse.setNumFinalOdd(ticket.getNumFinalOdd());
        ticketResponse.setClientResponse(clientResponse);

        return ticketResponse;
    }
}
