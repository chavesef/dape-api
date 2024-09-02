package com.dape.api.domain.enums.converter;

import com.dape.api.domain.enums.TicketStatusEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class TicketStatusEnumConverter implements AttributeConverter<TicketStatusEnum, Integer> {
    @Override
    public Integer convertToDatabaseColumn(TicketStatusEnum ticketStatusEnum) {
        if (ticketStatusEnum == null) {
            return null;
        }
        return ticketStatusEnum.getCodTicketStatus();
    }

    @Override
    public TicketStatusEnum convertToEntityAttribute(Integer ticketStatusEnum) {
        if (ticketStatusEnum == null) {
            return null;
        }
        return TicketStatusEnum.fromCode(ticketStatusEnum);
    }
}
