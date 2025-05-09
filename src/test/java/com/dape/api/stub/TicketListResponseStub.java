package com.dape.api.stub;

import com.dape.api.adapter.dto.response.TicketListResponse;
import com.dape.api.adapter.dto.response.TicketResponse;
import java.util.ArrayList;
import java.util.List;

public class TicketListResponseStub {
    private Integer page;
    private Integer size;
    private Integer totalPages;
    private Long totalElements;
    private List<TicketResponse> ticketResponseList = new ArrayList<>();

    private TicketListResponseStub() {
        this.page = 0;
        this.size = 10;
        this.totalPages = 1;
        this.totalElements = 1L;
        this.ticketResponseList.add(TicketResponseStub.builder().build());
    }

    public static TicketListResponseStub builder() {
        return new TicketListResponseStub();
    }

    public TicketListResponse build() {
        TicketListResponse ticketListResponse = new TicketListResponse();
        ticketListResponse.setSize(this.size);
        ticketListResponse.setPage(this.page);
        ticketListResponse.setTotalElements(this.totalElements);
        ticketListResponse.setTotalPages(this.totalPages);
        ticketListResponse.setTicketResponseList(this.ticketResponseList);

        return ticketListResponse;
    }

    public TicketListResponseStub withPage(Integer page) {
        this.page = page;
        return this;
    }

    public TicketListResponseStub withSize(Integer size) {
        this.size = size;
        return this;
    }

    public TicketListResponseStub withTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
        return this;
    }

    public TicketListResponseStub withTotalElements(Long totalElements) {
        this.totalElements = totalElements;
        return this;
    }

    public TicketListResponseStub withTicketResponseList(List<TicketResponse> ticketResponseList) {
        this.ticketResponseList = ticketResponseList;
        return this;
    }
}
