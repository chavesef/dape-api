package com.dape.api.adapter.dto.request;

import com.dape.api.domain.enums.BetStatusEnum;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BetStatusRequest {

    @NotBlank
    private BetStatusEnum betStatus;

    public BetStatusEnum getBetStatus() {
        return betStatus;
    }

    public void setBetStatus(BetStatusEnum betStatus) {
        this.betStatus = betStatus;
    }
}
