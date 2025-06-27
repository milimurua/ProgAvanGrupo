package tp2.pa.Operations;

import tp2.pa.controller.AccountController;
import tp2.pa.controller.TransactionController;
import tp2.pa.model.User;
import tp2.pa.controller.AuthController;

import java.util.Scanner;

public class LoanRequestOperation implements BankOperation {
    @Override
    public String getName() {
        return "Solicitar préstamo";
    }

    @Override
    public void execute(Scanner scanner, User currentUser, AccountController account, AuthController auth, TransactionController transaction) throws Exception {
        System.out.print("Monto del préstamo solicitado: ");
        double amount = Double.parseDouble(scanner.nextLine());
        transaction.askForALoan(currentUser, amount);
        System.out.println("Préstamo solicitado exitosamente.");
    }
}