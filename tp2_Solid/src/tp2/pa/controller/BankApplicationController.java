package tp2.pa.controller;

import tp2.pa.model.Account;
import tp2.pa.model.User;
import tp2.pa.services.AccountService;
import tp2.pa.services.AuthService;
import tp2.pa.services.TransactionService;
import javax.naming.AuthenticationException;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *Todo lo del menú y lectura de consola.
 */
public class BankApplicationController {
    private final Scanner scanner = new Scanner(System.in);
    private final AuthService authService;
    private final AccountService accountService;
    private final TransactionService transactionService;

    private User currentUser;

    public BankApplicationController(AccountService accountService, AuthService authService, TransactionService transactionService) {
        this.accountService = accountService;
        this.authService = authService;
        this.transactionService = transactionService;
    }

    //método que pide que inicies sesión o que crees un usuario
    public void start(){
        int opc;
        do{
            System.out.println("1. Iniciar Sesión");
            System.out.println("2. Crear un usuario y cuenta");
            System.out.println("0. Salir");
            System.out.println("Opción: ");
            opc = Integer.parseInt(scanner.nextLine());

            switch (opc) {
                case 1: loginFlow();
                    break;
                case 2: registerFlow();
                    break;
                case 0: System.out.println("Chau");
                    break;
                default: System.out.println("Opcion no válida");
            }
        }while (opc != 0);
    }

    private void loginFlow(){
        System.out.print("Usuario: ");
        String userName = scanner.nextLine();
        System.out.print("Contraseña: ");
        String password = scanner.nextLine();

        try {
            currentUser = authService.login(userName, password);
            System.out.println("Login exitoso. ¡Bienvenido, " + currentUser.getName() + "!");
            operationsMenu();
        } catch (SQLException se) {
            System.err.println("Error al acceder a la base de datos: " + se.getMessage());
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
    }

    private void registerFlow(){
        System.out.print("Nombre: ");
        String name = scanner.nextLine();

        System.out.print("Contraseña: ");
        String password = scanner.nextLine();

        System.out.print("Saldo inicial: ");
        double balance = Double.parseDouble(scanner.nextLine());

        try {
            int[] ids = accountService.createUserWithAccount(name, password, balance);
            //Muestra el id de la cuenta y usuario
            System.out.printf("Usuario (id=%d) y cuenta (id=%d) creados correctamente.%n", ids[0], ids[1]);

            // Si necesitas los objetos en memoria:
            Account account = new Account(ids[1], balance);
            this.currentUser = new User(ids[0], name, account, password);
            //lo lleva al menu
            operationsMenu();
        } catch (Exception e) {
            System.err.println("Error al crear usuario y cuenta: " + e.getMessage());
        }
    }

    private void operationsMenu() throws SQLException {
        int option;
        Double amount = 0.0;
        do{
            System.out.println("\n=== Menú de operaciones ===");
            System.out.println("1. Consultar saldo");
            System.out.println("2. Depositar dinero");
            System.out.println("3. Retirar dinero");
            System.out.println("4. Transferencia");
            System.out.println("5. Cambiar titular");
            System.out.println("6. Eliminar cuenta");
            System.out.println("7. Cambiar contraseña");
            System.out.println("8. Solicitar préstamo");
            System.out.println("9. Ver detalles de cuenta");
            System.out.println("10. Crear cuenta");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");
            option = Integer.parseInt(scanner.nextLine());

            switch (option) {

                case 1:
                    double currentBalance = accountService.getBalance(currentUser.getAccount().getIdAccount());
                    System.out.println("Saldo actual: $" + currentBalance);
                    break;
                case 2:
                    System.out.print("Monto a depositar: ");
                    amount = Double.parseDouble(scanner.nextLine());
                    try {
                        transactionService.deposit(currentUser, amount);
                        System.out.println("Depósito exitoso. Nuevo saldo: $" + currentUser.getAccount().getBalance());
                    } catch (Exception e) {
                        System.err.println("Error en depósito: " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.print("Monto a retirar: ");
                    amount = Double.parseDouble(scanner.nextLine());
                    try {
                        transactionService.withdraw(currentUser, amount);
                        System.out.println("Retiro exitoso. Nuevo saldo: $" + currentUser.getAccount().getBalance());
                    } catch (Exception e) {
                        System.err.println("Error en retiro: " + e.getMessage());
                    }
                    break;
                case 4:
                    System.out.print("ID cuenta destino: ");
                    int destId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Monto a transferir: ");
                    amount= Double.parseDouble(scanner.nextLine());
                    try {
                        transactionService.transfer(currentUser, destId, amount);
                        System.out.println("Transferencia exitosa. Nuevo saldo: $" + currentUser.getAccount().getBalance());
                    } catch (Exception e) {
                        System.err.println("Error en transferencia: " + e.getMessage());
                    }
                    break;
                case 5:
                    System.out.print("Nuevo titular: ");
                    String newHolder = scanner.nextLine();
                    try {
                        accountService.changeHolder(currentUser, newHolder);
                        System.out.println("Titular actualizado correctamente. Nuevo titular: " + currentUser.getName());
                    } catch (Exception e) {
                        System.err.println("Error al cambiar titular: " + e.getMessage());
                    }
                    break;
                case 6:
                    System.out.print("ID de la cuenta a eliminar: ");
                    int acctId = Integer.parseInt(scanner.nextLine());
                    System.out.print("¿Estás seguro de que deseas eliminar la cuenta " + acctId + "? (s/n): ");
                    if (scanner.nextLine().equalsIgnoreCase("s")) {
                        try {
                            accountService.deleteAccount(currentUser, acctId);
                            System.out.println("Cuenta " + acctId + " eliminada correctamente.");
                        } catch (Exception e) {
                            System.err.println("Error al eliminar cuenta: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Operación cancelada.");
                    }
                    break;
                case 7:
                    System.out.print("Nueva Contraseña: ");
                    String newPassword = scanner.nextLine();
                    try {
                        authService.changePassword(currentUser, newPassword);
                        System.out.println("Contraseña actualizada correctamente.");
                    } catch (Exception e) {
                        System.err.println("Error al cambiar la contraseña: " + e.getMessage());
                    }
                    break;
                case 8:
                    System.out.print("Monto del préstamo: ");
                    amount = Double.parseDouble(scanner.nextLine());
                    try {
                        transactionService.askForALoan(currentUser, amount);
                    } catch (Exception e) {
                        System.err.println("Error al solicitar préstamo: " + e.getMessage());
                    }
                    break;
                case 9:
                    try {
                        String details = accountService.accountDetails(currentUser);
                        System.out.println(details);
                    } catch (Exception e) {
                        System.err.println("Error al obtener detalles de cuenta: " + e.getMessage());
                    }
                    break;
                case 10:
                    System.out.print("Saldo inicial nueva cuenta: ");
                    double balance = Double.parseDouble(scanner.nextLine());
                    try {
                        int accountId = accountService.createNewAccount(currentUser.getId(), balance);
                        System.out.println("Cuenta creada con id=" + accountId);
                    } catch (Exception e) {
                        System.err.println("Error al crear cuenta: " + e.getMessage());
                    }
                    break;
                case 0: System.out.println("Cierre de Sesión");
                default: System.out.println("Opcion no válida");
            }
        }while (option!= 0);
    }
}


