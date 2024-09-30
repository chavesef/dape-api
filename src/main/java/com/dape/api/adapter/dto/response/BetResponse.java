package com.dape.api.adapter.dto.response;

import com.dape.api.domain.enums.BetStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BetResponse {

    private Long idtBet;
    private String desBet;
    private BigDecimal numOdd;
    private LocalDateTime datCreated;
    private LocalDateTime datUpdated;
    private BetStatusEnum betStatusEnum;
    private Integer flgSelected;

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

    public LocalDateTime getDatCreated() {
        return datCreated;
    }

    public void setDatCreated(LocalDateTime datCreated) {
        this.datCreated = datCreated;
    }

    public BetStatusEnum getBetStatusEnum() {
        return betStatusEnum;
    }

    public void setBetStatusEnum(BetStatusEnum betStatusEnum) {
        this.betStatusEnum = betStatusEnum;
    }

    public LocalDateTime getDatUpdated() {
        return datUpdated;
    }

    public void setDatUpdated(LocalDateTime datUpdated) {
        this.datUpdated = datUpdated;
    }

    public Integer getFlgSelected() {
        return flgSelected;
    }

    public void setFlgSelected(Integer flgSelected) {
        this.flgSelected = flgSelected;
    }
}
