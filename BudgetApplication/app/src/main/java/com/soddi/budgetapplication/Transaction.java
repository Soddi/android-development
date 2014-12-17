package com.soddi.budgetapplication;

/**
 * Created by soddi on 2014-12-17.
 */
public class Transaction {
    String id;
    String date;
    String amount;
    String title;

    public Transaction() {

    }

    public Transaction(String id, String date, String amount, String title) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
