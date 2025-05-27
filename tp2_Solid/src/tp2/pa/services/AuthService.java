package tp2.pa.services;

import tp2.pa.model.User;
import tp2.pa.util.DBConnexion;
import java.sql.Connection;
import java.sql.SQLException;

import tp2.pa.repository.UserRepository;

import javax.naming.AuthenticationException;


//loging, bloqueo, cambiar contraseña, cambiar titular y detalle de cuenta
public class AuthService {
    private final UserRepository userRepo = new UserRepository();

    /**
     * Intenta autenticar al usuario.
     * Si no existe, lanza AuthenticationException.
     * Si la contraseña no coincide, lanza AuthenticationException.
     * En caso de éxito, devuelve el User (puedes limpiar el password).
     */
    public User login(String userName, String password)
            throws SQLException, AuthenticationException {

        User user = userRepo.findByUsername(userName);
        if (user == null) {
            throw new AuthenticationException("Usuario no encontrado");
        }
        // Aquí podrías comparar hashes si usaras bcrypt u otro.
        if (!user.getPassword().equals(password)) {
            throw new AuthenticationException("Contraseña incorrecta");
        }
        // Opcional: para no exponer la contraseña en memoria
        user.setPassword(null);
        return user;
    }

    public void changePassword(User user, String newPassword) throws SQLException {
        try (Connection conn = DBConnexion.getConnection()) {
            conn.setAutoCommit(false);
            //Actualizamos en BD
            userRepo.updatePassword(conn, user.getId(), newPassword);

            //Confirmamos y reflejamos en el objeto
            conn.commit();
            user.setPassword(newPassword);
        }
    }


}
