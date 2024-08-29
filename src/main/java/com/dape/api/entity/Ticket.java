package com.dape.api.entity;

import com.dape.api.enums.TicketStatusEnum;
import com.dape.api.enums.TicketTypeEnum;
import com.dape.api.enums.converter.TicketStatusEnumConverter;
import com.dape.api.enums.converter.TicketTypeEnumConverter;

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
import java.time.LocalDate;

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
    private LocalDate datCreated;

    @Column(name = "DAT_UPDATED", nullable = false)
    private LocalDate datUpdated;

    @Column(name = "COD_TICKET_TYPE", nullable = false)
    @Convert(converter = TicketTypeEnumConverter.class)
    private TicketTypeEnum codTicketType;

    @Column(name = "COD_TICKET_STATUS", nullable = false)
    @Convert(converter = TicketStatusEnumConverter.class)
    private TicketStatusEnum codTicketStatus;

    @ManyToOne
    @JoinColumn(name = "IDT_CLIENT", nullable = false)
    private Client idtClient;

    public BigDecimal getNumAmmount() {
        return numAmmount;
    }

    public void setNumAmmount(BigDecimal numAmmount) {
        this.numAmmount = numAmmount;
    }

    public LocalDate getDatCreated() {
        return datCreated;
    }

    public void setDatCreated(LocalDate datCreated) {
        this.datCreated = datCreated;
    }

    public Long getIdtTicket() {
        return idtTicket;
    }

    public void setIdtTicket(Long idtTicket) {
        this.idtTicket = idtTicket;
    }

    public LocalDate getDatUpdated() {
        return datUpdated;
    }

    public void setDatUpdated(LocalDate datUpdated) {
        this.datUpdated = datUpdated;
    }

    public TicketTypeEnum getCodTicketType() {
        return codTicketType;
    }

    public void setCodTicketType(TicketTypeEnum codTicketType) {
        this.codTicketType = codTicketType;
    }

    public TicketStatusEnum getCodTicketStatus() {
        return codTicketStatus;
    }

    public void setCodTicketStatus(TicketStatusEnum codTicketStatus) {
        this.codTicketStatus = codTicketStatus;
    }

    public Client getIdtClient() {
        return idtClient;
    }

    public void setIdtClient(Client idtClient) {
        this.idtClient = idtClient;
    }
}
