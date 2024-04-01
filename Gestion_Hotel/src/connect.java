package DatabaseManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    static Connection connection;

    public Connect() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Database", "root",
                    ""); 
            System.out.println("Connected With the database successfully");
        } catch (SQLException e) {
            System.out.println("Error while connecting to the database");
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static void main(String[] args) {
        new Connect();
    }

}
