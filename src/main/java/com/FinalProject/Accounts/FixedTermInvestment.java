package com.FinalProject.Accounts;

import com.FinalProject.Time.ITimePassable;
import com.FinalProject.User;

public class FixedTermInvestment extends Account implements ITimePassable {
    private int term;

    public FixedTermInvestment(double balance, int term, User owner) {
        super(owner);
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

    public void closeAccount(){
        System.out.println("closing account, your balance was: " + balance +", and u had " + term +" months left" );
    }


}
