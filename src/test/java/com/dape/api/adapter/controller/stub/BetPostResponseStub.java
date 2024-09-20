package com.dape.api.adapter.controller.stub;

import com.dape.api.adapter.dto.response.BetResponse;
import com.dape.api.domain.entity.Bet;

import static com.dape.api.adapter.controller.stub.BetStub.createBet;

public class BetPostResponseStub {

    public static BetResponse createBetPostResponse() {
        Bet bet = createBet();

        return new BetResponse(
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
