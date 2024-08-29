package com.dape.api.entity;

import com.dape.api.enums.BetStatusEnum;
import com.dape.api.enums.converter.BetStatusEnumConverter;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

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
    private LocalDate datCreated;

    @Column(name = "DAT_UPDATED", nullable = false)
    private LocalDate datUpdated;

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

    public LocalDate getDatCreated() {
        return datCreated;
    }

    public void setDatCreated(LocalDate datCreated) {
        this.datCreated = datCreated;
    }

    public int getFlgSelected() {
        return flgSelected;
    }

    public void setFlgSelected(int flgSelected) {
        this.flgSelected = flgSelected;
    }

    public LocalDate getDatUpdated() {
        return datUpdated;
    }

    public void setDatUpdated(LocalDate datUpdated) {
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
