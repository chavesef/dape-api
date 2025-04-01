package com.dape.api.adapter.dto.request;

public class GetTicketsRequest {
    private Integer page;
    private Integer size;
    private String ticketStatus;
    private String ticketType;
    private Long idtClient;
    private String datCreated;
    private String datUpdated;

    public GetTicketsRequest(Integer page, Integer size, String ticketStatus, String ticketType, Long idtClient, String datCreated, String datUpdated) {
        this.page = page;
        this.size = size;
        this.ticketStatus = ticketStatus;
        this.ticketType = ticketType;
        this.idtClient = idtClient;
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

    public String getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public Long getIdtClient() {
        return idtClient;
    }

    public void setIdtClient(Long idtClient) {
        this.idtClient = idtClient;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
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