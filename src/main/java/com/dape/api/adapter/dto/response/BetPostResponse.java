package com.dape.api.adapter.dto.response;

import com.dape.api.domain.enums.BetStatusEnum;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;
import java.time.LocalDate;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BetPostResponse {

    private Long idtBet;
    private String desBet;
    private BigDecimal numOdd;
    private LocalDate datCreated;
    private LocalDate datUpdated;
    private BetStatusEnum betStatus;
    private Integer flgSelected;

    public BetPostResponse(Long idtBet, String desBet, BigDecimal numOdd,
                           LocalDate datCreated, LocalDate datUpdated,
                           BetStatusEnum betStatus, Integer flgSelected) {
        this.idtBet = idtBet;
        this.desBet = desBet;
        this.numOdd = numOdd;
        this.datCreated = datCreated;
        this.datUpdated = datUpdated;
        this.betStatus = betStatus;
        this.flgSelected = flgSelected;
    }

    public BetPostResponse() {}

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

    public LocalDate getDatUpdated() {
        return datUpdated;
    }

    public void setDatUpdated(LocalDate datUpdated) {
        this.datUpdated = datUpdated;
    }

    public Integer getFlgSelected() {
        return flgSelected;
    }

    public void setFlgSelected(Integer flgSelected) {
        this.flgSelected = flgSelected;
    }
}
