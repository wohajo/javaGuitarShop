package com.guitarShop.java.models;

import com.guitarShop.java.helpers.ConnectionFactory;
import com.guitarShop.java.models.objects.Seller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SellersModel {

    public ObservableList<Seller> getSellers() {
        ObservableList<Seller> sellersList = FXCollections.observableArrayList();
        Statement statement = null;
        String query = "SELECT SellerID, Name, Surname, PhoneNumber, Pesel, Email, AddressID FROM Sellers";

        try (Connection connection = ConnectionFactory.getConnection()) {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                sellersList.add(new Seller(resultSet.getInt("SellerID"), resultSet.getString("Name"),
                        resultSet.getString("Surname"), resultSet.getString("PhoneNumber"),
                        resultSet.getString("Pesel"), resultSet.getString("Email"), resultSet.getInt("AddressID")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sellersList;
    }

}
