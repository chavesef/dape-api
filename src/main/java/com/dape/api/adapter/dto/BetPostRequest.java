package com.dape.api.adapter.dto;

import java.math.BigDecimal;


public class BetPostRequest {

    private BigDecimal numOdd;
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
