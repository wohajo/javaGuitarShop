package com.guitarShop.java.models;

import com.guitarShop.java.helpers.AlertFactory;
import com.guitarShop.java.helpers.ConnectionFactory;
import com.guitarShop.java.models.objects.Guitar;
import com.guitarShop.java.models.objects.Order;
import com.guitarShop.java.models.objects.OrderGuitar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.StackPane;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;

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
                String seller = resultSet.getString("SellerFullName");
                int clientID = resultSet.getInt("ClientID");
                String client = resultSet.getString("ClientFullName");
                orderObservableList.add(new Order(orderID, orderDate, sellerID, seller, clientID, client, getGuitarsForOrder(orderID)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderObservableList;
    }

    public ObservableList<Order> getOrders() {
        return downloadOrders("SELECT (CONCAT(s.Name, ' ', s.Surname)) AS 'SellerFullName', (CONCAT(c.Name, ' ', c.Surname)) AS 'ClientFullName', OrderID, s.SellerID, c.ClientID, OrderDate FROM Orders o " +
                " JOIN Sellers s ON o.SellerID = s.SellerID" +
                " JOIN Clients c ON c.ClientID = o.ClientID");
    }

    public ObservableList<Order> getOrdersWithClientID(int givenClientID) {
        return downloadOrders("SELECT (CONCAT(s.Name, ' ', s.Surname)) AS 'SellerFullName', (CONCAT(c.Name, ' ', c.Surname)) AS 'ClientFullName', OrderID, s.SellerID, c.ClientID, OrderDate FROM Orders o " +
                " JOIN Sellers s ON o.SellerID = s.SellerID" +
                " JOIN Clients c ON c.ClientID = o.ClientID WHERE c.ClientID = " + givenClientID);
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

    public ObservableList<OrderGuitar> getOrderGuitarsByOrderID(StackPane stackPane, int givenOrderID) {
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

    public String getQuantityByIDs(StackPane stackPane, int orderID, int guitarID) {
        Statement statement = null;
        int quantity = 0;
        String query = "SELECT Quantity FROM Order_Guitar WHERE OrderID = " + orderID + " AND GuitarID = " + guitarID;

        try (Connection connection = ConnectionFactory.getConnection()) {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                quantity = resultSet.getInt("Quantity");
            }

        } catch (SQLException e) {
            AlertFactory.makeDatabaseConnectionError(stackPane);
        }

        return String.valueOf(quantity);
    }

    private void executeUpdate(StackPane stackPane, String query) {
        Statement statement = null;
        try (Connection connection = ConnectionFactory.getConnection()) {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            AlertFactory.makeDatabaseConnectionError(stackPane);
        }
    }

    public void updateOrder(StackPane stackPane, int orderID, int sellerID, int clientID, LocalDate date) {
        String query = "UPDATE Orders SET SellerID = " + sellerID + ", ClientID = " + clientID + ", OrderDate = '" + date + "' WHERE OrderID = " + orderID;
        executeUpdate(stackPane, query);
    }

    public void addOrder(StackPane ordersInfoStackPane, int clientID, int sellerID, LocalDate date, List<OrderGuitar> quantityToAdd) {
        String query = "INSERT INTO Orders(OrderDate, SellerID, ClientID)" +
                "VALUES ('" + date + "', "+ sellerID + ", " + clientID + ")";
        executeUpdate(ordersInfoStackPane, query);

        for (OrderGuitar og : quantityToAdd) {
            query = "INSERT INTO Order_Guitar(OrderID, GuitarID, Quantity)" +
                    "VALUES ((SELECT MAX(OrderID) as 'OrderID' FROM Orders), " + og.getGuitarID() + ", " + og.getQuantity() + ")";
            executeUpdate(ordersInfoStackPane, query);

            query = "UPDATE Guitars SET NumberOfGuitars = " +
                    "(SELECT NumberOfGuitars) - " + og.getQuantity() +
                    " WHERE GuitarID = " + og.getGuitarID();
            executeUpdate(ordersInfoStackPane, query);
        }
    }

    public void deleteOrder(StackPane ordersInfoStackPane, int orderID) {
        String query = "DELETE FROM Order_Guitar WHERE OrderID = " + orderID;
        executeUpdate(ordersInfoStackPane, query);
        query = "DELETE FROM Orders WHERE OrderID = " + orderID;
        executeUpdate(ordersInfoStackPane, query);
        // TODO ADD TO STOCK
    }
}
