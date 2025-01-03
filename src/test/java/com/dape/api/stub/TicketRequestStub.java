package com.dape.api.stub;

import com.dape.api.adapter.dto.request.TicketRequest;
import org.assertj.core.util.Lists;

import java.math.BigDecimal;
import java.util.List;

public class TicketRequestStub {
    private BigDecimal numAmount;
    private Long idtClient;
    private List<Long> idtBets;

    private TicketRequestStub(){
        this.numAmount = new BigDecimal("10.00");
        this.idtClient = 1L;
        this.idtBets = Lists.newArrayList(1L);
    }

    public static TicketRequestStub builder(){
        return new TicketRequestStub();
    }

    public TicketRequest build(){
        TicketRequest ticketRequest = new TicketRequest();
        ticketRequest.setNumAmount(numAmount);
        ticketRequest.setIdtClient(idtClient);
        ticketRequest.setIdtBets(idtBets);

        return ticketRequest;
    }

    public TicketRequestStub withNumAmount(BigDecimal numAmount){
        this.numAmount = numAmount;
        return this;
    }

    public TicketRequestStub withIdtClient(Long idtClient){
        this.idtClient = idtClient;
        return this;
    }

    public TicketRequestStub withIdtBets(List<Long> idtBets){
        this.idtBets = idtBets;
        return this;
    }
}
