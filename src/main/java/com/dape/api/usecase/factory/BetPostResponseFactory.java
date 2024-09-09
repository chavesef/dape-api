package com.dape.api.usecase.factory;

import com.dape.api.adapter.dto.response.BetPostResponse;
import com.dape.api.domain.entity.Bet;

public class BetPostResponseFactory {

    private BetPostResponseFactory() {}

    public static BetPostResponse createBetPostResponse(Bet bet) {
        final BetPostResponse betPostResponse = new BetPostResponse();
        betPostResponse.setIdtBet(bet.getIdtBet());
        betPostResponse.setDesBet(bet.getDesBet());
        betPostResponse.setNumOdd(bet.getNumOdd());
        betPostResponse.setDatCreated(bet.getDatCreated().toLocalDate());
        betPostResponse.setBetStatus(bet.getBetStatusEnum());

        return betPostResponse;
    }
}
