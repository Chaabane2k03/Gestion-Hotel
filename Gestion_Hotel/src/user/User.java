package user;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DatabaseManagement.Connect;

public class User {
	
	// Les variables de cette classe : 
	
	private int iduser;
    private String username;
    private String password;
    private int idclient;
    private int typeuser;
    
    // Constructeurs :
    public User() {
    }
    
    public User(int iduser, String username, String password, int idclient, int typeuser) {
        this.setIduser(iduser);
        this.setUsername(username);
        this.setPassword(password);
        this.setIdclient(idclient);
        this.setTypeuser(typeuser);
    }
    
    public User(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }
    
    //Getters and Setters :
	public int getIduser() {
		return iduser;
	}

	public void setIduser(int iduser) {
		this.iduser = iduser;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getIdclient() {
		return idclient;
	}

	public void setIdclient(int idclient) {
		this.idclient = idclient;
	}

	public int getTypeuser() {
		return typeuser;
	}

	public void setTypeuser(int typeuser) {
		this.typeuser = typeuser;
	}
	
	
	//Cryptage de mdp : Optionnel mais recommondé pour la sécurité de la base de Donnée
	public static String cryptPass(String motDePasse) {
        String Crypted = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(motDePasse.getBytes());

            byte byteData[] = md.digest();

            // convertir le tableau de bits en format hexadécimal
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            Crypted = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return Crypted;
    }
   
	
	//Interrogation de la Base de données :
	public static boolean isUsernameExists(String username) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel", "root", "");
            String query = "SELECT * FROM user WHERE username=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); 
        } catch (SQLException ex) {
            ex.printStackTrace();
            return true; 
        }
    }
	
	//Verifier qu'il existe dans la base de données :
	public User getUserFromDB() {
        try {
            String query = "SELECT * FROM user WHERE username=? and password=?";
            Connection connection = new Connect().getConnection();
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, getUsername());
            preparedStmt.setString(2, getPassword());
            ResultSet resultSet = preparedStmt.executeQuery();
            User utilisateur = null;
            while (resultSet.next()) {
                utilisateur = new User();
                utilisateur.setIduser(resultSet.getInt(1));
                utilisateur.setUsername(resultSet.getString(2));
                utilisateur.setPassword(resultSet.getString(3));
                utilisateur.setIdclient(resultSet.getInt(4));
                utilisateur.setTypeuser(resultSet.getInt(5));
            }
            connection.close();
            return utilisateur;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
	
	//L'ajout d'un utilisateur dans la base de données : soit Sign up de client ou l'ajout des admins
	public static void newSpecialUser(String username,String password,int typeuser) {
		try {
            String query = "INSERT INTO USER(username,password,typeuser) VALUES (?, ?, ?);";
            Connection connection = new Connect().getConnection();
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, username);
            preparedStmt.setString(2, cryptPass(password));
            preparedStmt.setInt(3, typeuser);
            preparedStmt.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	public static void newUser(String username, int idclient, int typeuser) {
        try {
            String query = "INSERT INTO USER(username,password,idclient,typeuser) VALUES (?, ?, ?, ?);";
            Connection connection = new Connect().getConnection();
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, username);
            preparedStmt.setString(2, cryptPass(username));
            preparedStmt.setInt(3, idclient);
            preparedStmt.setInt(4, typeuser);
            preparedStmt.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//Suppression d'un utilisateur :
	
	public static void DeleteUser(int idClient, int typeuser) {
        try {
            String query = "Delete from user where idClient=? and usertype=?";
            Connection connection = new Connect().getConnection();
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, idClient);
            preparedStmt.setInt(2, typeuser);
            preparedStmt.executeUpdate();
            connection.close();
        } catch (

        SQLException e) {
            e.printStackTrace();
        }
    }
	
	 @Override
	    public String toString() {
	        return "{" +
	                " iduser='" + getIduser() + "'" +
	                ", username='" + getUsername() + "'" +
	                ", password='" + getPassword() + "'" +
	                ", idclient='" + getIdclient() + "'" +
	                ", typeuser='" + getTypeuser() + "'" +
	                "}";
	    }

	
    
}
