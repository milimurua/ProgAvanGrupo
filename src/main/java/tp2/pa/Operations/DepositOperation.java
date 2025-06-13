package tp2.pa.Operations;

import tp2.pa.controller.AccountController;
import tp2.pa.model.User;
import tp2.pa.controller.AuthController;
import tp2.pa.controller.TransactionController;


import java.util.Scanner;

public class DepositOperation implements BankOperation {
    @Override
    public String getName() {
        return "Depositar dinero";
    }

    @Override
    public void execute(Scanner scanner, User currentUser, AccountController account, AuthController auth, TransactionController transaction) throws Exception {
        System.out.print("Monto a depositar: ");
        double amount = Double.parseDouble(scanner.nextLine());
        transaction.deposit(currentUser, amount);
        System.out.println("Depósito exitoso. Nuevo saldo: $" + currentUser.getAccount().getBalance());

    }
}