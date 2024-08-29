package com.dape.api.domain.entity;

import com.dape.api.domain.enums.BetStatusEnum;
import com.dape.api.domain.enums.converter.BetStatusEnumConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "BET")
public class Bet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDT_BET")
    private Long idtBet;

    @Column(name = "NUM_ODD", nullable = false)
    private BigDecimal numOdd;

    @Column(name = "FLG_SELECTED", nullable = false)
    private int flgSelected;

    @Column(name = "DAT_CREATED", nullable = false)
    private LocalDateTime datCreated;

    @Column(name = "DAT_UPDATED", nullable = false)
    private LocalDateTime datUpdated;

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

    public BigDecimal getNumOdd() {
        return numOdd;
    }

    public void setNumOdd(BigDecimal numOdd) {
        this.numOdd = numOdd;
    }

    public LocalDateTime getDatCreated() {
        return datCreated;
    }

    public void setDatCreated(LocalDateTime datCreated) {
        this.datCreated = datCreated;
    }

    public int getFlgSelected() {
        return flgSelected;
    }

    public void setFlgSelected(int flgSelected) {
        this.flgSelected = flgSelected;
    }

    public LocalDateTime getDatUpdated() {
        return datUpdated;
    }

    public void setDatUpdated(LocalDateTime datUpdated) {
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
