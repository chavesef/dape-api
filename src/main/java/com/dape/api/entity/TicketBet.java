package com.dape.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "TICKET_BET")
public class TicketBet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDT_TICKET_BET")
    private Long idtTicketBet;

    @ManyToOne
    @JoinColumn(name = "IDT_TICKET", nullable = false)
    private Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "IDT_BET", nullable = false)
    private Bet bet;

    public Long getIdtTicketBet() {
        return idtTicketBet;
    }

    public void setIdtTicketBet(Long idtTicketBet) {
        this.idtTicketBet = idtTicketBet;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Bet getBet() {
        return bet;
    }

    public void setBet(Bet bet) {
        this.bet = bet;
    }
}
