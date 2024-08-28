package com.dape.api.entity;

import jakarta.persistence.*;

import java.util.Date;

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
    private double numBalance;

    @Column(name = "DES_EMAIL", nullable = false)
    private String desEmail;

    @Column(name = "NUM_PASSWORD", nullable = false)
    private String numPassword;

    @Column(name = "DAT_CREATED", nullable = false)
    private Date datCreated;

    @Column(name = "DAT_UPDATED", nullable = false)
    private Date datUpdated;

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

    public double getNumBalance() {
        return numBalance;
    }

    public void setNumBalance(double numBalance) {
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

    public Date getDatCreated() {
        return datCreated;
    }

    public void setDatCreated(Date datCreated) {
        this.datCreated = datCreated;
    }

    public Date getDatUpdated() {
        return datUpdated;
    }

    public void setDatUpdated(Date datUpdated) {
        this.datUpdated = datUpdated;
    }
}
