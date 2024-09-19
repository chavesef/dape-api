package com.dape.api.adapter.controller.stub;

import com.dape.api.domain.entity.Bet;
import com.dape.api.domain.enums.BetStatusEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BetStub{

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
}
