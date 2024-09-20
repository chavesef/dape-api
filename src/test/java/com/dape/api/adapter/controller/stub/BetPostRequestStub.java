package com.dape.api.adapter.controller.stub;

import com.dape.api.adapter.dto.request.BetRequest;

import java.math.BigDecimal;

public class BetPostRequestStub{

    public static BetRequest createBetPostRequest() {
        return new BetRequest(new BigDecimal("2.12"), "Vitória do River Plate");
    }

    public static BetRequest createBetPatchRequest(){
        return new BetRequest(new BigDecimal("2.43"), "Vitória do Boca Juniors");
    }
}
