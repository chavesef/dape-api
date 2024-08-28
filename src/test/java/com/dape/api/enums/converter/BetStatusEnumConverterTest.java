package com.dape.api.enums.converter;

import com.dape.api.enums.BetStatusEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BetStatusEnumConverterTest {
    private final BetStatusEnumConverter betStatusEnumConverter = new BetStatusEnumConverter();

    @Test
    void testConvertToDatabaseColumn() {
        final BetStatusEnum betStatusEnum = BetStatusEnum.PENDING;
        final Integer converted = betStatusEnumConverter
                .convertToDatabaseColumn(betStatusEnum);
        Assertions.assertEquals(betStatusEnum.getCodBetStatus(), converted);
    }

    @Test
    void testConvertToEntityAttribute() {
        final Integer code = 1;
        final BetStatusEnum betStatusEnum = betStatusEnumConverter.convertToEntityAttribute(code);
        Assertions.assertEquals(BetStatusEnum.fromCode(1), betStatusEnum);
    }

    @Test
    void testConvertToDatabaseColumnNull(){
        final Integer converted = betStatusEnumConverter.convertToDatabaseColumn(null);
        Assertions.assertNull(converted);
    }

    @Test
    void testConvertToEntityAttributeNull() {
        final BetStatusEnum betStatusEnum = betStatusEnumConverter.convertToEntityAttribute(null);
        Assertions.assertNull(betStatusEnum);
    }

    //test that returns exception
    @Test
    void testConvertToEntityAttributeException(){
        final Integer code = 4;
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {betStatusEnumConverter.convertToEntityAttribute(code);});
    }
}
