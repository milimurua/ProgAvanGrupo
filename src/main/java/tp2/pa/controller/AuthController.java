package tp2.pa.controller;

import tp2.pa.model.User;
import tp2.pa.services.UserService;
import tp2.pa.util.DBConnexion;
import javax.naming.AuthenticationException;
import java.sql.Connection;
import java.sql.SQLException;

public class AuthController {
    private final UserService userService = new UserService();

    /**
     * Valida las credenciales de un usuario.
     * Devuelve el usuario si el username y contraseña coinciden.
     * Si el usuario no existe o la contraseña es incorrecta, lanza AuthenticationException.
     */
    public User login(String userName, String password) throws SQLException, AuthenticationException {
        return authenticate(userName, password);  // usamos la función centralizada
    }

    /**
     * Cambia la contraseña de un usuario.
     */
    public void changePassword(User user, String newPassword) throws SQLException {
        try (Connection conn = DBConnexion.getConnection()) {
            conn.setAutoCommit(false);

            // Actualizar en la base de datos
            userService.updatePassword(conn, user.getId(), newPassword);

            conn.commit(); // Confirmar cambios
            user.setPassword(newPassword); // Reflejar en el objeto en memoria
        }
    }

    /**
     * Verifica si la contraseña ingresada coincide con la del usuario.
     * Devuelve true si coincide, false si no.
     */
    public boolean verifyPassword(String username, String password) throws SQLException {
        try {
            authenticate(username, password);
            return true;
        } catch (AuthenticationException e) {
            return false;
        }
    }

    /**
     * Método privado centralizado para autenticación.
     * Si el usuario no existe o la contraseña es incorrecta, lanza AuthenticationException.
     */
    private User authenticate(String username, String password) throws SQLException, AuthenticationException {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new AuthenticationException("Usuario no encontrado");
        }
        if (!user.getPassword().equals(password)) {
            throw new AuthenticationException("Contraseña incorrecta");
        }
        return user;
    }
}