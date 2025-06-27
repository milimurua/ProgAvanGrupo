package tp2.pa.Operations;

import tp2.pa.controller.AccountController;
import tp2.pa.controller.AuthController;
import tp2.pa.model.User;
import tp2.pa.controller.TransactionController;

import java.util.Scanner;

public class TransferOperation implements BankOperation {
    @Override
    public String getName() {
        return "Realizar transferencia";
    }

    @Override
    public void execute(Scanner scanner, User currentUser, AccountController account, AuthController auth, TransactionController transaction) throws Exception {
        System.out.print("ID de la cuenta destino: ");
        int destId = Integer.parseInt(scanner.nextLine());
        System.out.print("Monto a transferir: ");
        double amount = Double.parseDouble(scanner.nextLine());
        transaction.transfer(currentUser, destId, amount);
        System.out.println("Transferencia exitosa. Nuevo saldo: $" + currentUser.getAccount().getBalance());
    }
}