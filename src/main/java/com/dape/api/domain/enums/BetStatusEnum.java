package com.dape.api.domain.enums;

public enum BetStatusEnum {
    PENDING(1, "Aposta pendente"),
    GREEN(2, "Aposta vencida"),
    RED(3, "Aposta perdida");

    private final int codBetStatus;
    private final String desBetStatus;

    BetStatusEnum(int codBetStatus, String desBetStatus) {
        this.codBetStatus = codBetStatus;
        this.desBetStatus = desBetStatus;
    }

    public int getCodBetStatus() {
        return codBetStatus;
    }

    public String getDesBetStatus() {
        return desBetStatus;
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
