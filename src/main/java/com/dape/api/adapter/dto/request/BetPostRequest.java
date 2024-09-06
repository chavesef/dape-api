package com.dape.api.adapter.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;


public class BetPostRequest {

    @JsonProperty(value = "num_odd")
    private BigDecimal numOdd;
    @JsonProperty(value = "des_bet")
    private String desBet;

    public BetPostRequest(BigDecimal numOdd, String desBet) {
        this.numOdd = numOdd;
        this.desBet = desBet;
    }

    public BigDecimal getNumOdd() {
        return numOdd;
    }

    public void setNumOdd(BigDecimal numOdd) {
        this.numOdd = numOdd;
    }

    public String getDesBet() {
        return desBet;
    }

    public void setDesBet(String desBet) {
        this.desBet = desBet;
    }
}
