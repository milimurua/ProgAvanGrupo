
package tp2.pa;

public class AplicacionBancaria {
    public static void main(String[] args) {
        Usuario cuenta = new Usuario("Pepe", "12345678", 1000);
        
        Transaccion transaccionU1 = new Transaccion (cuenta);
        DetalleUsuario detalleU1 = new DetalleUsuario (cuenta);
        Notificacion notificacionU1 = new Notificacion (cuenta);
        
        transaccionU1.depositar(500);
        transaccionU1.retirar(200);
        detalleU1.imprimirDetallesCuenta();
        notificacionU1.enviarNotificacionPorEmail("operación exitosa!");
    }
}



