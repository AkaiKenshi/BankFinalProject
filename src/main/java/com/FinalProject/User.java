package com.FinalProject;

import com.FinalProject.Accounts.Account;
import com.FinalProject.Accounts.Checking;
import com.FinalProject.Accounts.FixedTermInvestment;
import com.FinalProject.Accounts.Savings;
import com.FinalProject.Time.ITimePassable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User implements ITimePassable {
    private String firstName;
    private String lastName;
    private String userName;
    private final String id;
    private String password;
    private List<Account> accountList;
    public static HashMap<String,User> usersList;

    public User(@NotNull String firstName, @NotNull String lastName, @NotNull String userName, @NotNull String password, @NotNull String id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        if (!isValidPassword(password)) throw new IllegalArgumentException("Contraseña Invalida");
        this.password = password;

        if (usersList == null) usersList = new HashMap<String,User>();
        if (usersList.containsKey(id)) throw new IllegalArgumentException("ID ya esta en uso")
        this.id = id;
        usersList.put(id, this);


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
            throw new IllegalArgumentException("La contraseña debe tener al menos 1 carácter en mayúscula, carácter en minúscula, número y carácter especial.");
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
        return usersList.containsKey(id);
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
        if(!usersList.containsKey(id)) throw  new IllegalArgumentException("El usuario no existe");
        if(!usersList.get(id).password.equals(password)) throw new IllegalArgumentException("Contraseña inválida");
        return usersList.get(id);
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
