package tp2.pa.Operations;

import tp2.pa.controller.AccountController;
import tp2.pa.controller.AuthController;
import tp2.pa.controller.TransactionController;
import tp2.pa.model.User;

import java.util.Scanner;

public class WithdrawOperation implements BankOperation {
    @Override
    public String getName() {
        return "Retirar dinero";
    }

    @Override
    public void execute(Scanner scanner, User currentUser, AccountController account, AuthController auth, TransactionController transaction) throws Exception {
        System.out.print("Monto a retirar: ");
        double amount = Double.parseDouble(scanner.nextLine());
        transaction.withdraw(currentUser, amount);
        System.out.println("Retiro exitoso. Nuevo saldo: $" + currentUser.getAccount().getBalance());


    }

}