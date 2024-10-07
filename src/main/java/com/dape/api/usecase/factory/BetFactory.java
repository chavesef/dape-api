package com.dape.api.usecase.factory;

import com.dape.api.adapter.dto.request.BetRequest;
import com.dape.api.domain.entity.Bet;
import com.dape.api.domain.enums.BetStatusEnum;

import java.time.LocalDateTime;

public class BetFactory {

    private BetFactory() {
    }

    public static Bet createBet(BetRequest betRequest) {

        final Bet bet = new Bet();
        bet.setDesBet(betRequest.getDesBet());
        bet.setNumOdd(betRequest.getNumOdd());
        bet.setFlgSelected(0);
        bet.setDatCreated(LocalDateTime.now());
        bet.setDatUpdated(LocalDateTime.now());
        bet.setBetStatusEnum(BetStatusEnum.PENDING);

        return bet;
    }
}
