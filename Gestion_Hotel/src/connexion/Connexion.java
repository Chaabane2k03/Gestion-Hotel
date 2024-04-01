package connexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {
    static Connection connection;

    public Connexion() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel", "root", ""); 
            System.out.println("Connexion à la base de données a été faite avec succèes");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la connexion :" + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static void main(String[] args) {
        new Connexion();
    }

}