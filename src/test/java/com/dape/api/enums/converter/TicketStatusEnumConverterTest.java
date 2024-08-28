package com.dape.api.enums.converter;

import com.dape.api.enums.TicketStatusEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TicketStatusEnumConverterTest {
    private final TicketStatusEnumConverter ticketStatusEnumConverter = new TicketStatusEnumConverter();

    @Test
    void testConvertToDatabaseColumn(){
        final TicketStatusEnum ticketStatusEnum = TicketStatusEnum.GREEN;
        final Integer converted = ticketStatusEnumConverter.convertToDatabaseColumn(ticketStatusEnum);
        Assertions.assertEquals(ticketStatusEnum.getCodTicketStatus(), converted);
    }

    @Test
    void testConvertToEntityAttribute(){
        final Integer code = 2;
        final TicketStatusEnum ticketStatusEnum = ticketStatusEnumConverter.convertToEntityAttribute(code);
        Assertions.assertEquals(TicketStatusEnum.fromCode(code), ticketStatusEnum);
    }

    @Test
    void testConvertToDatabaseColumnNull(){
        final Integer converted = ticketStatusEnumConverter.convertToDatabaseColumn(null);
        Assertions.assertNull(converted);
    }

    @Test
    void testConvertToEntityAttributeNull(){
        final TicketStatusEnum ticketStatusEnum = ticketStatusEnumConverter.convertToEntityAttribute(null);
        Assertions.assertNull(ticketStatusEnum);
    }

    @Test
    void testConvertToEntityAttributeException(){
        final Integer code = 4;
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {ticketStatusEnumConverter.convertToEntityAttribute(code);});
    }
}
