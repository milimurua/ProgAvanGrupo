package tp2.pa;

import tp2.pa.model.Account;
import tp2.pa.model.User;
import tp2.pa.util.DBConnexion;
import tp2.pa.controller.AuthController;
import tp2.pa.Operations.*;
import tp2.pa.controller.AccountController;
import tp2.pa.controller.TransactionController;
import javax.naming.AuthenticationException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import tp2.pa.BankAppManager;


public class BankApplication {

    public static void main(String[] args) {

        Scanner scanner = new Scanner (System.in);

        //inicia conexion a la bd
        try (Connection conn = DBConnexion.getConnection()){
            System.out.println("Conexión exitosa a " + conn.getMetaData().getURL());
        }catch (Exception e){
            System.out.println("No se pudo conectar a la base de datos. Asegúrate de que este funcionando");
            e.printStackTrace();
            return;
        }


        //instancias que inician controlador
        AccountController accountController = new AccountController();
        AuthController authController = new AuthController();
        TransactionController transactionController = new TransactionController();

        //nueva instancia de la nueva clase para segmentar el main
        BankAppManager bankAppManager = new BankAppManager(scanner, accountController, authController, transactionController);

        bankAppManager.start(); //funcion que inicia la aplicacion bancaria
        scanner.close();
        System.out.println("App terminada");



    }

}


