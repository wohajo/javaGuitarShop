package com.guitarShop.java.models;

import com.google.gson.internal.$Gson$Types;
import com.guitarShop.java.helpers.AlertFactory;
import com.guitarShop.java.helpers.ConnectionFactory;
import com.guitarShop.java.models.objects.Guitar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
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

    private void updateStatement(StackPane stackPane, String query) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);

            } catch (SQLException e) {
                AlertFactory.makeDatabaseConnectionError(stackPane);
        }
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

    public void addQuantity(StackPane stackPane, int guitarID, int quantity) throws SQLException {

        String query = "UPDATE Guitars SET NumberOfGuitars = NumberOfGuitars + " + quantity + " WHERE GuitarID = " + guitarID;
        Statement statement = null;
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

    public void addGuitarToOrder(StackPane stackPane, int guitarID, int orderID, int quantity) {
        String query = "INSERT INTO Order_Guitar(OrderID, GuitarID, Quantity) VALUES (" + orderID + ", " + guitarID + ", " + quantity + ")";
        updateStatement(stackPane, query);
    }

    public void updateGuitarQuantity(StackPane stackPane, int guitarID, int orderID, int quantity) {
        String query = "UPDATE Order_Guitar SET Quantity = " + quantity + " WHERE GuitarID = " + guitarID + " AND OrderID = " + orderID;
        updateStatement(stackPane, query);
    }

    public ObservableList<PieChart.Data> getInfoForPieChart() {
        Statement statement = null;
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        String query = "SELECT TOP 5 Guitars.GuitarID, SUM(Quantity) as 'Quantity' FROM Order_Guitar \n" +
                "JOIN Guitars ON Order_Guitar.GuitarID = Guitars.GuitarID\n" +
                "GROUP BY (Guitars.GuitarID) ORDER BY Quantity DESC";

        try (Connection connection = ConnectionFactory.getConnection()) {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                pieChartData.add(new PieChart.Data((getGuitar(resultSet.getInt("GuitarID")).showModel()), resultSet.getInt("Quantity")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pieChartData;
    }

    public XYChart.Series getDataForIncomeChart() {
        Statement statement = null;
        XYChart.Series dataSeries = new XYChart.Series();
        dataSeries.setName("Revenue");

        String query = "SELECT DAY(OrderDate) as 'Day', SUM(Quantity * GuitarPrice) as 'Revenue' \n" +
                "FROM Guitars g\n" +
                "JOIN Order_Guitar og ON og.GuitarID = g.GuitarID\n" +
                "JOIN Orders o ON o.OrderID = og.OrderID \n" +
                "WHERE MONTH(OrderDate) = MONTH(GETDATE())\n" +
                "GROUP BY OrderDate";

        try (Connection connection = ConnectionFactory.getConnection()) {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                dataSeries.getData().add(new XYChart.Data( resultSet.getInt("Day"), resultSet.getInt("Revenue")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dataSeries;
    }
}
