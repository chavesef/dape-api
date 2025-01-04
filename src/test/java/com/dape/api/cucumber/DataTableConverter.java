package com.dape.api.cucumber;

import com.dape.api.domain.entity.Bet;
import com.dape.api.domain.entity.Client;
import com.dape.api.domain.entity.Ticket;
import com.dape.api.domain.entity.TicketBet;
import com.dape.api.domain.enums.BetStatusEnum;
import com.dape.api.domain.enums.TicketStatusEnum;
import com.dape.api.domain.enums.TicketTypeEnum;
import io.cucumber.java.DataTableType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public class DataTableConverter {

    @DataTableType
    public Bet betDataTableType(Map<String, String> map){
        Bet bet = new Bet();
        bet.setDesBet(map.get("desBet"));
        bet.setIdtBet(Long.parseLong(map.get("idt_bet")));
        bet.setNumOdd(new BigDecimal(map.get("num_odd")));
        bet.setFlgSelected(Integer.parseInt(map.get("flg_selected")));
        bet.setDatCreated(LocalDate.parse(map.get("dat_created")).atStartOfDay());
        bet.setDatUpdated(LocalDate.parse(map.get("dat_updated")).atStartOfDay());
        bet.setDesBet(map.get("des_bet"));
        bet.setBetStatusEnum(BetStatusEnum.valueOf(map.get("bet_status")));

        return bet;
    }

    @DataTableType
    public Ticket ticketDataTableType(Map<String, String> map){
        Ticket ticket = new Ticket();
        ticket.setNumFinalOdd(new BigDecimal(map.get("num_final_odd")));
        ticket.setTicketTypeEnum(TicketTypeEnum.valueOf(map.get("ticket_type")));
        ticket.setTicketStatusEnum(TicketStatusEnum.valueOf(map.get("cod_ticket_status")));
        ticket.setIdtTicket(Long.parseLong(map.get("idt_ticket")));
        ticket.setDatCreated(LocalDate.parse(map.get("dat_created")).atStartOfDay());
        ticket.setDatUpdated(LocalDate.parse(map.get("dat_updated")).atStartOfDay());
        ticket.setNumAmount(new BigDecimal(map.get("num_ammount")));
        Client client = new Client();
        client.setIdtClient(Long.parseLong(map.get("idt_client")));
        ticket.setClient(client);

        return ticket;
    }

    @DataTableType
    public Client clientDataTableType(Map<String, String> map){
        Client client = new Client();
        client.setIdtClient(Long.parseLong(map.get("idt_client")));
        client.setNumBalance(new BigDecimal(map.get("num_balance")));
        client.setNamClient(map.get("nam_client"));
        client.setDatCreated(LocalDate.parse(map.get("dat_created")).atStartOfDay());
        client.setDatUpdated(LocalDate.parse(map.get("dat_updated")).atStartOfDay());
        client.setDesEmail(map.get("des_email"));
        client.setNumDocument(map.get("num_document"));
        client.setNumPassword(map.get("num_password"));

        return client;
    }

    @DataTableType
    public TicketBet ticketBetDataTableType(Map<String, String> map){
        TicketBet ticketBet = new TicketBet();
        Bet bet = new Bet();
        bet.setIdtBet(Long.parseLong(map.get("idt_bet")));
        ticketBet.setBet(bet);
        Ticket ticket = new Ticket();
        ticket.setIdtTicket(Long.parseLong(map.get("idt_ticket")));
        ticketBet.setTicket(ticket);
        ticketBet.setIdtTicketBet(Long.parseLong(map.get("idt_ticket_bet")));

        return ticketBet;
    }
}
