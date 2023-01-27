package com.FinalProject;

import com.FinalProject.Accounts.Account;
import com.FinalProject.Accounts.Checking;
import com.FinalProject.Accounts.FixedTermInvestment;
import com.FinalProject.Accounts.Savings;
import com.FinalProject.Time.ITimePassable;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class User implements ITimePassable {
    private String firstName;
    private String lastName;
    private String userName;
    private final String id;
    private String password;
    private HashMap<String, Account> accountList;
    public static HashMap<String, User> usersList;

    public User(@NotNull String firstName, @NotNull String lastName, @NotNull String userName, @NotNull String password, @NotNull String id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;

        if (usersList == null) usersList = new HashMap<String, User>();
        if (usersList.containsKey(id)) throw new IllegalArgumentException("ID ya esta en uso");
        accountList = new HashMap<String, Account>();
        this.id = id;
        usersList.put(id, this);
    }

    @Override
    public void passTime() {
        for (Account account : accountList.values()) {
            if (account instanceof ITimePassable) {
                ((ITimePassable) account).passTime();
            }
        }
    }

    public Checking createCheckingAccount(double initMoney) {
        String id = String.format("%05d", accountList.size());
        Checking checking = new Checking(initMoney, this);
        accountList.put(id, checking);
        return checking;
    }

    public Savings createSavingsAccount(double initMoney) {
        String id = String.format("%05d", accountList.size());
        Savings savings = new Savings(initMoney, this);
        accountList.put(id, savings);
        return savings;
    }

    public FixedTermInvestment createFixedTermInvestingAccount(double initMoney, int term) {
        String id = String.format("%05d", accountList.size());
        FixedTermInvestment fixedTermInvestment = new FixedTermInvestment(initMoney, term, this);
        accountList.put(id, fixedTermInvestment);
        return fixedTermInvestment;
    }

    public void closeAccount(Account account) {
        account.closeAccount();
        String id = getIdByAccount(account);
        accountList.remove(id, account);
    }

    public String getIdByAccount(Account account) {
        for (Map.Entry<String, Account> entry : accountList.entrySet()) {
            if (Objects.equals(account, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    public Account getAccountByID(String id) {
        return accountList.get(id);
    }

    public void printAllAccountIDs() {
        for (Map.Entry<String, Account> a : accountList.entrySet()) {
            String tipo;
            if (a.getValue() instanceof Checking) tipo = "Corriente";
            else if (a.getValue() instanceof Savings) tipo = "Ahorros";
            else tipo = "Inversion de plazo fijo";
            System.out.println(a.getKey() + ": " + tipo);
        }
    }

    public static User GetUser(String id, String password) {
        if (!usersList.containsKey(id)) throw new IllegalArgumentException("El usuario no existe ");
        if (!usersList.get(id).password.equals(password)) throw new IllegalArgumentException("Contraseña inválida");
        return usersList.get(id);
    }
}
