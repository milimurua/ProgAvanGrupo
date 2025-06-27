package tp2.pa.Operations;

import tp2.pa.controller.AccountController;
import tp2.pa.controller.TransactionController;
import tp2.pa.model.User;
import tp2.pa.controller.AuthController;


import java.util.Scanner;

public class ChangePasswordOperation implements BankOperation {
    @Override
    public String getName() {
        return "Cambiar contraseña";
    }

    @Override
    public void execute(Scanner scanner, User currentUser, AccountController account, AuthController auth, TransactionController transaction) throws Exception {
        System.out.print("Ingrese su contraseña actual: ");
        String currentPassword = scanner.nextLine();

        if (auth.verifyPassword(currentUser.getName(), currentPassword)) {
            System.out.println("Contraseña actual correcta.");
            System.out.print("Ingrese la nueva contraseña: ");
            String newPassword = scanner.nextLine();
            auth.changePassword(currentUser, newPassword);
            System.out.println("Contraseña actualizada correctamente.");
        } else {
            System.out.println("Contraseña actual incorrecta. No se realizó el cambio.");
        }
    }
}