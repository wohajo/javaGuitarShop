package com.guitarShop.java.controllers.tabControllers;


import com.guitarShop.java.models.objects.Client;
import com.guitarShop.java.models.objects.Guitar;
import com.guitarShop.java.models.StockModel;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;

import java.sql.SQLException;

public class StockTabController {

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
}
