package tp2.pa.Operations;

import tp2.pa.controller.AccountController;
import tp2.pa.controller.AuthController;
import tp2.pa.controller.TransactionController;
import tp2.pa.model.User;

import java.util.Scanner;

public class ChangeHolderOperation implements BankOperation {
    @Override
    public String getName() {
        return "Cambiar titular de cuenta";
    }

    @Override
    public void execute(Scanner scanner, User currentUser, AccountController account, AuthController auth, TransactionController transaction) throws Exception {
        System.out.print("Nuevo titular para la cuenta actual: ");
        String newHolder = scanner.nextLine();
        account.changeHolder(currentUser, newHolder);
        System.out.println("Titular de cuenta actualizado correctamente. Nuevo titular: " + currentUser.getName());
    }
}
