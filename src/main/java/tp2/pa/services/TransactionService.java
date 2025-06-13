package tp2.pa.services;

import java.math.BigDecimal;
import java.sql.*;

//CRUD Transacción
public class TransactionService{
    /**
     * Inserta una fila en transaccion y devuelve el id generado.
     * Siempre rellena: cuenta_id, tipo, monto, cuenta_destino
     */
    public int saveTransaction(Connection conn,
                               int accountId,
                               double amount,
                               String type,
                               Integer destAccountId) throws SQLException {
        String sql =
                "INSERT INTO transaccion " +
                        "  (cuenta_id, tipo, monto, cuenta_destino) " +
                        "VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, accountId);
            ps.setString(2, type);
            ps.setBigDecimal(3, BigDecimal.valueOf(amount));
            if (destAccountId != null) {
                ps.setInt(4, destAccountId);
            } else {
                ps.setNull(4, Types.INTEGER);
            }

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                throw new SQLException("No se generó id_transaccion");
            }
        }
    }
}

