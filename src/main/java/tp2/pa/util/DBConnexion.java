package tp2.pa.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DBConnexion {
    private static final String PROPERTIES_FILE = System.getProperty("user.dir") + "/src/main/resources/db.properties";
    private static String url;
    private static String user;
    private static String password;


    static {
        //capta la información del archivo bd.properties para acceder a la bd
        try (InputStream inputStream = new FileInputStream(PROPERTIES_FILE)) {
            System.out.println(PROPERTIES_FILE);
            Properties props = new Properties();
            System.out.println("entro a bd");
            props.load(inputStream);
            url = props.getProperty("db.url");
            user = props.getProperty("db.user");
            password = props.getProperty("db.password");
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Error cargando configuración de BD: " + e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
