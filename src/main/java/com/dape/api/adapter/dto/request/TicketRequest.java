package com.dape.api.adapter.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TicketRequest {

    @Min(value = 1, message = "Valor a ser apostado deve ser maior do que 1")
    @NotNull(message = "Valor da aposta não deve ser nulo")
    private BigDecimal numAmount;
    @NotNull(message = "ID do cliente não deve ser nulo")
    private Long idtClient;
    private List<Long> idtBets;

    public TicketRequest(BigDecimal numAmount, Long idtClient, List<Long> idtBets) {
        this.numAmount = numAmount;
        this.idtClient = idtClient;
        this.idtBets = idtBets;
    }

    public BigDecimal getNumAmount() {
        return numAmount;
    }

    public void setNumAmount(BigDecimal numAmount) {
        this.numAmount = numAmount;
    }

    public Long getIdtClient() {
        return idtClient;
    }

    public void setIdtClient(Long idtClient) {
        this.idtClient = idtClient;
    }

    public List<Long> getIdtBets() {
        return idtBets;
    }

    public void setIdtBets(List<Long> idtBets) {
        this.idtBets = idtBets;
    }
}
