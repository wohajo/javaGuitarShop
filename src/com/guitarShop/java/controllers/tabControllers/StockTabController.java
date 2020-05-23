package com.guitarShop.java.controllers.tabControllers;

import com.guitarShop.java.helpers.AlertFactory;
import com.guitarShop.java.models.objects.Guitar;
import com.guitarShop.java.models.StockModel;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.sql.SQLException;
import java.util.ArrayList;

public class StockTabController {

    @FXML private StackPane stockStackPane;
    @FXML private JFXTreeTableView<Guitar> stockTable;
    @FXML private TreeTableColumn<Guitar, String> manufacturerCol;
    @FXML private TreeTableColumn<Guitar, String> modelCol;
    @FXML private TreeTableColumn<Guitar, Double> priceCol;
    @FXML private TreeTableColumn<Guitar, String> typeCol;
    @FXML private TreeTableColumn<Guitar, String> pickupsCol;
    @FXML private TreeTableColumn<Guitar, String> bridgeCol;
    @FXML private TreeTableColumn<Guitar, Integer> stringsCol;
    @FXML private TreeTableColumn<Guitar, String> tunersCol;
    @FXML private TreeTableColumn<Guitar, String> quantityCol;
    private AlertFactory alertFactory = new AlertFactory();
    private StockModel stockModel = new StockModel();
    private TreeItem<Guitar> root = new TreeItem<>();

    @FXML void initialize() throws SQLException {
        initTable();
    }

    @FXML
    public void initTable() throws SQLException {
        manufacturerCol.setCellValueFactory(new TreeItemPropertyValueFactory<Guitar, String>("manufacturer"));
        modelCol.setCellValueFactory(new TreeItemPropertyValueFactory<Guitar, String>("model"));
        priceCol.setCellValueFactory(new TreeItemPropertyValueFactory<Guitar, Double>("guitarPrice"));
        typeCol.setCellValueFactory(new TreeItemPropertyValueFactory<Guitar, String>("guitarType"));
        pickupsCol.setCellValueFactory(new TreeItemPropertyValueFactory<Guitar, String>("pickupsType"));
        bridgeCol.setCellValueFactory(new TreeItemPropertyValueFactory<Guitar, String>("bridgeType"));
        stringsCol.setCellValueFactory(new TreeItemPropertyValueFactory<Guitar, Integer>("numberOfStrings"));
        tunersCol.setCellValueFactory(new TreeItemPropertyValueFactory<Guitar, String>("lockingTuners"));
        quantityCol.setCellValueFactory(new TreeItemPropertyValueFactory<Guitar, String>("numberOfGuitars"));

        ObservableList<Guitar> stock = FXCollections.observableArrayList();
        stock.addAll(stockModel.getStock());

        for(int i = 0; i < stock.size(); i++)
            root.getChildren().add(new TreeItem<Guitar>(stock.get(i)));
        stockTable.getColumns().setAll(manufacturerCol, modelCol, priceCol, typeCol, pickupsCol,
                bridgeCol, stringsCol, tunersCol, quantityCol);
        stockTable.setRoot(root);
        stockTable.setShowRoot(false);
    }

    @FXML
    public void view() {
        try {

            GridPane dialogGrid = new GridPane();

            Label manufacturerLabel = new Label("Manufacturer");
            Label modelLabel = new Label("Model");
            Label priceLabel = new Label("Price");
            Label typeLabel = new Label("Type");
            Label pickupsLabel = new Label("Pickups");
            Label bridgeLabel = new Label("Bridge");
            Label tunersLabel = new Label("Tuners");
            Label quantityLabel = new Label("Quantity");
            Label descLabel = new Label("Description");
            Label stringsLabel = new Label("Strings");


        } catch (NullPointerException e) {
            alertFactory.makeAlertDialog(stockStackPane, "Error", "Choose a guitar to display informations.", "Close");
        }
    }
}
