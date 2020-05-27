package com.guitarShop.java.models;

import com.guitarShop.java.helpers.AlertFactory;
import com.guitarShop.java.helpers.ConnectionFactory;
import com.guitarShop.java.models.objects.Manufacturer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.StackPane;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ManufacturerModel {

    public ObservableList<Manufacturer> getManufacturers() {

        ObservableList<Manufacturer> manufacturerObservableList = FXCollections.observableArrayList();
        Statement statement = null;
        String query = "SELECT * FROM Manufacturers";
        try(Connection connection = ConnectionFactory.getConnection()) {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                manufacturerObservableList.add(new Manufacturer(resultSet.getInt("ManufacturerID"),
                        resultSet.getString("ManufacturerName"), resultSet.getInt("AddressID")));
            }

        } catch (SQLException e) {

        }
        return manufacturerObservableList;
    }

    public void updateManufacturer(StackPane manufacturersStackPane, int manufacturerID, String manufacturerName, int addressID) {
        Statement statement = null;
        String query = "UPDATE Manufacturers SET ManufacturerName = '" + manufacturerName
                + "',  AddressID = " + addressID
                + " WHERE ManufacturerID = " + manufacturerID;
        try(Connection connection = ConnectionFactory.getConnection()) {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            AlertFactory.makeAlertDialog(manufacturersStackPane, "Database error", "Cannot update manufacturer.", "Close");
        }
    }

    public void addManufacturer(StackPane manufacturersStackPane, String manufacturerName, int addressID) {
        Statement statement = null;
        String query = "INSERT INTO Manufacturers(ManufacturerName, AddressID) "
                + "VALUES ('" + manufacturerName + "', " + addressID + ")";
        try(Connection connection = ConnectionFactory.getConnection()) {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            AlertFactory.makeAlertDialog(manufacturersStackPane, "Database error", "Cannot add manufacturer.", "Close");
        }
    }

    public void deleteManufacturer(StackPane manufacturersStackPane, int manufacturerID) {
        Statement statement = null;
        String query = "DELETE FROM Manufacturers WHERE ManufacturerID = " + manufacturerID;
        try(Connection connection = ConnectionFactory.getConnection()) {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            AlertFactory.makeAlertDialog(manufacturersStackPane, "Database error", "Item cannot be deleted as it is connected to other tables.", "Close");
        }
    }
}
