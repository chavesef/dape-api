package com.dape.api.usecase.service;

import com.dape.api.adapter.dto.request.BetPostRequest;
import com.dape.api.adapter.repository.BetRepository;
import com.dape.api.domain.entity.Bet;
import com.dape.api.usecase.factory.BetFactory;
import org.springframework.stereotype.Service;

@Service
public class BetService {

    private final BetRepository betRepository;

    public BetService(BetRepository betRepository) {
        this.betRepository = betRepository;
    }

    public Bet registerBet(BetPostRequest betPostRequest) {
        return betRepository.save(BetFactory.createBet(betPostRequest));
    }

}
