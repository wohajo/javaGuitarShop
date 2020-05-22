package com.guitarShop.java.models;

import com.guitarShop.java.helpers.ConnectionFactory;
import com.guitarShop.java.models.objects.Client;
import com.sun.glass.ui.EventLoop;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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

    // TODO fix null flat number
    public String getAddressByID(int clientAddressID) {
        String address = "";
        Statement statement = null;
        String query = "SELECT City, Postcode, Street, BuildingNumber, FlatNumber FROM Addresses WHERE AddressID = " + clientAddressID;

        try (Connection connection = ConnectionFactory.getConnection()) {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                address = resultSet.getString("City") + " " + resultSet.getString("Postcode") + "\n"
                        + resultSet.getString("Street") + " " +resultSet.getInt("BuildingNumber")
                        + "/" + resultSet.getString("FlatNumber");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return address;
    }
}
