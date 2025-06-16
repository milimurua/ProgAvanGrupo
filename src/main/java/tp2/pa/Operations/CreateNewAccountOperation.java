// tp2.pa.operations/CreateNewAccountOperation.java
package tp2.pa.Operations;

import tp2.pa.controller.AccountController;
import tp2.pa.controller.TransactionController;
import tp2.pa.model.User;
import tp2.pa.services.AccountService;
import tp2.pa.controller.AuthController;
import tp2.pa.services.TransactionService;
import java.util.Scanner;

public class CreateNewAccountOperation implements BankOperation {
    @Override
    public String getName() {
        return "Crear nueva cuenta";
    }

    @Override
    public void execute(Scanner scanner, User currentUser, AccountController account, AuthController auth, TransactionController transaction) throws Exception {
        System.out.print("Saldo inicial para la nueva cuenta: ");
        double initialBalance = Double.parseDouble(scanner.nextLine());
        int accountId = account.createNewAccount(currentUser.getId(), initialBalance);
        System.out.println("Nueva cuenta creada con ID: " + accountId);
    }
}
