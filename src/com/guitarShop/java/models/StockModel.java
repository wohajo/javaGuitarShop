package com.guitarShop.java.models;

import com.guitarShop.java.helpers.ConnectionFactory;
import com.guitarShop.java.models.objects.Guitar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StockModel {

    public StockModel() {}

    public ObservableList<Guitar> getStock() throws SQLException {

        ObservableList<Guitar> stockList = FXCollections.observableArrayList();
        Statement statement = null;
        String query = "SELECT GuitarID, ManufacturerName, GuitarPrice," +
                " GuitarTypeName, PickupsTypeName, BridgeTypeName," +
                " LockingTuners, NumberOfGuitars, Model," +
                " ModelDescription, NumberOfStrings" +
                " FROM Guitars g" +
                " JOIN Manufacturers m ON m.ManufacturerID = g.ManufacturerID" +
                " JOIN PickupsTypes p ON p.PickupsTypeID = g.PickupsTypeID" +
                " JOIN BridgeTypes b ON b.BridgeTypeID = g.BridgeTypeID" +
                " JOIN GuitarTypes gt ON gt.GuitarTypeID = g.GuitarTypeID";

        try (Connection connection = ConnectionFactory.getConnection()) {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int guitarID = resultSet.getInt("GuitarID");
                String manufacturer = resultSet.getString("ManufacturerName");
                int guitarPrice = resultSet.getInt("GuitarPrice");
                String guitarType = resultSet.getString("GuitarTypeName");
                String pickupsType = resultSet.getString("PickupsTypeName");
                String bridgeType = resultSet.getString("BridgeTypeName");
                Boolean lockingTuners = resultSet.getBoolean("LockingTuners");
                int quantity = resultSet.getInt("NumberOfGuitars");
                String model = resultSet.getString("Model");
                String modelDesc = resultSet.getString("ModelDescription");
                int stringsQuantity = resultSet.getInt("NumberOfStrings");

                stockList.add(new Guitar(guitarID, manufacturer, guitarPrice, guitarType,
                        pickupsType, bridgeType, lockingTuners, quantity, model, modelDesc, stringsQuantity));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stockList;
    }
}
