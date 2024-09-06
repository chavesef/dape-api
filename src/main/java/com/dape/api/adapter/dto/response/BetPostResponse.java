package com.dape.api.adapter.dto.response;

import com.dape.api.domain.enums.BetStatusEnum;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BetPostResponse {

    @JsonProperty(value = "idt_bet")
    private Long idtBet;
    @JsonProperty(value = "des_bet")
    private String desBet;
    @JsonProperty(value = "num_odd")
    private BigDecimal numOdd;
    @JsonProperty(value = "dat_created")
    private LocalDate datCreated;
    @JsonProperty(value = "bet_status")
    private BetStatusEnum betStatus;

    public BetPostResponse(Long idtBet, String desBet, BigDecimal numOdd,
                           LocalDate datCreated, BetStatusEnum betStatus) {
        this.idtBet = idtBet;
        this.desBet = desBet;
        this.numOdd = numOdd;
        this.datCreated = datCreated;
        this.betStatus = betStatus;
    }

    public Long getIdtBet() {
        return idtBet;
    }

    public void setIdtBet(Long idtBet) {
        this.idtBet = idtBet;
    }

    public String getDesBet() {
        return desBet;
    }

    public void setDesBet(String desBet) {
        this.desBet = desBet;
    }

    public BigDecimal getNumOdd() {
        return numOdd;
    }

    public void setNumOdd(BigDecimal numOdd) {
        this.numOdd = numOdd;
    }

    public LocalDate getDatCreated() {
        return datCreated;
    }

    public void setDatCreated(LocalDate datCreated) {
        this.datCreated = datCreated;
    }

    public BetStatusEnum getBetStatus() {
        return betStatus;
    }

    public void setBetStatus(BetStatusEnum betStatus) {
        this.betStatus = betStatus;
    }
}
