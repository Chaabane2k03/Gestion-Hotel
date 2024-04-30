package chambre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import javax.swing.JOptionPane;

import DatabaseManagement.Connect;

public class Chambre {
    private int numChambre;
    private int capacity;
    private int typeChambre;
    private int status;
    private int etage;
    private double prix_par_jour; 

    public Chambre() {
    	
    }

    public Chambre(int numChambre, int capacity, int typeChambre, int status, int etage) {
        this.numChambre = numChambre;
        this.capacity = capacity;
        this.typeChambre = typeChambre;
        this.status = status;
        this.etage = etage;
    }

    public int getNumChambre() {
        return this.numChambre;
    }

    public void setNumChambre(int numChambre) {
        this.numChambre = numChambre;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getTypeChambre() {
        return this.typeChambre;
    }

    public void setTypeChambre(int typeChambre) {
        this.typeChambre = typeChambre;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getPrix_par_jour() {
        return this.prix_par_jour;
    }

    public void setPrix_par_jour(double prix_par_jour) {
        this.prix_par_jour = prix_par_jour;
    }

    public int getEtage() {
        return this.etage;
    }

    public void setEtage(int etage) {
        this.etage = etage;
    }
    
    //TODO : Fix Chambre classe en modifiant la base de données 
    public static ArrayList<Chambre> getAvailableRoomsFromDB(String CheckIn,String CheckOut,int capacity){
    	ArrayList<Chambre> listRooms = null;
    	try {
    		String query = "SELECT *\r\n"
    				+ "FROM chambre\r\n"
    				+ "WHERE status = 2\r\n"
    				+ "AND capacity >= ? \r\n"
    				+ "AND numchambre NOT IN (\r\n"
    				+ "    SELECT id_chambre_reserve\r\n"
    				+ "    FROM reservation\r\n"
    				+ "    WHERE (check_in_date <= ? AND check_out_date >= ?)\r\n"
    				+ "    OR (check_in_date BETWEEN ? AND ?)\r\n"
    				+ "    OR (check_out_date BETWEEN ? AND ?)\r\n"
    				+ ")\r\n"
    				+ "UNION\r\n"
    				+ "SELECT *\r\n"
    				+ "FROM chambre\r\n"
    				+ "WHERE status = 1\r\n"
    				+ "AND capacity >= ?";
    		Connection connection = new Connect().getConnection();
    		listRooms = new ArrayList<Chambre>();
    		PreparedStatement preparedStmt = connection.prepareStatement(query);
    		preparedStmt.setString(2,CheckOut);
    		preparedStmt.setString(5,CheckOut);
    		preparedStmt.setString(7,CheckOut);
    		preparedStmt.setString(3, CheckIn);
    		preparedStmt.setString(4, CheckIn);
    		preparedStmt.setString(6, CheckIn);
    		preparedStmt.setInt(1,capacity);
    		preparedStmt.setInt(8,capacity);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {
            	Chambre room = new Chambre();
            	room.setNumChambre(resultSet.getInt(1));
                room.setCapacity(resultSet.getInt(2));
                room.setTypeChambre(resultSet.getInt(3));
                room.setStatus(resultSet.getInt(4));
                room.setEtage(resultSet.getInt(5));
                room.setPrix_par_jour(resultSet.getDouble(6));
                listRooms.add(room);
            }
            connection.close();
            return listRooms;
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
    	return null;
    }
   
    public static Chambre getRoomFromId(int id) {
    	try {
    		String query="SELECT *\r\n"
    				+ "FROM chambre\r\n"
    				+ "WHERE numchambre = ?;\r\n";
    		Connection connection = new Connect().getConnection();
    		PreparedStatement preparedStmt = connection.prepareStatement(query);
    		preparedStmt.setInt(1, id);
            ResultSet resultSet = preparedStmt.executeQuery();
    		if (resultSet.next()) {
                Chambre room = new Chambre();
                room.setNumChambre(resultSet.getInt(1));
                room.setCapacity(resultSet.getInt(2));
                room.setTypeChambre(resultSet.getInt(3));
                room.setStatus(resultSet.getInt(4));
                room.setEtage(resultSet.getInt(5));
                room.setPrix_par_jour(resultSet.getDouble(6));
                connection.close();
                return room;
            }
    		else {
    			connection.close();
    			return null;
    		}
            
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
    	return null;
    }
    //Voir la liste des chambres :
    public static ArrayList<Chambre> getRoomsFromDB(int status, int floor, int type) {
        ArrayList<Chambre> ListRooms = null;
        try {
            String query = "SELECT a.numchambre,a.capacity,a.typechambre,a.status,a.etage,a.prix_par_jour FROM  chambre a";
            Connection connection = new Connect().getConnection();
            ListRooms = new ArrayList<Chambre>();
            if (status != 0)
                query += " INNER JOIN chambre b on a.numchambre=b.numchambre and b.status=" + String.valueOf(status);
            if (floor != 0)
                query += " INNER JOIN chambre c on a.numchambre=c.numchambre and c.etage=" + String.valueOf(floor);
            if (type != 0)
                query += " INNER JOIN chambre d on a.numchambre=d.numchambre and d.typechambre=" + String.valueOf(type);

            PreparedStatement preparedStmt = connection.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {
                Chambre room = new Chambre();
                room.setNumChambre(resultSet.getInt(1));
                room.setCapacity(resultSet.getInt(2));
                room.setTypeChambre(resultSet.getInt(3));
                room.setStatus(resultSet.getInt(4));
                room.setEtage(resultSet.getInt(5));
                room.setPrix_par_jour(resultSet.getDouble(6));
                ListRooms.add(room);
            }
            connection.close();
            
            return ListRooms;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    
    // Suppression d'une chambre : (Fixed)
    public static boolean DeleteChambre(int numChambre) {
        try {
            String query = "SELECT c.*\r\n"
            		+ "FROM chambre c\r\n"
            		+ "JOIN reservation r ON c.numchambre = r.id_chambre_reserve;\r\n"
            		+ "WHERE c.numchambre = ?";
            Connection connection = new Connect().getConnection();
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, numChambre);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {

                JOptionPane.showMessageDialog(null,
                        "Unable to delete room " + numChambre + " ! Room is booked in reservations",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            connection.close();
        } catch (

        SQLException e) {
            e.printStackTrace();
        }

        try {
            String query = "Delete from Chambre where numChambre=?";
            Connection connection = new Connect().getConnection();
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, numChambre);
            preparedStmt.executeUpdate();
            connection.close();
        } catch (

        SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    
    // Ajout d'une nouvelle chambre :
    
    public static boolean NewChambre(Chambre chambre) {
        try {
            String query = "INSERT INTO Chambre VALUES (?, ?, ?, ?, ?, ?);";
            Connection connection = new Connect().getConnection();
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, chambre.getNumChambre());
            preparedStmt.setInt(2, chambre.getCapacity());
            preparedStmt.setInt(3, chambre.getTypeChambre());
            preparedStmt.setInt(4, chambre.getStatus());
            preparedStmt.setInt(5, chambre.getEtage());
            preparedStmt.setDouble(6, chambre.getPrix_par_jour());
            preparedStmt.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    public static boolean hasReservations(Chambre chambre) {
    	Integer nb = 0;
        try {
            String query = "SELECT COUNT(*) AS nombre_reservations\r\n"
            		+ "FROM reservation\r\n"
            		+ "WHERE id_chambre_reserve = ?\r\n"
            		+ "AND  check_out_date > ?\r\n";
            Connection connection = new Connect().getConnection();
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, chambre.numChambre);
            //Date d'aujourd'hui
            Date today = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String aujourdhui = sdf.format(today);
            preparedStmt.setString(2,aujourdhui);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {
                nb = resultSet.getInt(1);
            }
            connection.close();
            return (nb > 0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (nb>0);
    }
    // Modification de la Base de données :
    public static void updateChambreDB(Chambre chambre) {
        try {
            String query = "UPDATE chambre SET capacity =? , typechambre=? , status=? , etage =? , prix_par_jour =? WHERE numChambre=?";
            Connection connection = new Connect().getConnection();
            PreparedStatement preparedStmt1 = connection.prepareStatement(query);
            preparedStmt1.setInt(1, chambre.getCapacity());
            preparedStmt1.setInt(2, chambre.getTypeChambre());
            preparedStmt1.setInt(3, chambre.getStatus());
            preparedStmt1.setInt(4, chambre.getEtage());
            preparedStmt1.setDouble(5, chambre.getPrix_par_jour());
            preparedStmt1.setInt(6, chambre.getNumChambre());
            preparedStmt1.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    
    
    
    // Nombre des demandes de réservation :
    public int getNbDemandes() {
        Integer nb = 0;
        try {
            String query = "SELECT COUNT(*) AS nombre_reservations\r\n"
            		+ "FROM reservation\r\n"
            		+ "WHERE id_chambre_reserve = ?\r\n";
            Connection connection = new Connect().getConnection();
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, numChambre);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {
                nb = resultSet.getInt(1);
            }
            connection.close();
            return nb;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nb;
    }
    
    // Pour le dashbord : Le nombre des chambres le plus demandées (Fixed)
    
    public static ArrayList<Chambre> getChambresPlusDemandesFromDB() {
        ArrayList<Chambre> listChambres = null;
        try {
            String query = "SELECT c.numchambre, c.capacity, c.typechambre, c.status, c.etage, c.prix_par_jour, COALESCE(r.nombre_reservations, 0) AS nombre_reservations\r\n"
            		+ "FROM chambre c\r\n"
            		+ "LEFT JOIN (\r\n"
            		+ "    SELECT id_chambre_reserve, COUNT(*) AS nombre_reservations\r\n"
            		+ "    FROM reservation\r\n"
            		+ "    GROUP BY id_chambre_reserve\r\n"
            		+ ") r ON c.numchambre = r.id_chambre_reserve\r\n"
            		+ "ORDER BY COALESCE(r.nombre_reservations, 0) DESC\r\n"
            		+ "LIMIT 5;\r\n"
            		+ "";

            Connection connection = new Connect().getConnection();
            listChambres = new ArrayList<Chambre>();
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {
                Chambre Chambre = new Chambre();
                Chambre.setNumChambre(resultSet.getInt(1));
                Chambre.setCapacity(resultSet.getInt(2));
                Chambre.setTypeChambre(resultSet.getInt(3));
                Chambre.setStatus(resultSet.getInt(4));
                Chambre.setEtage(resultSet.getInt(5));
                Chambre.setPrix_par_jour(resultSet.getDouble(6));
                listChambres.add(Chambre);
            }
            connection.close();
            
            return listChambres;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }
    @Override
    public String toString() {
        return "{" +
                " numChambre='" + getNumChambre() + "'" +
                ", capacity='" + getCapacity() + "'" +
                ", typeChambre='" + getTypeChambre() + "'" +
                ", status='" + getStatus() + "'" +
                ", etage='" + getEtage() + "'" +
                ", Prix_par_jour='" + getPrix_par_jour() + "'" +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Chambre)) {
            return false;
        }
        Chambre chambre = (Chambre) o;
        return numChambre == chambre.numChambre && capacity == chambre.capacity && typeChambre == chambre.typeChambre
                && status == chambre.status && etage == chambre.etage && prix_par_jour == chambre.prix_par_jour;
    }

    @Override
    // Méthode de hashage pour assurer la sécurité lors de l'usage de données : 
    public int hashCode() {
        return Objects.hash(numChambre, capacity, typeChambre, status, etage, prix_par_jour);
    }

    
}

