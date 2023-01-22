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
        if (!isValidPassword(password)) throw new IllegalArgumentException("Contraseña Invalida");
        this.password = password;

        if (usersList == null) usersList = new HashMap<String, User>();
        if (usersList.containsKey(id)) throw new IllegalArgumentException("ID ya esta en uso");
        accountList = new HashMap<String, Account>();
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
        for (Account account : accountList.values()) {
            if (account instanceof ITimePassable) {
                ((ITimePassable) account).passTime();
            }
        }
    }

    public boolean isCorrectPassword(String password) {
        return this.password.equals(password);
    }

    public static boolean isValidId(String id) {
        return usersList.containsKey(id);
    }

    public Checking createCheckingAccount(double initMoney) {
        String id = String.format("%05d", accountList.size());
        Checking checking = new Checking(initMoney, this);
        accountList.put(id, checking);
        return checking;
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
        accountList.remove(id,account);
    }

    public String getIdByAccount(Account account) {
        for (Map.Entry<String, Account> entry : accountList.entrySet()) {
            if (Objects.equals(account, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    public Savings createSavingsAccount(double initMoney) {
        String id = String.format("%05d", accountList.size());
        Savings savings = new Savings(initMoney, this);
        accountList.put(id, savings);
        return savings;
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
        if (!usersList.containsKey(id)) throw new IllegalArgumentException("El usuario no existe");
        if (!usersList.get(id).password.equals(password)) throw new IllegalArgumentException("Contraseña inválida");
        return usersList.get(id);
    }

    public static User getUserById(String id) {
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
