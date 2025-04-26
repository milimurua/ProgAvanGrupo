package tp2.pa;

public class DetalleUsuario {
    private Usuario usuario1;

    public DetalleUsuario(Usuario usuario1) {
        this.usuario1 = usuario1;
    }

    public void imprimirDetallesCuenta(){
        System.out.println("Titular de la cuenta: " + usuario1.titular);
        System.out.println("ID de la cuenta: " + usuario1.idCuenta);
        System.out.println("Saldo actual: $" + usuario1.saldo);
    }
}

