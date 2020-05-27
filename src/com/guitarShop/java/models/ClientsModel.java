package com.guitarShop.java.models;

import com.guitarShop.java.helpers.AlertFactory;
import com.guitarShop.java.helpers.ConnectionFactory;
import com.guitarShop.java.models.objects.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.StackPane;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClientsModel {

    public ObservableList<Client> getClients() {
        ObservableList<Client> clientsList = FXCollections.observableArrayList();
        Statement statement = null;
        String query = "SELECT ClientID, Name, Surname, PhoneNumber, Pesel, Email, AddressID FROM Clients";

        try (Connection connection = ConnectionFactory.getConnection()) {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                clientsList.add(new Client(resultSet.getInt("ClientID"), resultSet.getString("Name"),
                        resultSet.getString("Surname"), resultSet.getString("PhoneNumber"),
                        resultSet.getString("Pesel"), resultSet.getString("Email"), resultSet.getInt("AddressID")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientsList;
    }

    public void updateClient (StackPane clientsStackPane, int clientID, String name, String surname, String phoneNumber, String pesel, int addressID, String email) {
        Statement statement = null;
        String query = "UPDATE Clients SET Name = '" + name + "', Surname = '" + surname
                + "', PhoneNumber = " + phoneNumber + ", Pesel = " + pesel + ",  AddressID = " + addressID
                + ", Email = '" + email + "' WHERE ClientID = " + clientID;

        try(Connection connection = ConnectionFactory.getConnection()) {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            AlertFactory.makeAlertDialog(clientsStackPane, "Database error", "Cannot update client.", "Close");
        }
    }

    public void addClient(StackPane clientsStackPane, String name, String surname, String phoneNumber, String pesel, int addressID, String email) {
        Statement statement = null;
        String query = "INSERT INTO Clients (Name, Surname, PhoneNumber, Pesel, AddressID, Email)\n" +
                "VALUES ('" + name + "', '" + surname+ "', " + phoneNumber + ", "+ pesel + ", " + addressID + ", '" + email + "')";

        try(Connection connection = ConnectionFactory.getConnection()) {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            AlertFactory.makeAlertDialog(clientsStackPane, "Database error", "Cannot add client.", "Close");
        }
    }

    public void deleteClient(StackPane clientsStackPane, int clientID) {
        Statement statement = null;
        String query = "DELETE FROM Clients WHERE ClientID = " + clientID;
        try(Connection connection = ConnectionFactory.getConnection()) {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            AlertFactory.makeAlertDialog(clientsStackPane, "Database error", "Item cannot be deleted as it is connected to other tables.", "Close");
        }
    }
}
