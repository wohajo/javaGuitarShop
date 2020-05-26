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
        String query = "SELECT GuitarID, ManufacturerName, g.ManufacturerID, GuitarPrice," +
                " GuitarTypeName, g.GuitarTypeID, PickupsTypeName, g.PickupsTypeID, BridgeTypeName, g.BridgeTypeID," +
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
                int manufacturerID = resultSet.getInt("ManufacturerID");
                int guitarPrice = resultSet.getInt("GuitarPrice");
                String guitarType = resultSet.getString("GuitarTypeName");
                int guitarTypeID = resultSet.getInt("guitarTypeID");
                String pickupsType = resultSet.getString("PickupsTypeName");
                int pickupsTypeID = resultSet.getInt("PickupsTypeID");
                String bridgeType = resultSet.getString("BridgeTypeName");
                int bridgeTypeID = resultSet.getInt("BridgeTypeID");
                Boolean lockingTuners = resultSet.getBoolean("LockingTuners");
                String lockingTunersString = "";

                if(lockingTuners)
                    lockingTunersString = "Locking";
                else
                    lockingTunersString = "Standard";

                int quantity = resultSet.getInt("NumberOfGuitars");
                String model = resultSet.getString("Model");
                String modelDesc = resultSet.getString("ModelDescription");
                int stringsQuantity = resultSet.getInt("NumberOfStrings");

                stockList.add(new Guitar(guitarID, manufacturer, manufacturerID, guitarPrice, guitarType, guitarTypeID,
                        pickupsType, pickupsTypeID, bridgeType, bridgeTypeID, lockingTunersString, quantity, model, modelDesc, stringsQuantity));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stockList;
    }

    public void updateGuitar(int guitarID, int manufacturerID, String model, String modelDesc, int numbersOfStrings, int guitarPrice, int guitarTypeID,
                             int PickupsTypeID, int BridgeTypeID, Boolean lockingTuners, int quantity) throws SQLException {
        try(Connection connection = ConnectionFactory.getConnection()) {

        } catch (SQLException e) {

        }
    }
}
