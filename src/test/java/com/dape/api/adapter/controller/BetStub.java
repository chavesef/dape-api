package com.dape.api.adapter.controller;

import com.dape.api.adapter.dto.request.BetPostRequest;
import com.dape.api.adapter.dto.response.BetPostResponse;
import com.dape.api.domain.entity.Bet;
import com.dape.api.domain.enums.BetStatusEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BetStub extends Bet{

    public static BetPostRequest createBetPostRequest() {
        return new BetPostRequest(new BigDecimal("2.12"), "Vitória do River Plate");
    }

    public static Bet createBet() {
        Bet bet = new Bet();
        bet.setIdtBet(1L);
        bet.setDesBet("Vitória do River Plate");
        bet.setNumOdd(new BigDecimal("2.12"));
        bet.setDatCreated(LocalDateTime.now());
        bet.setDatUpdated(LocalDateTime.now());
        bet.setBetStatusEnum(BetStatusEnum.PENDING);
        bet.setFlgSelected(0);

        return bet;
    }

    public static BetPostResponse createBetPostResponse() {
        Bet bet = createBet();

        return new BetPostResponse(
                bet.getIdtBet(),
                bet.getDesBet(),
                bet.getNumOdd(),
                bet.getDatCreated().toLocalDate(),
                bet.getDatUpdated().toLocalDate(),
                bet.getBetStatusEnum(),
                bet.getFlgSelected()
        );
    }
}
