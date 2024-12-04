package com.dape.api.adapter.dto.response;

import java.math.BigDecimal;

public class ClientResponse {

    private Long idtClient;
    private String namClient;
    private String numDocument;
    private BigDecimal numBalance;

    public Long getIdtClient() {
        return idtClient;
    }

    public void setIdtClient(Long idtClient) {
        this.idtClient = idtClient;
    }

    public String getNamClient() {
        return namClient;
    }

    public void setNamClient(String namClient) {
        this.namClient = namClient;
    }

    public String getNumDocument() {
        return numDocument;
    }

    public void setNumDocument(String numDocument) {
        this.numDocument = numDocument;
    }

    public BigDecimal getNumBalance() {
        return numBalance;
    }

    public void setNumBalance(BigDecimal numBalance) {
        this.numBalance = numBalance;
    }
}
