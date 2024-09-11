package com.dape.api.usecase.factory;

import com.dape.api.adapter.dto.response.BetPostResponse;
import com.dape.api.domain.entity.Bet;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class BetPostResponseFactory {

    private BetPostResponseFactory() {}

    public static BetPostResponse createBetPostResponse(Bet bet) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        return objectMapper.convertValue(bet, BetPostResponse.class);
    }
}
