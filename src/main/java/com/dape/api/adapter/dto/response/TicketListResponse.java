package com.dape.api.adapter.dto.response;

import java.util.ArrayList;
import java.util.List;

public class TicketListResponse {
    private Integer page;
    private Integer size;
    private Integer totalPages;
    private Long totalElements;
    private List<TicketResponse> ticketResponseList = new ArrayList<>();

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

    public List<TicketResponse> getTicketResponseList() {
        return ticketResponseList;
    }

    public void setTicketResponseList(List<TicketResponse> ticketResponseList) {
        this.ticketResponseList = ticketResponseList;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }
}
