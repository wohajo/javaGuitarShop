package com.guitarShop.java.models;

import com.guitarShop.java.helpers.AlertFactory;
import com.guitarShop.java.helpers.ConnectionFactory;
import com.guitarShop.java.models.objects.parts.Bridge;
import com.guitarShop.java.models.objects.parts.GuitarType;
import com.guitarShop.java.models.objects.parts.Pickups;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.StackPane;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PartsModel {

    private void executeQuery(StackPane partsStackPane, String query, String errorMsq) {
        Statement statement = null;
        try(Connection connection = ConnectionFactory.getConnection()) {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            AlertFactory.makeAlertDialog(partsStackPane, "Database error", "Cannot " + errorMsq + ".", "Close");
        }
    }

    public ObservableList<Pickups> getPickups() {
        ObservableList<Pickups> pickupsObservableList = FXCollections.observableArrayList();
        Statement statement = null;
        String query = "SELECT PickupsTypeID, PickupsTypeName, PickupsTypes.ManufacturerID, ManufacturerName FROM PickupsTypes" +
                " JOIN Manufacturers" +
                " ON PickupsTypes.ManufacturerID = Manufacturers.ManufacturerID";

        try(Connection connection = ConnectionFactory.getConnection()) {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                pickupsObservableList.add(new Pickups(resultSet.getInt("PickupsTypeID"), resultSet.getString("PickupsTypeName"),
                        resultSet.getString("ManufacturerName"), resultSet.getInt("ManufacturerID")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pickupsObservableList;
    }

    public ObservableList<Bridge> getBridges() {
        ObservableList<Bridge> bridgeObservableList = FXCollections.observableArrayList();
        Statement statement = null;
        String query = "SELECT BridgeTypeID, BridgeTypeName, BridgeTypes.ManufacturerID, ManufacturerName FROM BridgeTypes JOIN Manufacturers ON" +
        " BridgeTypes.ManufacturerID = Manufacturers.ManufacturerID";

        try(Connection connection = ConnectionFactory.getConnection()) {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                bridgeObservableList.add(new Bridge(resultSet.getInt("BridgeTypeID"), resultSet.getString("BridgeTypeName"),
                        resultSet.getString("ManufacturerName"), resultSet.getInt("ManufacturerID")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bridgeObservableList;
    }

    public ObservableList<GuitarType> getGuitarTypes() {
        ObservableList<GuitarType> guitarTypeObservableList = FXCollections.observableArrayList();
        Statement statement = null;
        String query = "SELECT * FROM GuitarTypes";

        try(Connection connection = ConnectionFactory.getConnection()) {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                guitarTypeObservableList.add(new GuitarType(resultSet.getInt("GuitarTypeID"), resultSet.getString("GuitarTypeName")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return guitarTypeObservableList;
    }

    public void updateBridge(StackPane partsStackPane, int bridgeID, String nameText, int manufacturerID) {
        String query = "UPDATE BridgeTypes SET BridgeTypeName = '" + nameText + "', ManufacturerID = " + manufacturerID + " WHERE BridgeTypeID = " + bridgeID;
        executeQuery(partsStackPane, query, "update bridge");
    }

    public void addBridge(StackPane partsStackPane, String nameText, int manufacturerID) {
        String query = "INSERT INTO BridgeTypes(BridgeTypeName, ManufacturerID)"
                + " VALUES ('" + nameText + "', " + manufacturerID + ")";
        executeQuery(partsStackPane, query, "add bridge");
    }

    public void deleteBridge(StackPane partsStackPane, int bridgeID) {
        String query = "DELETE FROM BridgeTypes WHERE BridgeTypeID = " + bridgeID;
        executeQuery(partsStackPane, query, "delete bridge as it is connected to other tables");
    }

    public void updatePickups(StackPane partsStackPane, int pickupsID, String nameText, int manufacturerID) {
        String query = "UPDATE PickupsTypes SET PickupsTypeName = '" + nameText + "', ManufacturerID = " + manufacturerID + " WHERE PickupsTypeID = " + pickupsID;
        executeQuery(partsStackPane, query, "update pickups");
    }

    public void addPickups(StackPane partsStackPane, String nameText, int manufacturerID) {
        String query = "INSERT INTO PickupsTypes(PickupsTypeName, ManufacturerID)"
                + " VALUES ('" + nameText + "', " + manufacturerID + ")";
        executeQuery(partsStackPane, query, "add pickups");
    }

    public void deletePickups(StackPane partsStackPane, int pickupsID) {
        String query = "DELETE FROM PickupsTypes WHERE PickupsTypeID = " + pickupsID;
        executeQuery(partsStackPane, query, "delete pickups as it is connected to other tables");
    }

    public void updateType(StackPane partsStackPane, int typeID, String nameText) {
        String query = "UPDATE GuitarTypes SET GuitarTypeName = '" + nameText + "' WHERE GuitarTypeID = " + typeID;
        executeQuery(partsStackPane, query, "update type");

    }

    public void addType(StackPane partsStackPane, String nameText) {
        String query = "INSERT INTO GuitarTypes(GuitarTypeName)" +
                " VALUES ('" + nameText + "')";
        executeQuery(partsStackPane, query, "update type");

    }

    public void deleteType(StackPane partsStackPane, int typeID) {
        String query = "DELETE FROM GuitarTypes WHERE GuitarTypeID = " + typeID;
        executeQuery(partsStackPane, query, "delete type as it is connected to other tables");
    }
}
