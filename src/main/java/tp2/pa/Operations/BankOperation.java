package tp2.pa.Operations;

import tp2.pa.controller.AccountController;
import tp2.pa.controller.TransactionController;
import tp2.pa.model.User;
import tp2.pa.services.AccountService;
import tp2.pa.controller.AuthController;
import tp2.pa.services.TransactionService;

import java.util.Scanner;

public interface BankOperation {
    String getName(); // Nombre de la operación para mostrar en el menú
    void execute(Scanner scanner, User currentUser, AccountController account, AuthController auth, TransactionController transaction) throws Exception;
}