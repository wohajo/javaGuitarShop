package com.guitarShop.java.controllers.tabControllers;

import com.guitarShop.java.helpers.AlertFactory;
import com.guitarShop.java.models.AddressModel;
import com.guitarShop.java.models.objects.Address;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class AddressesTabController {

    @FXML private StackPane addressesStackPane;
    @FXML private TableView<Address> addressTable;
    @FXML private TableColumn<Address, String> cityCol;
    @FXML private TableColumn<Address, String> postcodeCol;
    @FXML private TableColumn<Address, String> streetCol;
    @FXML private TextField citySearchText;
    @FXML private TextField postcodeSearchText;
    @FXML private TextField streetSearchText;

    private AddressModel addressModel = new AddressModel();
    private TreeItem<Address> root = new TreeItem<>();
    private AlertFactory alertFactory = new AlertFactory();

    @FXML void initialize() {
        initTable();
    }

    @FXML private void initTable() {
        cityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
        postcodeCol.setCellValueFactory(new PropertyValueFactory<>("postcode"));
        streetCol.setCellValueFactory(new PropertyValueFactory<>("street"));

        ObservableList<Address> addresses = FXCollections.observableArrayList();
        addresses.setAll(addressModel.getAddresses());
        FilteredList<Address> filteredList = new FilteredList<>(addresses, p -> true);
        initSearchFields(filteredList);
        SortedList<Address> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(addressTable.comparatorProperty());
        addressTable.setItems(sortedList);
        addressTable.getColumns().setAll(cityCol, postcodeCol, streetCol);
    }

    private void initSearchFields(FilteredList<Address> filteredList) {

        AlertFactory.preventInjection(citySearchText);
        AlertFactory.preventInjection(streetSearchText);
        AlertFactory.preventAp(postcodeSearchText);

        citySearchText.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(Address -> {
                if(newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowercaseFilter = newValue.toLowerCase();
                if(Address.getCity().toLowerCase().contains(lowercaseFilter))
                    return true;

                return false;
            });
        });
        streetSearchText.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(Address -> {
                if(newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowercaseFilter = newValue.toLowerCase();
                if(Address.getStreet().toLowerCase().contains(lowercaseFilter))
                    return true;

                return false;
            });
        });
        postcodeSearchText.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(Address -> {
                if(newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowercaseFilter = newValue.toLowerCase();
                if(Address.getPostcode().toLowerCase().contains(lowercaseFilter))
                    return true;

                return false;
            });
        });
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
        return addressTable.getSelectionModel().getSelectedItem();
    }

    private void refreshTable() {
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

            AlertFactory.preventInjection(cityText);
            AlertFactory.preventInjection(streetText);
            AlertFactory.restrictToNumbers(buildingText);
            AlertFactory.restrictToNumbers(flatText);
            AlertFactory.preventAp(postcodeText);

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
                    if (cityText.getText().isEmpty() || postcodeText.getText().isEmpty() || streetText.getText().isEmpty() || buildingText.getText().isEmpty() || flatText.getText().isEmpty() ||
                    getSelectedItem() == null) {
                        AlertFactory.makeFillAllFieldsError(addressesStackPane);
                    } else {
                        addressModel.updateAddress(addressesStackPane, cityText.getText(), postcodeText.getText(),
                                streetText.getText(), buildingText.getText(), flatText.getText(), getSelectedItem().getAddressID());
                        refreshTable();
                        viewDialog.close();
                    }
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

            AlertFactory.preventInjection(cityText);
            AlertFactory.preventInjection(streetText);
            AlertFactory.restrictToNumbers(buildingText);
            AlertFactory.restrictToNumbers(flatText);
            AlertFactory.preventAp(postcodeText);

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

            acceptButton.setOnAction(actionEvent -> {
                if (cityText.getText().isEmpty() || postcodeText.getText().isEmpty() || streetText.getText().isEmpty() || buildingText.getText().isEmpty() || flatText.getText().isEmpty()) {
                    AlertFactory.makeFillAllFieldsError(addressesStackPane);
                } else {
                    addressModel.addAddress(addressesStackPane, cityText.getText(), postcodeText.getText(),
                            streetText.getText(), buildingText.getText(), flatText.getText());
                    refreshTable();
                    viewDialog.close();
                }
            });

            closeButton.setOnAction(actionEvent -> viewDialog.close());
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
