package com.dape.api.stub;

import com.dape.api.domain.entity.Bet;
import com.dape.api.domain.enums.BetStatusEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BetStub{
    private Long idtBet;
    private String desBet;
    private BigDecimal numOdd;
    private LocalDateTime datCreated;
    private LocalDateTime datUpdated;
    private BetStatusEnum betStatusEnum;
    private Integer flgSelected;

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

    public Bet build(){
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

    public BetStub withNumOdd(BigDecimal numOdd){
        this.numOdd = numOdd;
        return this;
    }

    public BetStub withDesBet(String desBet){
        this.desBet = desBet;
        return this;
    }

    public BetStub withIdtBet(Long idtBet){
        this.idtBet = idtBet;
        return this;
    }

    public BetStub withDatCreated(LocalDateTime datCreated){
        this.datCreated = datCreated;
        return this;
    }

    public BetStub withDatUpdated(LocalDateTime datUpdated){
        this.datUpdated = datUpdated;
        return this;
    }

    public BetStub withBetStatusEnum(BetStatusEnum betStatusEnum){
        this.betStatusEnum = betStatusEnum;
        return this;
    }

    public BetStub withFlgSelected(Integer flgSelected){
        this.flgSelected = flgSelected;
        return this;
    }
}
