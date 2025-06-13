package tp2.pa.model;

public class NotificationEmail implements INotification {
    private User user;

    public NotificationEmail(User user) {
        this.user = user;
    }

    @Override
    public void notify(String mensaje) {
        System.out.println("Enviando correo a Email de " + user.getName() + ": " + mensaje);
    }
}
