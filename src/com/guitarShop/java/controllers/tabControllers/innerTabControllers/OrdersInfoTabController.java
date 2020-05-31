package com.guitarShop.java.controllers.tabControllers.innerTabControllers;

import com.guitarShop.java.helpers.AlertFactory;
import com.guitarShop.java.models.OrdersModel;
import com.guitarShop.java.models.objects.Address;
import com.guitarShop.java.models.objects.Order;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.time.LocalDate;

public class OrdersInfoTabController {

    @FXML StackPane ordersInfoStackPane;
    @FXML JFXTreeTableView<Order> ordersTable;
    @FXML TreeTableColumn<Order, String> clientCol;
    @FXML TreeTableColumn<Order, String> sellerCol;
    @FXML TreeTableColumn<Order, LocalDate> dateCol;
    private TreeItem<Order> root = new TreeItem<>();
    private OrdersModel ordersModel = new OrdersModel();

    @FXML
    private void initialize() {
        initTable();
    }

    public void initTable() {
        clientCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("client"));
        sellerCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("seller"));
        dateCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("date"));

        ObservableList<Order> orders = FXCollections.observableArrayList();
        orders.addAll(ordersModel.getOrders());
        for (int i = 0; i < orders.size(); i++)
            root.getChildren().add(new TreeItem<>(orders.get(i)));

        ordersTable.getColumns().setAll(clientCol, sellerCol, dateCol);
        ordersTable.setRoot(root);
        ordersTable.setShowRoot(false);
    }

    private GridPane getGridPaneWithText() {
        GridPane dialogGrid = new GridPane();

        Label sellerLabel = new Label("Seller");
        Label clientLabel = new Label("Client");
        Label dateLabel = new Label("Date");
        Label guitarsLabel = new Label("Guitars");
        dialogGrid.add(sellerLabel, 0,0);
        dialogGrid.add(clientLabel, 0,1);
        dialogGrid.add(dateLabel, 0,2);
        dialogGrid.add(guitarsLabel, 0,3);

        return dialogGrid;
    }

    private Order getSelectedItem() {
        return ordersTable.getSelectionModel().getSelectedItem().getValue();
    }

    private void refreshTable() {
        ordersTable.getRoot().getChildren().clear();
        initTable();
    }

    @FXML
    private void view() {
        try {
            GridPane dialogGrid = getGridPaneWithText();
            Text sellerText = new Text(getSelectedItem().getSeller());
            Text clientText = new Text(getSelectedItem().getClient());
            Text dateText = new Text(getSelectedItem().getDate().toString());

            JFXButton closeButton = new JFXButton("close");

            dialogGrid.add(sellerText, 1, 0);
            dialogGrid.add(clientText, 1, 1);
            dialogGrid.add(dateText, 1, 2);

            dialogGrid.setAlignment(Pos.CENTER);
            dialogGrid.setVgap(10);
            dialogGrid.setHgap(10);

            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            dialogLayout.setHeading(new Text("Order"));
            dialogLayout.setBody(dialogGrid);
            dialogLayout.setActions(closeButton);

            JFXDialog viewDialog = new JFXDialog(ordersInfoStackPane, dialogLayout, JFXDialog.DialogTransition.BOTTOM);
            closeButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    viewDialog.close();
                }
            });
            viewDialog.show();

        } catch (NullPointerException e) {
            AlertFactory.makeItemNotChoosenDialog(ordersInfoStackPane);
        }
    }

    @FXML
    private void update() {

    }

    @FXML
    private void add() {

    }

    @FXML
    private void delete() {

    }
}
