package com.dape.api.stub;

import com.dape.api.adapter.dto.request.BetStatusRequest;

public class BetStatusRequestStub {
    private String betStatus;

    private BetStatusRequestStub(){
        this.betStatus = "PENDING";
    }

    public static BetStatusRequestStub builder() {
        return new BetStatusRequestStub();
    }

    public BetStatusRequest build(){
        BetStatusRequest betStatusRequest = new BetStatusRequest();
        betStatusRequest.setBetStatus(this.betStatus);

        return betStatusRequest;
    }

    public BetStatusRequestStub withBetStatus(String betStatus) {
        this.betStatus = betStatus;
        return this;
    }
}
