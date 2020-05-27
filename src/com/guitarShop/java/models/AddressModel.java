package com.guitarShop.java.models;

import com.guitarShop.java.helpers.ConnectionFactory;
import com.guitarShop.java.models.objects.Address;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AddressModel {

    public ObservableList<Address> getAddresses() {
        ObservableList<Address> addressesList = FXCollections.observableArrayList();
        Statement statement = null;
        String query = "SELECT * FROM Addresses";

        try (Connection connection = ConnectionFactory.getConnection()) {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                addressesList.add(new Address(resultSet.getInt("AddressID"),
                        resultSet.getString("City"), resultSet.getString("Postcode"),
                        resultSet.getString("Street"), resultSet.getInt("BuildingNumber"),
                        resultSet.getString("FlatNumber")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return addressesList;
    }

    public Address getAddress(int addressID) {
        Statement statement = null;
        String query = "SELECT * FROM Addresses WHERE AddressID = " + addressID;
        Address address = null;
        try (Connection connection = ConnectionFactory.getConnection()) {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                address = new Address(resultSet.getInt("AddressID"),
                        resultSet.getString("City"), resultSet.getString("Postcode"),
                        resultSet.getString("Street"), resultSet.getInt("BuildingNumber"),
                        resultSet.getString("FlatNumber"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return address;
    }
}
