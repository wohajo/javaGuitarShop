package com.guitarShop.java.models;

import com.guitarShop.java.helpers.ConnectionFactory;
import com.guitarShop.java.models.objects.parts.Bridge;
import com.guitarShop.java.models.objects.parts.GuitarType;
import com.guitarShop.java.models.objects.parts.Pickups;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PartsModel {

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

}
