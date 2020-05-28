package com.guitarShop.java.controllers.tabControllers.innerTabControllers;

import com.guitarShop.java.controllers.tabControllers.StockTabController;
import com.guitarShop.java.helpers.AlertFactory;
import com.guitarShop.java.models.ManufacturerModel;
import com.guitarShop.java.models.objects.Manufacturer;
import com.guitarShop.java.models.objects.parts.Bridge;
import com.guitarShop.java.models.objects.parts.GuitarType;
import com.guitarShop.java.models.objects.parts.Pickups;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import com.guitarShop.java.models.PartsModel;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.SQLException;

public class PartsTabController {

    @FXML
    private StackPane partsStackPane;
    @FXML private JFXTreeTableView<Bridge> bridgeTable;
    @FXML private JFXTreeTableView<GuitarType> typeTable;
    @FXML private JFXTreeTableView<Pickups> pickupsTable;
    @FXML private TreeTableColumn <Bridge, String> bridgeManCol;
    @FXML private TreeTableColumn <Bridge, String> bridgeCol;
    @FXML private TreeTableColumn <GuitarType, String> typeCol;
    @FXML private TreeTableColumn <Pickups, String> pickupsManCol;
    @FXML private TreeTableColumn <Pickups, String> pickupsCol;
    private PartsModel partsModel = new PartsModel();
    private ManufacturerModel manufacturerModel = new ManufacturerModel();
    private TreeItem<Bridge> bridgesTreeRoot = new TreeItem<>();
    private TreeItem<GuitarType> guitarTypeRoot = new TreeItem<>();
    private TreeItem<Pickups> pickupsRoot = new TreeItem<>();
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
        pickupsManCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        pickupsCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("manufacturer"));
        ObservableList<Pickups> pickups = FXCollections.observableArrayList();
        pickups.addAll(partsModel.getPickups());
        for (int i = 0; i < pickups.size(); i ++)
            pickupsRoot.getChildren().add(new TreeItem<>(pickups.get(i)));

        pickupsTable.getColumns().setAll(pickupsManCol, pickupsCol);
        pickupsTable.setRoot(pickupsRoot);
        pickupsTable.setShowRoot(false);
    }

    private void initGuitarTypesTable() {
        ObservableList<GuitarType> guitarTypes = FXCollections.observableArrayList();
        typeCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        guitarTypes.addAll(partsModel.getGuitarTypes());
        for (int i = 0; i < guitarTypes.size(); i ++)
            guitarTypeRoot.getChildren().add(new TreeItem<>(guitarTypes.get(i)));

        typeTable.getColumns().setAll(typeCol);
        typeTable.setRoot(guitarTypeRoot);
        typeTable.setShowRoot(false);
    }

    private void initBridgesTable() {
        ObservableList<Bridge> bridges = FXCollections.observableArrayList();
        bridgeManCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("manufacturer"));
        bridgeCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        bridges.addAll(partsModel.getBridges());
        for (int i = 0; i < bridges.size(); i ++)
            bridgesTreeRoot.getChildren().add(new TreeItem<>(bridges.get(i)));

        bridgeTable.getColumns().setAll(bridgeManCol, bridgeCol);
        bridgeTable.setRoot(bridgesTreeRoot);
        bridgeTable.setShowRoot(false);
    }

    public void refreshBridgesTable() throws IOException, SQLException {
        bridgeTable.getRoot().getChildren().clear();
        initBridgesTable();
        refreshStockTable();
    }

    public void refreshPickupsTable() throws IOException, SQLException {
        pickupsTable.getRoot().getChildren().clear();
        initPickupsTable();
        refreshStockTable();
    }

    public void refreshTypeTable() throws IOException, SQLException {
        typeTable.getRoot().getChildren().clear();
        initGuitarTypesTable();
        refreshStockTable();
    }

    private void refreshStockTable() throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/guitarShop/resources/tabs/StockTab.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        StockTabController controller = fxmlLoader.getController();
        controller.refreshTable();
    }

    private GridPane getGridPaneWithText() {
        GridPane dialogGrid = new GridPane();
        dialogGrid.add(new Label("Name"), 0, 0);
        dialogGrid.add(new Label("Manufacturer"), 0, 1);
        return dialogGrid;
    }

    private Bridge getSelectedBridge() {
        return bridgeTable.getSelectionModel().getSelectedItem().getValue();
    }

    private GuitarType getSelectedType() {
        return typeTable.getSelectionModel().getSelectedItem().getValue();
    }

    private Pickups getSelectedPickups() {
        return pickupsTable.getSelectionModel().getSelectedItem().getValue();
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
                    try {
                        refreshBridgesTable();
                    } catch (IOException | SQLException ex) {
                        AlertFactory.makeRefreshTableError(partsStackPane);
                    }
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
                try {
                    refreshBridgesTable();
                } catch (IOException | SQLException ex) {
                    AlertFactory.makeRefreshTableError(partsStackPane);
                }
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
            try {
                refreshBridgesTable();
            } catch (IOException | SQLException ex) {
                AlertFactory.makeRefreshTableError(partsStackPane);
            }
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
                    try {
                        refreshTypeTable();
                    } catch (IOException | SQLException ex) {
                        AlertFactory.makeRefreshTableError(partsStackPane);
                    }
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
                try {
                    refreshTypeTable();
                } catch (IOException | SQLException ex) {
                    AlertFactory.makeRefreshTableError(partsStackPane);
                }
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
            try {
                refreshTypeTable();
            } catch (IOException | SQLException ex) {
                AlertFactory.makeRefreshTableError(partsStackPane);
            }
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
                    try {
                        refreshPickupsTable();
                    } catch (IOException | SQLException ex) {
                        AlertFactory.makeRefreshTableError(partsStackPane);
                    }
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
                try {
                    refreshPickupsTable();
                } catch (IOException | SQLException ex) {
                    AlertFactory.makeRefreshTableError(partsStackPane);
                }
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
            try {
                refreshPickupsTable();
            } catch (IOException | SQLException ex) {
                AlertFactory.makeRefreshTableError(partsStackPane);
            }
        } catch (NullPointerException e) {
            AlertFactory.makeItemNotChoosenDialog(partsStackPane);
        }
    }
}
