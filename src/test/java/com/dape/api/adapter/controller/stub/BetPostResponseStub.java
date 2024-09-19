package com.dape.api.adapter.controller.stub;

import com.dape.api.adapter.dto.response.BetPostResponse;
import com.dape.api.domain.entity.Bet;

import static com.dape.api.adapter.controller.stub.BetStub.createBet;

public class BetPostResponseStub {

    public static BetPostResponse createBetPostResponse() {
        Bet bet = createBet();

        return new BetPostResponse(
                bet.getIdtBet(),
                bet.getDesBet(),
                bet.getNumOdd(),
                bet.getDatCreated(),
                bet.getDatUpdated(),
                bet.getBetStatusEnum(),
                bet.getFlgSelected()
        );
    }
}
