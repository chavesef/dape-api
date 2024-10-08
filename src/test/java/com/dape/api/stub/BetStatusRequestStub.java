package com.dape.api.stub;

import com.dape.api.adapter.dto.request.BetStatusRequest;
import com.dape.api.domain.enums.BetStatusEnum;

public class BetStatusRequestStub {
    private BetStatusEnum betStatus;

    private BetStatusRequestStub(){
        this.betStatus = BetStatusEnum.PENDING;
    }

    public static BetStatusRequestStub builder() {
        return new BetStatusRequestStub();
    }

    public BetStatusRequest build(){
        BetStatusRequest betStatusRequest = new BetStatusRequest();
        betStatusRequest.setBetStatus(this.betStatus);

        return betStatusRequest;
    }

    public BetStatusRequestStub withBetStatus(BetStatusEnum betStatus) {
        this.betStatus = betStatus;
        return this;
    }
}
