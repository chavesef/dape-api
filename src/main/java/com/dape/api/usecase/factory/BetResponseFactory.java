package com.dape.api.usecase.factory;

import com.dape.api.adapter.dto.response.BetResponse;
import com.dape.api.domain.entity.Bet;

public class BetResponseFactory {

    private BetResponseFactory() {}

    public static BetResponse createBetResponse(Bet bet) {
        final BetResponse betResponse = new BetResponse();
        betResponse.setIdtBet(bet.getIdtBet());
        betResponse.setDesBet(bet.getDesBet());
        betResponse.setNumOdd(bet.getNumOdd());
        betResponse.setDatCreated(bet.getDatCreated());
        betResponse.setBetStatusEnum(bet.getBetStatusEnum());
        betResponse.setFlgSelected(bet.getFlgSelected());
        betResponse.setDatUpdated(bet.getDatUpdated());

        return betResponse;
    }
}
