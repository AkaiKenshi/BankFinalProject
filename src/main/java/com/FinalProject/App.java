package com.FinalProject;

import com.FinalProject.Accounts.*;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class App {
    static boolean exit = false;
    static int mes;

    public static void main(String[] args) {
        while (!exit) {
            System.out.println("el mes es " + mes);
            printOptions("Quiere", "Ingresar/Crear Usuario", "Pasar el tiempo", "Terminar Aplicacion");
            int command = safeUserInt();
            switch (command) {
                case 1 -> enterAsUser();
                case 2 -> passTime();
                case 3 -> exit = true;
                default -> System.out.println("Argumento Invalido");
            }
        }

    }

    public static int safeUserInt() {
        try {
            Scanner sc = new Scanner(system.in);
            return sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Argumento Invalido");
            return safeUserInt();
        }
    }

    public static double safeUserDouble() {
        try {
            Scanner sc = new Scanner(system.in);
            return sc.nextDouble();
        } catch (InputMismatchException e) {
            System.out.println("Argumento Invalido");
            return safeUserInt();
        }
    }

    public static void printOptions(String initMsg, String... options) {
        System.out.println(initMsg);
        for (int i = 0; i < options.length; i++) {
            System.out.println("    " + i + ") " + options[i]);
        }
    }

    private static void enterAsUser() {
        boolean goBack = false;
        while (!goBack) {
            printOptions("¿Qué necesita?: ", "Crear Usuario Nuevo", "Ingresar a un Usuario Existente", "Ingresar a un Usuario Existente", "Regresar");
            int command = safeUserInt();
            switch (command) {
                case 1 -> createUsuario();
                case 2 -> ingresarUsuario();
                case 3 -> goBack = true;
                default -> System.out.println("Argumento Invalido");
            }
        }
    }

    private static void ingresarUsuario() {
        boolean wasLogin = false;
        Scanner sc = new Scanner(System.in);
        while (!wasLogin) {
            System.out.println("Ingrese su ID");
            String id = sc.nextLine();
            System.out.println("Ingrese su Contraseña");
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
        Scanner sc = new Scanner(system.in);
        boolean wasLogin = false;
        while (!wasLogin) {
            System.out.println("Ingrese su nombre");
            String name = sc.nextLine();

            System.out.println("Ingrese su apellido");
            String lastName = sc.nextLine();

            System.out.println("Ingrese su nombre de usuario");
            String userName = sc.nextLine();

            System.out.println("Ingrese su id");
            String id = sc.nextLine().trim();

            System.out.println("Ingrese su Contraseña");
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
                    else if (!command.equals("s") && !command.equals("n")) System.out.println("Comando Invalido");
                }
            }
        }
    }

    private static void userActions(User user) {
        boolean wasLogout = false;
        while (!wasLogout) {
            printOptions("¿Qué necesita?: ", "Crear cuenta Nueva", "Ingresar a una cuenta Existente", "Cerrar sesión");
            int command = safeUserInt();
            switch (command) {
                case 1 -> createAccount(user);
                case 2 -> AccessAccount(user);
                case 3 -> wasLogout = true;
                default -> System.out.println("Argumento Invalido");
            }
        }
    }

    private static void AccessAccount(User user) {
        boolean changeAccount = false;
        while (!changeAccount) {
            printOptions("¿Qué necesita?: ", "Ingresar a cuentas", "Ver cuales cuentas existen", "Regresar");

            int command = safeUserInt();

            Account account;
            switch (command) {
                case 1 -> {
                    System.out.println("ingrese id de cuenta");
                    int id = safeUserInt();
                    try {
                        account = user.accountList.get(id);
                        accountActions(account, user);
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("ID Invalido");
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
                default -> System.out.println("Comando invalido");
            }
        }
    }

    private static void createAccount(User user) {
        boolean goBack = false;
        while (!goBack) {
            System.out.println("Ingrese cuanto dinero quiere ingresar");
            double money = safeUserDouble();
            printOptions("Ingrese el tipo de cuenta que quiere", "Corriente", "Ahorros", "Inversion a plazo fijo", "regresar")

            int command = safeUserInt();

            Account account;
            switch (command) {
                case 1 -> account = user.createCheckingAccount(money);
                case 2 -> account = user.createSavingsAccount(money);
                case 3 -> {
                    System.out.println("Ingrese el plazo");
                    int term = safeUserInt();
                    account = user.createFixedTermInvestingAccount(money, term);
                }
                case 4 -> {
                    goBack = true;
                    continue;
                }
                default -> {
                    System.out.println("Cuenta Inválida");
                    continue;
                }
            }

            System.out.println("El ID de tu cuenta es: " + account.getId());
            accountActions(account, user);
            goBack = true;

        }
    }

    private static void accountActions(Account account, User user) {
        boolean endActions = false;
        if (account instanceof IAccount) {
            while (!endActions) {
                printOptions("Ingrese lo que quiere hacer", "Ver balance", "Transferir", "Depositar", "Retirar", "Salir");

                int command = safeUserInt();

                switch (command) {
                    case 1 -> System.out.println(account.CheckBalance());
                    case 2 -> {
                        System.out.println("Ingrese el valor a transferir");
                        double money = safeUserDouble();
                        System.out.println("Escriba el ID del otro usuario");
                        String id = Integer.toString(safeUserInt());
                        try {
                            User otherUser = User.usersList.get(id);
                            IAccount otherAccount = otherUser.accountList()
                            ((IAccount) account).transfer(money, otherUser.accountList());
                        } catch (NoSuchElementException e) {
                            System.out.println("Usuario no existe");
                        }
                    }
                    case 3 -> {
                        System.out.println("Ingrese el monto a depositar");
                        double money = safeUserDouble();

                        ((IAccount) account).deposit(money);
                    }
                    case 4 -> {
                        System.out.println("Ingrese el monto a retirar");
                        double money = safeUserDouble();
                        try {
                            ((IAccount) account).retire(money);
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    case 5 -> endActions = true;
                    default -> System.out.println("Comando Invalido");
                }
            }
        } else {
            while (!endActions) {
                printOptions("Ingrese lo que quiere hacer", "Ver balance", "Ver plazo", "Cerrar cuenta", "Salir");

                int command = safeUserInt();
                switch (command) {
                    case 1 -> System.out.println(account.CheckBalance());
                    case 2 -> System.out.println(((FixedTermInvestment) account).getTerm());
                    case 3 -> {
                        System.out.println("Cuenta cerrada éxitosamente! hiciste: " + ((FixedTermInvestment) account).Close());
                        user.accountList.remove(account);
                    }
                    case 4 -> endActions = true;
                }
            }
        }
    }

    private static void passTime() {
        for (Map.Entry<String, User> u : User.usersList.entrySet()) {
            u.getValue().passTime();
        }
    }


}

