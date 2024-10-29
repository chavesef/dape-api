package com.dape.api.usecase.service;

import com.dape.api.adapter.dto.request.BetRequest;
import com.dape.api.adapter.dto.request.BetStatusRequest;
import com.dape.api.adapter.dto.request.GetBetsRequest;
import com.dape.api.adapter.repository.BetRepository;
import com.dape.api.domain.entity.Bet;
import com.dape.api.domain.enums.BetStatusEnum;
import com.dape.api.domain.exception.BetNotExistentException;
import com.dape.api.domain.exception.InvalidStatusForUpdateException;
import com.dape.api.usecase.factory.BetFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

import static com.dape.api.domain.enums.BetStatusEnum.fromRequest;
import static com.dape.api.domain.enums.BetStatusEnum.fromString;

@Service
public class BetService {

    public static final int IS_SELECTED = 1;
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

        validateBetToUpdate(betToUpdate);

        updateBetFields(betToUpdate, betRequest);

        LOGGER.info("m=updateBet, msg=Atualizando aposta com id:{}, descrição: {} e odd: {}", idtBet, betToUpdate.getDesBet(), betToUpdate.getNumOdd());
        return betRepository.save(betToUpdate);
    }

    public Bet updateBetStatus(Long idtBet, BetStatusRequest betStatusRequest) {
        final BetStatusEnum betStatus = fromRequest(betStatusRequest);

        validateBetStatusRequest(betStatus);

        final Bet betToUpdate = getBetById(idtBet);

        validateBetToUpdateStatus(betToUpdate);

        updateBetStatusAndDatUpdatedFields(betToUpdate, betStatus);

        LOGGER.info("m=updateBetStatus, msg=Atualizando status da aposta com id:{} para: {}", idtBet, betToUpdate.getBetStatusEnum());
        return betRepository.save(betToUpdate);
    }

    public Page<Bet> getBetList(GetBetsRequest getBetsRequest) {
        final Pageable pageable = PageRequest.of(getBetsRequest.getPage(), getBetsRequest.getSize());

        final Integer codBetStatus = fromString(getBetsRequest.getBetStatus());

        validateDatesParameters(getBetsRequest);

        LOGGER.info("m=getBetList, msg=Buscando apostas cadastradas no banco de dados");
        return betRepository.findAllWithFilters(pageable, codBetStatus, getBetsRequest.getDatCreated(), getBetsRequest.getDatUpdated());
    }

    private Bet getBetById(Long idtBet) {
        return betRepository.findById(idtBet).orElseThrow(() -> new BetNotExistentException("Aposta com id " + idtBet + " não existe no banco de dados"));
    }

    private void validateBetToUpdate(Bet betToUpdate){
        if(betIsNotUpdatable(betToUpdate))
            throw new InvalidStatusForUpdateException("Condições inválidas para atualização da aposta com id: " + betToUpdate.getIdtBet() + " - BetStatus=" + betToUpdate.getBetStatusEnum() + ", FlgSelected=" + betToUpdate.getFlgSelected());
    }

    private boolean betIsNotUpdatable(Bet betToUpdate) {
        return betToUpdate.getFlgSelected() == IS_SELECTED || betToUpdate.getBetStatusEnum() != BetStatusEnum.PENDING;
    }

    private void updateBetFields(Bet betToUpdate, BetRequest betRequest){
        betToUpdate.setDesBet(betRequest.getDesBet());
        betToUpdate.setNumOdd(betRequest.getNumOdd());
        betToUpdate.setDatUpdated(LocalDateTime.now());
    }

    private void validateBetStatusRequest(BetStatusEnum betStatus) {
        if(betStatus == BetStatusEnum.PENDING)
            throw new InvalidStatusForUpdateException("Não é permitido atualizar o status de uma aposta para PENDING");
    }

    private void validateBetToUpdateStatus(Bet betToUpdate) {
        if(betToUpdate.getBetStatusEnum() != BetStatusEnum.PENDING)
            throw new InvalidStatusForUpdateException("Condições inválidas para atualização do status da aposta com id: " + betToUpdate.getIdtBet() + " - BetStatus=" + betToUpdate.getBetStatusEnum());
    }

    private void updateBetStatusAndDatUpdatedFields(Bet betToUpdate, BetStatusEnum betStatus) {
            betToUpdate.setBetStatusEnum(betStatus);
            betToUpdate.setDatUpdated(LocalDateTime.now());
    }

    private void validateDatesParameters(GetBetsRequest getBetsRequest) {
        if(getBetsRequest.getDatCreated() != null){
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                getBetsRequest.setDatCreated(sdf.format(sdf.parse(getBetsRequest.getDatCreated())));
            } catch (ParseException e) {
                throw new IllegalArgumentException("Formato de data inválido.");
            }
        }
        if(getBetsRequest.getDatUpdated() != null){
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                getBetsRequest.setDatUpdated(sdf.format(sdf.parse(getBetsRequest.getDatUpdated())));
            } catch (ParseException e) {
                throw new IllegalArgumentException("Formato de data inválido.");
            }
        }
    }
}
