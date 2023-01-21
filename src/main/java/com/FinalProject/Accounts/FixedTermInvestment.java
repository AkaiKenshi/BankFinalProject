package com.FinalProject.Accounts;

import com.FinalProject.Time.ITimePassable;

public class FixedTermInvestment extends Account implements ITimePassable {
    private int term;

    public FixedTermInvestment(double balance, int term, int id) {
        super(id);
        this.balance = balance;
        this.term = term;
    }

    public int getTerm() {
        return term;
    }

    @Override
    public void passTime() {
        if (term > 0) {
            balance = balance + (balance * 4.5);
            term--;
        }
    }

    public double Close(){
        double temp = balance;
        term = 0;
        balance = 0;
        return balance;
    }


}
