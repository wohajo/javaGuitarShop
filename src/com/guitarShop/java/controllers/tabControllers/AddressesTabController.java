package com.guitarShop.java.controllers.tabControllers;

import com.guitarShop.java.helpers.AlertFactory;
import com.guitarShop.java.models.AddressModel;
import com.guitarShop.java.models.objects.Address;
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
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;


public class AddressesTabController {

    @FXML private StackPane addressesStackPane;
    @FXML private JFXTreeTableView<Address> addressTable;
    @FXML private TreeTableColumn<Address, String> cityCol;
    @FXML private TreeTableColumn<Address, String> postcodeCol;
    @FXML private TreeTableColumn<Address, String> streetCol;
    @FXML private TreeTableColumn<Address, Integer> buildingCol;
    @FXML private TreeTableColumn<Address, String> flatCol;
    private AddressModel addressModel = new AddressModel();
    private TreeItem<Address> root = new TreeItem<>();
    private AlertFactory alertFactory = new AlertFactory();

    @FXML void initialize() {
        initTable();
    }

    @FXML private void initTable() {
        cityCol.setCellValueFactory(new TreeItemPropertyValueFactory<Address, String>("city"));
        postcodeCol.setCellValueFactory(new TreeItemPropertyValueFactory<Address, String>("postcode"));
        streetCol.setCellValueFactory(new TreeItemPropertyValueFactory<Address, String>("street"));
        buildingCol.setCellValueFactory(new TreeItemPropertyValueFactory<Address, Integer>("buildingNumber"));
        flatCol.setCellValueFactory(new TreeItemPropertyValueFactory<Address, String>("flatNumber"));

        ObservableList<Address> addresses = FXCollections.observableArrayList();
        addresses.addAll(addressModel.getAddresses());
        for(int i = 0; i < addresses.size(); i ++)
            root.getChildren().add(new TreeItem<>(addresses.get(i)));

        addressTable.getColumns().setAll(cityCol, postcodeCol, streetCol, buildingCol, flatCol);
        addressTable.setRoot(root);
        addressTable.setShowRoot(false);
    }

    private GridPane getGridPaneWithText() {
        GridPane dialogGrid = new GridPane();

        Label cityLabel = new Label("City");
        Label postcodeLabel = new Label("Postcode");
        Label streetLabel = new Label("Street");
        Label buildingLabel = new Label("Building");
        Label flatLabel = new Label("Flat");
        dialogGrid.add(cityLabel, 0,0);
        dialogGrid.add(postcodeLabel, 0,1);
        dialogGrid.add(streetLabel, 0,2);
        dialogGrid.add(buildingLabel, 0,3);
        dialogGrid.add(flatLabel, 0,4);

        return dialogGrid;
    }

    private Address getSelectedItem() {
        return addressTable.getSelectionModel().getSelectedItem().getValue();
    }

    private void refreshTable() {
        addressTable.getRoot().getChildren().clear();
        initTable();
    }

    @FXML private void view() {
        try {
            GridPane dialogGrid = getGridPaneWithText();

            Text cityText = new Text(getSelectedItem().getCity());
            Text postcodeText = new Text(getSelectedItem().getPostcode());
            Text streetText = new Text(getSelectedItem().getStreet());
            Text buildingText = new Text(String.valueOf(getSelectedItem().getBuildingNumber()));
            Text flatText = new Text(String.valueOf(getSelectedItem().getFlatNumber()));
            JFXButton closeButton = new JFXButton("close");

            dialogGrid.add(cityText, 1, 0);
            dialogGrid.add(postcodeText, 1, 1);
            dialogGrid.add(streetText, 1, 2);
            dialogGrid.add(buildingText, 1, 3);
            dialogGrid.add(flatText, 1, 4);

            dialogGrid.setAlignment(Pos.CENTER);
            dialogGrid.setVgap(10);
            dialogGrid.setHgap(10);

            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            dialogLayout.setHeading(new Text("Address"));
            dialogLayout.setBody(dialogGrid);
            dialogLayout.setActions(closeButton);

            JFXDialog viewDialog = new JFXDialog(addressesStackPane, dialogLayout, JFXDialog.DialogTransition.BOTTOM);
            closeButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    viewDialog.close();
                }
            });
            viewDialog.show();

        } catch (NullPointerException e) {
            alertFactory.makeAlertDialog(addressesStackPane, "Error", "Choose an address to display informations.", "Close");
        }
    }

    @FXML private void update() {
        try {
            GridPane dialogGrid = getGridPaneWithText();

            TextField cityText = new TextField(getSelectedItem().getCity());
            TextField postcodeText = new TextField(getSelectedItem().getPostcode());
            TextField streetText = new TextField(getSelectedItem().getStreet());
            TextField buildingText = new TextField(String.valueOf(getSelectedItem().getBuildingNumber()));
            TextField flatText = new TextField(String.valueOf(getSelectedItem().getFlatNumber()));
            JFXButton closeButton = new JFXButton("Close");
            JFXButton acceptButton = new JFXButton("Accept");

            dialogGrid.add(cityText, 1, 0);
            dialogGrid.add(postcodeText, 1, 1);
            dialogGrid.add(streetText, 1, 2);
            dialogGrid.add(buildingText, 1, 3);
            dialogGrid.add(flatText, 1, 4);

            dialogGrid.setAlignment(Pos.CENTER);
            dialogGrid.setVgap(10);
            dialogGrid.setHgap(10);

            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            dialogLayout.setHeading(new Text("Address"));
            dialogLayout.setBody(dialogGrid);
            dialogLayout.setActions(acceptButton, closeButton);

            JFXDialog viewDialog = new JFXDialog(addressesStackPane, dialogLayout, JFXDialog.DialogTransition.BOTTOM);

            acceptButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    addressModel.updateAddress(addressesStackPane, cityText.getText(), postcodeText.getText(),
                            streetText.getText(), buildingText.getText(), flatText.getText(), getSelectedItem().getAddressID());
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

        } catch (NullPointerException e) {
            alertFactory.makeAlertDialog(addressesStackPane, "Error", "Choose an address to update informations.", "Close");
        }
    }

    @FXML private void add() {
            GridPane dialogGrid = getGridPaneWithText();

            TextField cityText = new TextField();
            TextField postcodeText = new TextField();
            TextField streetText = new TextField();
            TextField buildingText = new TextField();
            TextField flatText = new TextField();
            JFXButton closeButton = new JFXButton("Close");
            JFXButton acceptButton = new JFXButton("Accept");

            dialogGrid.add(cityText, 1, 0);
            dialogGrid.add(postcodeText, 1, 1);
            dialogGrid.add(streetText, 1, 2);
            dialogGrid.add(buildingText, 1, 3);
            dialogGrid.add(flatText, 1, 4);

            dialogGrid.setAlignment(Pos.CENTER);
            dialogGrid.setVgap(10);
            dialogGrid.setHgap(10);

            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            dialogLayout.setHeading(new Text("Address"));
            dialogLayout.setBody(dialogGrid);
            dialogLayout.setActions(acceptButton, closeButton);

            JFXDialog viewDialog = new JFXDialog(addressesStackPane, dialogLayout, JFXDialog.DialogTransition.BOTTOM);

            acceptButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    addressModel.addAddress(addressesStackPane, cityText.getText(), postcodeText.getText(),
                            streetText.getText(), buildingText.getText(), flatText.getText());
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
        try {
            addressModel.deleteAddress(addressesStackPane, getSelectedItem().getAddressID());
            refreshTable();
        } catch (NullPointerException e) {
            AlertFactory.makeAlertDialog(addressesStackPane, "Error", "No address selected.", "Close");
        }
    }
}
