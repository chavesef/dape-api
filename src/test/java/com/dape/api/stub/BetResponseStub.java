package com.dape.api.stub;

import com.dape.api.adapter.dto.response.BetResponse;
import com.dape.api.domain.entity.Bet;
import com.dape.api.domain.enums.BetStatusEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public class BetResponseStub {
    private Long idtBet;
    private String desBet;
    private BigDecimal numOdd;
    private LocalDateTime datCreated;
    private LocalDateTime datUpdated;
    private BetStatusEnum betStatusEnum;
    private Integer flgSelected;

    private BetResponseStub(){
        Bet bet = BetStub.builder().build();
        this.idtBet = bet.getIdtBet();
        this.desBet = bet.getDesBet();
        this.numOdd = bet.getNumOdd();
        this.datCreated = bet.getDatCreated();
        this.datUpdated = bet.getDatUpdated();
        this.betStatusEnum = bet.getBetStatusEnum();
        this.flgSelected = bet.getFlgSelected();
    }

    public static BetResponseStub builder(){
        return new BetResponseStub();
    }

    public BetResponse build(){
        BetResponse response = new BetResponse();
        response.setIdtBet(this.idtBet);
        response.setDesBet(this.desBet);
        response.setNumOdd(this.numOdd);
        response.setDatCreated(this.datCreated);
        response.setDatUpdated(this.datUpdated);
        response.setBetStatusEnum(this.betStatusEnum);
        response.setFlgSelected(this.flgSelected);

        return response;
    }

    public BetResponseStub withIdtBet(Long idtBet){
        this.idtBet = idtBet;
        return this;
    }

    public BetResponseStub withDesBet(String desBet){
        this.desBet = desBet;
        return this;
    }

    public BetResponseStub withNumOdd(BigDecimal numOdd){
        this.numOdd = numOdd;
        return this;
    }

    public BetResponseStub withDatCreated(LocalDateTime datCreated){
        this.datCreated = datCreated;
        return this;
    }

    public BetResponseStub withDatUpdated(LocalDateTime datUpdated){
        this.datUpdated = datUpdated;
        return this;
    }

    public BetResponseStub withBetStatusEnum(BetStatusEnum betStatusEnum){
        this.betStatusEnum = betStatusEnum;
        return this;
    }

    public BetResponseStub withFlgSelected(Integer flgSelected){
        this.flgSelected = flgSelected;
        return this;
    }
}
