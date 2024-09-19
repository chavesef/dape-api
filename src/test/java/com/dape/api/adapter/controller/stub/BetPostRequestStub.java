package com.dape.api.adapter.controller.stub;

import com.dape.api.adapter.dto.request.BetPostRequest;

import java.math.BigDecimal;

public class BetPostRequestStub{

    public static BetPostRequest createBetPostRequest() {
        return new BetPostRequest(new BigDecimal("2.12"), "Vitória do River Plate");
    }
}
