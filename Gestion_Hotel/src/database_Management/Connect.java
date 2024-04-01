package database_Management;

import java.sql.*;

public class Connect {
    static Connection connection;

    public Connect() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel", "root",""); 
            System.out.println("Connected successfully !!");
             } 

             catch(SQLException e)
                      { System.out.println("Error while connecting!!!"); } }

    public Connection getConnection() {
        return connection;
    }

    public static void main(String[] args) throws SQLException {
        new Connect();
       
        
    }

}