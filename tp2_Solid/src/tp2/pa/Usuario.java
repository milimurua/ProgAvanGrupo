package tp2.pa;

public class Usuario {
    private String titular;
    private String idCuenta;
    private double saldo;

    //constructor
    public Usuario(String titular, String idCuenta, double saldoInicial) {
        this.titular = titular;
        this.idCuenta = idCuenta;
        this.saldo = saldoInicial;
    }

    public String getIdCuenta() {
        return idCuenta;
    }

    public double getSaldo() {
        return saldo;
    }

    public String getTitular() {
        return titular;
    }

    void actualizarSaldo(double nuevoSaldo) {
        this.saldo = nuevoSaldo;
    }
}
