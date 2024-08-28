package com.dape.api.enums;

public enum BetStatusEnum {
    PENDING(1),
    GREEN(2),
    RED(3);

    private final int codBetStatus;

    BetStatusEnum(int codBetStatus) {
        this.codBetStatus = codBetStatus;
    }

    public int getCodBetStatus() {
        return codBetStatus;
    }

    public static BetStatusEnum fromCode(int codBetStatus) {
        for (BetStatusEnum betStatusEnum : BetStatusEnum.values()) {
            if (betStatusEnum.getCodBetStatus() == codBetStatus) {
                return betStatusEnum;
            }
        }
        throw new UnsupportedOperationException("Unsupported bet status code: " + codBetStatus);
    }
}
