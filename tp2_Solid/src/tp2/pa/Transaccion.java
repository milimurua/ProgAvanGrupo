package tp2.pa;

public class Transaccion {
    public void depositar (Usuario usuario, double monto){
        double nuevoSaldo = usuario.getSaldo() + monto;
        usuario.actualizarSaldo(nuevoSaldo);
    }

    public boolean retirar (Usuario usuario,double monto){
        if (usuario.getSaldo() >= monto){
            usuario.actualizarSaldo(usuario.getSaldo() - monto);
            return true;
        }
        return false;
    }
}
