package com.guitarShop.java.models;

import com.guitarShop.java.helpers.ConnectionFactory;
import com.guitarShop.java.models.objects.Manufacturer;
import com.jfoenix.controls.RecursiveTreeItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
}
