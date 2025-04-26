package tp2.pa;

public class Notificacion {
    private Usuario usuario1;

    public Notificacion(Usuario usuario1) {
        this.usuario1 = usuario1;
    }

    public void enviarNotificacionPorEmail(String mensaje) {
        System.out.println("Enviando correo a " + usuario1.titular + ": " + mensaje);
    }
}
