package tp2.pa.controller;

import tp2.pa.model.INotification;
import tp2.pa.model.NotificationEmail;
import tp2.pa.model.NotificationSMS;
import tp2.pa.model.User;
import tp2.pa.services.AccountService;
import tp2.pa.services.TransactionService;
import tp2.pa.util.DBConnexion;

import java.sql.Connection;
import java.sql.SQLException;


//deposito, retiro, transferencia, solicitar préstamo
public class TransactionController {
    private final AccountService accountService = new AccountService();
    private final TransactionService transactionService = new TransactionService();

    public void deposit(User user, double amount) throws SQLException {
        int acctId = user.getAccount().getIdAccount();
        try (Connection conn = DBConnexion.getConnection()) {
            conn.setAutoCommit(false);

            double oldBal = accountService.findBalanceById(conn, acctId);
            double newBal = oldBal + amount;
            accountService.updateBalance(conn, acctId, newBal);

            int txId = transactionService.saveTransaction(conn, acctId, amount, "Deposito", acctId);

            conn.commit();
            user.getAccount().setBalance(newBal);

            System.out.println("Depósito registrado. ID transacción=" + txId);
        }
    }

    public void withdraw(User user, double amount) throws SQLException {
        int acctId = user.getAccount().getIdAccount();
        try (Connection conn = DBConnexion.getConnection()) {
            conn.setAutoCommit(false);

            double oldBal = accountService.findBalanceById(conn, acctId);
            if (oldBal < amount) {
                throw new SQLException("Fondos insuficientes");
            }
            double newBal = oldBal - amount;
            accountService.updateBalance(conn, acctId, newBal);

            int txId = transactionService.saveTransaction(conn, acctId, amount, "Retiro", acctId);

            conn.commit();
            user.getAccount().setBalance(newBal);

            System.out.println("Retiro registrado. ID transacción=" + txId);
        }
    }

    public void transfer(User user, int destAccountId, double amount) throws SQLException {
        int srcId = user.getAccount().getIdAccount();
        try (Connection conn = DBConnexion.getConnection()) {
            conn.setAutoCommit(false);

            double srcBal = accountService.findBalanceById(conn, srcId);
            if (srcBal < amount) {
                throw new SQLException("Fondos insuficientes en cuenta origen.");
            }
            double dstBal = accountService.findBalanceById(conn, destAccountId);

            double newSrc = srcBal - amount;
            double newDst = dstBal + amount;

            accountService.updateBalance(conn, srcId, newSrc);
            accountService.updateBalance(conn, destAccountId, newDst);

            int txOut = transactionService.saveTransaction(conn, srcId, amount, "Transferencia", destAccountId);
            int txIn = transactionService.saveTransaction(conn, destAccountId, amount, "Transferencia", srcId);

            conn.commit();
            user.getAccount().setBalance(newSrc);

            System.out.println("Transferencia exitosa.");
            System.out.println("  ID salida:  " + txOut + " | Saldo origen:  $" + newSrc);
            System.out.println("  ID entrada: " + txIn + " | Saldo destino: $" + newDst);
        }

        //  CREAR INSTANCIAS de notificación
        INotification emailNotifier = new NotificationEmail(user);
        INotification smsNotifier = new NotificationSMS(user);

        //  MENSAJE personalizado
        String mensaje = "Se realizó una transferencia de $" + amount + " a la cuenta ID " + destAccountId;

        // ENVIAR notificaciones
        emailNotifier.notify(mensaje);
        smsNotifier.notify(mensaje);

    }

    /**
     * Solicita un préstamo: guarda la solicitud, actualiza saldo y registra la transacción.
     */
    public void askForALoan(User user, double amount) throws SQLException {
        int acctId = user.getAccount().getIdAccount();  // antes usabas getIdAccount()

        Connection conn = null;
        try {
            conn = DBConnexion.getConnection();
            conn.setAutoCommit(false);

            // 1) Obtener saldo actual y validar
            double oldBal = accountService.findBalanceById(conn, acctId);
            if (oldBal < 0) {
                throw new SQLException("Saldo negativo: no se puede solicitar un préstamo");
            }

            // 2) Calcular y actualizar nuevo saldo
            double newBal = oldBal + amount;
            accountService.updateBalance(conn, acctId, newBal);

            // 3) Registrar transacción de tipo “Prestamo”
            // Ajusta el método según tu firma actual de saveTransaction
            int transactionId = transactionService.saveTransaction(conn, acctId, amount, "Prestamo", acctId);

            // 4) Confirmar transacción
            conn.commit();

            // 5) Reflejar el cambio en el objeto en memoria
            user.getAccount().setBalance(newBal);

            // 6) Feedback al usuario
            System.out.println("Préstamo registrado. ID transacción = " + transactionId);

        } catch (SQLException ex) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ignored) {
                }
            }
            throw ex;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ignored) {
                }
            }
        }
    }


}
