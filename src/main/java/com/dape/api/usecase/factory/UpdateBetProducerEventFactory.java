package com.dape.api.usecase.factory;

import com.dape.api.adapter.dto.UpdateBetProducerEvent;

public class UpdateBetProducerEventFactory {

    private UpdateBetProducerEventFactory() {}

    public static UpdateBetProducerEvent createUpdateBetProducerEvent(Long idtBet, int betStatus) {
        return new UpdateBetProducerEvent.Builder()
                .idtBet(idtBet)
                .betStatus(betStatus)
                .build();
    }
}
