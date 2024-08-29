package com.dape.api.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

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
    private LocalDate datCreated;

    @Column(name = "DAT_UPDATED", nullable = false)
    private LocalDate datUpdated;

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

    public LocalDate getDatCreated() {
        return datCreated;
    }

    public void setDatCreated(LocalDate datCreated) {
        this.datCreated = datCreated;
    }

    public LocalDate getDatUpdated() {
        return datUpdated;
    }

    public void setDatUpdated(LocalDate datUpdated) {
        this.datUpdated = datUpdated;
    }
}
