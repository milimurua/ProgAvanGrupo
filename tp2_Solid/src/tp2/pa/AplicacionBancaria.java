
package tp2.pa;

public class AplicacionBancaria {
    public static void main(String[] args) {
        usuarioBancario cuenta = new usuarioBancario("Pepe", "12345678", 1000);
        
        transacciones transaccionU1 = new transacciones (cuenta);
        detallesUsuario detallesU1 = new detallesUsuario (cuenta);
        notificacion notificacionU1 = new notificacion (cuenta);
        
        transaccionU1.depositar(500);
        transaccionU1.retirar(200);
        detallesU1.imprimirDetallesCuenta();
        notificacionU1.enviarNotificacionPorEmail("operación exitosa!");
        
        
       
    }
}

class usuarioBancario{
    public String titular;
    public String idCuenta;
    public double saldo;

    public usuarioBancario (String titular, String idCuenta, double saldo) {
        this.titular = titular;
        this.idCuenta = idCuenta;
        this.saldo = saldo;
    }
    
}

class transacciones{
    private usuarioBancario usuario1;

    public transacciones(usuarioBancario usuario1) {
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

class detallesUsuario{
    private usuarioBancario usuario1;

    public detallesUsuario(usuarioBancario usuario1) {
        this.usuario1 = usuario1;
    }

    public void imprimirDetallesCuenta(){
        System.out.println("Titular de la cuenta: " + usuario1.titular);
        System.out.println("ID de la cuenta: " + usuario1.idCuenta);
        System.out.println("Saldo actual: $" + usuario1.saldo);
    }
}

class notificacion {
    private usuarioBancario usuario1;

    public notificacion(usuarioBancario usuario1) {
        this.usuario1 = usuario1;
    }
    
    public void enviarNotificacionPorEmail(String mensaje) {
        System.out.println("Enviando correo a " + usuario1.titular + ": " + mensaje);
    }   
}


