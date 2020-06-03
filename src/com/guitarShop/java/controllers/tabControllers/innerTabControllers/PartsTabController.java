package com.guitarShop.java.controllers.tabControllers.innerTabControllers;

import com.guitarShop.java.helpers.AlertFactory;
import com.guitarShop.java.models.ManufacturerModel;
import com.guitarShop.java.models.objects.Manufacturer;
import com.guitarShop.java.models.objects.parts.Bridge;
import com.guitarShop.java.models.objects.parts.GuitarType;
import com.guitarShop.java.models.objects.parts.Pickups;
import com.jfoenix.controls.*;
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
import com.guitarShop.java.models.PartsModel;
import javafx.scene.text.Text;

public class PartsTabController {

    @FXML
    private StackPane partsStackPane;
    @FXML private TableView<Bridge> bridgeTable;
    @FXML private TableView<GuitarType> typeTable;
    @FXML private TableView<Pickups> pickupsTable;
    @FXML private TableColumn <Bridge, String> bridgeManCol;
    @FXML private TableColumn <Bridge, String> bridgeCol;
    @FXML private TableColumn <GuitarType, String> typeCol;
    @FXML private TableColumn <Pickups, String> pickupsManCol;
    @FXML private TableColumn <Pickups, String> pickupsCol;
    @FXML private TextField typeSearchText;
    @FXML private TextField pickupsSearchText;
    @FXML private TextField bridgeSearchText;
    private PartsModel partsModel = new PartsModel();
    private ManufacturerModel manufacturerModel = new ManufacturerModel();
    private AlertFactory alertFactory = new AlertFactory();

    @FXML void initialize() {
        initTables();
    }

    @FXML private void initTables() {
        initBridgesTable();
        initGuitarTypesTable();
        initPickupsTable();
    }

    private void initPickupsTable() {
        pickupsManCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        pickupsCol.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        ObservableList<Pickups> pickups = FXCollections.observableArrayList();
        pickups.addAll(partsModel.getPickups());

        FilteredList<Pickups> filteredList = new FilteredList<>(pickups, p -> true);
        initSearchFieldsPickups(filteredList);
        SortedList<Pickups> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(pickupsTable.comparatorProperty());
        pickupsTable.setItems(sortedList);
        pickupsTable.getColumns().setAll(pickupsManCol, pickupsCol);
    }

    private void initSearchFieldsPickups(FilteredList<Pickups> filteredList) {
        pickupsSearchText.textProperty().addListener((observableValue, s, t1) -> {
            filteredList.setPredicate(Pickups -> {
                if(t1 == null || t1.isEmpty()) {
                    return true;
                }
                String lowercaseFilter = t1.toLowerCase();
                if(Pickups.getName().toLowerCase().contains(lowercaseFilter))
                    return true;

                return false;
            });
        });
    }

    private void initGuitarTypesTable() {
        ObservableList<GuitarType> guitarTypes = FXCollections.observableArrayList();
        typeCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        guitarTypes.addAll(partsModel.getGuitarTypes());
        FilteredList<GuitarType> filteredList = new FilteredList<>(guitarTypes, p -> true);
        initSearchFieldsTypes(filteredList);
        SortedList<GuitarType> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(typeTable.comparatorProperty());
        typeTable.setItems(sortedList);
        typeTable.getColumns().setAll();
        typeTable.getColumns().setAll(typeCol);
    }

    private void initSearchFieldsTypes(FilteredList<GuitarType> filteredList) {
        typeSearchText.textProperty().addListener((observableValue, s, t1) -> {
            filteredList.setPredicate(GuitarType -> {
                if(t1 == null || t1.isEmpty()) {
                    return true;
                }
                String lowercaseFilter = t1.toLowerCase();
                if(GuitarType.getName().toLowerCase().contains(lowercaseFilter))
                    return true;

                return false;
            });
        });
    }

    private void initBridgesTable() {
        ObservableList<Bridge> bridges = FXCollections.observableArrayList();
        bridgeManCol.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        bridgeCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        bridges.addAll(partsModel.getBridges());
        FilteredList<Bridge> filteredList = new FilteredList<>(bridges, p -> true);
        initSearchFieldsBridges(filteredList);
        SortedList<Bridge> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(bridgeTable.comparatorProperty());
        bridgeTable.setItems(sortedList);
        bridgeTable.getColumns().setAll();
        bridgeTable.getColumns().setAll(bridgeManCol, bridgeCol);
    }

    private void initSearchFieldsBridges(FilteredList<Bridge> filteredList) {
        bridgeSearchText.textProperty().addListener((observableValue, s, t1) -> {
            filteredList.setPredicate(Bridge -> {
                if(t1 == null || t1.isEmpty()) {
                    return true;
                }
                String lowercaseFilter = t1.toLowerCase();
                if(Bridge.getName().toLowerCase().contains(lowercaseFilter))
                    return true;

                return false;
            });
        });
    }

    public void refreshBridgesTable() {
        initBridgesTable();
    }

    public void refreshPickupsTable(){
        initPickupsTable();
    }

    public void refreshTypeTable(){
        initGuitarTypesTable();
    }

    private GridPane getGridPaneWithText() {
        GridPane dialogGrid = new GridPane();
        dialogGrid.add(new Label("Name"), 0, 0);
        dialogGrid.add(new Label("Manufacturer"), 0, 1);
        return dialogGrid;
    }

    private Bridge getSelectedBridge() {
        return bridgeTable.getSelectionModel().getSelectedItem();
    }

    private GuitarType getSelectedType() {
        return typeTable.getSelectionModel().getSelectedItem();
    }

    private Pickups getSelectedPickups() {
        return pickupsTable.getSelectionModel().getSelectedItem();
    }

    @FXML private void updateBridge() {
        try {
            GridPane dialogGrid = getGridPaneWithText();

            TextField nameText = new TextField(getSelectedBridge().getName());
            JFXListView<Manufacturer> addressJFXListView = new JFXListView<>();
            addressJFXListView.setMaxWidth(200);
            addressJFXListView.setMaxHeight(100);
            ObservableList<Manufacturer> manufacturerObservableList = manufacturerModel.getManufacturers();
            addressJFXListView.setItems(manufacturerObservableList);
            for (Manufacturer m : manufacturerObservableList)
                if(getSelectedBridge().getManufacturerID() == m.getManufacturerID())
                    addressJFXListView.getSelectionModel().select(m);

            JFXButton acceptButton = new JFXButton("Accept");
            JFXButton closeButton = new JFXButton("Close");

            dialogGrid.add(nameText, 1, 0);
            dialogGrid.add(addressJFXListView, 1, 1);

            dialogGrid.setAlignment(Pos.CENTER);
            dialogGrid.setVgap(10);
            dialogGrid.setHgap(10);

            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            dialogLayout.setHeading(new Text("Bridge"));
            dialogLayout.setBody(dialogGrid);
            dialogLayout.setActions(acceptButton, closeButton);

            JFXDialog viewDialog = new JFXDialog(partsStackPane, dialogLayout, JFXDialog.DialogTransition.BOTTOM);

            acceptButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    partsModel.updateBridge(partsStackPane, getSelectedBridge().getBridgeID(), nameText.getText(), addressJFXListView.getSelectionModel().getSelectedItem().getManufacturerID());
                    refreshBridgesTable();
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
            alertFactory.makeAlertDialog(partsStackPane, "Error", "Choose a bridge to display informations.", "Close");
        }
    }

    @FXML private void addBridge() {
        GridPane dialogGrid = getGridPaneWithText();
        TextField nameText = new TextField();
        JFXListView<Manufacturer> addressJFXListView = new JFXListView<>();
        addressJFXListView.setMaxWidth(200);
        addressJFXListView.setMaxHeight(100);
        ObservableList<Manufacturer> manufacturerObservableList = manufacturerModel.getManufacturers();
        addressJFXListView.setItems(manufacturerObservableList);
        JFXButton acceptButton = new JFXButton("Accept");
        JFXButton closeButton = new JFXButton("Close");

        dialogGrid.add(nameText, 1, 0);
        dialogGrid.add(addressJFXListView, 1, 1);

        dialogGrid.setAlignment(Pos.CENTER);
        dialogGrid.setVgap(10);
        dialogGrid.setHgap(10);

        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        dialogLayout.setHeading(new Text("Bridge"));
        dialogLayout.setBody(dialogGrid);
        dialogLayout.setActions(acceptButton, closeButton);

        JFXDialog viewDialog = new JFXDialog(partsStackPane, dialogLayout, JFXDialog.DialogTransition.BOTTOM);

        acceptButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                partsModel.addBridge(partsStackPane, nameText.getText(), addressJFXListView.getSelectionModel().getSelectedItem().getManufacturerID());
                refreshBridgesTable();
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

    @FXML private void deleteBridge() {
        try {
            partsModel.deleteBridge(partsStackPane, getSelectedBridge().getBridgeID());
            refreshBridgesTable();
        } catch (NullPointerException e) {
            AlertFactory.makeItemNotChoosenDialog(partsStackPane);
        }
    }

    @FXML private void updateType() {
        try {
            GridPane dialogGrid = new GridPane();

            Label nameLabel = new Label("Name");
            TextField nameText = new TextField(getSelectedType().getName());

            JFXButton acceptButton = new JFXButton("Accept");
            JFXButton closeButton = new JFXButton("Close");

            dialogGrid.add(nameLabel, 0, 0);
            dialogGrid.add(nameText, 1, 0);

            dialogGrid.setAlignment(Pos.CENTER);
            dialogGrid.setVgap(10);
            dialogGrid.setHgap(10);

            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            dialogLayout.setHeading(new Text("Type"));
            dialogLayout.setBody(dialogGrid);
            dialogLayout.setActions(acceptButton, closeButton);

            JFXDialog viewDialog = new JFXDialog(partsStackPane, dialogLayout, JFXDialog.DialogTransition.BOTTOM);

            acceptButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    partsModel.updateType(partsStackPane, getSelectedType().getTypeID(), nameText.getText());
                    refreshTypeTable();
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
            alertFactory.makeAlertDialog(partsStackPane, "Error", "Choose a guitar type to update informations.", "Close");
        }
    }

    @FXML private void addType() {
        GridPane dialogGrid = new GridPane();

        Label nameLabel = new Label("Name");
        TextField nameText = new TextField();

        JFXButton acceptButton = new JFXButton("Accept");
        JFXButton closeButton = new JFXButton("Close");

        dialogGrid.add(nameLabel, 0, 0);
        dialogGrid.add(nameText, 1, 0);

        dialogGrid.setAlignment(Pos.CENTER);
        dialogGrid.setVgap(10);
        dialogGrid.setHgap(10);

        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        dialogLayout.setHeading(new Text("Type"));
        dialogLayout.setBody(dialogGrid);
        dialogLayout.setActions(acceptButton, closeButton);

        JFXDialog viewDialog = new JFXDialog(partsStackPane, dialogLayout, JFXDialog.DialogTransition.BOTTOM);

        acceptButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                partsModel.addType(partsStackPane, nameText.getText());
                refreshTypeTable();
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

    @FXML private void deleteType() {
        try {
            partsModel.deleteType(partsStackPane, getSelectedType().getTypeID());
            refreshTypeTable();
        } catch (NullPointerException e) {
            AlertFactory.makeItemNotChoosenDialog(partsStackPane);
        }
    }

    @FXML private void updatePickups() {
        try {
            GridPane dialogGrid = getGridPaneWithText();
            TextField nameText = new TextField(getSelectedPickups().getName());
            JFXListView<Manufacturer> addressJFXListView = new JFXListView<>();
            addressJFXListView.setMaxWidth(200);
            addressJFXListView.setMaxHeight(100);
            ObservableList<Manufacturer> manufacturerObservableList = manufacturerModel.getManufacturers();
            addressJFXListView.setItems(manufacturerObservableList);
            for (Manufacturer m : manufacturerObservableList)
                if(getSelectedPickups().getManufacturerID() == m.getManufacturerID())
                    addressJFXListView.getSelectionModel().select(m);

            JFXButton acceptButton = new JFXButton("Accept");
            JFXButton closeButton = new JFXButton("Close");

            dialogGrid.add(nameText, 1, 0);
            dialogGrid.add(addressJFXListView, 1, 1);

            dialogGrid.setAlignment(Pos.CENTER);
            dialogGrid.setVgap(10);
            dialogGrid.setHgap(10);

            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            dialogLayout.setHeading(new Text("Pickups"));
            dialogLayout.setBody(dialogGrid);
            dialogLayout.setActions(acceptButton, closeButton);

            JFXDialog viewDialog = new JFXDialog(partsStackPane, dialogLayout, JFXDialog.DialogTransition.BOTTOM);

            acceptButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    partsModel.updatePickups(partsStackPane, getSelectedPickups().getPickupsID(), nameText.getText(), addressJFXListView.getSelectionModel().getSelectedItem().getManufacturerID());
                    refreshPickupsTable();
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
            alertFactory.makeAlertDialog(partsStackPane, "Error", "Choose pickups to display informations.", "Close");
        }
    }

    @FXML private void addPickups() {
        GridPane dialogGrid = getGridPaneWithText();

        TextField nameText = new TextField();
        JFXListView<Manufacturer> addressJFXListView = new JFXListView<>();
        addressJFXListView.setMaxWidth(200);
        addressJFXListView.setMaxHeight(100);
        ObservableList<Manufacturer> manufacturerObservableList = manufacturerModel.getManufacturers();
        addressJFXListView.setItems(manufacturerObservableList);

        JFXButton acceptButton = new JFXButton("Accept");
        JFXButton closeButton = new JFXButton("Close");

        dialogGrid.add(nameText, 1, 0);
        dialogGrid.add(addressJFXListView, 1, 1);

        dialogGrid.setAlignment(Pos.CENTER);
        dialogGrid.setVgap(10);
        dialogGrid.setHgap(10);

        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        dialogLayout.setHeading(new Text("Pickups"));
        dialogLayout.setBody(dialogGrid);
        dialogLayout.setActions(acceptButton, closeButton);

        JFXDialog viewDialog = new JFXDialog(partsStackPane, dialogLayout, JFXDialog.DialogTransition.BOTTOM);

        acceptButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                partsModel.addPickups(partsStackPane, nameText.getText(), addressJFXListView.getSelectionModel().getSelectedItem().getManufacturerID());
                refreshPickupsTable();
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

    @FXML private void deletePickups() {
        try {
            partsModel.deletePickups(partsStackPane, getSelectedPickups().getPickupsID());
            refreshPickupsTable();
        } catch (NullPointerException e) {
            AlertFactory.makeItemNotChoosenDialog(partsStackPane);
        }
    }
}
