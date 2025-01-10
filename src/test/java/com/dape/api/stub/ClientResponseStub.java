package com.dape.api.stub;

import com.dape.api.adapter.dto.response.ClientResponse;

import java.math.BigDecimal;

public class ClientResponseStub {
    private Long idtClient;
    private String namClient;
    private String numDocument;
    private BigDecimal numBalance;

    private ClientResponseStub(){
        this.idtClient = 1L;
        this.namClient = "Elias";
        this.numDocument = "123456";
        this.numBalance = BigDecimal.valueOf(1000);
    }

    public static ClientResponseStub builder(){
        return new ClientResponseStub();
    }

    public ClientResponse build(){
        ClientResponse clientResponse = new ClientResponse();
        clientResponse.setIdtClient(this.idtClient);
        clientResponse.setNamClient(this.namClient);
        clientResponse.setNumDocument(this.numDocument);
        clientResponse.setNumBalance(this.numBalance);

        return clientResponse;
    }

    public ClientResponseStub withIdtClient(Long idtClient) {
        this.idtClient = idtClient;
        return this;
    }

    public ClientResponseStub withNamClient(String namClient) {
        this.namClient = namClient;
        return this;
    }

    public ClientResponseStub withNumDocument(String numDocument) {
        this.numDocument = numDocument;
        return this;
    }

    public ClientResponseStub withNumBalance(BigDecimal numBalance) {
        this.numBalance = numBalance;
        return this;
    }
}
