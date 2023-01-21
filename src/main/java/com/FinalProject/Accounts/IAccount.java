package com.FinalProject.Accounts;


public interface IAccount {
    void deposit(double amount) throws IllegalArgumentException;
    void retire(double amount) throws IllegalArgumentException;
    void transfer(double amount, IAccount otherAccount) throws IllegalArgumentException;
}
