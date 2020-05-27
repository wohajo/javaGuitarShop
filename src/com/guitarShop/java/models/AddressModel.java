package com.guitarShop.java.models;

import com.guitarShop.java.helpers.AlertFactory;
import com.guitarShop.java.helpers.ConnectionFactory;
import com.guitarShop.java.models.objects.Address;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.StackPane;

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

    public void updateAddress(StackPane addressesStackPane, String city, String postcode, String street, String building, String flat, int addressID) {
        Statement statement = null;
        String query = "UPDATE Addresses SET City = '" + city + "', Postcode = '" + postcode
                + "', Street = '" + street + "', BuildingNumber = " + building + ",  FlatNumber = " + flat
                + " WHERE AddressID = " + addressID;

        try(Connection connection = ConnectionFactory.getConnection()) {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            AlertFactory.makeAlertDialog(addressesStackPane, "Database error", "Cannot update address.", "Close");
        }
    }

    public void addAddress(StackPane addressesStackPane, String city, String postcode, String street, String building, String flat) {
        Statement statement = null;
        String query = "INSERT INTO Addresses(City, Postcode, Street, BuildingNumber, FlatNumber)" +
                "VALUES('" + city + "', '" + postcode + "', '" + street + "', " + building + ", " + flat + ")";

        try(Connection connection = ConnectionFactory.getConnection()) {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            AlertFactory.makeAlertDialog(addressesStackPane, "Database error", "Cannot add address.", "Close");
        }
    }

    public void deleteAddress(StackPane addressesStackPane, int addressID) {
        Statement statement = null;
        String query = "DELETE FROM Addresses WHERE AddressID = " + addressID;
        try(Connection connection = ConnectionFactory.getConnection()) {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            AlertFactory.makeAlertDialog(addressesStackPane, "Database error", "Item cannot be deleted as it is connected to other tables.", "Close");
        }
    }
}
