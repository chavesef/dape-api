package com.dape.api.domain.enums.converter;

import com.dape.api.domain.enums.TicketTypeEnum;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TicketTypeEnumConverterTest {
    private final TicketTypeEnumConverter ticketTypeEnumConverter = new TicketTypeEnumConverter();

    @Test
    void testConvertToDatabaseColumn() {
        final TicketTypeEnum ticketTypeEnum = TicketTypeEnum.MULTIPLE;
        final Integer converted = ticketTypeEnumConverter.convertToDatabaseColumn(ticketTypeEnum);
        assertEquals(ticketTypeEnum.getCodTicketType(), converted);
    }

    @Test
    void testConvertToEntityAttribute() {
        final Integer code = 2;
        final TicketTypeEnum ticketTypeEnum = ticketTypeEnumConverter.convertToEntityAttribute(code);
        assertEquals(TicketTypeEnum.fromCode(code), ticketTypeEnum);
    }

    @Test
    void testConvertToDatabaseColumnNull(){
        final Integer converted = ticketTypeEnumConverter.convertToDatabaseColumn(null);
        assertNull(converted);
    }

    @Test
    void testConvertToEntityAttributeNull() {
        final TicketTypeEnum ticketTypeEnum = ticketTypeEnumConverter.convertToEntityAttribute(null);
        assertNull(ticketTypeEnum);
    }

    @Test
    void testConvertToEntityAttributeException(){
        final Integer code = 4;
        assertThrows(UnsupportedOperationException.class, () -> {ticketTypeEnumConverter.convertToEntityAttribute(code);});
    }
}
