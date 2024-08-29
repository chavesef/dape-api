package com.dape.api.enums.converter;

import com.dape.api.enums.BetStatusEnum;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BetStatusEnumConverterTest {
    private final BetStatusEnumConverter betStatusEnumConverter = new BetStatusEnumConverter();

    @Test
    void testConvertToDatabaseColumn() {
        final BetStatusEnum betStatusEnum = BetStatusEnum.PENDING;
        final Integer converted = betStatusEnumConverter
                .convertToDatabaseColumn(betStatusEnum);
        assertEquals(betStatusEnum.getCodBetStatus(), converted);
    }

    @Test
    void testConvertToEntityAttribute() {
        final Integer code = 1;
        final BetStatusEnum betStatusEnum = betStatusEnumConverter.convertToEntityAttribute(code);
        assertEquals(BetStatusEnum.fromCode(1), betStatusEnum);
    }

    @Test
    void testConvertToDatabaseColumnNull(){
        final Integer converted = betStatusEnumConverter.convertToDatabaseColumn(null);
        assertNull(converted);
    }

    @Test
    void testConvertToEntityAttributeNull() {
        final BetStatusEnum betStatusEnum = betStatusEnumConverter.convertToEntityAttribute(null);
        assertNull(betStatusEnum);
    }

    @Test
    void testConvertToEntityAttributeException(){
        final Integer code = 4;
        assertThrows(UnsupportedOperationException.class, () -> {betStatusEnumConverter.convertToEntityAttribute(code);});
    }
}
