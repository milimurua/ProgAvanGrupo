// tp2.pa.BankApplication.java
package tp2.pa;

import tp2.pa.model.Account;
import tp2.pa.model.User;
import tp2.pa.util.DBConnexion;
import tp2.pa.controller.AuthController;
import tp2.pa.Operations.*;
import tp2.pa.controller.AccountController;
import tp2.pa.controller.TransactionController;
import javax.naming.AuthenticationException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Inicia la conexión con la base de datos, iniciar los servicios y maneja
 * toda la interacción con el usuario a través de los menús.
 */
public class BankApplication {

    private static final Scanner scanner = new Scanner(System.in);
    private static AuthController authService;
    private static AccountController accountController;
    private static TransactionController transactionController;
    private static User currentUser;

    // Mapa para almacenar las operaciones bancarias
    private static final Map<Integer, BankOperation> operations = new LinkedHashMap<>();

    public static void main(String[] args) {
        // Inicializa la conexión
        try (Connection conn = DBConnexion.getConnection()) {
            System.out.println("¡Conexión exitosa a " + conn.getMetaData().getURL());
        } catch (Exception e) {
            System.out.println("No se pudo conectar a la base de datos. Asegúrate de que esté funcionando.");
            e.printStackTrace();
            return;
        }

        //instancias de servicios
        authService = new AuthController();
        accountController = new AccountController();
        transactionController = new TransactionController();


        initializeOperations();

        startApplicationMenu();
    }

    private static void initializeOperations() {
        // operaciones con map
        operations.put(1, new CheckBalanceOperation());
        operations.put(2, new DepositOperation());

    }


    private static void startApplicationMenu() {
        int opc;
        do {
            System.out.println("\n--- Menú Principal ---");
            System.out.println("1. Iniciar Sesión");
            System.out.println("2. Crear un usuario y cuenta");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            opc = Integer.parseInt(scanner.nextLine());

            switch (opc) {
                case 1:
                    loginFlow();
                    break;
                case 2:
                    registerFlow();
                    break;
                case 0:
                    System.out.println("¡Hasta pronto!");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, intenta de nuevo.");
            }
        } while (opc != 0);
    }

    private static void loginFlow() {
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
            System.err.println("Error de autenticación: " + e.getMessage());
        }
    }

    private static void registerFlow() {
        System.out.print("Nombre de usuario: ");
        String name = scanner.nextLine();

        System.out.print("Contraseña: ");
        String password = scanner.nextLine();

        System.out.print("Saldo inicial para la cuenta: ");
        double balance = Double.parseDouble(scanner.nextLine());

        try {
            int[] ids = accountController.createUserWithAccount(name, password, balance);
            System.out.printf("Usuario (id=%d) y cuenta (id=%d) creados correctamente.%n", ids[0], ids[1]);

            Account account = new Account(ids[1], balance);
            currentUser = new User(ids[0], name, account, password);
            operationsMenu();
        } catch (Exception e) {
            System.err.println("Error al crear usuario y cuenta: " + e.getMessage());
        }
    }

    private static void operationsMenu() {
        int option;
        do {
            System.out.println("\n=== Menú de Operaciones ===");
            for (Map.Entry<Integer, BankOperation> entry : operations.entrySet()) {
                System.out.println(entry.getKey() + ". " + entry.getValue().getName());
            }
            System.out.println("0. Cerrar Sesión");
            System.out.print("Elige una opción: ");

            try {
                option = Integer.parseInt(scanner.nextLine());

                if (option == 0) {
                    System.out.println("Cerrando sesión...");
                    currentUser = null;
                } else if (operations.containsKey(option)) {
                    BankOperation selectedOperation = operations.get(option);
                    selectedOperation.execute(scanner, currentUser, accountController, authService, transactionController);
                } else {
                    System.out.println("Opción no válida. Por favor, intenta de nuevo.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Entrada inválida. Por favor, ingresa un número.");
                option = -1; // Para que el bucle continúe
            } catch (Exception e) {
                System.err.println("Error durante la operación: " + e.getMessage());
                option = -1; // Para que el bucle continúe
            }
        } while (option != 0);
    }
}


