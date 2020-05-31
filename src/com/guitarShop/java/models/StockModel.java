package com.guitarShop.java.models;

import com.guitarShop.java.helpers.AlertFactory;
import com.guitarShop.java.helpers.ConnectionFactory;
import com.guitarShop.java.models.objects.Guitar;
import com.guitarShop.java.models.objects.OrderGuitar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StockModel {

    public StockModel() {}

    public ObservableList<Guitar> getStock() {

        String query = "SELECT GuitarID, ManufacturerName, g.ManufacturerID, GuitarPrice," +
                " GuitarTypeName, g.GuitarTypeID, PickupsTypeName, g.PickupsTypeID, BridgeTypeName, g.BridgeTypeID," +
                " LockingTuners, NumberOfGuitars, Model," +
                " ModelDescription, NumberOfStrings" +
                " FROM Guitars g" +
                " JOIN Manufacturers m ON m.ManufacturerID = g.ManufacturerID" +
                " JOIN PickupsTypes p ON p.PickupsTypeID = g.PickupsTypeID" +
                " JOIN BridgeTypes b ON b.BridgeTypeID = g.BridgeTypeID" +
                " JOIN GuitarTypes gt ON gt.GuitarTypeID = g.GuitarTypeID";
        return getGuitarsStatement(query);
    }

    public ObservableList<Guitar> getStockByOrderID(int orderID) {

        String query = "SELECT g.GuitarID, ManufacturerName, m.ManufacturerID, GuitarPrice, " +
                "GuitarTypeName, g.GuitarTypeID, PickupsTypeName, g.PickupsTypeID, BridgeTypeName, g.BridgeTypeID, " +
                "LockingTuners, NumberOfGuitars, Model, " +
                "ModelDescription, NumberOfStrings " +
                "FROM Guitars g " +
                "JOIN Manufacturers m ON m.ManufacturerID = g.ManufacturerID " +
                "JOIN PickupsTypes p ON p.PickupsTypeID = g.PickupsTypeID " +
                "JOIN BridgeTypes b ON b.BridgeTypeID = g.BridgeTypeID " +
                "JOIN GuitarTypes gt ON gt.GuitarTypeID = g.GuitarTypeID " +
                "JOIN Order_Guitar og ON g.GuitarID = og.GuitarID " +
                "WHERE OrderID = " + orderID;

        return getGuitarsStatement(query);
    }

    private ObservableList<Guitar> getGuitarsStatement(String query) {
        ObservableList<Guitar> stockList = FXCollections.observableArrayList();
        Statement statement = null;


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

    public void updateGuitar(StackPane stackPane, int guitarID, int manufacturerID, String model, String modelDesc, int numbersOfStrings, int guitarPrice, int guitarTypeID,
                             int pickupsTypeID, int bridgeTypeID, Boolean lockingTuners, int quantity) throws SQLException {
        int tuners = 0;
        Statement statement = null;
        if(lockingTuners)
            tuners = 1;

        String query = "UPDATE Guitars SET ManufacturerID = " + manufacturerID +
                ", Model = '" + model + "', ModelDescription = '" + modelDesc + "'," +
                "NumberOfStrings = " + numbersOfStrings + ", GuitarPrice = " + guitarPrice
                +", GuitarTypeID = " + guitarTypeID + ", PickupsTypeID = " + pickupsTypeID
                + ", BridgeTypeID = " + bridgeTypeID + ", LockingTuners = " + tuners + " , NumberOfGuitars = " + quantity + " WHERE GuitarID = " + guitarID;
        try(Connection connection = ConnectionFactory.getConnection()) {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            AlertFactory.makeAlertDialog(stackPane, "Database error", "Item cannot be updated.", "Close");
        }
    }

    public void deleteGuitar(StackPane stackPane, int guitarID) throws SQLException {
        Statement statement = null;
        String query = "DELETE FROM Guitars WHERE GuitarID = " + guitarID;

        try(Connection connection = ConnectionFactory.getConnection()) {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            AlertFactory.makeAlertDialog(stackPane, "Database error", "Item cannot be deleted as it is connected to other tables.", "Close");
        }
    }

    public void addGuitar(StackPane stackPane, int manufacturerID, String model, String modelDesc, int numbersOfStrings, int guitarPrice, int guitarTypeID, int pickupsTypeID, int bridgeTypeID, Boolean lockingTuners, int quantity) {
        int tuners = 0;
        Statement statement = null;
        if(lockingTuners)
            tuners = 1;

        String query = "INSERT INTO Guitars(ManufacturerID, Model, ModelDescription, NumberOfStrings, " +
                "GuitarPrice, GuitarTypeID, PickupsTypeID, BridgeTypeID, LockingTuners, NumberOfGuitars) " +
                "VALUES (" + manufacturerID + ", '" + model + "', '" + modelDesc + "', " + numbersOfStrings +
                ", " + guitarPrice + ", " + guitarTypeID + ", " + pickupsTypeID + ", " + bridgeTypeID + ", " + tuners + ", " + quantity + ")";

        try(Connection connection = ConnectionFactory.getConnection()) {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            AlertFactory.makeAlertDialog(stackPane, "Database error", "Item cannot be added to database.", "Close");
        }
    }

    public Guitar getGuitar(int givenGuitarID) {

        Guitar guitar = null;
        Statement statement = null;
        String query = "SELECT GuitarID, ManufacturerName, g.ManufacturerID, GuitarPrice," +
                " GuitarTypeName, g.GuitarTypeID, PickupsTypeName, g.PickupsTypeID, BridgeTypeName, g.BridgeTypeID," +
                " LockingTuners, NumberOfGuitars, Model," +
                " ModelDescription, NumberOfStrings" +
                " FROM Guitars g" +
                " JOIN Manufacturers m ON m.ManufacturerID = g.ManufacturerID" +
                " JOIN PickupsTypes p ON p.PickupsTypeID = g.PickupsTypeID" +
                " JOIN BridgeTypes b ON b.BridgeTypeID = g.BridgeTypeID" +
                " JOIN GuitarTypes gt ON gt.GuitarTypeID = g.GuitarTypeID WHERE GuitarID = " + givenGuitarID;

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

                guitar = new Guitar(guitarID, manufacturer, manufacturerID, guitarPrice, guitarType, guitarTypeID,
                        pickupsType, pickupsTypeID, bridgeType, bridgeTypeID, lockingTunersString, quantity, model, modelDesc, stringsQuantity);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return guitar;
    }

    public ObservableList<OrderGuitar> getOrderGuitars(StackPane stackPane, int givenOrderID) {
        ObservableList<OrderGuitar> orderGuitarObservableList = FXCollections.observableArrayList();
        Statement statement = null;
        String query = "SELECT * FROM Order_Guitar WHERE OrderID = " + givenOrderID;

        try (Connection connection = ConnectionFactory.getConnection()) {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int orderID = resultSet.getInt("OrderID");
                int guitarID = resultSet.getInt("GuitarID");
                int quantity = resultSet.getInt("Quantity");
                orderGuitarObservableList.add(new OrderGuitar(orderID, guitarID, quantity));
            }

        } catch (SQLException e) {
            AlertFactory.makeDatabaseConnectionError(stackPane);
        }
        return orderGuitarObservableList;
    }
}
