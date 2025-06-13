package tp2.pa.controller;

import tp2.pa.model.User;
import tp2.pa.services.AccountService;
import tp2.pa.services.UserService;
import tp2.pa.util.DBConnexion;

import java.sql.Connection;
import java.sql.SQLException;

//crear cuenta, consultar saldo
public class AccountController {
    /**
     * Método donde se crea un nuevo usuario
     */
    private final UserService userService = new UserService();
    private final AccountService accountService = new AccountService();

    /**
     * Método para crear una cuenta que llama a la clase AccountRepository que se encarga de los CRUD de las cuentas y Usuarios
     */
    public int[] createUserWithAccount(String name, String password, double initialBalance) throws SQLException {
        //Verifica que este conectada la bd
        try (Connection conn = DBConnexion.getConnection()) {
            //
            conn.setAutoCommit(false);
            try {
                //llama a userRepo y accountRepo que
                int userId = userService.createUser(conn, name, password);
                int accountId = accountService.createAccount(conn, userId, initialBalance);
                //
                conn.commit();
                //devuelve el id_usuario y el id_cuenta
                return new int[]{userId, accountId};
            } catch (SQLException ex) {
                //
                conn.rollback();
                throw ex;
            }
        }
    }

    /**
     * Crea una nueva cuenta para un usuario ya existente y devuelve el accountId.
     */
    public int createNewAccount(int userId, double initialBalance)
            throws SQLException {
        try (Connection conn = DBConnexion.getConnection()) {
            conn.setAutoCommit(false);
            try {
                int accountId = accountService.createAccount(conn, userId, initialBalance);
                conn.commit();
                return accountId;
            } catch (SQLException ex) {
                conn.rollback();
                throw ex;
            }
        }
    }

    //devuelve el saldo actual de la cuenta.

    public double getBalance(int accountId) throws SQLException {
        try (Connection conn = DBConnexion.getConnection()) {
            return accountService.findBalanceById(conn, accountId);
        }
    }

    /**
     * Cambia el nombre del titular de la cuenta (campo usuario.nombre).
     */
    public void changeHolder(User user, String newHolder) throws SQLException {
        try (Connection conn = DBConnexion.getConnection()) {
            conn.setAutoCommit(false);
            // 1) Actualizamos en BD
            userService.updateName(conn, user.getAccount().getIdAccount(), newHolder);
            // 2) Commit y reflejar en el objeto
            conn.commit();
            user.setName(newHolder);
        } catch (SQLException ex) {
            throw ex;
        }
    }


    public void deleteAccount(User user, int accountId) throws SQLException {
        try (Connection conn = DBConnexion.getConnection()) {
            conn.setAutoCommit(false);
            //Eliminar en BD
            accountService.deleteById(conn, accountId);
            //Commit
            conn.commit();
            //Reflejar en memoria
            user.setAccount(null);
        }
    }

    /**
     * Muestra los datos de la cuenta
     */
    public String accountDetails(User user) throws SQLException {
        int userId = user.getId();
        String name = user.getName();
        int acctId = user.getAccount().getIdAccount();

        try (Connection conn = DBConnexion.getConnection()) {
            double balance = accountService.findBalanceById(conn, acctId);
            // Formateamos con dos decimales
            return String.format(
                    "DETALLES DE CUENTA%n" +
                            "Usuario: %s (id=%d)%n" +
                            "Cuenta: %d%n" +
                            "Saldo actual: $%.2f%n",
                    name, userId, acctId, balance
            );
        }
    }
}
