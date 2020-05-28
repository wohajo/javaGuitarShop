package com.guitarShop.java.controllers.tabControllers.innerTabControllers;

import com.guitarShop.java.controllers.tabControllers.StockTabController;
import com.guitarShop.java.helpers.AlertFactory;
import com.guitarShop.java.models.AddressModel;
import com.guitarShop.java.models.ManufacturerModel;
import com.guitarShop.java.models.objects.Address;
import com.guitarShop.java.models.objects.Manufacturer;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.SQLException;

public class ManufacturersTabController {
    @FXML private StackPane manufacturersStackPane;
    @FXML private JFXTreeTableView<Manufacturer> manufacturerTable;
    @FXML private TreeTableColumn<Manufacturer, String> nameCol;
    private AddressModel addressModel = new AddressModel();
    private ManufacturerModel manufacturerModel = new ManufacturerModel();
    private TreeItem<Manufacturer> root = new TreeItem<>();
    private AlertFactory alertFactory = new AlertFactory();

    @FXML void initialize() {
        initTable();
    }

    @FXML private void initTable() {
        nameCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("manufacturerName"));
        ObservableList<Manufacturer> manufacturerObservableList = FXCollections.observableArrayList();

        manufacturerObservableList.addAll(manufacturerModel.getManufacturers());
        for (int i = 0; i < manufacturerObservableList.size(); i++)
            root.getChildren().add(new TreeItem<>(manufacturerObservableList.get(i)));

        manufacturerTable.getColumns().setAll(nameCol);
        manufacturerTable.setRoot(root);
        manufacturerTable.setShowRoot(false);
    }

    private void refreshTable() {
        manufacturerTable.getRoot().getChildren().clear();
        initTable();
    }

    private void refreshPartsTables() throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/guitarShop/resources/tabs/innerTabs/PartsTab.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        PartsTabController controller = fxmlLoader.getController();
        controller.refreshTypeTable();
        controller.refreshPickupsTable();
        controller.refreshBridgesTable();
    }

    private Manufacturer getSelectedItem() {
        return manufacturerTable.getSelectionModel().getSelectedItem().getValue();
    }

    private GridPane getDialogGridWithText() {
        GridPane dialogGrid = new GridPane();
        Label nameLabel = new Label("Name");
        Label addressLabel = new Label("Address");
        dialogGrid.add(nameLabel, 0,0);
        dialogGrid.add(addressLabel, 0,1);
        return dialogGrid;
    }

    @FXML private void view() {
        try {
            GridPane dialogGrid = getDialogGridWithText();

            Text nameText = new Text(getSelectedItem().getManufacturerName());
            Text addressText = new Text(addressModel.getAddress(getSelectedItem().getAddressID()).toString());
            JFXButton closeButton = new JFXButton("Close");

            dialogGrid.add(nameText, 1, 0);
            dialogGrid.add(addressText, 1, 1);

            dialogGrid.setAlignment(Pos.CENTER);
            dialogGrid.setVgap(10);
            dialogGrid.setHgap(10);
            dialogGrid.setPadding(new Insets(10));

            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            dialogLayout.setHeading(new Text("Person"));
            dialogLayout.setBody(dialogGrid);

            dialogLayout.setActions(closeButton);

            JFXDialog viewDialog = new JFXDialog(manufacturersStackPane, dialogLayout, JFXDialog.DialogTransition.BOTTOM);

            closeButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    viewDialog.close();
                }
            });
            viewDialog.show();
        } catch (NullPointerException e) {
            alertFactory.makeAlertDialog(manufacturersStackPane, "Error", "Choose a manufacturer to display informations.", "Close");
        }
    }

    @FXML private void update() {
        try {
            GridPane dialogGrid = getDialogGridWithText();

            TextField nameText = new TextField(getSelectedItem().getManufacturerName());
            JFXListView<Address> addressJFXListView = new JFXListView<>();
            addressJFXListView.setMaxWidth(200);
            addressJFXListView.setMaxHeight(100);
            ObservableList<Address> addressObservableList = addressModel.getAddresses();
            addressJFXListView.setItems(addressObservableList);
            for (Address a : addressObservableList)
                if(getSelectedItem().getAddressID() == a.getAddressID())
                    addressJFXListView.getSelectionModel().select(a);

            JFXButton acceptButton = new JFXButton("Accept");
            JFXButton closeButton = new JFXButton("Close");

            dialogGrid.add(nameText, 1, 0);
            dialogGrid.add(addressJFXListView, 1, 1);

            dialogGrid.setAlignment(Pos.CENTER);
            dialogGrid.setVgap(10);
            dialogGrid.setHgap(10);
            dialogGrid.setPadding(new Insets(10));

            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            dialogLayout.setHeading(new Text("Person"));
            dialogLayout.setBody(dialogGrid);

            dialogLayout.setActions(acceptButton, closeButton);

            JFXDialog viewDialog = new JFXDialog(manufacturersStackPane, dialogLayout, JFXDialog.DialogTransition.BOTTOM);

            acceptButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    manufacturerModel.updateManufacturer(manufacturersStackPane, getSelectedItem().getManufacturerID(),
                            nameText.getText(), addressJFXListView.getSelectionModel().getSelectedItem().getAddressID());
                    refreshTable();
                    viewDialog.close();
                }
            });

            closeButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try {
                        refreshPartsTables();
                    } catch (IOException | SQLException e) {
                        AlertFactory.makeRefreshTableError(manufacturersStackPane);
                    }
                    viewDialog.close();
                }
            });
            viewDialog.show();
        } catch (NullPointerException e) {
            alertFactory.makeAlertDialog(manufacturersStackPane, "Error", "Choose a manufacturer to display informations.", "Close");
        }
    }

    @FXML private void add() {
        GridPane dialogGrid = getDialogGridWithText();

        TextField nameText = new TextField();
        JFXListView<Address> addressJFXListView = new JFXListView<>();
        addressJFXListView.setMaxWidth(200);
        addressJFXListView.setMaxHeight(100);
        ObservableList<Address> addressObservableList = addressModel.getAddresses();
        addressJFXListView.setItems(addressObservableList);

        JFXButton acceptButton = new JFXButton("Accept");
        JFXButton closeButton = new JFXButton("Close");

        dialogGrid.add(nameText, 1, 0);
        dialogGrid.add(addressJFXListView, 1, 1);

        dialogGrid.setAlignment(Pos.CENTER);
        dialogGrid.setVgap(10);
        dialogGrid.setHgap(10);
        dialogGrid.setPadding(new Insets(10));

        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        dialogLayout.setHeading(new Text("Person"));
        dialogLayout.setBody(dialogGrid);

        dialogLayout.setActions(acceptButton, closeButton);

        JFXDialog viewDialog = new JFXDialog(manufacturersStackPane, dialogLayout, JFXDialog.DialogTransition.BOTTOM);

        acceptButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                manufacturerModel.addManufacturer(manufacturersStackPane,
                        nameText.getText(),
                        addressJFXListView.getSelectionModel().getSelectedItem().getAddressID());
                refreshTable();
                viewDialog.close();
            }
        });

        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                viewDialog.close();
            }
        });
        viewDialog.show();
    }

    @FXML private void delete() {
        manufacturerModel.deleteManufacturer(manufacturersStackPane, getSelectedItem().getManufacturerID());
        refreshTable();
    }
}
