package com.dape.api.entity;

import com.dape.api.enums.TicketStatusEnum;
import com.dape.api.enums.TicketTypeEnum;
import com.dape.api.enums.converter.TicketStatusEnumConverter;
import com.dape.api.enums.converter.TicketTypeEnumConverter;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "TICKET")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDT_TICKET", nullable = false)
    private Long idtTicket;

    @Column(name = "NUM_AMOUNT", nullable = false)
    private double numAmmount;

    @Column(name = "DAT_CREATED", nullable = false)
    private Date datCreated;

    @Column(name = "DAT_UPDATED", nullable = false)
    private Date datUpdated;

    @Column(name = "COD_TICKET_TYPE", nullable = false)
    @Convert(converter = TicketTypeEnumConverter.class)
    private TicketTypeEnum codTicketType;

    @Column(name = "COD_TICKET_STATUS", nullable = false)
    @Convert(converter = TicketStatusEnumConverter.class)
    private TicketStatusEnum codTicketStatus;

    @ManyToOne
    @JoinColumn(name = "IDT_CLIENT", nullable = false)
    private Client idtClient;

    public double getNumAmmount() {
        return numAmmount;
    }

    public void setNumAmmount(double numAmmount) {
        this.numAmmount = numAmmount;
    }

    public Date getDatCreated() {
        return datCreated;
    }

    public void setDatCreated(Date datCreated) {
        this.datCreated = datCreated;
    }

    public Long getIdtTicket() {
        return idtTicket;
    }

    public void setIdtTicket(Long idtTicket) {
        this.idtTicket = idtTicket;
    }

    public Date getDatUpdated() {
        return datUpdated;
    }

    public void setDatUpdated(Date datUpdated) {
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
