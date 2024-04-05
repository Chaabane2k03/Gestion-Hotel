package DatabaseManagement;

import java.sql.*;

import javax.swing.JOptionPane;

public class Connect {
    static Connection connection;

    public Connect() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel", "root",""); 
        } 
        catch(SQLException e){ 
        	JOptionPane.showMessageDialog(null, "Error while connecting with Database","Error", JOptionPane.ERROR_MESSAGE);
        } 
    }

    public Connection getConnection() {
        return connection;
    }

    public static void main(String[] args) throws SQLException {
        new Connect();
       
        
    }

}