package com.dape.api.enums.converter;

import com.dape.api.enums.TicketTypeEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class TicketTypeEnumConverter implements AttributeConverter<TicketTypeEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TicketTypeEnum ticketTypeEnum) {
        if (ticketTypeEnum == null) {
            return null;
        }
        return ticketTypeEnum.getCodTicketType();
    }

    @Override
    public TicketTypeEnum convertToEntityAttribute(Integer ticketTypeEnum) {
        if (ticketTypeEnum == null) {
            return null;
        }
        return TicketTypeEnum.fromCode(ticketTypeEnum);
    }
}
