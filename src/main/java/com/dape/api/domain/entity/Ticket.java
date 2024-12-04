package com.dape.api.domain.entity;

import com.dape.api.domain.enums.TicketStatusEnum;
import com.dape.api.domain.enums.TicketTypeEnum;
import com.dape.api.domain.enums.converter.TicketStatusEnumConverter;
import com.dape.api.domain.enums.converter.TicketTypeEnumConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "TICKET")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDT_TICKET", nullable = false)
    private Long idtTicket;

    @Column(name = "NUM_AMOUNT", nullable = false)
    private BigDecimal numAmmount;

    @Column(name = "DAT_CREATED", nullable = false)
    private LocalDateTime datCreated;

    @Column(name = "DAT_UPDATED", nullable = false)
    private LocalDateTime datUpdated;

    @Column(name = "COD_TICKET_TYPE", nullable = false)
    @Convert(converter = TicketTypeEnumConverter.class)
    private TicketTypeEnum ticketTypeEnum;

    @Column(name = "COD_TICKET_STATUS", nullable = false)
    @Convert(converter = TicketStatusEnumConverter.class)
    private TicketStatusEnum ticketStatusEnum;

    @Column(name = "NUM_FINAL_ODD", nullable = false)
    private BigDecimal numFinalOdd;

    @ManyToOne
    @JoinColumn(name = "IDT_CLIENT", nullable = false)
    private Client client;

    public BigDecimal getNumAmmount() {
        return numAmmount;
    }

    public void setNumAmmount(BigDecimal numAmmount) {
        this.numAmmount = numAmmount;
    }

    public LocalDateTime getDatCreated() {
        return datCreated;
    }

    public void setDatCreated(LocalDateTime datCreated) {
        this.datCreated = datCreated;
    }

    public Long getIdtTicket() {
        return idtTicket;
    }

    public void setIdtTicket(Long idtTicket) {
        this.idtTicket = idtTicket;
    }

    public LocalDateTime getDatUpdated() {
        return datUpdated;
    }

    public void setDatUpdated(LocalDateTime datUpdated) {
        this.datUpdated = datUpdated;
    }

    public TicketTypeEnum getTicketTypeEnum() {
        return ticketTypeEnum;
    }

    public void setTicketTypeEnum(TicketTypeEnum codTicketType) {
        this.ticketTypeEnum = codTicketType;
    }

    public TicketStatusEnum getTicketStatusEnum() {
        return ticketStatusEnum;
    }

    public void setTicketStatusEnum(TicketStatusEnum codTicketStatus) {
        this.ticketStatusEnum = codTicketStatus;
    }

    public BigDecimal getNumFinalOdd() {
        return numFinalOdd;
    }

    public void setNumFinalOdd(BigDecimal numFinalOdd) {
        this.numFinalOdd = numFinalOdd;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client idtClient) {
        this.client = idtClient;
    }
}
