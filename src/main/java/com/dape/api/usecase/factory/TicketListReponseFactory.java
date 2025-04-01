package com.dape.api.usecase.factory;

import com.dape.api.adapter.dto.response.TicketListResponse;
import com.dape.api.domain.entity.Ticket;
import org.springframework.data.domain.Page;

import static com.dape.api.usecase.factory.TicketResponseFactory.createTicketResponse;

public class TicketListReponseFactory {
    private TicketListReponseFactory() {}

    public static TicketListResponse createTicketListResponse(Page<Ticket> ticketPage) {
        final TicketListResponse ticketListResponse = new TicketListResponse();
        ticketListResponse.setPage(ticketPage.getNumber());
        ticketListResponse.setSize(ticketPage.getSize());
        ticketListResponse.setTotalPages(ticketPage.getTotalPages());
        ticketListResponse.setTotalElements(ticketPage.getTotalElements());
        for (Ticket ticket : ticketPage.getContent()) {
            ticketListResponse.getTicketResponseList().add(createTicketResponse(ticket));
        }
        return ticketListResponse;
    }
}
