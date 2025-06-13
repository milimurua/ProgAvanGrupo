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
     *
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

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
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

