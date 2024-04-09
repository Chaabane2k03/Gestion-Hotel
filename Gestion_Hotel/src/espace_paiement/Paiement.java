package espace_paiement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

import espace_client.Client;
import DatabaseManagement.Connect;

public class Paiement {
    int idpaiement;
    int idreservation;
    Double montant;
    String date;
    Double surcharge;
    int typepaiement;
    Client client;

    public Paiement() {
    }

    public Paiement(int idpaiement, int idreservation, Double montant, String date, Double surcharge,
            int typepaiement) {
        this.idpaiement = idpaiement;
        this.idreservation = idreservation;
        this.montant = montant;
        this.date = date;
        this.surcharge = surcharge;
        this.typepaiement = typepaiement;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public int getIdpaiement() {
        return this.idpaiement;
    }

    public void setIdpaiement(int idpaiement) {
        this.idpaiement = idpaiement;
    }

    public int getIdreservation() {
        return this.idreservation;
    }

    public void setIdreservation(int idreservation) {
        this.idreservation = idreservation;
    }

    public Double getMontant() {
        return this.montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getSurcharge() {
        return this.surcharge;
    }

    public void setSurcharge(Double surcharge) {
        this.surcharge = surcharge;
    }

    public int getTypepaiement() {
        return this.typepaiement;
    }

    public void setTypepaiement(int typepaiement) {
        this.typepaiement = typepaiement;
    }

    public Paiement idpaiement(int idpaiement) {
        setIdpaiement(idpaiement);
        return this;
    }

    public Paiement idreservation(int idreservation) {
        setIdreservation(idreservation);
        return this;
    }

    public Paiement montant(Double montant) {
        setMontant(montant);
        return this;
    }

    public Paiement date(String date) {
        setDate(date);
        return this;
    }

    public Paiement surcharge(Double surcharge) {
        setSurcharge(surcharge);
        return this;
    }

    public Paiement typepaiement(int typepaiement) {
        setTypepaiement(typepaiement);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Paiement)) {
            return false;
        }
        Paiement paiement = (Paiement) o;
        return idpaiement == paiement.idpaiement && idreservation == paiement.idreservation
                && Objects.equals(montant, paiement.montant) && Objects.equals(date, paiement.date)
                && Objects.equals(surcharge, paiement.surcharge) && typepaiement == paiement.typepaiement;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idpaiement, idreservation, montant, date, surcharge, typepaiement);
    }

    @Override
    public String toString() {
        return "{" +
                " idpaiement='" + getIdpaiement() + "'" +
                ", idreservation='" + getIdreservation() + "'" +
                ", montant='" + getMontant() + "'" +
                ", date='" + getDate() + "'" +
                ", surcharge='" + getSurcharge() + "'" +
                ", typepaiement='" + getTypepaiement() + "'" +
                "}";
    }

    public static ArrayList<Paiement> getpaiementsFromDB(int typepaiement, int orderby, int desc) {
        ArrayList<Paiement> listPaiements = null;
        try {
            String query = "SELECT idpaiement,paiement.idreservation,nom_client,prenom_client,montant,date,surcharge,typepaiement FROM  paiement,client,reservation where paiement.idReservation=Reservation.idReservation and Reservation.id_client_reservant=client.idclient";
            Connection connection = new Connect().getConnection();
            listPaiements = new ArrayList<Paiement>();
            if (typepaiement != 0)
                query += " and typepaiement="
                        + String.valueOf(typepaiement);
            switch (orderby) {
                case 1:
                    query += " Order by montant";
                    break;
                case 2:
                    query += " Order by date";
                    break;
                case 3:
                    query += " Order by surcharge";
                    break;
                case 4:
                    query += " Order by typepaiement";
                    break;
                default:
                    break;
            }
            if (desc != 0)
                query += " DESC";
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {
                Paiement paiement = new Paiement();
                Client client = new Client();
                paiement.setIdpaiement(resultSet.getInt(1));
                paiement.setIdreservation(resultSet.getInt(2));
                client.setNom_client(resultSet.getString(3));
                client.setPrenom_client(resultSet.getString(4));
                paiement.setMontant(resultSet.getDouble(5));
                paiement.setDate(resultSet.getString(6));
                paiement.setSurcharge(resultSet.getDouble(7));
                paiement.setTypepaiement(resultSet.getInt(8));
                paiement.setClient(client);
                listPaiements.add(paiement);
            }
            connection.close();
            for (int i = 0; i < listPaiements.size(); i++) {
                System.out.println(listPaiements.get(i));
            }
            return listPaiements;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void DeletePaiement(int idPaiement) {
        try {
            String query = "Delete from Paiement where idPaiement=?";
            Connection connection = new Connect().getConnection();
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, idPaiement);
            preparedStmt.executeUpdate();
            connection.close();
        } catch (

        SQLException e) {
            e.printStackTrace();
        }
    }

    public static void NewPaiement(Paiement paiement) {
        try {
            String query = "INSERT INTO Paiement(idreservation,montant,date,surcharge,typepaiement) VALUES (?, ?, ?, ?, ?);";
            Connection connection = new Connect().getConnection();
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, paiement.getIdreservation());
            preparedStmt.setDouble(2, paiement.getMontant());
            preparedStmt.setString(3, paiement.getDate());
            preparedStmt.setDouble(4, paiement.getSurcharge());
            preparedStmt.setInt(5, paiement.getTypepaiement());
            preparedStmt.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updatePaiementDB(Paiement paiement) {
        try {
            String query = "UPDATE paiement SET idreservation=? , montant=? , date=? , surcharge=? , typepaiement=? WHERE idPaiement=?";
            Connection connection = new Connect().getConnection();
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, paiement.getIdreservation());
            preparedStmt.setDouble(2, paiement.getMontant());
            preparedStmt.setString(3, paiement.getDate());
            preparedStmt.setDouble(4, paiement.getSurcharge());
            preparedStmt.setInt(5, paiement.getTypepaiement());
            preparedStmt.setInt(6, paiement.getIdpaiement());
            preparedStmt.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static int getIdLastInseredPaiement() {
        int id = 0;
        try {
            String query = "select max(idPaiement) from Paiement";
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
        Paiement.getpaiementsFromDB(0, 0, 0);
    }

}
