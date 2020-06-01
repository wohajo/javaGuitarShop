package com.guitarShop.java.controllers.tabControllers.innerTabControllers;

import com.guitarShop.java.helpers.AlertFactory;
import com.guitarShop.java.models.OrdersModel;
import com.guitarShop.java.models.StockModel;
import com.guitarShop.java.models.objects.Guitar;
import com.guitarShop.java.models.objects.Order;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
    private StockModel stockModel = new StockModel();
    ListView<Guitar> guitarListView = new ListView<>();
    ListView<Guitar> newGuitarListView = new ListView<>();

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
            Label countLabel = new Label("Quantity");
            Text countText = new Text("0");
            ListView<Guitar> guitarListView = new ListView<>();

            guitarListView.setItems(stockModel.getStockByOrderID(getSelectedItem().getOrderID()));

            guitarListView.setMaxHeight(200);
            guitarListView.setMaxWidth(240);

            guitarListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Guitar>() {

                @Override
                public void changed(ObservableValue<? extends Guitar> observable, Guitar oldValue, Guitar newValue) {
                    countText.setText(ordersModel.getQuantityByIDs(ordersInfoStackPane, getSelectedItem().getOrderID(), newValue.getGuitarID()));
                }
            });

            JFXButton closeButton = new JFXButton("close");

            dialogGrid.add(sellerText, 1, 0);
            dialogGrid.add(clientText, 1, 1);
            dialogGrid.add(dateText, 1, 2);
            dialogGrid.add(guitarListView, 1, 3);
            dialogGrid.add(countLabel, 0, 4);
            dialogGrid.add(countText, 1, 4);

            dialogGrid.setAlignment(Pos.CENTER);
            dialogGrid.setVgap(10);
            dialogGrid.setHgap(10);

            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            dialogLayout.setHeading(new Text("Order"));
            dialogLayout.setBody(dialogGrid);
            dialogLayout.setActions(closeButton);

            JFXDialog viewDialog = new JFXDialog(ordersInfoStackPane, dialogLayout, JFXDialog.DialogTransition.BOTTOM);
            closeButton.setOnAction(actionEvent -> viewDialog.close());
            viewDialog.show();

        } catch (NullPointerException e) {
            AlertFactory.makeItemNotChoosenDialog(ordersInfoStackPane);
        }
    }

    @FXML
    private void update() {
        try {
            HBox hbox = new HBox();
            StackPane stackPane = new StackPane();
            GridPane dialogGrid = getGridPaneWithText();
            stackPane.getChildren().add(dialogGrid);
            TextField sellerText = new TextField(getSelectedItem().getSeller());
            TextField clientText = new TextField(getSelectedItem().getClient());
            DatePicker dateText = new DatePicker(getSelectedItem().getDate());
            Label countLabel = new Label("Quantity");
            TextField countText = new TextField("0");
            Label addLabel = new Label("Add");
            addLabel.setStyle("-fx-text-fill: black");
            TextField addCountText = new TextField("0");

            guitarListView.setItems(stockModel.getStockByOrderID(getSelectedItem().getOrderID()));
            newGuitarListView.setItems(stockModel.getStock());

            guitarListView.setMaxHeight(100);
            guitarListView.setMaxWidth(240);
            newGuitarListView.setMaxHeight(150);
            newGuitarListView.setMaxWidth(240);
            addCountText.setMaxWidth(60);

            JFXButton closeButton = new JFXButton("close");
            JFXButton acceptButton = new JFXButton("accept");
            JFXButton confirmQuantityButton = new JFXButton("Confirm quantity");
            JFXButton deleteButton = new JFXButton("delete guitar(s)");
            JFXButton confirmNewGuitarButton = new JFXButton("Confirm quantity");
            confirmQuantityButton.setButtonType(JFXButton.ButtonType.RAISED);

            hbox.getChildren().addAll(deleteButton, confirmQuantityButton);

            dialogGrid.add(sellerText, 1, 0);
            dialogGrid.add(clientText, 1, 1);
            dialogGrid.add(dateText, 1, 2);
            dialogGrid.add(guitarListView, 1, 3);
            dialogGrid.add(countLabel, 0, 4);
            dialogGrid.add(countText, 1, 4);
            dialogGrid.add(hbox, 1, 5);
            dialogGrid.add(addLabel, 1, 6);
            dialogGrid.add(addCountText, 0, 7);
            dialogGrid.add(newGuitarListView, 1, 7);
            dialogGrid.add(confirmNewGuitarButton, 1, 8);

            dialogGrid.setAlignment(Pos.CENTER);
            dialogGrid.setVgap(10);
            dialogGrid.setHgap(10);

            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            dialogLayout.setHeading(new Text("Order"));
            dialogLayout.setBody(dialogGrid);
            dialogLayout.setActions(acceptButton, closeButton);

            JFXDialog viewDialog = new JFXDialog(ordersInfoStackPane, dialogLayout, JFXDialog.DialogTransition.BOTTOM);

            guitarListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Guitar>() {
                @Override
                public void changed(ObservableValue<? extends Guitar> observable, Guitar oldValue, Guitar newValue) {
                    countText.setText(ordersModel.getQuantityByIDs(ordersInfoStackPane, getSelectedItem().getOrderID(), newValue.getGuitarID()));
                }
            });

            newGuitarListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Guitar>() {
                @Override
                public void changed(ObservableValue<? extends Guitar> observable, Guitar oldValue, Guitar newValue) {
                    addCountText.setText(String.valueOf(Integer.valueOf(getSelectedGuitarFromView(newGuitarListView).getNumberOfGuitars())));
                }
            });

            confirmQuantityButton.setOnAction(e -> {
                // TODO works with guitars id's
            });

            confirmNewGuitarButton.setOnAction(e -> {
                if (getSelectedGuitarFromView(newGuitarListView) == null)
                    AlertFactory.makeItemNotChoosenDialog(ordersInfoStackPane);
                else if(getSelectedGuitarFromView(newGuitarListView).getNumberOfGuitars() < Integer.parseInt(addCountText.getText()))
                    AlertFactory.makeNotEnoughItemsInStock(ordersInfoStackPane);
                else {
                    
                }
            });

            acceptButton.setOnAction(e -> {

            });

            closeButton.setOnAction(actionEvent -> viewDialog.close());
            viewDialog.show();

        } catch (NullPointerException e) {
            AlertFactory.makeItemNotChoosenDialog(ordersInfoStackPane);
        }
    }

    @FXML
    private void add() {

    }

    @FXML
    private void delete() {

    }

    private Guitar getSelectedGuitarFromView(ListView listView) {
        return (Guitar) listView.getSelectionModel().getSelectedItem();
    }
}
