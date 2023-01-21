package com.FinalProject.Accounts;

public abstract class Account {
    protected double balance;
    private final int id;

    protected Account(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public double CheckBalance() {
        return balance;
    }
}
