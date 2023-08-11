package agendaContactos;

import java.sql.*;

public class MySQLConnection {

    private static final String USERNAME = "root";
    private static final String PASSWORD = "androidl3";
    private static final String URL = "jdbc:mysql://localhost:3306/contactos";

    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        return connection;
    }
    
    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        System.out.println("conexion correcta");
    }
}