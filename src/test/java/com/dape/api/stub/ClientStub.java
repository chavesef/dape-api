package com.dape.api.stub;

import com.dape.api.domain.entity.Client;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ClientStub {
    private Long idtClient;
    private String namClient;
    private String numDocument;
    private BigDecimal numBalance;
    private String desEmail;
    private String numPassword;
    private LocalDateTime datCreated;
    private LocalDateTime datUpdated;

    private ClientStub(){
        this.idtClient = 1L;
        this.namClient = "Elias";
        this.numDocument = "123456";
        this.numPassword = "123456";
        this.numBalance = BigDecimal.valueOf(1000);
        this.desEmail = "elias@pagbank.com";
        this.datCreated = LocalDateTime.now();
        this.datUpdated = LocalDateTime.now();
    }

    public static ClientStub builder(){
        return new ClientStub();
    }

    public Client build(){
        Client client = new Client();
        client.setIdtClient(idtClient);
        client.setNamClient(namClient);
        client.setNumDocument(numDocument);
        client.setNumPassword(numPassword);
        client.setNumBalance(numBalance);
        client.setDesEmail(desEmail);
        client.setDatCreated(datCreated);
        client.setDatUpdated(datUpdated);

        return client;
    }

    public ClientStub withIdtClient(Long idtClient){
        this.idtClient = idtClient;
        return this;
    }

    public ClientStub withNamClient(String namClient){
        this.namClient = namClient;
        return this;
    }


    public ClientStub withNumDocument(String numDocument){
        this.numDocument = numDocument;
        return this;
    }

    public ClientStub withNumPassword(String numPassword){
        this.numPassword = numPassword;
        return this;
    }

    public ClientStub withNumBalance(BigDecimal numBalance){
        this.numBalance = numBalance;
        return this;
    }

    public ClientStub withDesEmail(String desEmail){
        this.desEmail = desEmail;
        return this;
    }

    public ClientStub withDatCreated(LocalDateTime datCreated){
        this.datCreated = datCreated;
        return this;
    }

    public ClientStub withDatUpdated(LocalDateTime datUpdated){
        this.datUpdated = datUpdated;
        return this;
    }
}
