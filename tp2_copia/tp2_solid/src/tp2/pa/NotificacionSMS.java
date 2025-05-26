
package tp2.pa;

public class NotificacionSMS implements Notificacion{
    private Usuario usuario;

    public NotificacionSMS (Usuario usuario){
        this.usuario = usuario;
    }

    @Override
    public void notificar(String mensaje) {
        System.out.println("Enviando SMS a " + usuario.getTitular() + ": " + mensaje);
    }
}

    

