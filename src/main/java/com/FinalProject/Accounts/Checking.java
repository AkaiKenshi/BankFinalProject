package com.FinalProject.Accounts;

public class Checking extends Account implements IAccount{
    public Checking(double balance, int id) {
        super(id);
        this.balance = balance;
    }

    public Checking(int id) {
        this(0, id);
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
        if (amount < 0 ) throw new IllegalArgumentException("La cantidad debe ser mayor que 0");
        else if (otherAccount == this) throw new IllegalArgumentException("Can't transfer to the same account you're transferring from");
        else if (amount > balance) throw new IllegalArgumentException("Fondos Insuficientes");
        retire(amount);
        otherAccount.deposit(amount);
    }
}
