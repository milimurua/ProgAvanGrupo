package tp2.pa;

public class Usuario {
    private String titular;
    private String idCuenta;
    private double saldo;
    private boolean cuentaBloqueada;
    private String pin;

    public Usuario(String titular, String idCuenta, double saldoInicial, String pin) {
        this.titular = titular;
        this.idCuenta = idCuenta;
        this.saldo = saldoInicial;
        this.pin = pin;
        this.cuentaBloqueada = false;
    }

    public String getIdCuenta() { return idCuenta; }

    public double getSaldo() { return saldo; }

    public String getTitular() { return titular; }

    public boolean estaBloqueada() { return cuentaBloqueada; }

    public String getPin() { return pin; }

    void actualizarSaldo(double nuevoSaldo) {
        this.saldo = nuevoSaldo;
    }

    public void cambiarTitular(String nuevoTitular) {
        this.titular = nuevoTitular;
        System.out.println("Titular cambiado exitosamente a: " + nuevoTitular);
    }

    public void cambiarPin(String nuevoPin) {
        this.pin = nuevoPin;
        System.out.println("PIN cambiado correctamente.");
    }

    public void bloquearCuenta() {
        this.cuentaBloqueada = true;
        System.out.println("Cuenta bloqueada con éxito.");
    }
}

