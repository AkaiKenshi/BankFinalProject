package com.FinalProject.Accounts;

import com.FinalProject.User;

public abstract class Account {
    protected double balance;
    private final User owner;

    protected Account(User owner) {
        this.owner = owner;
    }

    public double CheckBalance() {
        return balance;
    }

    public abstract void  closeAccount();
}
