package com.dape.api.usecase.factory;

import com.dape.api.adapter.dto.request.GetBetsRequest;
import com.dape.api.domain.entity.QBet;
import com.dape.api.domain.enums.BetStatusEnum;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.time.LocalDate;
import java.time.LocalTime;

public class GetBetRequestPredicateFactory {

    private GetBetRequestPredicateFactory() {}

    public static BooleanExpression createBetPredicate(GetBetsRequest getBetsRequest) {
        BooleanExpression predicate = QBet.bet.isNotNull();

        if(getBetsRequest.getBetStatus() != null)
            predicate = predicate.and(QBet.bet.betStatusEnum.eq(BetStatusEnum.valueOf(getBetsRequest.getBetStatus())));

        if(getBetsRequest.getDatCreated() != null)
            predicate = predicate.and(QBet.bet.datCreated.between(LocalDate.parse(getBetsRequest.getDatCreated()).atStartOfDay(), LocalDate.parse(getBetsRequest.getDatCreated()).atStartOfDay().plusDays(1)));

        if(getBetsRequest.getDatUpdated() != null)
            predicate = predicate.and(QBet.bet.datUpdated.between(LocalDate.parse(getBetsRequest.getDatUpdated()).atStartOfDay(), LocalDate.parse(getBetsRequest.getDatUpdated()).atTime(LocalTime.MAX)));

        return predicate;
    }


}
