package com.guitarShop.java.models;

import com.guitarShop.java.helpers.ConnectionFactory;
import com.guitarShop.java.models.objects.Client;
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
        String query = "SELECT ClientID, Name, Surname, PhoneNumber, Pesel, Email FROM Clients";

        try (Connection connection = ConnectionFactory.getConnection()) {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                clientsList.add(new Client(resultSet.getInt("ClientID"), resultSet.getString("Name"),
                        resultSet.getString("Surname"), resultSet.getString("PhoneNumber"),
                        resultSet.getString("Pesel"), resultSet.getString("Email")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientsList;
    }
}
