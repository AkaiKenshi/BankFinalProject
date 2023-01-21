package com.FinalProject;

import com.FinalProject.Accounts.*;

import java.util.Scanner;

/**
 * Hello world!
 */
public class App {
    static Scanner sc = new Scanner(System.in);
    static boolean exit = false;
    static int mes;

    public static void main(String[] args) {
        while (!exit) {
            System.out.println("el mes es " + mes);
            print
            int command = sc.nextInt();
            sc.nextLine();
            switch (command) {
                case 1 -> enterAsUser();
                case 2 -> passTime();
                case 3 -> exit = true;
                default -> System.out.println("Invalid argument");
            }
        }

    }

    public static void printOptions(String initMsg, String ... options){
        System.out.println(initMsg);
        for (int i = 0; i < options.length; i++) {
            System.out.println("    " + i + ") " + options[i]);
        }
    }

    private static void enterAsUser() {
        boolean goBack = false;
        while (!goBack) {
            System.out.println("quiere: ");
            System.out.println("  1) Crear Usuario Nuevo");
            System.out.println("  2) Ingresar a un Usuario Existente");
            System.out.println("  3) Go back");
            int command = sc.nextInt();
            sc.nextLine();
            switch (command) {
                case 1 -> createUsuario();
                case 2 -> ingresarUsuario();
                case 3 -> goBack = true;
                default -> System.out.println("Invalid Argument");
            }
        }
    }

    private static void ingresarUsuario() {
        boolean wasLogin = false;
        while (!wasLogin) {
            System.out.println("ingrese su id");
            String id = sc.nextLine();
            System.out.println("ingrese su Contraseña");
            String password = sc.nextLine();
            try {
                userActions(User.GetUser(id, password));
                wasLogin = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void createUsuario() {
        boolean wasLogin = false;
        while (!wasLogin) {
            System.out.println("ingrese su nombre");
            String name = sc.nextLine();

            System.out.println("ingrese su apellido");
            String lastName = sc.nextLine();

            System.out.println("ingrese su nombre de usuario");
            String userName = sc.nextLine();

            System.out.println("ingrese su id");
            String id = sc.nextLine();

            System.out.println("ingrese su Contraseña");
            String password = sc.nextLine();

            try {
                User user = new User(name, lastName, userName, id, password);
                userActions(user);
                wasLogin = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                String command = "";
                while (!command.equals("s") && !command.equals("n")) {
                    System.out.println("intenta de nuevo (s/n)");
                    command = sc.nextLine().toLowerCase();

                    if (command.equals("")) wasLogin = true;
                    else if (!command.equals("s") && !command.equals("n")) System.out.println("Invalid Command");
                }
            }
        }
    }

    private static void userActions(User user) {
        boolean wasLogout = false;
        while (!wasLogout) {
            System.out.println("quiere: ");
            System.out.println("  1) Crear cuenta Nueva");
            System.out.println("  2) Ingresar a una cuenta Existente");
            System.out.println("  3) logout");
            int command = sc.nextInt();
            sc.nextLine();
            switch (command) {
                case 1 -> createAccount(user);
                case 2 -> AccessAccount(user);
                case 3 -> wasLogout = true;
                default -> System.out.println("Invalid Argument");
            }
        }
    }

    private static void AccessAccount(User user) {
        boolean changeAccount = false;
        while (!changeAccount) {
            System.out.println("quiere: ");
            System.out.println("   1) ingresar a cuentas");
            System.out.println("   2) ver cuales cuentas existen");
            System.out.println("   3) regresar");

            int command = sc.nextInt();
            sc.nextLine();

            Account account;
            switch (command) {
                case 1 -> {
                    System.out.println("ingrese id de cuenta");
                    int id = sc.nextInt();
                    sc.nextLine();
                    try {
                        account = user.accountList.get(id);
                        accountActions(account, user);
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("id Invalido");
                    }
                }
                case 2 -> {
                    for (Account a : user.accountList) {
                        String tipo;
                        if (a instanceof Checking) tipo = "Corriente";
                        else if (a instanceof Savings) tipo = "Ahorros";
                        else tipo = "Inversion de plazo fijo";
                        System.out.println(a.getId() + ": " + tipo);
                    }
                }
                case 3 -> changeAccount = true;
                default -> System.out.println("Commando invalido");
            }
        }
    }

    private static void createAccount(User user) {
        boolean goBack = false;
        while (!goBack) {
            System.out.println("ingrese cuanto dinero quiere ingresar");
            double money = sc.nextDouble();
            sc.nextLine();
            System.out.println("ingrese el tipo de cuenta que quiere");
            System.out.println("  1) Corriente");
            System.out.println("  2) Ahorros");
            System.out.println("  3) Inversion a plazo fijo");
            System.out.println("  4) regresar");

            int command = sc.nextInt();
            sc.nextLine();

            Account account;
            switch (command) {
                case 1 -> account = user.createCheckingAccount(money);
                case 2 -> account = user.createSavingsAccount(money);
                case 3 -> {
                    System.out.println("ingrese el plazo");
                    int term = sc.nextInt();
                    sc.nextLine();
                    account = user.createFixedTermInvestingAccount(money, term);
                }
                case 4 -> {
                    goBack = true;
                    continue;
                }
                default -> {
                    System.out.println("invalid account");
                    continue;
                }
            }

            System.out.println("el id de tu cuenta es: " + account.getId());
            accountActions(account, user);
            goBack = true;

        }
    }

    private static void accountActions(Account account, User user) {
        boolean endActions = false;
        if (account instanceof IAccount) {
            while (!endActions) {
                System.out.println("ingrese lo que quiere hacer");
                System.out.println("  1) ver balance");
                System.out.println("  2) Transferir");
                System.out.println("  3) depositar");
                System.out.println("  4) retirar");
                System.out.println("  5) salir");

                int command = sc.nextInt();
                sc.nextLine();

                switch (command) {
                    case 1 -> System.out.println(account.CheckBalance());
                    case 2 -> {
                        System.out.println("ingrese el valor a transferir");
                        double money = sc.nextDouble();
                        sc.nextLine();
                        System.out.println("escriba el id del otro usuario");
                        String id = sc.nextLine();
                        User otherUser = null;
                        try {
                            for (User u : User.usersList) {
                                if (u.getId().equals(id)) otherUser = u;
                            }
                            if (otherUser == null) throw new IllegalArgumentException("Usuario no existente");
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                            continue;
                        }
                        try {
                            System.out.println("escriba el id de la cuenta del otro usuario");
                            int accountId = sc.nextInt();
                            sc.nextLine();

                            IAccount otherAccount = (IAccount) otherUser.accountList.get(accountId);
                            ((Savings) account).transfer(money, otherAccount);
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.out.println("id de cuenta invalido: la otra cuenta puede que no exista");
                        } catch (ClassCastException e) {
                            System.out.println("cuenta es de Inversion a plazo fijo, no se puede transferir a esa cuenta ");
                        }
                    }
                    case 3 -> {
                        System.out.println("ingrese el monto a depositar");
                        double money = sc.nextDouble();
                        sc.nextLine();
                        ((IAccount) account).deposit(money);
                    }
                    case 4 -> {
                        System.out.println("ingrese el monto a retirar");
                        double money = sc.nextDouble();
                        sc.nextLine();
                        try {
                            ((IAccount) account).retire(money);
                        } catch (IllegalArgumentException e){
                            System.out.println(e.getMessage());
                        }
                    }
                    case 5 -> endActions = true;
                    default -> System.out.println("Commando Invalido");
                }
            }
        }
        else {
            while (!endActions) {
                System.out.println("ingrese lo que quiere hacer");
                System.out.println("  1) ver balance");
                System.out.println("  2) ver plazo");
                System.out.println("  3) cerrar cuenta");
                System.out.println("  4) salir");

                int command = sc.nextInt();
                sc.nextLine();
                switch (command){
                    case 1 -> System.out.println(account.CheckBalance());
                    case 2 -> System.out.println(((FixedTermInvestment) account).getTerm());
                    case 3 -> {
                        System.out.println( "cuenta cerrada! hiciste: "  + ((FixedTermInvestment)account).Close());
                        user.accountList.remove(account);
                    }
                    case 4 -> endActions = true;
                }
            }
        }
    }

    private static void passTime() {
        for (User u : User.usersList) {
            u.passTime();
        }
    }


}

