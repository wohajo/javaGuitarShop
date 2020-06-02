package com.guitarShop.java.controllers.tabControllers.innerTabControllers;

import com.guitarShop.java.helpers.AlertFactory;
import com.guitarShop.java.models.ClientsModel;
import com.guitarShop.java.models.OrdersModel;
import com.guitarShop.java.models.SellersModel;
import com.guitarShop.java.models.StockModel;
import com.guitarShop.java.models.objects.*;
import com.jfoenix.controls.*;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrdersInfoTabController {

    @FXML StackPane ordersInfoStackPane;
    @FXML JFXTreeTableView<Order> ordersTable;
    @FXML TreeTableColumn<Order, String> clientCol;
    @FXML TreeTableColumn<Order, String> sellerCol;
    @FXML TreeTableColumn<Order, LocalDate> dateCol;
    private TreeItem<Order> root = new TreeItem<>();
    private OrdersModel ordersModel = new OrdersModel();
    private StockModel stockModel = new StockModel();
    private SellersModel sellersModel = new SellersModel();
    private ClientsModel clientsModel = new ClientsModel();

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
            ListView<Guitar> guitarListView = new ListView<>();
            StackPane stackPane = new StackPane();
            GridPane dialogGrid = getGridPaneWithText();
            stackPane.getChildren().add(dialogGrid);
            JFXComboBox<Seller> sellerBox = new JFXComboBox<>();
            JFXComboBox<Client> clientBox = new JFXComboBox<>();
            DatePicker dateText = new DatePicker(getSelectedItem().getDate());
            Label countLabel = new Label("Quantity");
            TextField countText = new TextField("0");
            countText.setEditable(false);

            ObservableList<Seller> sellers = sellersModel.getSellers();
            sellerBox.setItems(sellers);
            for (Seller s : sellers) {
                if (s.getSellerID() == getSelectedItem().getSellerID())
                    sellerBox.getSelectionModel().select(s);
            }

            ObservableList<Client> clients = clientsModel.getClients();
            clientBox.setItems(clients);
            for (Client c : clients) {
                if (c.getClientID() == getSelectedItem().getClientID())
                    clientBox.getSelectionModel().select(c);
            }

            guitarListView.setItems(stockModel.getStockByOrderID(getSelectedItem().getOrderID()));
            guitarListView.setMaxHeight(100);
            guitarListView.setMaxWidth(240);


            JFXButton closeButton = new JFXButton("close");
            JFXButton acceptButton = new JFXButton("accept");

            dialogGrid.add(sellerBox, 1, 0);
            dialogGrid.add(clientBox, 1, 1);
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
            dialogLayout.setActions(acceptButton, closeButton);

            JFXDialog viewDialog = new JFXDialog(ordersInfoStackPane, dialogLayout, JFXDialog.DialogTransition.BOTTOM);

            guitarListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Guitar>() {
                @Override
                public void changed(ObservableValue<? extends Guitar> observable, Guitar oldValue, Guitar newValue) {
                    countText.setText(ordersModel.getQuantityByIDs(ordersInfoStackPane, getSelectedItem().getOrderID(), newValue.getGuitarID()));
                }
            });

            acceptButton.setOnAction(e -> {
                ordersModel.updateOrder(ordersInfoStackPane, getSelectedItem().getOrderID(), sellerBox.getSelectionModel().getSelectedItem().getSellerID(), clientBox.getSelectionModel().getSelectedItem().getClientID(), dateText.getValue());
                viewDialog.close();
                refreshTable();
            });
            closeButton.setOnAction(actionEvent -> viewDialog.close());

            viewDialog.show();

        } catch (NullPointerException e) {
            AlertFactory.makeItemNotChoosenDialog(ordersInfoStackPane);
        }
    }

    @FXML
    private void add() {
        try {
            List<Integer> guitarsToAdd = new ArrayList<>();
            List<OrderGuitar> quantityToAdd = new ArrayList<>();
            JFXComboBox<Guitar> guitarListView = new JFXComboBox<>();
            JFXComboBox<Guitar> addedGuitarsListView = new JFXComboBox<>();
            StackPane stackPane = new StackPane();
            GridPane dialogGrid = getGridPaneWithText();
            stackPane.getChildren().add(dialogGrid);
            JFXComboBox<Seller> sellerBox = new JFXComboBox<>();
            JFXComboBox<Client> clientBox = new JFXComboBox<>();
            DatePicker dateText = new DatePicker();
            Label countLabel = new Label("Quantity");
            TextField countText = new TextField("0");
            Label addedLabel = new Label("Added guitars");
            Label countLabel2 = new Label("Quantity");
            TextField addedCountText = new TextField("0");
            addedCountText.setEditable(false);

            ObservableList<Seller> sellers = sellersModel.getSellers();
            sellerBox.setItems(sellers);

            ObservableList<Client> clients = clientsModel.getClients();
            clientBox.setItems(clients);


            guitarListView.setItems(stockModel.getStock());
            guitarListView.setMaxHeight(100);
            guitarListView.setMaxWidth(240);
            addedGuitarsListView.setMaxHeight(100);
            addedGuitarsListView.setMaxWidth(240);


            JFXButton closeButton = new JFXButton("close");
            JFXButton acceptButton = new JFXButton("accept");
            JFXButton addGuitarButton = new JFXButton("add guitar(s)");
            JFXButton deleteButton = new JFXButton("delete guitar");

            dialogGrid.add(sellerBox, 1, 0);
            dialogGrid.add(clientBox, 1, 1);
            dialogGrid.add(dateText, 1, 2);
            dialogGrid.add(guitarListView, 1, 3);
            dialogGrid.add(countLabel, 0, 4);
            dialogGrid.add(countText, 1, 4);
            dialogGrid.add(addGuitarButton, 1, 5);
            dialogGrid.add(addedLabel, 0, 6);
            dialogGrid.add(addedGuitarsListView, 1, 6);
            dialogGrid.add(countLabel2, 0, 7);
            dialogGrid.add(addedCountText, 1, 7);
            dialogGrid.add(deleteButton, 1, 8);

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
                    countText.setText(String.valueOf(guitarListView.getSelectionModel().getSelectedItem().getNumberOfGuitars()));
                }
            });

            addedGuitarsListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Guitar>() {
                @Override
                public void changed(ObservableValue<? extends Guitar> observable, Guitar oldValue, Guitar newValue) {
                    if(addedGuitarsListView.getSelectionModel().getSelectedItem() != null) {
                        for (int i = 0; i < quantityToAdd.size(); i++) {
                            if(addedGuitarsListView.getSelectionModel().getSelectedItem().getGuitarID() == quantityToAdd.get(i).getGuitarID())
                                addedCountText.setText(String.valueOf(quantityToAdd.get(i).getQuantity()));
                        }
                }
            }});

            addGuitarButton.setOnAction(e -> {
                int selectedQuantity = Integer.valueOf(countText.getText());
                Guitar selectedGuitar = guitarListView.getSelectionModel().getSelectedItem();
                int selectedGuitarQuantity = guitarListView.getSelectionModel().getSelectedItem().getNumberOfGuitars();
                int guitarsLeftInStock = selectedGuitarQuantity - selectedQuantity;

                if (selectedQuantity > selectedGuitar.getNumberOfGuitars()) {

                    AlertFactory.makeNotEnoughItemsInStock(ordersInfoStackPane);
                } else if (selectedQuantity == 0) {
                    AlertFactory.makeItemNotChoosenDialog(ordersInfoStackPane);
                } else if (selectedGuitarQuantity == 0) {
                    AlertFactory.makeNotEnoughItemsInStock(ordersInfoStackPane);
                } else {
                    if (addedGuitarsListView.getItems().size() > 0) {

                        if (!addedGuitarsListView.getItems().contains(selectedGuitar)) {
                            addedGuitarsListView.getItems().add(selectedGuitar);
                        } else {
                            AlertFactory.makeAlertDialog(ordersInfoStackPane, "Error", "Item already added!", "Close");
                            return;
                        }
                    } else {
                        addedGuitarsListView.getItems().add(selectedGuitar);
                    }

                    int index = guitarListView.getSelectionModel().getSelectedIndex();
                    Guitar guitarToModify = guitarListView.getItems().get(index);
                    guitarToModify.setNumberOfGuitars(guitarsLeftInStock);
                    guitarListView.getItems().set(index, guitarToModify);

                    quantityToAdd.add(new OrderGuitar(0, guitarToModify.getGuitarID(), selectedQuantity));

                    countText.setText(String.valueOf(guitarListView.getSelectionModel().getSelectedItem().getNumberOfGuitars()));
                }
            });

            deleteButton.setOnAction(e -> {
                if (getSelectedGuitarFromView(addedGuitarsListView) == null) {
                    AlertFactory.makeItemNotChoosenDialog(ordersInfoStackPane);
                } else {
                    int index = addedGuitarsListView.getSelectionModel().getSelectedIndex();
                    int number = addedGuitarsListView.getItems().get(index).getNumberOfGuitars();

                    for (int i = 0; i < guitarListView.getItems().size(); i++) {
                        if (guitarListView.getItems().get(i).getGuitarID() == addedGuitarsListView.getItems().get(index).getGuitarID()) {
                            guitarListView.getItems().get(i).setNumberOfGuitars(guitarListView.getItems().get(i).getNumberOfGuitars() + number);
                        }
                    }
                    addedGuitarsListView.getItems().remove(index);
                }
            });

            acceptButton.setOnAction(e -> {
                if(dateText.getValue() == null || clientBox.getSelectionModel().getSelectedItem() == null || sellerBox.getValue() == null || guitarsToAdd.size() < 1) {
                    AlertFactory.makeItemNotChoosenDialog(ordersInfoStackPane);
                } else {
                    ordersModel.addOrder(ordersInfoStackPane, clientBox.getSelectionModel().getSelectedItem().getClientID(), sellerBox.getSelectionModel().getSelectedItem().getSellerID(), dateText.getValue(), quantityToAdd);
                    viewDialog.close();
                    refreshTable();
                }
            });

            closeButton.setOnAction(actionEvent -> viewDialog.close());

            viewDialog.show();

        } catch (NullPointerException e) {
            AlertFactory.makeItemNotChoosenDialog(ordersInfoStackPane);
        }
    }

    @FXML
    private void delete() {

    }

    private Guitar getSelectedGuitarFromView(JFXComboBox<Guitar> listView) {
        return (Guitar) listView.getSelectionModel().getSelectedItem();
    }
}
