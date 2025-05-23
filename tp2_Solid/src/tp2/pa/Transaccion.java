package tp2.pa;

public class Transaccion {
    public void depositar(Usuario usuario, double monto) {
        if (!usuario.estaBloqueada()) {
            double nuevoSaldo = usuario.getSaldo() + monto;
            usuario.actualizarSaldo(nuevoSaldo);
        } else {
            System.out.println("No se puede depositar. Cuenta bloqueada.");
        }
    }

    public boolean retirar(Usuario usuario, double monto) {
        if (usuario.estaBloqueada()) {
            System.out.println("No se puede retirar. Cuenta bloqueada.");
            return false;
        }

        if (usuario.getSaldo() >= monto) {
            usuario.actualizarSaldo(usuario.getSaldo() - monto);
            return true;
        }
        return false;
    }

    public boolean transferir(Usuario origen, Usuario destino, double monto) {
        if (origen.estaBloqueada() || destino.estaBloqueada()) {
            System.out.println("No se puede transferir. Una de las cuentas está bloqueada.");
            return false;
        }

        if (origen.getSaldo() >= monto) {
            origen.actualizarSaldo(origen.getSaldo() - monto);
            destino.actualizarSaldo(destino.getSaldo() + monto);
            System.out.println("Transferencia realizada con éxito.");
            return true;
        } else {
            System.out.println("Saldo insuficiente para la transferencia.");
            return false;
        }
    }

    public void solicitarPrestamo(Usuario usuario, double monto) {
        if (!usuario.estaBloqueada()) {
            usuario.actualizarSaldo(usuario.getSaldo() + monto);
            System.out.println("Préstamo otorgado: $" + monto);
        } else {
            System.out.println("No se puede solicitar préstamo. Cuenta bloqueada.");
        }
    }
}
