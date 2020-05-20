package com.guitarShop.java.controllers.tabControllers;

import com.guitarShop.java.controllers.Guitar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class StockTabController {

    @FXML private TableView<Guitar> stockTable;
    @FXML private TableColumn<Guitar, String> manufacturerCol;
    @FXML private TableColumn<Guitar, String> modelCol;
    @FXML private TableColumn<Guitar, Double> priceCol;
    @FXML private TableColumn<Guitar, String> typeCol;
    @FXML private TableColumn<Guitar, String> pickupsCol;
    @FXML private TableColumn<Guitar, String> bridgeCol;
    @FXML private TableColumn<Guitar, Integer> stringsCol;
    @FXML private TableColumn<Guitar, String> tunersCol;
    @FXML private TableColumn<Guitar, String> quantityCol;
    @FXML private Button refreshStockTableButton;

    @FXML
    private void initializeTable() {
        manufacturerCol.setCellValueFactory(new PropertyValueFactory<Guitar, String>("manufacturer"));
        modelCol.setCellValueFactory(new PropertyValueFactory<Guitar, String>("model"));
        priceCol.setCellValueFactory(new PropertyValueFactory<Guitar, Double>("guitarPrice"));
        typeCol.setCellValueFactory(new PropertyValueFactory<Guitar, String>("guitarType"));
        pickupsCol.setCellValueFactory(new PropertyValueFactory<Guitar, String>("pickupsType"));
        bridgeCol.setCellValueFactory(new PropertyValueFactory<Guitar, String>("pickupsType"));
        stringsCol.setCellValueFactory(new PropertyValueFactory<Guitar, Integer>("numberOfStrings"));
        tunersCol.setCellValueFactory(new PropertyValueFactory<Guitar, String>("lockingTuners"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<Guitar, String>("numberOfGuitars"));
        this.stockTable.setItems(getStock());
    }


    public ObservableList<Guitar> getStock() {
        ObservableList<Guitar> stockList = FXCollections.observableArrayList();
        for (int i = 0; i < 5; i ++) {
            stockList.add(new Guitar( i, "Femder", i * 1000, "strat", "sss", "floating",
                    true, i, "aaaaaaaaa", "aaaaaaawww", 6));
        }
        return stockList;
    }
}
