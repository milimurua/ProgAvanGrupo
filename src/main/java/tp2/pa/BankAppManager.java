package tp2.pa;

import tp2.pa.Operations.BankOperation;
import tp2.pa.controller.AccountController;
import tp2.pa.controller.AuthController;
import tp2.pa.controller.TransactionController;
import tp2.pa.model.Account;
import tp2.pa.model.User;
import tp2.pa.Operations.*;

import javax.naming.AuthenticationException;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class BankAppManager {

    private final Scanner scanner;
    private final AccountController accountController;
    private final AuthController authController;
    private final TransactionController transactionController;
    private User currentUser;
    private final Map<Integer, BankOperation> operations = new LinkedHashMap<>();

    public BankAppManager(Scanner scanner, AccountController accountController, AuthController authController, TransactionController transactionController) {
        this.scanner = scanner;
        this.accountController = accountController;
        this.authController = authController;
        this.transactionController = transactionController;
        initializeOperations();
    }

    /**
     *inicializa las operaciones bancarias, y las introduce en un map
     * Map: clave, valor -> opción del menu, operación del menu (objeto implementa intefaz bankoperation)
     */
    private void initializeOperations() {
        operations.put(1, new CheckBalanceOperation());
        operations.put(2, new DepositOperation());
        operations.put(3, new WithdrawOperation());
        operations.put(4, new TransferOperation());
        operations.put(5, new ChangeHolderOperation());
        operations.put(6, new DeleteAccountOperation());
        operations.put(7, new ChangePasswordOperation());
        operations.put(8, new LoanRequestOperation());
        operations.put(9, new AccountDetailsOperation());
        operations.put(10, new CreateNewAccountOperation());
    }

    /**
     * Menu inicial: permite registrarse o crear una cuenta
     */
    public void start() {
        int opc;
        do {
            System.out.println("\n--- Menú Principal ---");
            System.out.println("1. Iniciar Sesión");
            System.out.println("2. Crear un usuario y cuenta");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            try {
                opc = Integer.parseInt(scanner.nextLine());

                switch (opc) {
                    case 1: loginFlow(); break;
                    case 2: registerFlow(); break;
                    case 0: System.out.println("¡Hasta pronto!"); break;
                    default: System.out.println("Opción no válida. Por favor, intenta de nuevo."); //si es un entero no válido
                }
            } catch (NumberFormatException e) { // si ingesa cualquier simbolo o caracter
                System.err.println("Entrada inválida. Por favor, ingresa un número.");
                opc = -1; // Para asegurar que el bucle continúe
            }
        } while (opc != 0);
    }

    /**
     * Maneja el flujo de inicio de sesión del usuario.
     */
    private void loginFlow() {
        System.out.print("Usuario: ");
        String userName = scanner.nextLine();
        System.out.print("Contraseña: ");
        String password = scanner.nextLine();

        try {
            currentUser = authController.login(userName, password);
            System.out.println("Login exitoso. ¡Bienvenido, " + currentUser.getName() + "!");
            operationsMenu(); // Lleva al menú de operaciones si el login es exitoso
        } catch (SQLException se) {
            System.err.println("Error al acceder a la base de datos: " + se.getMessage());
        } catch (AuthenticationException e) {
            System.err.println("Error de autenticación: " + e.getMessage());
        }
    }

    /**
     * Maneja el flujo de registro de un nuevo usuario y su cuenta.
     */
    private void registerFlow() {
        System.out.print("Nombre de usuario: ");
        String name = scanner.nextLine();

        System.out.print("Contraseña: ");
        String password = scanner.nextLine();

        System.out.print("Saldo inicial para la cuenta: ");
        double balance = Double.parseDouble(scanner.nextLine());

        try {
            int[] ids = accountController.createUserWithAccount(name, password, balance); // Devuelve ids para cuenta y usuario
            System.out.printf("Usuario (id=%d) y cuenta (id=%d) creados correctamente.%n", ids[0], ids[1]);

            // Se asume que el usuario recién creado es el current user para la sesión
            Account account = new Account(ids[1], balance);
            currentUser = new User(ids[0], name, account, password);
            operationsMenu(); // Lleva al menú de operaciones después del registro
        } catch (Exception e) {
            System.err.println("Error al crear usuario y cuenta: " + e.getMessage());
        }
    }

    /**
     * Muestra el menú de operaciones bancarias y gestiona su ejecución.
     */
    private void operationsMenu() {
        int option;
        do {
            System.out.println("\n=== Menú de Operaciones ===");
            // Imprime las opciones dinámicamente usando el mapa de operaciones
            for (Map.Entry<Integer, BankOperation> entry : operations.entrySet()) {
                System.out.println(entry.getKey() + ". " + entry.getValue().getName());
            }
            System.out.println("0. Cerrar Sesión");
            System.out.print("Elige una opción: ");

            try {
                option = Integer.parseInt(scanner.nextLine());

                if (option == 0) {
                    System.out.println("Cerrando sesión...");
                    currentUser = null; // Limpiar el usuario actual al cerrar sesión
                } else if (operations.containsKey(option)) {
                    BankOperation selectedOperation = operations.get(option);
                    // Pasa las dependencias necesarias para que la operación se ejecute
                    selectedOperation.execute(
                            scanner,
                            currentUser,
                            accountController,
                            authController,
                            transactionController
                    );
                } else {
                    System.out.println("Opción no válida. Por favor, intenta de nuevo.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Entrada inválida. Por favor, ingresa un número.");
                option = -1; // Para asegurar que el bucle continúe si la entrada es inválida
            } catch (Exception e) {
                System.err.println("Error durante la operación: " + e.getMessage());
                // Considera si quieres que la sesión se cierre en caso de error grave
                // o si el menú de operaciones debe continuar. Aquí se continúa.
                option = -1;
            }
        } while (option != 0);
    }

}