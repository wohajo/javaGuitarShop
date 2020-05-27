package com.guitarShop.java.controllers.tabControllers.innerTabControllers;

import com.guitarShop.java.helpers.AlertFactory;
import com.guitarShop.java.models.objects.parts.Bridge;
import com.guitarShop.java.models.objects.parts.GuitarType;
import com.guitarShop.java.models.objects.parts.Pickups;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.StackPane;
import com.guitarShop.java.models.PartsModel;

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
    private TreeItem<Bridge> bridgesTreeRoot = new TreeItem<>();
    private TreeItem<GuitarType> guitarTypeRoot = new TreeItem<>();
    private TreeItem<Pickups> pickupsRoot = new TreeItem<>();
    private AlertFactory alertFactory = new AlertFactory();

    @FXML void initialize() {
        initTables();
    }

    @FXML private void initTables() {
        bridgeManCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("manufacturer"));
        bridgeCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        typeCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        pickupsManCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        pickupsCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("manufacturer"));

        ObservableList<Bridge> bridges = FXCollections.observableArrayList();
        ObservableList<GuitarType> guitarTypes = FXCollections.observableArrayList();
        ObservableList<Pickups> pickups = FXCollections.observableArrayList();

        bridges.addAll(partsModel.getBridges());
        for (int i = 0; i < bridges.size(); i ++) {
            bridgesTreeRoot.getChildren().add(new TreeItem<>(bridges.get(i)));
        }
        bridgeTable.getColumns().setAll(bridgeManCol, bridgeCol);
        bridgeTable.setRoot(bridgesTreeRoot);
        bridgeTable.setShowRoot(false);

        guitarTypes.addAll(partsModel.getGuitarTypes());
        for (int i = 0; i < guitarTypes.size(); i ++) {
            guitarTypeRoot.getChildren().add(new TreeItem<>(guitarTypes.get(i)));
        }
        typeTable.getColumns().setAll(typeCol);
        typeTable.setRoot(guitarTypeRoot);
        typeTable.setShowRoot(false);

        pickups.addAll(partsModel.getPickups());
        for (int i = 0; i < pickups.size(); i ++) {
            pickupsRoot.getChildren().add(new TreeItem<>(pickups.get(i)));
        }
        pickupsTable.getColumns().setAll(pickupsManCol, pickupsCol);
        pickupsTable.setRoot(pickupsRoot);
        pickupsTable.setShowRoot(false);
    }

    @FXML private void viewBridge() {

    }
    @FXML private void updateBridge() {

    }
    @FXML private void addBridge() {

    }
    @FXML private void deleteBridge() {

    }
    @FXML private void viewType() {

    }
    @FXML private void updateType() {

    }
    @FXML private void addType() {

    }
    @FXML private void deleteType() {

    }
    @FXML private void viewPickups() {

    }
    @FXML private void updatePickups() {

    }
    @FXML private void addPickups() {

    }
    @FXML private void deletePickups() {

    }
}
