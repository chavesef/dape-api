package com.dape.api.usecase.service;

import com.dape.api.adapter.dto.request.GetTicketsRequest;
import com.dape.api.adapter.dto.request.TicketRequest;
import com.dape.api.adapter.repository.BetRepository;
import com.dape.api.adapter.repository.ClientRepository;
import com.dape.api.adapter.repository.TicketBetRepository;
import com.dape.api.adapter.repository.TicketRepository;
import com.dape.api.domain.entity.Bet;
import com.dape.api.domain.entity.Client;
import com.dape.api.domain.entity.Ticket;
import com.dape.api.domain.enums.BetStatusEnum;
import com.dape.api.domain.enums.TicketStatusEnum;
import com.dape.api.domain.enums.TicketTypeEnum;
import com.dape.api.domain.exception.BetNotExistentException;
import com.dape.api.domain.exception.ClientNotExistentException;
import com.dape.api.domain.exception.InvalidStatusException;
import com.dape.api.domain.exception.UnavailableBalanceException;
import com.dape.api.usecase.factory.GetTicketRequestPredicateFactory;
import com.dape.api.usecase.factory.TicketBetFactory;
import com.dape.api.usecase.factory.TicketFactory;
import com.querydsl.core.types.dsl.BooleanExpression;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.dape.api.domain.enums.TicketStatusEnum.validateFromString;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final BetRepository betRepository;
    private final ClientRepository clientRepository;
    private final TicketBetRepository ticketBetRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(TicketService.class);

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

    public Page<Ticket> getTicketList(GetTicketsRequest getTicketsRequest) {
        final Pageable pageable = PageRequest.of(getTicketsRequest.getPage(), getTicketsRequest.getSize());

        validateDatesParameters(getTicketsRequest);

        validateFromString(getTicketsRequest.getTicketStatus());

        final BooleanExpression predicate = GetTicketRequestPredicateFactory.createTicketPredicate(getTicketsRequest);

        LOGGER.info("m=getTicketList, msg=Buscando apostas cadastradas no banco de dados");
        return ticketRepository.findAll(predicate, pageable);
    }

    public void updateTicketStatus(Long idtBet, int betStatus) {
        List<Long> ticketsWithBetToUpdate = ticketRepository.updateTicketStatusByBetId(idtBet);
        for (Long ticketId : ticketsWithBetToUpdate) {
            Ticket ticketToUpdate = ticketRepository.findById(ticketId).get();

            if(betStatus == BetStatusEnum.RED.getCodBetStatus())
                updateTicket(ticketToUpdate, TicketStatusEnum.RED);
            else {
                if(ticketToUpdate.getTicketTypeEnum() == TicketTypeEnum.SIMPLE)
                    updateTicket(ticketToUpdate, TicketStatusEnum.GREEN);
                else {
                    updateTypeMultipleTicketStatusIfNeeded(ticketId, ticketToUpdate, idtBet);
                }
            }
            ticketRepository.save(ticketToUpdate);
        }
    }

    private BigDecimal calculateFinalOdd(TicketRequest ticketRequest) {
        BigDecimal numFinalOdd = BigDecimal.ONE;

        for (Long idtBet : ticketRequest.getIdtBets()){
            Optional<Bet> optionalBet = betRepository.findById(idtBet);
            if (optionalBet.isPresent()) {
                Bet bet = optionalBet.get();
                verifyBetStatus(bet);
                numFinalOdd = numFinalOdd.multiply(bet.getNumOdd());
            } else
                throw new BetNotExistentException("Aposta com id " + idtBet + " não existe no banco de dados");
        }

        return numFinalOdd;
    }

    private void verifyBetStatus(Bet bet) {
        if (bet.getBetStatusEnum() != BetStatusEnum.PENDING)
            throw new InvalidStatusException("Não é permitido criar bilhetes com apostas resolvidas");
    }

    private Client getClient(TicketRequest ticketRequest) {
        try {
            return clientRepository.findById(ticketRequest.getIdtClient()).get();
        } catch (Exception e) {
            throw new ClientNotExistentException("O cliente com id " + ticketRequest.getIdtClient() + " não existe");
        }
    }

    private void verifyClientBalance(Client client, TicketRequest ticketRequest) {
        if(ticketRequest.getNumAmount().compareTo(client.getNumBalance()) > 0)
            throw new UnavailableBalanceException("O saldo da conta (" + client.getNumBalance() + ") é menor do que o valor a ser apostado (" + ticketRequest.getNumAmount() + ")");
    }

    private void updateOddsFromSelectedBets(TicketRequest ticketRequest) {
        for (Long idtBet : ticketRequest.getIdtBets()){
            Optional<Bet> optionalBet = betRepository.findById(idtBet);
            Bet bet = optionalBet.get();
            bet.setNumOdd(bet.getNumOdd().subtract(BigDecimal.valueOf(0.05)).compareTo(BigDecimal.ONE) <= 0 ? BigDecimal.valueOf(1.01) : bet.getNumOdd().subtract(BigDecimal.valueOf(0.05)));
            bet.setFlgSelected(1);
            bet.setDatUpdated(LocalDateTime.now());
            betRepository.save(bet);
        }
    }

    private void updateClientBalance(Client client, TicketRequest ticketRequest) {
        client.setNumBalance(client.getNumBalance().subtract(ticketRequest.getNumAmount()));
        client.setDatUpdated(LocalDateTime.now());
        clientRepository.save(client);
    }

    private void saveRelationTicketBet(Ticket ticket, TicketRequest ticketRequest) {
        for (Long idtBet : ticketRequest.getIdtBets()){
            Optional<Bet> bet = betRepository.findById(idtBet);
            ticketBetRepository.save(TicketBetFactory.createTicketBet(ticket, bet.get()));
        }
    }

    private void validateDatesParameters(GetTicketsRequest getTicketsRequest) {
        if(getTicketsRequest.getDatCreated() != null){
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                getTicketsRequest.setDatCreated(sdf.format(sdf.parse(getTicketsRequest.getDatCreated())));
            } catch (ParseException e) {
                throw new IllegalArgumentException("Formato de data inválido.");
            }
        }
        if(getTicketsRequest.getDatUpdated() != null){
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                getTicketsRequest.setDatUpdated(sdf.format(sdf.parse(getTicketsRequest.getDatUpdated())));
            } catch (ParseException e) {
                throw new IllegalArgumentException("Formato de data inválido.");
            }
        }
    }

    private void updateTypeMultipleTicketStatusIfNeeded(Long ticketId, Ticket ticketToUpdate, Long idtBet) {
        if(ticketToUpdate.getTicketStatusEnum() == TicketStatusEnum.PENDING){
            List<Long> betsByTicket = ticketBetRepository.findBetsByTicketId(ticketId);
            for (Long betId : betsByTicket) {
                if(!Objects.equals(betId, idtBet)){
                    Bet bet = betRepository.findById(betId).get();
                    if(bet.getBetStatusEnum() == BetStatusEnum.PENDING)
                        return;
                }
            }
            updateTicket(ticketToUpdate, TicketStatusEnum.GREEN);
        }
    }

    private void updateTicket(Ticket ticketToUpdate, TicketStatusEnum ticketStatusEnum) {
        LOGGER.info("m=updateTicket, msg=Atualizando status do bilhete com id:{} para: {}", ticketToUpdate.getIdtTicket(), ticketStatusEnum);
        ticketToUpdate.setTicketStatusEnum(ticketStatusEnum);
        ticketToUpdate.setDatUpdated(LocalDateTime.now());

        if (ticketStatusEnum == TicketStatusEnum.GREEN)
            updateClientBalanceAfterGreen(ticketToUpdate);
    }

    private void updateClientBalanceAfterGreen(Ticket ticketToUpdate) {
        Client client = ticketToUpdate.getClient();

        LOGGER.info("m=updateClientBalanceAfterGreen, msg=Atualizando saldo do cliente com id:{}", client.getIdtClient());
        client.setNumBalance(client.getNumBalance().add(ticketToUpdate.getNumAmount().multiply(ticketToUpdate.getNumFinalOdd())));
        client.setDatUpdated(LocalDateTime.now());
        clientRepository.save(client);
    }
}
