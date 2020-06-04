package com.guitarShop.java.controllers.tabControllers.innerTabControllers;

import com.guitarShop.java.helpers.AlertFactory;
import com.guitarShop.java.models.AddressModel;
import com.guitarShop.java.models.ManufacturerModel;
import com.guitarShop.java.models.objects.Address;
import com.guitarShop.java.models.objects.Manufacturer;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.SQLException;

public class ManufacturersTabController {
    @FXML private StackPane manufacturersStackPane;
    @FXML private TableView<Manufacturer> manufacturerTable;
    @FXML private TableColumn<Manufacturer, String> nameCol;
    @FXML private TextField searchField;
    private AddressModel addressModel = new AddressModel();
    private ManufacturerModel manufacturerModel = new ManufacturerModel();
    private TreeItem<Manufacturer> root = new TreeItem<>();
    private AlertFactory alertFactory = new AlertFactory();

    @FXML void initialize() {
        initTable();
    }

    @FXML private void initTable() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("manufacturerName"));
        ObservableList<Manufacturer> manufacturerObservableList = FXCollections.observableArrayList();
        manufacturerObservableList.addAll(manufacturerModel.getManufacturers());
        FilteredList<Manufacturer> filteredList = new FilteredList<>(manufacturerObservableList, p -> true);

        initSearchFields(filteredList);

        SortedList<Manufacturer> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(manufacturerTable.comparatorProperty());
        manufacturerTable.setItems(sortedList);

        manufacturerTable.getColumns().setAll(nameCol);
    }

    private void initSearchFields(FilteredList<Manufacturer> filteredList) {
        AlertFactory.preventInjection(searchField);
        searchField.textProperty().addListener((observableValue, s, t1) -> {
            filteredList.setPredicate(Manufacturer -> {
                if(t1 == null || t1.isEmpty()) {
                    return true;
                }
                String lowercaseFilter = t1.toLowerCase();
                if(Manufacturer.getManufacturerName().toLowerCase().contains(lowercaseFilter))
                    return true;

                return false;
            });
        });
    }

    private void refreshTable() {
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
        return manufacturerTable.getSelectionModel().getSelectedItem();
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
            AlertFactory.preventInjection(nameText);

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

        AlertFactory.preventInjection(nameText);

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
               if (nameText.getText() == "" || addressJFXListView.getSelectionModel().getSelectedItem() == null) {
                   AlertFactory.makeFillAllFieldsError(manufacturersStackPane);
               } else {
                   manufacturerModel.addManufacturer(manufacturersStackPane,
                           nameText.getText(),
                           addressJFXListView.getSelectionModel().getSelectedItem().getAddressID());
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
    }

    @FXML private void delete() {
        manufacturerModel.deleteManufacturer(manufacturersStackPane, getSelectedItem().getManufacturerID());
        refreshTable();
    }
}
