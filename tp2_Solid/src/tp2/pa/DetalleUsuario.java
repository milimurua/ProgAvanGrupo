package tp2.pa;

public class DetalleUsuario {
    public void imprimirTransaccion(String tipo, double monto, Usuario usuario) {
        System.out.printf("%s: %.2f$. Saldo actual: %.2f$%n", tipo, monto, usuario.getSaldo());
    }

    public void imprimirDetallesCuenta(Usuario usuario){
        System.out.println("Titular de la cuenta: " + usuario.getTitular());
        System.out.println("ID de la cuenta: " + usuario.getIdCuenta());
        System.out.println("Saldo actual: $" + usuario.getSaldo());
    }
}

