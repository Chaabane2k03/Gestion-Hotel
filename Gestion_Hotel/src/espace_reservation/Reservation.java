package espace_reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

import espace_client.Client;
import DatabaseManagement.Connect;

public class Reservation {
    int idreservation;
    Client client;
    String date_reservation;
    int typereservation;
    String check_in_date;
    String check_out_date;
    String souhait_particulier;

    public Reservation() {
    }

    public Reservation(int idreservation, Client client, String date_reservation, int typereservation,
            String check_in_date, String check_out_date, String souhait_particulier) {
        this.idreservation = idreservation;
        this.client = client;
        this.date_reservation = date_reservation;
        this.typereservation = typereservation;
        this.check_in_date = check_in_date;
        this.check_out_date = check_out_date;
        this.souhait_particulier = souhait_particulier;
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
                && Objects.equals(souhait_particulier, reservation.souhait_particulier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idreservation, client, date_reservation, typereservation, check_in_date, check_out_date,
                souhait_particulier);
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
                "}";
    }

    public static ArrayList<Reservation> getreservationsFromDB(int typereservation, int orderby, int desc) {
        ArrayList<Reservation> listReservations = null;
        try {
            String query = "SELECT idreservation,id_client_reservant,nom_client,prenom_client,date_reservation,typereservation,check_in_date,check_out_date,souhait_particulier FROM  reservation ,client where id_client_reservant = idclient";
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
                Reservation reservation = new Reservation();
                reservation.setIdreservation(resultSet.getInt(1));
                client.setIdclient(resultSet.getInt(2));
                client.setNom_client(resultSet.getString(3));
                client.setPrenom_client(resultSet.getString(4));
                reservation.setDate_reservation(resultSet.getString(5));
                reservation.setTypereservation(resultSet.getInt(6));
                reservation.setCheck_in_date(resultSet.getString(7));
                reservation.setCheck_out_date(resultSet.getString(8));
                reservation.setSouhait_particulier(resultSet.getString(9));
                reservation.setClient(client);
                listReservations.add(reservation);
            }
            connection.close();
            for (int i = 0; i < listReservations.size(); i++) {
                System.out.println(listReservations.get(i));
            }
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

    public static void NewReservation(Reservation reservation) {
        try {
            String query = "INSERT INTO Reservation(id_client_reservant,date_reservation,typereservation,check_in_date,check_out_date,souhait_particulier) VALUES (?, ?, ?, ?, ?, ?);";
            Connection connection = new Connect().getConnection();
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, reservation.getClient().getIdclient());
            preparedStmt.setString(2, reservation.getDate_reservation());
            preparedStmt.setInt(3, reservation.getTypereservation());
            preparedStmt.setString(4, reservation.getCheck_in_date());
            preparedStmt.setString(5, reservation.getCheck_out_date());
            preparedStmt.setString(6, reservation.getSouhait_particulier());
            preparedStmt.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateReservationDB(Reservation reservation) {
        try {
            String query = "UPDATE reservation SET id_client_reservant=? , date_reservation=? , typereservation=? , check_in_date=? , check_out_date=? , souhait_particulier=?  WHERE idReservation=?";
            Connection connection = new Connect().getConnection();
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, reservation.getClient().getIdclient());
            preparedStmt.setString(2, reservation.getDate_reservation());
            preparedStmt.setInt(3, reservation.getTypereservation());
            preparedStmt.setString(4, reservation.getCheck_in_date());
            preparedStmt.setString(5, reservation.getCheck_out_date());
            preparedStmt.setString(6, reservation.getSouhait_particulier());
            preparedStmt.setInt(7, reservation.getIdreservation());
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

    public static void main(String[] args) {
        Reservation.getreservationsFromDB(0, 0, 0);
    }
}
