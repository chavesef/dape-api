package com.dape.api.stub;

import com.dape.api.adapter.dto.request.GetTicketsRequest;

public class GetTicketsRequestStub {
    private Integer page;
    private Integer size;
    private String ticketStatus;
    private String ticketType;
    private Long idtClient;
    private String datCreated;
    private String datUpdated;

    private GetTicketsRequestStub() {
        this.page = 0;
        this.size = 10;
        this.ticketStatus = "PENDING";
        this.datCreated = "2024-10-21";
        this.datUpdated = "2024-10-21";
        this.ticketType = "SIMPLE";
        this.idtClient = 1L;
    }

    public static GetTicketsRequestStub builder(){
        return new GetTicketsRequestStub();
    }

    public GetTicketsRequest build(){
        return new GetTicketsRequest(this.page, this.size, this.ticketStatus, this.ticketType, this.idtClient, this.datCreated, this.datUpdated);
    }

    public GetTicketsRequestStub withPage(Integer page) {
        this.page = page;
        return this;
    }

    public GetTicketsRequestStub withSize(Integer size) {
        this.size = size;
        return this;
    }

    public GetTicketsRequestStub withTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
        return this;
    }

    public GetTicketsRequestStub withTicketType(String ticketType) {
        this.ticketType = ticketType;
        return this;
    }

    public GetTicketsRequestStub withIdtClient(Long idtClient) {
        this.idtClient = idtClient;
        return this;
    }

    public GetTicketsRequestStub withDatCreated(String datCreated) {
        this.datCreated = datCreated;
        return this;
    }

    public GetTicketsRequestStub withDatUpdated(String datUpdated) {
        this.datUpdated = datUpdated;
        return this;
    }
}
