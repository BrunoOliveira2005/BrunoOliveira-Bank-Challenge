package br.com.compass.model;

import java.util.Date;

public class Transaction {
    private int id;
    private long userId;
    private int accountId;
    private double amount;
    private Date date;
    private String type;
    private String descricao;
    private Long destinationUserId;

    public Transaction() {}

    public Transaction(int id, long userId, int accountId, double amount, Date date, String type, String descricao, Long destinationUserId) {
        this.id = id;
        this.userId = userId;
        this.accountId = accountId;
        this.amount = amount;
        this.date = date;
        this.type = type;
        this.descricao = descricao;
        this.destinationUserId = destinationUserId;
    }

    public Transaction(long userId, int accountId, double amount, Date date, String type, String descricao, Long destinationUserId) {
        this.userId = userId;
        this.accountId = accountId;
        this.amount = amount;
        this.date = date;
        this.type = type;
        this.descricao = descricao;
        this.destinationUserId = destinationUserId;
    }

 
    public int getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public int getAccountId() {
        return accountId;
    }

    public double getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public String getDescricao() {
        return descricao;
    }

    public Long getDestinationUserId() {
        return destinationUserId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setDestinationUserId(Long destinationUserId) {
        this.destinationUserId = destinationUserId;
    }
}
