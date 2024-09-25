package com.dape.api.adapter.controller.stub;

import com.dape.api.adapter.dto.request.BetRequest;

import java.math.BigDecimal;

public class BetRequestStub {
    private BigDecimal numOdd;
    private String desBet;

    private BetRequestStub() {
        this.numOdd = new BigDecimal("2.12");
        this.desBet = "Vitória do River Plate";
    }

    public static BetRequestStub builder() {
        return new BetRequestStub();
    }

    public BetRequest createBetRequest() {
        return new BetRequest(this.numOdd, this.desBet);
    }

    public BetRequestStub setNumOdd(BigDecimal numOdd) {
        this.numOdd = numOdd;
        return this;
    }

    public BetRequestStub setDesBet(String desBet) {
        this.desBet = desBet;
        return this;
    }
}
