package com.guitarShop.java.controllers.tabControllers.innerTabControllers;
import com.guitarShop.java.models.*;
import com.guitarShop.java.models.objects.Client;
import com.guitarShop.java.models.objects.Guitar;
import com.guitarShop.java.models.objects.Order;
import com.guitarShop.java.models.objects.OrderGuitar;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.PropertyValueFactory;
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
    @FXML private TableView<Guitar> itemsTable;
    @FXML private TableView<OrderGuitar> quantityTable;
    @FXML private TableColumn<Guitar, String> manCol;
    @FXML private TableColumn<Guitar, String> modelCol;
    @FXML private TableColumn<Guitar, Double> priceCol;
    @FXML private TableColumn<OrderGuitar, Integer> quantityCol;
    private TreeItem<Guitar> itemsRoot = new TreeItem<>();
    private TreeItem<OrderGuitar> ordersRoot = new TreeItem<>();
    private int itemCount = 0;

    private ClientsModel clientsModel = new ClientsModel();
    private OrdersModel ordersModel = new OrdersModel();
    private AddressModel addressModel = new AddressModel();
    private SellersModel sellersModel = new SellersModel();
    private StockModel stockModel = new StockModel();

    @FXML
    private void initialize() {
        clientsComboBox.setItems(clientsModel.getClients());
    }

    @FXML private void refresh() {
        clientsComboBox.setValue(null);
        clientsComboBox.setItems(clientsModel.getClients());
        clearFields();
    }

    private void clearFields() {
        dateBox.setText("");
        addressBox.setText("");
        sellerBox.setText("");
        priceBox.setText("");

        if (itemsTable.getItems().size() != 0) {
            orderComboBox.setValue(null);
            itemsTable.getItems().clear();
            quantityTable.getItems().clear();
        }
    }

    private void initTable(int orderID) {
        manCol.setCellValueFactory(new PropertyValueFactory<Guitar, String>("manufacturer"));
        modelCol.setCellValueFactory(new PropertyValueFactory<Guitar, String>("model"));
        priceCol.setCellValueFactory(new PropertyValueFactory<Guitar, Double>("guitarPrice"));

        ObservableList<Guitar> stock = FXCollections.observableArrayList();
        stock.addAll(stockModel.getStockByOrderID(orderID));
        itemsTable.setItems(stock);

        quantityCol.setCellValueFactory(new PropertyValueFactory<OrderGuitar, Integer>("quantity"));
        ObservableList<OrderGuitar> orderGuitars = FXCollections.observableArrayList();
        orderGuitars.addAll(ordersModel.getOrderGuitarsByOrderID(clientsOrdersStackPane, orderID));
        quantityTable.setItems(orderGuitars);
   }

    @FXML private void changeClient() {
        if (getSelectedClient() != null) {
            clearFields();
            int clientID = getSelectedClient().getClientID();
            ObservableList<Order> items = ordersModel.getOrdersWithClientID(clientID);
            orderComboBox.setItems(items);
            addressBox.setText(addressModel.getAddress(getSelectedClient().getAddressID()).toString());
        }
    }

    @FXML private void changeOrder() throws SQLException {
        if (getSelectedOrder() != null) {
            int orderID = getSelectedOrder().getOrderID();
            int sellerID = getSelectedOrder().getSellerID();
            dateBox.setText(getSelectedOrder().getDate().toString());
            sellerBox.setText(sellersModel.getSellerByID(sellerID).toString());
            initTable(orderID);
            int totalPrice = 0;

           for(int i = 0; i < itemsTable.getItems().size(); i++)
                totalPrice += itemsTable.getItems().get(i).getGuitarPrice() * quantityTable.getItems().get(i).getQuantity();

            priceBox.setText(totalPrice + "$");
        }
    }

    public Client getSelectedClient() {
        return (Client) clientsComboBox.getValue();
    }

    public Order getSelectedOrder() {
        return (Order) orderComboBox.getValue();
    }
}
