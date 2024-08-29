package com.dape.api.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "CLIENT")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDT_CLIENT")
    private Long idtClient;

    @Column(name = "NAM_CLIENT", nullable = false)
    private String namClient;

    @Column(name = "NUM_DOCUMENT", nullable = false)
    private String numDocument;

    @Column(name = "NUM_BALANCE", nullable = false)
    private BigDecimal numBalance;

    @Column(name = "DES_EMAIL", nullable = false)
    private String desEmail;

    @Column(name = "NUM_PASSWORD", nullable = false)
    private String numPassword;

    @Column(name = "DAT_CREATED", nullable = false)
    private LocalDateTime datCreated;

    @Column(name = "DAT_UPDATED", nullable = false)
    private LocalDateTime datUpdated;

    public Long getIdtClient() {
        return idtClient;
    }

    public void setIdtClient(Long idtClient) {
        this.idtClient = idtClient;
    }

    public String getNamClient() {
        return namClient;
    }

    public void setNamClient(String namClient) {
        this.namClient = namClient;
    }

    public String getNumDocument() {
        return numDocument;
    }

    public void setNumDocument(String numDocument) {
        this.numDocument = numDocument;
    }

    public BigDecimal getNumBalance() {
        return numBalance;
    }

    public void setNumBalance(BigDecimal numBalance) {
        this.numBalance = numBalance;
    }

    public String getDesEmail() {
        return desEmail;
    }

    public void setDesEmail(String desEmail) {
        this.desEmail = desEmail;
    }

    public String getNumPassword() {
        return numPassword;
    }

    public void setNumPassword(String numPassword) {
        this.numPassword = numPassword;
    }

    public LocalDateTime getDatCreated() {
        return datCreated;
    }

    public void setDatCreated(LocalDateTime datCreated) {
        this.datCreated = datCreated;
    }

    public LocalDateTime getDatUpdated() {
        return datUpdated;
    }

    public void setDatUpdated(LocalDateTime datUpdated) {
        this.datUpdated = datUpdated;
    }
}
