package tp2.pa;

public class Transaccion {
    private Usuario usuario1;

    public Transaccion(Usuario usuario1) {
        this.usuario1 = usuario1;
    }

    public void depositar (double monto){
        usuario1.saldo += monto;
        System.out.println("Depositó: $" + monto + ". Ahora su dinero disponible es de: $" + usuario1.saldo);
    }

    public void retirar (double monto){
        if (usuario1.saldo >= monto){
            usuario1.saldo -= monto;
            System.out.println("Retiró: $" + monto + ". Ahora su dinero disponible es de: $" + usuario1.saldo);
        }else{
            System.out.println("saldo insuficiente");
        }
    }
}
