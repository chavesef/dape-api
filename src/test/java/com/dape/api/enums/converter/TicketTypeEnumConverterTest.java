package com.dape.api.enums.converter;

import com.dape.api.enums.TicketTypeEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TicketTypeEnumConverterTest {
    private final TicketTypeEnumConverter ticketTypeEnumConverter = new TicketTypeEnumConverter();

    @Test
    void testConvertToDatabaseColumn() {
        final TicketTypeEnum ticketTypeEnum = TicketTypeEnum.MULTIPLE;
        final Integer converted = ticketTypeEnumConverter.convertToDatabaseColumn(ticketTypeEnum);
        Assertions.assertEquals(ticketTypeEnum.getCodTicketType(), converted);
    }

    @Test
    void testConvertToEntityAttribute() {
        final Integer code = 2;
        final TicketTypeEnum ticketTypeEnum = ticketTypeEnumConverter.convertToEntityAttribute(code);
        Assertions.assertEquals(TicketTypeEnum.fromCode(code), ticketTypeEnum);
    }

    @Test
    void testConvertToDatabaseColumnNull(){
        final Integer converted = ticketTypeEnumConverter.convertToDatabaseColumn(null);
        Assertions.assertNull(converted);
    }

    @Test
    void testConvertToEntityAttributeNull() {
        final TicketTypeEnum ticketTypeEnum = ticketTypeEnumConverter.convertToEntityAttribute(null);
        Assertions.assertNull(ticketTypeEnum);
    }

    @Test
    void testConvertToEntityAttributeException(){
        final Integer code = 4;
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {ticketTypeEnumConverter.convertToEntityAttribute(code);});
    }
}
