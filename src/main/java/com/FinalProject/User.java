package com.FinalProject;

import com.FinalProject.Accounts.Account;
import com.FinalProject.Accounts.Checking;
import com.FinalProject.Accounts.FixedTermInvestment;
import com.FinalProject.Accounts.Savings;
import com.FinalProject.Time.ITimePassable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class User implements ITimePassable {
    private String firstName;
    private String lastName;
    private String userName;
    private final String id;
    private String password;
    public List<Account> accountList;
    public static List<User> usersList;

    public User(@NotNull String firstName, @NotNull String lastName, @NotNull String userName, @NotNull String password, @NotNull String id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        if (!isValidPassword(password)) throw new IllegalArgumentException("Invalid Password");
        this.password = password;

        if (usersList == null) usersList = new ArrayList<User>();
        usersList.add(this);

        for (User u : usersList) {
            if (u.getId() == null) continue;
            else if (u.getId().equals(id)) throw new IllegalArgumentException("ID must be unique");
        }
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotNull String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(@NotNull String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(@NotNull String userName) {
        this.userName = userName;
    }

    public void setPassword(@NotNull String password, @NotNull String confirmationPassword, @NotNull String oldPassword) {
        if (!oldPassword.equals(this.password) && !password.equals(confirmationPassword) && !isValidPassword(password)) {
            throw new IllegalArgumentException("Password must have at least 1 upper cased character, lower cased character, number and special character");
        }
        this.password = password;
    }

    public String getId() {
        return id;
    }

    @Override
    public void passTime() {
        for (Account account : accountList) {
            if (account instanceof ITimePassable) {
                ((ITimePassable) account).passTime();
            }
        }
    }

    public boolean isCorrectPassword(String password){
        return this.password.equals(password);
    }

    public static boolean isValidId(String id) {
        for (User u : usersList) {
            if (id.equals(u.getId()) || id.length() != 10 ) return false;
        }
        return true;
    }

    public Checking createCheckingAccount(double initMoney){
        if (accountList == null) accountList = new ArrayList<Account>();
        int id = accountList.size();
        Checking checking = new Checking(initMoney, id);
        accountList.add(checking);
        return  checking;
    }

    public FixedTermInvestment createFixedTermInvestingAccount(double initMoney, int term){
        if (accountList == null) accountList = new ArrayList<Account>();
        int id = accountList.size();
        FixedTermInvestment fixedTermInvestment = new FixedTermInvestment(initMoney, term, id);
        accountList.add(fixedTermInvestment);
        return fixedTermInvestment;
    }

    public Savings createSavingsAccount(double initMoney){
        if (accountList == null) accountList = new ArrayList<Account>();
        int id = accountList.size();
        Savings savings = new Savings(initMoney, id);
        accountList.add(savings);
        return savings;
    }

    public static User GetUser(String id, String password) {
        for (User u : usersList) {
            if(!u.getId().equals(id)) continue;
            else if(!u.isCorrectPassword(password)) throw new IllegalArgumentException("Invalid Password");
        }
        throw new IllegalArgumentException("user doesn't exist");
    }

    public static boolean isValidPassword(@NotNull String password) {
        if (password.length() < 8) return false;
        boolean hasUpperCase = !password.equals(password.toLowerCase());
        boolean hasLowerCase = !password.equals(password.toUpperCase());
        boolean hasSpecialCharacter = !password.matches("[A-Za-z0-9]*");
        boolean hasNumber = password.matches(".*\\d+.*");

        return true;
    }

}
