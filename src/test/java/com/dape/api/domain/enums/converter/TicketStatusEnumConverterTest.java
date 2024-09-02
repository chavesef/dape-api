package com.dape.api.domain.enums.converter;

import com.dape.api.domain.enums.TicketStatusEnum;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TicketStatusEnumConverterTest {
    private final TicketStatusEnumConverter ticketStatusEnumConverter = new TicketStatusEnumConverter();

    @Test
    void testConvertToDatabaseColumn(){
        final TicketStatusEnum ticketStatusEnum = TicketStatusEnum.GREEN;
        final Integer converted = ticketStatusEnumConverter.convertToDatabaseColumn(ticketStatusEnum);
        assertEquals(ticketStatusEnum.getCodTicketStatus(), converted);
    }

    @Test
    void testConvertToEntityAttribute(){
        final Integer code = 2;
        final TicketStatusEnum ticketStatusEnum = ticketStatusEnumConverter.convertToEntityAttribute(code);
        assertEquals(TicketStatusEnum.fromCode(code), ticketStatusEnum);
    }

    @Test
    void testConvertToDatabaseColumnNull(){
        final Integer converted = ticketStatusEnumConverter.convertToDatabaseColumn(null);
        assertNull(converted);
    }

    @Test
    void testConvertToEntityAttributeNull(){
        final TicketStatusEnum ticketStatusEnum = ticketStatusEnumConverter.convertToEntityAttribute(null);
        assertNull(ticketStatusEnum);
    }

    @Test
    void testConvertToEntityAttributeException(){
        final Integer code = 4;
        assertThrows(UnsupportedOperationException.class, () -> {ticketStatusEnumConverter.convertToEntityAttribute(code);});
    }
}
