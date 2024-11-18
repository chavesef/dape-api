package com.dape.api.domain.enums;

import com.dape.api.adapter.dto.request.BetStatusRequest;
import com.dape.api.domain.exception.InvalidBetStatusException;
import com.dape.api.domain.exception.InvalidStatusForUpdateException;

import java.util.Objects;

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
        for (BetStatusEnum betStatusEnum : BetStatusEnum.values())
            if (betStatusEnum.getCodBetStatus() == codBetStatus)
                return betStatusEnum;
        throw new UnsupportedOperationException("Unsupported bet status code: " + codBetStatus);
    }

    public static BetStatusEnum fromRequest(BetStatusRequest betStatusRequest) {
        for (BetStatusEnum betStatusEnum : BetStatusEnum.values())
            if (Objects.equals(betStatusEnum.toString(), betStatusRequest.getBetStatus()))
                return betStatusEnum;
        throw new InvalidStatusForUpdateException("Descrição de aposta inválida: " + betStatusRequest.getBetStatus());
    }

    public static void validateFromString(String betStatus){
        if(betStatus != null){
            try {
                BetStatusEnum.valueOf(betStatus);
            } catch (IllegalArgumentException e) {
                throw new InvalidBetStatusException("Status não existente: " + betStatus);
            }
        }
    }
}
