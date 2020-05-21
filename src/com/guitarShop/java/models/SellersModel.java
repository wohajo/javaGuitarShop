package com.guitarShop.java.models;

import com.guitarShop.java.helpers.ConnectionFactory;
import com.guitarShop.java.models.objects.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SellersModel {

    public ObservableList<Client> getSellers() {
        ObservableList<Client> sellersList = FXCollections.observableArrayList();
        Statement statement = null;
        String query = "SELECT SellerID, Name, Surname, PhoneNumber, Pesel, Email FROM Sellers";

        try (Connection connection = ConnectionFactory.getConnection()) {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                sellersList.add(new Client(resultSet.getInt("SellerID"), resultSet.getString("Name"),
                        resultSet.getString("Surname"), resultSet.getString("PhoneNumber"),
                        resultSet.getString("Pesel"), resultSet.getString("Email")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sellersList;
    }

}
