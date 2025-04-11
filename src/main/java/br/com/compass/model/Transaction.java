package br.com.compass.model;

import java.util.Date;

public class Transaction {
    private int id;
    private long userId;
    private double amount;
    private Date date;
    private String type;
    private Long destinationUserId; // Use Long (wrapper) pois pode ser null

    public Transaction() {
    }

    public Transaction(int id, long userId, double amount, Date date, String type, Long destinationUserId) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.date = date;
        this.type = type;
        this.destinationUserId = destinationUserId;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getDestinationUserId() {
        return destinationUserId;
    }

    public void setDestinationUserId(Long destinationUserId) {
        this.destinationUserId = destinationUserId;
    }
}
