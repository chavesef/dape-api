package com.dape.api.adapter.dto.request;

public class GetBetsRequest {
    private Integer page;
    private Integer size;
    private String betStatus;
    private String datCreated;
    private String datUpdated;

    public GetBetsRequest(Integer page, Integer size, String betStatus, String datCreated, String datUpdated) {
        this.page = page;
        this.size = size;
        this.betStatus = betStatus;
        this.datCreated = datCreated;
        this.datUpdated = datUpdated;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getBetStatus() {
        return betStatus;
    }

    public void setBetStatus(String betStatus) {
        this.betStatus = betStatus;
    }

    public String getDatCreated() {
        return datCreated;
    }

    public void setDatCreated(String datCreated) {
        this.datCreated = datCreated;
    }

    public String getDatUpdated() {
        return datUpdated;
    }

    public void setDatUpdated(String datUpdated) {
        this.datUpdated = datUpdated;
    }
}
