package com.FinalProject.Accounts;

import com.FinalProject.Time.ITimePassable;
import com.FinalProject.User;

public class Savings extends Account implements IAccount, ITimePassable {

    public Savings(double balance, User owner) {
        super(owner);
        this.balance = balance;
    }

    @Override
    public void deposit(double amount) throws IllegalArgumentException {
        if (amount < 0) throw new IllegalArgumentException("La cantidad debe ser mayor que 0");
        balance += amount;
    }

    @Override
    public void retire(double amount) throws IllegalArgumentException {
        if (amount < 0) throw new IllegalArgumentException("La cantidad debe ser mayor que 0");
        else if (amount > balance) throw new IllegalArgumentException("Fondos Insuficientes");
        balance -= amount;
    }

    @Override
    public void transfer(double amount, IAccount otherAccount) throws IllegalArgumentException {
        if (amount < 0) throw new IllegalArgumentException("La cantidad debe ser mayor que 0");
        else if (otherAccount == this)
            throw new IllegalArgumentException("No se puede transferir a la misma cuenta desde la que estás transfiriendo");
        else if (amount > balance) throw new IllegalArgumentException("Fondos Insuficientes");
        retire(amount);
        otherAccount.deposit(amount);
    }

    @Override
    public void passTime() {
        balance *= 1.6;
    }

    @Override
    public void closeAccount() {
        System.out.println("closing account your balance was: " + balance);
    }
}
