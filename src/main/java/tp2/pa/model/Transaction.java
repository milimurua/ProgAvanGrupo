package tp2.pa.model;

import java.sql.Timestamp;

/**
 * Representa una transacción sobre una cuenta bancaria.
 */
public class Transaction {
    private int id;
    private int accountId;
    private double amount;
    private String type;
    private Timestamp createdAt;

    /**
     * Constructor completo.
     * @param id        Identificador de la transacción
     * @param accountId Identificador de la cuenta asociada
     * @param amount    Monto de la transacción
     * @param type      Tipo de transacción (DEPOSIT, WITHDRAW, etc.)
     * @param createdAt Fecha y hora de creación
     */
    public Transaction(int id, int accountId, double amount, String type, Timestamp createdAt) {
        this.id = id;
        this.accountId = accountId;
        this.amount = amount;
        this.type = type;
        this.createdAt = createdAt;
    }

    /**
     * Constructor sin id, para cuando aún no se ha generado en base de datos.
     */
    public Transaction(int accountId, double amount, String type, Timestamp createdAt) {
        this(0, accountId, amount, type, createdAt);
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", amount=" + amount +
                ", type='" + type + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}

