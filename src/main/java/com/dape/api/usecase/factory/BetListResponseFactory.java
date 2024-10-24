package com.dape.api.usecase.factory;

import com.dape.api.adapter.dto.response.BetListResponse;
import com.dape.api.domain.entity.Bet;
import org.springframework.data.domain.Page;

import static com.dape.api.usecase.factory.BetResponseFactory.createBetResponse;

public class BetListResponseFactory {

    private BetListResponseFactory() {}

    public static BetListResponse createBetListResponse(Page<Bet> betPage) {
        final BetListResponse betListResponse = new BetListResponse();
        betListResponse.setPage(betPage.getNumber());
        betListResponse.setSize(betPage.getSize());
        betListResponse.setTotalPages(betPage.getTotalPages());
        betListResponse.setTotalElements(betPage.getTotalElements());
        for (Bet bet : betPage.getContent()) {
            betListResponse.getBetResponseList().add(createBetResponse(bet));
        }
        return betListResponse;
    }

}
