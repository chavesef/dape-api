package com.dape.api.adapter.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BetPostRequest {

    @Min(value = 1, message = "Valor da odd deve ser maior que 1")
    @NotNull(message = "Valor da aposta não deve ser nulo")
    private BigDecimal numOdd;
    @NotBlank(message = "Descrição da aposta não deve ser nula/vazia")
    private String desBet;

    public BetPostRequest(BigDecimal numOdd, String desBet) {
        this.numOdd = numOdd;
        this.desBet = desBet;
    }

    public BigDecimal getNumOdd() {
        return numOdd;
    }

    public void setNumOdd(BigDecimal numOdd) {
        this.numOdd = numOdd;
    }

    public String getDesBet() {
        return desBet;
    }

    public void setDesBet(String desBet) {
        this.desBet = desBet;
    }
}
