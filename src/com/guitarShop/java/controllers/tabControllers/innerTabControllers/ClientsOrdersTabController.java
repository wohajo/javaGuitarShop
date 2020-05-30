package com.guitarShop.java.controllers.tabControllers.innerTabControllers;
import com.guitarShop.java.helpers.AlertFactory;
import com.guitarShop.java.models.*;
import com.guitarShop.java.models.objects.Client;
import com.guitarShop.java.models.objects.Guitar;
import com.guitarShop.java.models.objects.Order;
import com.guitarShop.java.models.objects.OrderGuitar;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.StackPane;

import java.sql.SQLException;

public class ClientsOrdersTabController {

    @FXML private JFXTextArea addressBox;
    @FXML private JFXTextArea sellerBox;
    @FXML private JFXTextArea priceBox;
    @FXML private JFXTextArea dateBox;
    @FXML private StackPane clientsOrdersStackPane;
    @FXML private JFXComboBox clientsComboBox;
    @FXML private JFXComboBox orderComboBox;
    @FXML private JFXTreeTableView<Guitar> itemsTable;
    @FXML private JFXTreeTableView<OrderGuitar> quantityTable;
    @FXML private TreeTableColumn<Guitar, String> manCol;
    @FXML private TreeTableColumn<Guitar, String> modelCol;
    @FXML private TreeTableColumn<Guitar, Double> priceCol;
    @FXML private TreeTableColumn<OrderGuitar, Integer> quantityCol;
    private TreeItem<Guitar> itemsRoot = new TreeItem<>();
    private TreeItem<OrderGuitar> ordersRoot = new TreeItem<>();

    private ClientsModel clientsModel = new ClientsModel();
    private OrdersModel ordersModel = new OrdersModel();
    private AddressModel addressModel = new AddressModel();
    private SellersModel sellersModel = new SellersModel();
    private StockModel stockModel = new StockModel();

    @FXML
    private void initialize() {
        clientsComboBox.setItems(clientsModel.getClients());
    }

   private void initTable(int orderID) throws SQLException {
        manCol.setCellValueFactory(new TreeItemPropertyValueFactory<Guitar, String>("manufacturer"));
        modelCol.setCellValueFactory(new TreeItemPropertyValueFactory<Guitar, String>("model"));
        priceCol.setCellValueFactory(new TreeItemPropertyValueFactory<Guitar, Double>("guitarPrice"));

        ObservableList<Guitar> stock = FXCollections.observableArrayList();
        stock.addAll(stockModel.getStockByOrderID(orderID));

        for(int i = 0; i < stock.size(); i++)
            itemsRoot.getChildren().add(new TreeItem<Guitar>(stock.get(i)));

        itemsTable.getColumns().setAll(manCol, modelCol, priceCol);
        itemsTable.setRoot(itemsRoot);
        itemsTable.setShowRoot(false);

        quantityCol.setCellValueFactory(new TreeItemPropertyValueFactory<OrderGuitar, Integer>("quantity"));
        ObservableList<OrderGuitar> orderGuitars = FXCollections.observableArrayList();
        orderGuitars.addAll(stockModel.getOrderGuitars(orderID));

        for(int i = 0; i < orderGuitars.size(); i++)
            ordersRoot.getChildren().add(new TreeItem<OrderGuitar>(orderGuitars.get(i)));

        quantityTable.getColumns().setAll(quantityCol);
        quantityTable.setRoot(ordersRoot);
        quantityTable.setShowRoot(false);

    }

    @FXML private void changeClient() {
        dateBox.setText("");
        addressBox.setText("");
        sellerBox.setText("");
        priceBox.setText("");

        try {
            System.out.println("Clearing tables and order combobox");
            orderComboBox.getItems().clear();
            itemsTable.getRoot().getChildren().clear();
            quantityTable.getRoot().getChildren().clear();
        } catch (NullPointerException e) {

        }

        int clientID = getSelectedClient().getClientID();
        System.out.println("Got selected client ID: " + clientID);
        try {
            orderComboBox.setItems(ordersModel.getOrdersWithClientID(clientID));
        } catch (NullPointerException e) {
            AlertFactory.makeNullTableError(clientsOrdersStackPane);
        }
        addressBox.setText(addressModel.getAddress(getSelectedClient().getAddressID()).toString());
    }

    @FXML private void changeOrder() {
        try {
            int orderID = getSelectedOrder().getOrderID();
            int sellerID = getSelectedOrder().getSellerID();
            dateBox.setText(getSelectedOrder().getDate().toString());
            initTable(orderID);
            System.out.println("Changed order, got IDs: order: " + orderID + ", seller: " + sellerID);
            //TODO FIX
            int totalPrice = 0;

            for(int i = 0; i < quantityTable.getCurrentItemsCount(); i++) {
                System.out.println("table count:" + i);
                totalPrice = totalPrice + itemsTable.getTreeItem(i).getValue().getGuitarPrice() * quantityTable.getTreeItem(i).getValue().getQuantity();
            }
            priceBox.setText(totalPrice + "$");
            sellerBox.setText(sellersModel.getSellerByID(sellerID).toString());

            System.out.println(quantityTable.getCurrentItemsCount() + " items");
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        }
    }

    public Client getSelectedClient() {
        return (Client) clientsComboBox.getValue();
    }

    public Order getSelectedOrder() {
        return (Order) orderComboBox.getValue();
    }
}
