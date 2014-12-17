package com.soddi.budgetapplication;

/**
 * Created by soddi on 2014-12-17.
 */
public class Transaction {
    String date;
    int amount;
    String title;

    public Transaction() {

    }

    public Transaction(String date, int amount, String title) {
        this.date = date;
        this.amount = amount;
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
