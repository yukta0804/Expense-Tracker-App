package com.example.expensetracker;

import java.util.Date;

public class Transaction {
    private long id;
    private double amount;
    private String description;
    private boolean isIncome;
    private Date date;

    public Transaction(double amount, String description, boolean isIncome) {
        this.amount = amount;
        this.description = description;
        this.isIncome = isIncome;
        this.date = new Date();
    }

    // Getters and setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public double getAmount() { return amount; }
    public String getDescription() { return description; }
    public boolean isIncome() { return isIncome; }
    public Date getDate() { return date; }
}