package com.dape.api.entity;

import com.dape.api.enums.BetStatusEnum;
import com.dape.api.enums.converter.BetStatusEnumConverter;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "BET")
public class Bet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDT_BET")
    private Long idtBet;

    @Column(name = "NUM_ODD", nullable = false)
    private double numOdd;

    @Column(name = "FLG_SELECTED", nullable = false)
    private int flgSelected;

    @Column(name = "DAT_CREATED", nullable = false)
    private Date datCreated;

    @Column(name = "DAT_UPDATED", nullable = false)
    private Date datUpdated;

    @Column(name = "DES_BET", nullable = false)
    private String desBet;

    @Column(name = "COD_BET_STATUS", nullable = false)
    @Convert(converter = BetStatusEnumConverter.class)
    private BetStatusEnum codBetStatus;

    public Long getIdtBet() {
        return idtBet;
    }

    public void setIdtBet(Long idtBet) {
        this.idtBet = idtBet;
    }

    public double getNumOdd() {
        return numOdd;
    }

    public void setNumOdd(double numOdd) {
        this.numOdd = numOdd;
    }

    public Date getDatCreated() {
        return datCreated;
    }

    public void setDatCreated(Date datCreated) {
        this.datCreated = datCreated;
    }

    public int getFlgSelected() {
        return flgSelected;
    }

    public void setFlgSelected(int flgSelected) {
        this.flgSelected = flgSelected;
    }

    public Date getDatUpdated() {
        return datUpdated;
    }

    public void setDatUpdated(Date datUpdated) {
        this.datUpdated = datUpdated;
    }

    public String getDesBet() {
        return desBet;
    }

    public void setDesBet(String desBet) {
        this.desBet = desBet;
    }

    public BetStatusEnum getCodBetStatus() {
        return codBetStatus;
    }

    public void setCodBetStatus(BetStatusEnum codBetStatus) {
        this.codBetStatus = codBetStatus;
    }
}
