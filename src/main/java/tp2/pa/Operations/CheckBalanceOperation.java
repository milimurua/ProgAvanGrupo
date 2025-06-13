package tp2.pa.Operations;

import tp2.pa.model.User;
import tp2.pa.controller.AccountController;
import tp2.pa.controller.AuthController;
import tp2.pa.controller.TransactionController;

import java.util.Scanner;

public class CheckBalanceOperation implements BankOperation {
    @Override
    public String getName() {
        return "Consultar saldo";
    }

    @Override
    public void execute(Scanner scanner, User currentUser, AccountController account, AuthController auth, TransactionController transaction) throws Exception {

        double currentBalance = currentUser.getAccount().getBalance();
        System.out.println("Saldo actual: $" + currentBalance);


    }
}
