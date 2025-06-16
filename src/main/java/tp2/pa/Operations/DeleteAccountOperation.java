// tp2.pa.operations/DeleteAccountOperation.java
package tp2.pa.Operations;

import tp2.pa.controller.AccountController;
import tp2.pa.controller.TransactionController;
import tp2.pa.model.User;
import tp2.pa.services.AccountService;
import tp2.pa.controller.AuthController;
import tp2.pa.services.TransactionService;
import java.util.Scanner;

public class DeleteAccountOperation implements BankOperation {
    @Override
    public String getName() {
        return "Eliminar cuenta";
    }

    @Override
    public void execute(Scanner scanner, User currentUser, AccountController account, AuthController auth, TransactionController transaction) throws Exception {
        System.out.print("ID de la cuenta a eliminar: ");
        int acctId = Integer.parseInt(scanner.nextLine());
        System.out.print("¿Estás seguro de que deseas eliminar la cuenta " + acctId + "? (s/n): ");
        if (scanner.nextLine().equalsIgnoreCase("s")) {
            account.deleteAccount(currentUser, acctId);
            System.out.println("Cuenta " + acctId + " eliminada correctamente.");
        } else {
            System.out.println("Operación cancelada.");
        }
    }
}