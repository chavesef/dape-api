package com.dape.api.usecase.service;

import com.dape.api.adapter.dto.request.BetRequest;
import com.dape.api.adapter.repository.BetRepository;
import com.dape.api.domain.entity.Bet;
import com.dape.api.domain.enums.BetStatusEnum;
import com.dape.api.domain.exception.BetNotExistentException;
import com.dape.api.domain.exception.InvalidStatusForUpdateException;
import com.dape.api.usecase.factory.BetFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BetService {

    private final BetRepository betRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(BetService.class);

    public BetService(BetRepository betRepository) {
        this.betRepository = betRepository;
    }

    public Bet registerBet(BetRequest betRequest) {
        LOGGER.info("m=registerBet, msg=Criando aposta com descrição: {} e odd: {}", betRequest.getDesBet(), betRequest.getNumOdd());
        return betRepository.save(BetFactory.createBet(betRequest));
    }

    public Bet updateBet(Long idtBet, BetRequest betRequest) {
        Bet betToUpdate = getBetById(idtBet);

        if(betIsNotSelectedNorResolved(betToUpdate)){
            betToUpdate.setDesBet(betRequest.getDesBet());
            betToUpdate.setNumOdd(betRequest.getNumOdd());
            betToUpdate.setDatUpdated(LocalDateTime.now());
            LOGGER.info("m=updateBet, msg=Atualizando aposta com descrição: {} e odd: {}", betToUpdate.getDesBet(), betToUpdate.getNumOdd());
            return betRepository.save(betToUpdate);
        } else {
            throw new InvalidStatusForUpdateException("Condições inválidas para atualização: BetStatus=" + betToUpdate.getBetStatusEnum() + ", FlgSelected=" + betToUpdate.getFlgSelected());
        }
    }

    private Bet getBetById(Long idtBet) {
        return betRepository.findById(idtBet).orElseThrow(() -> new BetNotExistentException("Aposta com id " + idtBet + " não existe no banco de dados"));
    }

    private boolean betIsNotSelectedNorResolved(Bet betToUpdate) {
        return betToUpdate.getFlgSelected() == 0 && betToUpdate.getBetStatusEnum() == BetStatusEnum.PENDING;
    }
}
