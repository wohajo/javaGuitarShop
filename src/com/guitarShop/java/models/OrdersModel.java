package com.guitarShop.java.models;

import com.guitarShop.java.helpers.ConnectionFactory;
import com.guitarShop.java.models.objects.Guitar;
import com.guitarShop.java.models.objects.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.LinkedHashMap;

public class OrdersModel {

    StockModel stockModel = new StockModel();

    private ObservableList<Order> downloadOrders(String query) {
        ObservableList<Order> orderObservableList = FXCollections.observableArrayList();
        Statement statement = null;

        try (Connection connection = ConnectionFactory.getConnection()) {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int orderID = resultSet.getInt("OrderID");
                LocalDate orderDate = resultSet.getDate("OrderDate").toLocalDate();
                int sellerID = resultSet.getInt("SellerID");
                int clientID = resultSet.getInt("ClientID");
                orderObservableList.add(new Order(orderID, orderDate, sellerID, clientID, getGuitarsForOrder(orderID)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderObservableList;
    }

    public ObservableList<Order> getOrders() {
        return downloadOrders("SELECT * FROM Orders");
    }

    public ObservableList<Order> getOrdersWithClientID(int givenClientID) {
        return downloadOrders("SELECT * FROM Orders WHERE ClientID = " + givenClientID);
    }



    public LinkedHashMap<Guitar, Integer> getGuitarsForOrder(int givenOrderID) {
        LinkedHashMap<Guitar, Integer> guitarsInOrder = new LinkedHashMap<>();
        Statement statement = null;
        String query = "SELECT * FROM Order_Guitar WHERE OrderID = " + givenOrderID;

        try (Connection connection = ConnectionFactory.getConnection()) {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int guitarID = resultSet.getInt("GuitarID");
                int quanitity = resultSet.getInt("GuitarID");
                guitarsInOrder.put(stockModel.getGuitar(guitarID), quanitity);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return guitarsInOrder;
    }
}
