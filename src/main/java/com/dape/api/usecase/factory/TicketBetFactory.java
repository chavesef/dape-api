package com.dape.api.usecase.factory;

import com.dape.api.domain.entity.Bet;
import com.dape.api.domain.entity.Ticket;
import com.dape.api.domain.entity.TicketBet;

public class TicketBetFactory {
    private TicketBetFactory() {}

    public static TicketBet createTicketBet(Ticket ticket, Bet bet) {
        TicketBet ticketBet = new TicketBet();
        ticketBet.setTicket(ticket);
        ticketBet.setBet(bet);

        return ticketBet;
    }
}
