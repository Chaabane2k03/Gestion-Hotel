package espace_reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import espace_client.Client;
import DatabaseManagement.Connect;
import chambre.Chambre;

public class Reservation {
    int idreservation;
    Client client;
    String date_reservation;
    int typereservation;
    String check_in_date;
    String check_out_date;
    String souhait_particulier;
    Chambre chambre;

    public Reservation() {
    }

    public Reservation(int idreservation, Client client, String date_reservation, int typereservation,
            String check_in_date, String check_out_date, String souhait_particulier,Chambre chambre) {
        this.idreservation = idreservation;
        this.client = client;
        this.date_reservation = date_reservation;
        this.typereservation = typereservation;
        this.check_in_date = check_in_date;
        this.check_out_date = check_out_date;
        this.souhait_particulier = souhait_particulier;
        this.setChambre(chambre);
    }

    
    public Chambre getChambre() {
		return chambre;
	}

	public void setChambre(Chambre chambre) {
		this.chambre = chambre;
	}
	
	
    public int getIdreservation() {
        return this.idreservation;
    }

    public void setIdreservation(int idreservation) {
        this.idreservation = idreservation;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getDate_reservation() {
        return this.date_reservation;
    }

    public void setDate_reservation(String date_reservation) {
        this.date_reservation = date_reservation;
    }

    public int getTypereservation() {
        return this.typereservation;
    }

    public void setTypereservation(int typereservation) {
        this.typereservation = typereservation;
    }

    public String getCheck_in_date() {
        return this.check_in_date;
    }

    public void setCheck_in_date(String check_in_date) {
        this.check_in_date = check_in_date;
    }

    public String getCheck_out_date() {
        return this.check_out_date;
    }

    public void setCheck_out_date(String check_out_date) {
        this.check_out_date = check_out_date;
    }

    public String getSouhait_particulier() {
        return this.souhait_particulier;
    }

    public void setSouhait_particulier(String souhait_particulier) {
        this.souhait_particulier = souhait_particulier;
    }

    public Reservation idreservation(int idreservation) {
        setIdreservation(idreservation);
        return this;
    }

    public Reservation client(Client client) {
        setClient(client);
        return this;
    }

    public Reservation chambre(Chambre chambre) {
    	setChambre(chambre);
    	return this;
    }
    public Reservation date_reservation(String date_reservation) {
        setDate_reservation(date_reservation);
        return this;
    }

    public Reservation typereservation(int typereservation) {
        setTypereservation(typereservation);
        return this;
    }

    public Reservation check_in_date(String check_in_date) {
        setCheck_in_date(check_in_date);
        return this;
    }

    public Reservation check_out_date(String check_out_date) {
        setCheck_out_date(check_out_date);
        return this;
    }

    public Reservation souhait_particulier(String souhait_particulier) {
        setSouhait_particulier(souhait_particulier);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Reservation)) {
            return false;
        }
        Reservation reservation = (Reservation) o;
        return idreservation == reservation.idreservation && Objects.equals(client, reservation.client)
                && Objects.equals(date_reservation, reservation.date_reservation)
                && typereservation == reservation.typereservation
                && Objects.equals(check_in_date, reservation.check_in_date)
                && Objects.equals(check_out_date, reservation.check_out_date)
                && Objects.equals(souhait_particulier, reservation.souhait_particulier)
                && Objects.equals(chambre, reservation.chambre);
        		
    }

    @Override
    public int hashCode() {
        return Objects.hash(idreservation, client, date_reservation, typereservation, check_in_date, check_out_date,
                souhait_particulier,chambre);
    }

    @Override
    public String toString() {
        return "{" +
                " idreservation='" + getIdreservation() + "'" +
                ", client='" + getClient() + "'" +
                ", date_reservation='" + getDate_reservation() + "'" +
                ", typereservation='" + getTypereservation() + "'" +
                ", check_in_date='" + getCheck_in_date() + "'" +
                ", check_out_date='" + getCheck_out_date() + "'" +
                ", souhait_particulier='" + getSouhait_particulier() + "'" +
                ", chambre='" + getChambre() + "'" +
                "}";
    }

    public static ArrayList<Reservation> getreservationsFromDB(int typereservation, int orderby, int desc) {
        ArrayList<Reservation> listReservations = null;
        try {
            String query = "SELECT r.*,\r\n"
            		+ "       cl.nom_client, cl.prenom_client, cl.adresse, cl.num_tel, cl.email\r\n"
            		+ "FROM reservation r\r\n"
            		+ "JOIN chambre c ON r.id_chambre_reserve = c.numchambre\r\n"
            		+ "JOIN client cl ON r.id_client_reservant = cl.idclient;\r\n"
            		+ "";
            Connection connection = new Connect().getConnection();
            listReservations = new ArrayList<Reservation>();
            if (typereservation != 0)
                query += " and typereservation="
                        + String.valueOf(typereservation);
            switch (orderby) {
                case 1:
                    query += " Order by date_reservation";
                    break;
                case 2:
                    query += " Order by typereservation";
                    break;
                case 3:
                    query += " Order by check_in_date";
                    break;
                case 4:
                    query += " Order by check_out_date";
                    break;
                default:
                    break;
            }
            if (desc != 0)
                query += " DESC";
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {
                Client client = new Client();
                Chambre chambre = new Chambre();
                Reservation reservation = new Reservation();
                //Elements de Reservation
                reservation.setIdreservation(resultSet.getInt(1));
                client.setIdclient(resultSet.getInt(2));
                reservation.setDate_reservation(resultSet.getString(3));
                reservation.setTypereservation(resultSet.getInt(4));
                reservation.setCheck_in_date(resultSet.getString(5));
                reservation.setCheck_out_date(resultSet.getString(6));
                reservation.setSouhait_particulier(resultSet.getString(7));
                
                chambre = Chambre.getRoomFromId(resultSet.getInt(8));
                reservation.setChambre(chambre);
                
                
                client.setNom_client(resultSet.getString(9));
                client.setPrenom_client(resultSet.getString(10));
                
                reservation.setClient(client);
                listReservations.add(reservation);
            }
            connection.close();
            
            return listReservations;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void DeleteReservation(int idReservation) {
        try {
            String query = "Delete from Reservation where idReservation=?";
            Connection connection = new Connect().getConnection();
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, idReservation);
            preparedStmt.executeUpdate();
            connection.close();
        } catch (

        SQLException e) {
            e.printStackTrace();
        }
    }

    // A Ajouter la réservation :
    public static void NewReservation(Reservation reservation) {
        try {
            String query = "INSERT INTO Reservation(id_client_reservant,date_reservation,typereservation,check_in_date,check_out_date,souhait_particulier,id_chambre_reserve) VALUES (?, ?, ?, ?, ?, ?,?);";
            Connection connection = new Connect().getConnection();
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, reservation.getClient().getIdclient());
            preparedStmt.setString(2, reservation.getDate_reservation());
            preparedStmt.setInt(3, reservation.getTypereservation());
            preparedStmt.setString(4, reservation.getCheck_in_date());
            preparedStmt.setString(5, reservation.getCheck_out_date());
            preparedStmt.setString(6, reservation.getSouhait_particulier());
            preparedStmt.setInt(7,reservation.getChambre().getNumChambre());
            preparedStmt.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateReservationDB(Reservation reservation) {
        try {
            String query = "UPDATE reservation SET id_client_reservant=? , date_reservation=? , typereservation=? , check_in_date=? , check_out_date=? , souhait_particulier=? , id_chambre_reserve=? WHERE idReservation=?";
            Connection connection = new Connect().getConnection();
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, reservation.getClient().getIdclient());
            preparedStmt.setString(2, reservation.getDate_reservation());
            preparedStmt.setInt(3, reservation.getTypereservation());
            preparedStmt.setString(4, reservation.getCheck_in_date());
            preparedStmt.setString(5, reservation.getCheck_out_date());
            preparedStmt.setString(6, reservation.getSouhait_particulier());
            preparedStmt.setInt(7, reservation.getChambre().getNumChambre());
            preparedStmt.setInt(8, reservation.getIdreservation());
            preparedStmt.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static int getIdLastInseredReservation() {
        int id = 0;
        try {
            String query = "select max(idReservation) from Reservation";
            Connection connection = new Connect().getConnection();
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            connection.close();
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public static boolean comparerDate(Date d1,Date d2) {
    	int comparisonResult = d1.compareTo(d2);
        
        // Affichage du résultat de la comparaison
        if (comparisonResult < 0) {
            return true; // Le cas ou la date d2 est postérieure
        } else
        	return false; 
    }
    public static void main(String[] args) {
        Reservation.getreservationsFromDB(0, 0, 0);
    }

	
}
