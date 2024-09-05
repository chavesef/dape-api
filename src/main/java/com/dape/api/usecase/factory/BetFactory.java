package com.dape.api.usecase.factory;

import com.dape.api.adapter.dto.request.BetPostRequest;
import com.dape.api.domain.entity.Bet;
import com.dape.api.domain.enums.BetStatusEnum;

import java.time.LocalDateTime;

public class BetFactory {

    private BetFactory() {
    }

    public static Bet createBet(BetPostRequest betPostRequest) {

        Bet bet = new Bet();
        bet.setDesBet(betPostRequest.getDesBet());
        bet.setNumOdd(betPostRequest.getNumOdd());
        bet.setFlgSelected(0);
        bet.setDatCreated(LocalDateTime.now());
        bet.setDatUpdated(LocalDateTime.now());
        bet.setBetStatusEnum(BetStatusEnum.PENDING);

        return bet;
    }
}
