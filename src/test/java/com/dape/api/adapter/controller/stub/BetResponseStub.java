package com.dape.api.adapter.controller.stub;

import com.dape.api.adapter.dto.response.BetResponse;
import com.dape.api.domain.entity.Bet;

import java.math.BigDecimal;


public class BetResponseStub {

    public static BetResponse createBetResponse(String desBet, BigDecimal numOdd) {
        Bet bet = BetStub.builder().setDesBet(desBet).setNumOdd(numOdd).createBet();

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
