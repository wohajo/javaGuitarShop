package com.guitarShop.java.models;

import com.google.gson.internal.bind.SqlDateTypeAdapter;
import com.guitarShop.java.helpers.AlertFactory;
import com.guitarShop.java.helpers.ConnectionFactory;
import com.guitarShop.java.models.objects.Seller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.StackPane;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SellersModel {

    public ObservableList<Seller> getSellers() {
        ObservableList<Seller> sellersList = FXCollections.observableArrayList();
        Statement statement = null;
        String query = "SELECT SellerID, Name, Surname, PhoneNumber, Pesel, Email, AddressID FROM Sellers";

        try (Connection connection = ConnectionFactory.getConnection()) {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                sellersList.add(new Seller(resultSet.getInt("SellerID"), resultSet.getString("Name"),
                        resultSet.getString("Surname"), resultSet.getString("PhoneNumber"),
                        resultSet.getString("Pesel"), resultSet.getString("Email"), resultSet.getInt("AddressID")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sellersList;
    }

    public Seller getSeller(String query) {
        Seller seller = null;
        Statement statement = null;

        try (Connection connection = ConnectionFactory.getConnection()) {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                seller = new Seller(resultSet.getInt("SellerID"), resultSet.getString("Name"),
                        resultSet.getString("Surname"), resultSet.getString("PhoneNumber"),
                        resultSet.getString("Pesel"), resultSet.getString("Email"), resultSet.getInt("AddressID"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seller;
    }

    public Seller getSellerByID(int sellerID) {
        String query = "SELECT SellerID, Name, Surname, PhoneNumber, Pesel, Email, AddressID FROM Sellers WHERE SellerID = " + sellerID;
        return getSeller(query);
    }

    public void updateSeller(StackPane sellersStackPane, int sellerID, String name, String surname, String phoneNumber, String pesel, int addressID, String email) {

        Statement statement = null;
        String query = "UPDATE Sellers SET Name = '" + name + "', Surname = '" + surname
                + "', PhoneNumber = " + phoneNumber + ", Pesel = " + pesel + ",  AddressID = " + addressID
                + ", Email = '" + email + "' WHERE SellerID = " + sellerID;

        try(Connection connection = ConnectionFactory.getConnection()) {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            AlertFactory.makeAlertDialog(sellersStackPane, "Database error", "Cannot update seller.", "Close");
        }
    }

    public void addSeller(StackPane sellersStackPane, String name, String surname, String phoneNumber, String pesel, int addressID, String email, String password) {
        Statement statement = null;
        String query = "INSERT INTO Sellers (Name, Surname, PhoneNumber, Pesel, AddressID, PasswordHash, Email)\n" +
                "VALUES ('" + name + "', '" + surname+ "', " + phoneNumber + ", "+ pesel + ", " + addressID + ", '"+ password + "', '" + email + "')";

        try(Connection connection = ConnectionFactory.getConnection()) {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            AlertFactory.makeAlertDialog(sellersStackPane, "Database error", "Cannot add seller.", "Close");
        }
    }

    public void deleteSeller(StackPane sellersStackPane, int sellerID) {
        Statement statement = null;
        String query = "DELETE FROM Sellers WHERE SellerID = " + sellerID;
        try(Connection connection = ConnectionFactory.getConnection()) {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            AlertFactory.makeAlertDialog(sellersStackPane, "Database error", "Item cannot be deleted as it is connected to other tables.", "Close");
        }
    }

    public Seller getSellerByEmail(String loggedUserLogin) {
        String query = "SELECT SellerID, Name, Surname, PhoneNumber, Pesel, Email, AddressID FROM Sellers WHERE Email = " + loggedUserLogin;
        return getSeller(query);
    }
}
