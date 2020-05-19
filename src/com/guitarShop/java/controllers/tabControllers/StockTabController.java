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

    @FXML TableView<Guitar> stockTable;
    @FXML Button refreshStockTableButton;

    private void initializeTable() {

        ObservableList<Guitar> data = FXCollections.observableArrayList();

        Guitar guitar = new Guitar(1, "fender", 2500, "strat", "sss",
                "yes", true, 6, "aaa", "aaaaaa", 4);

        data.add(guitar);

        TableColumn<Guitar, String> manufacturerCol = new TableColumn<>("Maufacturer");
        manufacturerCol.setMinWidth(200);
        manufacturerCol.setCellValueFactory(new PropertyValueFactory<Guitar, String>("manufacturer"));

        TableColumn<Guitar, String> guitarModelCol = new TableColumn<>("Model");
        guitarModelCol.setMinWidth(200);
        guitarModelCol.setCellValueFactory(new PropertyValueFactory<Guitar, String>("model"));

//        TableColumn priceColumn = new TableColumn("Price");
//        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
//
//        TableColumn guitarTypeCol = new TableColumn("Type");
//        guitarTypeCol.setCellValueFactory(new PropertyValueFactory<>("guitarType"));
//
//        TableColumn pickupsCol = new TableColumn("Pickups");
//        pickupsCol.setCellValueFactory(new PropertyValueFactory<>("pickupsType"));
//
//        TableColumn bridgeCol = new TableColumn("Bridge");
//        bridgeCol.setCellValueFactory(new PropertyValueFactory<>("bridgeType"));
//
//        TableColumn tunersCol = new TableColumn("Tuners");
//        priceColumn.setCellValueFactory(new PropertyValueFactory<>("lockingTuners"));
//
//        TableColumn inStockCol = new TableColumn("In Stock");
//        priceColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfGuitars"));
//
//        TableColumn stringsCol = new TableColumn("Strings");
//        priceColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfStrings"));

        this.stockTable = new TableView<>();
        this.stockTable.setItems(data);
        stockTable.getColumns().addAll(manufacturerCol, guitarModelCol);
//        stockTable.getColumns().addAll(manufacturerCol, guitarModelCol, priceColumn, guitarTypeCol, pickupsCol,
//                bridgeCol, tunersCol, inStockCol, stringsCol);
    }

    @FXML
    private void refreshStockTable() {
        initializeTable();

        System.out.println();
    }
}
