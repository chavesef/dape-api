package com.dape.api.stub;

import com.dape.api.adapter.dto.response.BetListResponse;
import com.dape.api.adapter.dto.response.BetResponse;

import java.util.ArrayList;
import java.util.List;

public class BetListResponseStub {
    private Integer page;
    private Integer size;
    private Integer totalPages;
    private Long totalElements;
    private List<BetResponse> betResponseList = new ArrayList<>();

    private BetListResponseStub(){
        this.page = 0;
        this.size = 10;
        this.totalPages = 1;
        this.totalElements = 1L;
        this.betResponseList.add(BetResponseStub.builder().build());
    }

    public static BetListResponseStub builder(){
        return new BetListResponseStub();
    }

    public BetListResponse build(){
        BetListResponse betListResponse = new BetListResponse();
        betListResponse.setSize(this.size);
        betListResponse.setPage(this.page);
        betListResponse.setTotalElements(this.totalElements);
        betListResponse.setTotalPages(this.totalPages);
        betListResponse.setBetResponseList(this.betResponseList);

        return betListResponse;
    }

    public BetListResponseStub withPage(Integer page){
        this.page = page;
        return this;
    }

    public BetListResponseStub withSize(Integer size){
        this.size = size;
        return this;
    }

    public BetListResponseStub withTotalPages(Integer totalPages){
        this.totalPages = totalPages;
        return this;
    }

    public BetListResponseStub withTotalElements(Long totalElements){
        this.totalElements = totalElements;
        return this;
    }

    public BetListResponseStub withBetResponseList(List<BetResponse> betResponseList){
        this.betResponseList = betResponseList;
        return this;
    }
}
