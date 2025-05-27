package tp2.pa.services;

import tp2.pa.model.User;
import tp2.pa.repository.AccountRepository;
import tp2.pa.repository.TransactionRepository;
import tp2.pa.util.DBConnexion;

import java.sql.Connection;
import java.sql.SQLException;


//deposito, retiro, transferencia, solicitar préstamo
public class TransactionService {
    private final AccountRepository    accountRepo = new AccountRepository();
    private final TransactionRepository transRepo   = new TransactionRepository();

    public void deposit(User user, double amount) throws SQLException {
        int acctId = user.getAccount().getIdAccount();
        try (Connection conn = DBConnexion.getConnection()) {
            conn.setAutoCommit(false);

            double oldBal = accountRepo.findBalanceById(conn, acctId);
            double newBal = oldBal + amount;
            accountRepo.updateBalance(conn, acctId, newBal);

            int txId = transRepo.saveTransaction(conn, acctId, amount, "Deposito", acctId);

            conn.commit();
            user.getAccount().setBalance(newBal);

            System.out.println("Depósito registrado. ID transacción=" + txId);
        }
    }

    public void withdraw(User user, double amount) throws SQLException {
        int acctId = user.getAccount().getIdAccount();
        try (Connection conn = DBConnexion.getConnection()) {
            conn.setAutoCommit(false);

            double oldBal = accountRepo.findBalanceById(conn, acctId);
            if (oldBal < amount) {
                throw new SQLException("Fondos insuficientes");
            }
            double newBal = oldBal - amount;
            accountRepo.updateBalance(conn, acctId, newBal);

            int txId = transRepo.saveTransaction(conn, acctId, amount, "Retiro", acctId);

            conn.commit();
            user.getAccount().setBalance(newBal);

            System.out.println("Retiro registrado. ID transacción=" + txId);
        }
    }

    public void transfer(User user, int destAccountId, double amount) throws SQLException {
        int srcId = user.getAccount().getIdAccount();
        try (Connection conn = DBConnexion.getConnection()) {
            conn.setAutoCommit(false);

            double srcBal = accountRepo.findBalanceById(conn, srcId);
            if (srcBal < amount) {
                throw new SQLException("Fondos insuficientes en cuenta origen.");
            }
            double dstBal = accountRepo.findBalanceById(conn, destAccountId);

            double newSrc = srcBal - amount;
            double newDst = dstBal + amount;

            accountRepo.updateBalance(conn, srcId, newSrc);
            accountRepo.updateBalance(conn, destAccountId, newDst);

            int txOut = transRepo.saveTransaction(conn, srcId, amount, "Transferencia", destAccountId);
            int txIn  = transRepo.saveTransaction(conn, destAccountId, amount, "Transferencia", srcId);

            conn.commit();
            user.getAccount().setBalance(newSrc);

            System.out.println("Transferencia exitosa.");
            System.out.println("  ID salida:  " + txOut + " | Saldo origen:  $" + newSrc);
            System.out.println("  ID entrada: " + txIn  + " | Saldo destino: $" + newDst);
        }
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
            double oldBal = accountRepo.findBalanceById(conn, acctId);
            if (oldBal < 0) {
                throw new SQLException("Saldo negativo: no se puede solicitar un préstamo");
            }

            // 2) Calcular y actualizar nuevo saldo
            double newBal = oldBal + amount;
            accountRepo.updateBalance(conn, acctId, newBal);

            // 3) Registrar transacción de tipo “Prestamo”
            // Ajusta el método según tu firma actual de saveTransaction
            int transactionId = transRepo.saveTransaction(conn, acctId, amount, "Prestamo", acctId);

            // 4) Confirmar transacción
            conn.commit();

            // 5) Reflejar el cambio en el objeto en memoria
            user.getAccount().setBalance(newBal);

            // 6) Feedback al usuario
            System.out.println("Préstamo registrado. ID transacción = " + transactionId);

        } catch (SQLException ex) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ignored) {}
            }
            throw ex;
        } finally {
            if (conn != null) {
                try { conn.close(); } catch (SQLException ignored) {}
            }
        }
    }


}
