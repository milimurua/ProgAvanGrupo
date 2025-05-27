package tp2.pa;

import tp2.pa.repository.AccountRepository;
import tp2.pa.util.DBConnexion;
import tp2.pa.controller.BankApplicationController;
import tp2.pa.services.AuthService;
import tp2.pa.services.AccountService;
import tp2.pa.services.TransactionService;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Incia la conexión con la base de datos, iniciar los serivicos y llama
 * al controlador para que muestre el menú
 */
public class BankApplication{
    public static void main(String[] args) throws SQLException {
        //Inicializa la conexión
        try (Connection conn = DBConnexion.getConnection()) {
            System.out.println("¡Conexión exitosa a " + conn.getMetaData().getURL());
        } catch (Exception e) {
            System.out.println("no se pudo conectar");
            e.printStackTrace();
        }

        //Crea instancias de servicios
        AuthService authSvc = new AuthService();
        AccountService accountSvc = new AccountService();
        TransactionService transSvc = new TransactionService();

        //Arranca el controlador
        new BankApplicationController(accountSvc, authSvc, transSvc)
                .start();
    }
}

