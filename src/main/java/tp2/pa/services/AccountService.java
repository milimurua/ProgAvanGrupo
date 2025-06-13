package tp2.pa.services;

import java.sql.*;

//CRUD Cuenta
public class AccountService {
    /**
     * Inserta una cuenta para el usuario dado y devuelve el id de cuenta.
     */
    public int createAccount(Connection conn, int userId, double balance) throws SQLException {
        String sql = "INSERT INTO cuenta(usuario_id, saldo) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, userId);
            ps.setDouble(2, balance);
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    throw new SQLException("No se generó id_cuenta");
                }
            }
        }
    }

    /**
     * @param conn
     * @param accountId
     * @return
     * @throws SQLException Muestra el saldo actual de cada cuenta
     */
    public double findBalanceById(Connection conn, int accountId) throws SQLException {
        //Query para selecionar segun el id cuenta que actual el saldo actual
        String sql = "SELECT saldo FROM cuenta WHERE id_cuenta = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            //Captura lo que devuelve la base de datos
            ps.setInt(1, accountId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    //Devuelve el saldo al controller
                    return rs.getDouble("saldo");
                } else {
                    //si la cuenta no existe
                    throw new SQLException("Cuenta no encontrada: " + accountId);
                }
            }
        }
    }

    /**
     * Actualiza el saldo de la cuenta con update
     */
    public void updateBalance(Connection conn, int accountId, double newBalance) throws SQLException {
        //Query donde actualiza los datos del saldo
        String sql = "UPDATE cuenta SET saldo = ? WHERE id_cuenta = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            //
            ps.setDouble(1, newBalance);
            ps.setInt(2, accountId);
            if (ps.executeUpdate() == 0) {
                throw new SQLException("No se encontró la cuenta id=" + accountId);
            }
        }
    }

    /**
     * Elimina la cuenta con el id dado.
     * Lanza SQLException si no existe.
     */
    public void deleteById(Connection conn, int accountId) throws SQLException {
        String sql = "DELETE FROM cuenta WHERE id_cuenta = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new SQLException("No se encontró la cuenta id=" + accountId);
            }
        }
    }

}
