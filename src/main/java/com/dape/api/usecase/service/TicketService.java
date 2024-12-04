package com.dape.api.usecase.service;

import com.dape.api.adapter.dto.request.TicketRequest;
import com.dape.api.adapter.repository.BetRepository;
import com.dape.api.adapter.repository.ClientRepository;
import com.dape.api.adapter.repository.TicketBetRepository;
import com.dape.api.adapter.repository.TicketRepository;
import com.dape.api.domain.entity.Bet;
import com.dape.api.domain.entity.Client;
import com.dape.api.domain.entity.Ticket;
import com.dape.api.domain.exception.BetNotExistentException;
import com.dape.api.domain.exception.ClientNotExistentException;
import com.dape.api.domain.exception.UnavailableBalanceException;
import com.dape.api.usecase.factory.TicketBetFactory;
import com.dape.api.usecase.factory.TicketFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final BetRepository betRepository;
    private final ClientRepository clientRepository;
    private final TicketBetRepository ticketBetRepository;

    public TicketService(TicketRepository ticketRepository, BetRepository betRepository, ClientRepository clientRepository, TicketBetRepository ticketBetRepository) {
        this.ticketRepository = ticketRepository;
        this.betRepository = betRepository;
        this.clientRepository = clientRepository;
        this.ticketBetRepository = ticketBetRepository;
    }

    public Ticket registerTicket(TicketRequest ticketRequest) {
        final BigDecimal numFinalOdd = calculateFinalOdd(ticketRequest);
        final Client client = getClient(ticketRequest);

        verifyClientBalance(client, ticketRequest);

        updateOddsFromSelectedBets(ticketRequest);
        updateClientBalance(client, ticketRequest);

        Ticket ticket = ticketRepository.save(TicketFactory.createTicket(ticketRequest, numFinalOdd, client));

        saveRelationTicketBet(ticket, ticketRequest);

        return ticket;
    }

    private void saveRelationTicketBet(Ticket ticket, TicketRequest ticketRequest) {
        for (Long idtBet : ticketRequest.getIdtBets()){
            Optional<Bet> bet = betRepository.findById(idtBet);
            ticketBetRepository.save(TicketBetFactory.createTicketBet(ticket, bet.get()));
        }
    }

    private void verifyClientBalance(Client client, TicketRequest ticketRequest) {
        if(ticketRequest.getNumAmount().compareTo(client.getNumBalance()) > 0)
            throw new UnavailableBalanceException("O saldo da conta (" + client.getNumBalance() + ") é menor do que o valor a ser apostado (" + ticketRequest.getNumAmount() + ")");
    }

    private Client getClient(TicketRequest ticketRequest) {
        try {
            return clientRepository.findById(ticketRequest.getIdtClient()).get();
        } catch (Exception e) {
            throw new ClientNotExistentException("O cliente com id " + ticketRequest.getIdtClient() + " não existe");
        }
    }

    private BigDecimal calculateFinalOdd(TicketRequest ticketRequest) {
        BigDecimal numFinalOdd = BigDecimal.ONE;

        for (Long idtBet : ticketRequest.getIdtBets()){
            Optional<Bet> optionalBet = betRepository.findById(idtBet);
            if (optionalBet.isPresent()) {
                Bet bet = optionalBet.get();
                numFinalOdd = numFinalOdd.multiply(bet.getNumOdd());
            } else
                throw new BetNotExistentException("Aposta com id " + idtBet + " não existe no banco de dados");
        }

        return numFinalOdd;
    }

    private void updateOddsFromSelectedBets(TicketRequest ticketRequest) {
        for (Long idtBet : ticketRequest.getIdtBets()){
            Optional<Bet> optionalBet = betRepository.findById(idtBet);
            if (optionalBet.isPresent()) {
                Bet bet = optionalBet.get();
                bet.setNumOdd(bet.getNumOdd().subtract(BigDecimal.valueOf(0.05)).compareTo(BigDecimal.ONE) <= 0 ? BigDecimal.valueOf(1.01) : bet.getNumOdd().subtract(BigDecimal.valueOf(0.05)));
                bet.setFlgSelected(1);
                bet.setDatUpdated(LocalDateTime.now());
                betRepository.save(bet);
            }
        }
    }

    private void updateClientBalance(Client client, TicketRequest ticketRequest) {
        client.setNumBalance(client.getNumBalance().subtract(ticketRequest.getNumAmount()));
        client.setDatUpdated(LocalDateTime.now());
        clientRepository.save(client);
    }

}
