package com.dape.api.usecase.service;

import com.dape.api.adapter.dto.request.BetRequest;
import com.dape.api.adapter.dto.request.BetStatusRequest;
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
import java.util.List;

@Service
public class BetService {

    public static final int IS_SELECTED = 1;
    private final BetRepository betRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(BetService.class);
    private static final List<BetStatusEnum> VALID_STATUSES = List.of(BetStatusEnum.GREEN, BetStatusEnum.RED);

    public BetService(BetRepository betRepository) {
        this.betRepository = betRepository;
    }

    public Bet registerBet(BetRequest betRequest) {
        LOGGER.info("m=registerBet, msg=Criando aposta com descrição: {} e odd: {}", betRequest.getDesBet(), betRequest.getNumOdd());
        return betRepository.save(BetFactory.createBet(betRequest));
    }

    public Bet updateBet(Long idtBet, BetRequest betRequest) {
        Bet betToUpdate = getBetById(idtBet);

        validateBetToUpdate(betToUpdate);

        updateBetFields(betToUpdate, betRequest);

        LOGGER.info("m=updateBet, msg=Atualizando aposta com descrição: {} e odd: {}", betToUpdate.getDesBet(), betToUpdate.getNumOdd());
        return betRepository.save(betToUpdate);
    }

    public Bet updateBetStatus(Long idtBet, BetStatusRequest betStatus) {
        final Bet betToUpdate = getBetById(idtBet);

        validateBetToUpdateStatus(betToUpdate);

        updateBetStatusAndDatUpdatedFields(betToUpdate, betStatus);

        LOGGER.info("m=updateBetStatus, msg=Atualizando status da aposta para: {}", betToUpdate.getBetStatusEnum());
        return betRepository.save(betToUpdate);
    }

    private Bet getBetById(Long idtBet) {
        return betRepository.findById(idtBet).orElseThrow(() -> new BetNotExistentException("Aposta com id " + idtBet + " não existe no banco de dados"));
    }

    private void validateBetToUpdate(Bet betToUpdate){
        if(betIsNotUpdatable(betToUpdate))
            throw new InvalidStatusForUpdateException("Condições inválidas para atualização: BetStatus=" + betToUpdate.getBetStatusEnum() + ", FlgSelected=" + betToUpdate.getFlgSelected());
    }

    private void updateBetFields(Bet betToUpdate, BetRequest betRequest){
        betToUpdate.setDesBet(betRequest.getDesBet());
        betToUpdate.setNumOdd(betRequest.getNumOdd());
        betToUpdate.setDatUpdated(LocalDateTime.now());
    }

    private boolean betIsNotUpdatable(Bet betToUpdate) {
        return betToUpdate.getFlgSelected() == IS_SELECTED || betToUpdate.getBetStatusEnum() != BetStatusEnum.PENDING;
    }

    private void validateBetToUpdateStatus(Bet betToUpdate) {
        if(betToUpdate.getBetStatusEnum() != BetStatusEnum.PENDING)
            throw new InvalidStatusForUpdateException("Condições inválidas para atualização do status: BetStatus=" + betToUpdate.getBetStatusEnum());
    }

    private void updateBetStatusAndDatUpdatedFields(Bet betToUpdate, BetStatusRequest betStatus) {
        if(VALID_STATUSES.contains(betStatus.getBetStatus())) {
            betToUpdate.setBetStatusEnum(betStatus.getBetStatus());
            betToUpdate.setDatUpdated(LocalDateTime.now());
        } else
            throw new InvalidStatusForUpdateException("Aposta já se encontra com o status PENDING");
    }
}
