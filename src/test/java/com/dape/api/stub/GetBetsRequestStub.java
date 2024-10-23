package com.dape.api.stub;

import com.dape.api.adapter.dto.request.GetBetsRequest;

public class GetBetsRequestStub {
    private Integer page;
    private Integer size;
    private String betStatus;
    private String datCreated;
    private String datUpdated;

    private GetBetsRequestStub() {
        this.page = 0;
        this.size = 10;
        this.betStatus = "PENDING";
        this.datCreated = "2024-10-21";
        this.datUpdated = null;
    }

    public static GetBetsRequestStub builder(){
        return new GetBetsRequestStub();
    }

    public GetBetsRequest build(){
        return new GetBetsRequest(this.page, this.size, this.betStatus, this.datCreated, this.datUpdated);
    }

    public GetBetsRequestStub withPage(Integer page) {
        this.page = page;
        return this;
    }

    public GetBetsRequestStub withSize(Integer size) {
        this.size = size;
        return this;
    }

    public GetBetsRequestStub withBetStatus(String betStatus) {
        this.betStatus = betStatus;
        return this;
    }

    public GetBetsRequestStub withDatCreated(String datCreated) {
        this.datCreated = datCreated;
        return this;
    }

    public GetBetsRequestStub withDatUpdated(String datUpdated) {
        this.datUpdated = datUpdated;
        return this;
    }
}
