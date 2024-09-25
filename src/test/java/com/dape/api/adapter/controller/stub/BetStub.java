package com.dape.api.adapter.controller.stub;

import com.dape.api.domain.entity.Bet;
import com.dape.api.domain.enums.BetStatusEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BetStub{
    private final Long idtBet;
    private String desBet;
    private BigDecimal numOdd;
    private final LocalDateTime datCreated;
    private final LocalDateTime datUpdated;
    private final BetStatusEnum betStatusEnum;
    private final Integer flgSelected;

    private BetStub(){
        this.idtBet = 1L;
        this.desBet = "Vitória do River Plate";
        this.numOdd = new BigDecimal("2.12");
        this.datCreated = LocalDateTime.now();
        this.datUpdated = LocalDateTime.now();
        this.betStatusEnum = BetStatusEnum.PENDING;
        this.flgSelected = 0;
    }

    public static BetStub builder(){
        return new BetStub();
    }

    public Bet createBet(){
        Bet bet = new Bet();
        bet.setIdtBet(this.idtBet);
        bet.setDesBet(this.desBet);
        bet.setNumOdd(this.numOdd);
        bet.setDatCreated(this.datCreated);
        bet.setDatUpdated(this.datUpdated);
        bet.setBetStatusEnum(this.betStatusEnum);
        bet.setFlgSelected(this.flgSelected);

        return bet;
    }

    public BetStub setNumOdd(BigDecimal numOdd){
        this.numOdd = numOdd;
        return this;
    }

    public BetStub setDesBet(String desBet){
        this.desBet = desBet;
        return this;
    }
}
