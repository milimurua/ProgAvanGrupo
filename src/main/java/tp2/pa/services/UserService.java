package tp2.pa.services;

import tp2.pa.model.Account;
import tp2.pa.model.User;
import tp2.pa.util.DBConnexion;

import java.sql.*;

//CRUD usuario
public class UserService{

    /**
     * loging capturar la info de la base de datos
     */
    private static final String SQL_FIND_BY_USERNAME =
            "SELECT u.id_usuario, u.nombre, u.contraseña, " +
                    " c.id_cuenta, c.saldo " +
                    " FROM usuario u " +
                    " LEFT JOIN cuenta c ON c.usuario_id = u.id_usuario " +
                    " WHERE u.nombre = ?";

    /**
     * Busca un usuario por su username.
     * Devuelve null si no existe.
     */
    public User findByUsername(String userName) throws SQLException {
        try (Connection conn = DBConnexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_FIND_BY_USERNAME)) {

            ps.setString(1, userName);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }

                int userId = rs.getInt("id_usuario");
                String name = rs.getString("nombre");
                String passowrd = rs.getString("contraseña");
                int accountID = rs.getInt("id_cuenta");
                double balance = rs.getDouble("saldo");

                Account account = new Account(accountID, balance);
                // Aquí almacenamos la contraseña tal cual vino de BD;
                // la validación la haremos en AuthService
                return new User(userId, name, account, passowrd);
            }
        }
    }

    /**
     * Inserta un nuevo usuario y devuelve su id generado.
     */
    public int createUser(Connection conn, String name, String password) throws SQLException {
        String sql = "INSERT INTO usuario(nombre, contraseña) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.setString(2, password);
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    throw new SQLException("No se generó id_usuario");
                }
            }
        }
    }

    /**
     * Actualiza el nombre ("titular") de un usuario dado su id.
     */
    public void updateName(Connection conn, int userId, String newName) throws SQLException {
        String sql = "UPDATE usuario SET nombre = ? WHERE id_usuario = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newName);
            ps.setInt(2, userId);
            if (ps.executeUpdate() == 0) {
                throw new SQLException("Usuario no encontrado (id=" + userId + ")");
            }
        }
    }

    /**
     * @param conn
     * @param userId
     * @param newPassword
     * @throws SQLException Actualiza la contraseña de un usuario dado su id.
     */
    public void updatePassword(Connection conn, int userId, String newPassword) throws SQLException {
        String sql = "UPDATE usuario SET contraseña = ? WHERE id_usuario= ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newPassword);
            ps.setInt(2, userId);
            if (ps.executeUpdate() == 0) {
                throw new SQLException("Usuario no encontrado (id=" + userId + ")");
            }
        }
    }
}
