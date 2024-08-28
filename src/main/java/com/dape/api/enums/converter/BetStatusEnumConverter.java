package com.dape.api.enums.converter;

import com.dape.api.enums.BetStatusEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class BetStatusEnumConverter implements AttributeConverter<BetStatusEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(BetStatusEnum betStatusEnum) {
        if (betStatusEnum == null) {
            return null;
        }
        return betStatusEnum.getCodBetStatus();
    }

    @Override
    public BetStatusEnum convertToEntityAttribute(Integer betStatusEnum) {
        if(betStatusEnum == null) {
            return null;
        }
        return BetStatusEnum.fromCode(betStatusEnum);
    }
}
