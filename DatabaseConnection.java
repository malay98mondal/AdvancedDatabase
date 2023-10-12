package swing;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/samplesales";
    private static final String DB_USERNAME = "root"; // enter your database user name
    private static final String DB_PASSWORD = "malay";  //use your database password
  

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }
}

