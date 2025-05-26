package tp2.pa;

public class NotificacionEmail implements Notificacion{
    private Usuario usuario;

    public NotificacionEmail(Usuario usuario){
        this.usuario = usuario;
    }

    @Override
    public void notificar(String mensaje) {
        System.out.println("Enviando correo a Email de " + usuario.getTitular() + ": " + mensaje);
    }
}
