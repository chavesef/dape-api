package com.dape.api.usecase.service;

import com.dape.api.adapter.dto.BetPostRequest;
import com.dape.api.adapter.repository.BetRepository;
import com.dape.api.domain.entity.Bet;
import com.dape.api.domain.enums.BetStatusEnum;
import com.dape.api.domain.exception.BetPostException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class BetService {

    private final BetRepository betRepository;

    public BetService(BetRepository betRepository) {
        this.betRepository = betRepository;
    }

    public Bet cadastrarAposta(BetPostRequest betPostRequest) {
        if (betPostRequest.getNumOdd().compareTo(BigDecimal.ONE) <= 0)
            throw new BetPostException("Valor da odd deve ser maior que 1");
        if (betPostRequest.getDesBet().isBlank())
                throw new BetPostException("Descrição da aposta não deve ser nula/vazia");

        Bet bet = new Bet();
        bet.setDesBet(betPostRequest.getDesBet());
        bet.setNumOdd(betPostRequest.getNumOdd());
        bet.setBetStatusEnum(BetStatusEnum.PENDING);
        bet.setDatCreated(LocalDateTime.now());
        bet.setDatUpdated(LocalDateTime.now());
        bet.setFlgSelected(0);

        return betRepository.save(bet);
    }

}
