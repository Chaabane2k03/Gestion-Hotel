package espace_client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

import DatabaseManagement.Connect;

public class Client {
    int idclient;
    String nom_client;
    String prenom_client;
    String adresse;
    String num_tel;
    String email;

    public Client() {
    }

    public Client(int idclient) {
        this.idclient = idclient;
    }

    public Client(int idclient, String nom_client, String prenom_client, String adresse, String num_tel, String email) {
        this.idclient = idclient;
        this.nom_client = nom_client;
        this.prenom_client = prenom_client;
        this.adresse = adresse;
        this.num_tel = num_tel;
        this.email = email;
    }

    public int getIdclient() {
        return this.idclient;
    }

    public void setIdclient(int idclient) {
        this.idclient = idclient;
    }

    public String getNom_client() {
        return this.nom_client;
    }

    public void setNom_client(String nom_client) {
        this.nom_client = nom_client;
    }

    public String getPrenom_client() {
        return this.prenom_client;
    }

    public void setPrenom_client(String prenom_client) {
        this.prenom_client = prenom_client;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getNum_tel() {
        return this.num_tel;
    }

    public void setNum_tel(String num_tel) {
        this.num_tel = num_tel;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Client idclient(int idclient) {
        setIdclient(idclient);
        return this;
    }

    public Client nom_client(String nom_client) {
        setNom_client(nom_client);
        return this;
    }

    public Client prenom_client(String prenom_client) {
        setPrenom_client(prenom_client);
        return this;
    }

    public Client adresse(String adresse) {
        setAdresse(adresse);
        return this;
    }

    public Client num_tel(String num_tel) {
        setNum_tel(num_tel);
        return this;
    }

    public Client email(String email) {
        setEmail(email);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Client)) {
            return false;
        }
        Client client = (Client) o;
        return idclient == client.idclient && Objects.equals(nom_client, client.nom_client)
                && Objects.equals(prenom_client, client.prenom_client) && Objects.equals(adresse, client.adresse)
                && Objects.equals(num_tel, client.num_tel) && Objects.equals(email, client.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idclient, nom_client, prenom_client, adresse, num_tel, email);
    }

    @Override
    public String toString() {
        return "{" +
                " idclient='" + getIdclient() + "'" +
                ", nom_client='" + getNom_client() + "'" +
                ", prenom_client='" + getPrenom_client() + "'" +
                ", adresse='" + getAdresse() + "'" +
                ", num_tel='" + getNum_tel() + "'" +
                ", email='" + getEmail() + "'" +
                "}";
    }

    public static ArrayList<Client> getClientsFromDB(int orderby, int desc) {
        ArrayList<Client> listClients = null;
        try {
            String query = "SELECT idclient,nom_client,prenom_client,adresse,num_tel,email FROM  client";
            Connection connection = new Connect().getConnection();
            listClients = new ArrayList<Client>();

            switch (orderby) {
                case 1:
                    query += " Order by nom_client";
                    break;
                case 2:
                    query += " Order by prenom_client";
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
                client.setIdclient(resultSet.getInt(1));
                client.setNom_client(resultSet.getString(2));
                client.setPrenom_client(resultSet.getString(3));
                client.setAdresse(resultSet.getString(4));
                client.setNum_tel(resultSet.getString(5));
                client.setEmail(resultSet.getString(6));
                listClients.add(client);
            }
            connection.close();
            for (int i = 0; i < listClients.size(); i++) {
                System.out.println(listClients.get(i));
            }
            return listClients;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void DeleteClient(int idclient) {
        try {
            String query = "Delete from Client where idclient=?";
            Connection connection = new Connect().getConnection();
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, idclient);
            preparedStmt.executeUpdate();
            connection.close();
        } catch (

        SQLException e) {
            e.printStackTrace();
        }
    }

    public static void NewClient(Client client) {
        try {
            String query = "INSERT INTO Client(idclient,nom_client,prenom_client,adresse,num_tel,email) VALUES (?, ?, ?, ?, ?, ?);";
            Connection connection = new Connect().getConnection();
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, client.getIdclient());
            preparedStmt.setString(2, client.getNom_client());
            preparedStmt.setString(3, client.getPrenom_client());
            preparedStmt.setString(4, client.getAdresse());
            preparedStmt.setString(5, client.getNum_tel());
            preparedStmt.setString(6, client.getEmail());
            preparedStmt.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateclientDB(Client client) {
        try {
            String query = "UPDATE client SET idclient=? , nom_client=? , prenom_client=? , adresse=? , num_tel=? , email=?  WHERE idclient=?";
            Connection connection = new Connect().getConnection();
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, client.getIdclient());
            preparedStmt.setString(2, client.getNom_client());
            preparedStmt.setString(3, client.getPrenom_client());
            preparedStmt.setString(4, client.getAdresse());
            preparedStmt.setString(5, client.getNum_tel());
            preparedStmt.setString(6, client.getEmail());
            preparedStmt.setInt(7, client.getIdclient());
            preparedStmt.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public int getNbReservation() {
        Integer nb = 0;
        try {
            String query = "SELECT count(*)\r\n"
                    + "	FROM reservation \r\n"
                    + "	WHERE id_client_reservant = ?;";
            Connection connection = new Connect().getConnection();
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, idclient);
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

    public static ArrayList<Client> getRegulierClientsFromDB() {
        ArrayList<Client> listClients = null;
        try {
            String query = "SELECT idclient, nom_client, prenom_client\r\n" // Determiner top 3 clients reguliers
                    + "FROM client c1\r\n"
                    + "WHERE 3 > ( SELECT count(*)\r\n"
                    + "			FROM client c2\r\n"
                    + "			WHERE (SELECT count(*)\r\n"
                    + "			        FROM reservation r3\r\n"
                    + "			        WHERE r3.id_client_reservant = c1.idclient) < (SELECT count(*)\r\n"
                    + "																	FROM reservation r4\r\n"
                    + "																	WHERE r4.id_client_reservant = c2.idclient)\r\n"
                    + "\r\n"
                    + ")\r\n"
                    + "ORDER BY (SELECT count(*)\r\n"
                    + "			FROM reservation r3\r\n"
                    + "			WHERE r3.id_client_reservant = c1.idclient) DESC;";
            Connection connection = new Connect().getConnection();
            listClients = new ArrayList<Client>();
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {
                Client client = new Client();
                client.setIdclient(resultSet.getInt(1));
                client.setNom_client(resultSet.getString(2));
                client.setPrenom_client(resultSet.getString(3));
                listClients.add(client);
            }
            connection.close();
            for (int i = 0; i < listClients.size(); i++) {
                System.out.println(listClients.get(i));
            }
            return listClients;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getIdLastInseredClient() {
        int id = 0;
        try {
            String query = "select max(idclient) from Client";
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

}
