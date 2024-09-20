package com.dape.api.usecase.service;

import com.dape.api.adapter.dto.request.BetPostRequest;
import com.dape.api.adapter.repository.BetRepository;
import com.dape.api.domain.entity.Bet;
import com.dape.api.domain.enums.BetStatusEnum;
import com.dape.api.domain.exception.BetSelectedOrResolvedException;
import com.dape.api.usecase.factory.BetFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class BetService {

    private final BetRepository betRepository;

    public BetService(BetRepository betRepository) {
        this.betRepository = betRepository;
    }

    public Bet registerBet(BetPostRequest betPostRequest) {
        return betRepository.save(BetFactory.createBet(betPostRequest));
    }

    public Bet updateBet(Long idtBet, BetPostRequest betPostRequest) {
        Bet betToUpdate = getBetById(idtBet);

        if(betIsNotSelectedNorResolved(betToUpdate)){
            betToUpdate.setDesBet(betPostRequest.getDesBet());
            betToUpdate.setNumOdd(betPostRequest.getNumOdd());
            betToUpdate.setDatUpdated(LocalDateTime.now());
            return betRepository.save(betToUpdate);
        } else {
            throw new BetSelectedOrResolvedException("Aposta já selecionada ou já resolvida, não é permitido atualizá-la");
        }
    }

    public Bet getBetById(Long idtBet) {
        Optional<Bet> bet = betRepository.findById(idtBet);

        if(bet.isPresent())
            return bet.get();
        else
            throw new BetSelectedOrResolvedException("Aposta com id " + idtBet + "não existe no banco de dados");
    }

    public boolean betIsNotSelectedNorResolved(Bet betToUpdate) {
        return betToUpdate.getFlgSelected() == 0 && betToUpdate.getBetStatusEnum() == BetStatusEnum.PENDING;
    }
}
