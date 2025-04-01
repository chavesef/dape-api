package com.dape.api.usecase.factory;

import com.dape.api.adapter.dto.request.GetTicketsRequest;
import com.dape.api.domain.entity.QTicket;
import com.dape.api.domain.enums.TicketStatusEnum;
import com.dape.api.domain.enums.TicketTypeEnum;
import com.querydsl.core.types.dsl.BooleanExpression;
import java.time.LocalDate;
import java.time.LocalTime;

public class GetTicketRequestPredicateFactory {

    private GetTicketRequestPredicateFactory() {}

    public static BooleanExpression createTicketPredicate(GetTicketsRequest getTicketsRequest) {
        BooleanExpression predicate = QTicket.ticket.isNotNull();

        if(getTicketsRequest.getTicketStatus() != null)
            predicate = predicate.and(QTicket.ticket.ticketStatusEnum.eq(TicketStatusEnum.valueOf(getTicketsRequest.getTicketStatus())));

        if (getTicketsRequest.getTicketType() != null)
            predicate = predicate.and(QTicket.ticket.ticketTypeEnum.eq(TicketTypeEnum.valueOf(getTicketsRequest.getTicketType())));

        if(getTicketsRequest.getIdtClient() != null)
            predicate = predicate.and(QTicket.ticket.client.idtClient.eq(getTicketsRequest.getIdtClient()));

        if(getTicketsRequest.getDatCreated() != null)
            predicate = predicate.and(QTicket.ticket.datCreated.between(LocalDate.parse(getTicketsRequest.getDatCreated()).atStartOfDay(), LocalDate.parse(getTicketsRequest.getDatCreated()).atStartOfDay().plusDays(1)));

        if(getTicketsRequest.getDatUpdated() != null)
            predicate = predicate.and(QTicket.ticket.datUpdated.between(LocalDate.parse(getTicketsRequest.getDatUpdated()).atStartOfDay(), LocalDate.parse(getTicketsRequest.getDatUpdated()).atTime(LocalTime.MAX)));

        return predicate;
    }


}
