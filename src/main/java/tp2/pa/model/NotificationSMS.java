package tp2.pa.model;

public class NotificationSMS implements INotification {
    private User user;

    public NotificationSMS(User user) {
        this.user = user;
    }

    @Override
    public void notify(String mensaje) {
        System.out.println("Enviando SMS a " + user.getName() + ": " + mensaje);
    }
}

    

