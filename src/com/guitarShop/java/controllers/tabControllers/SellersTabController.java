package com.guitarShop.java.controllers.tabControllers;

import com.guitarShop.java.models.SellersModel;
import com.guitarShop.java.models.objects.Seller;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;

public class SellersTabController {

    @FXML JFXTreeTableView<Seller> sellersTable;
    @FXML TreeTableColumn<Seller, String> nameCol;
    @FXML TreeTableColumn<Seller, String> surnameCol;
    @FXML TreeTableColumn<Seller, String> phoneCol;
    @FXML TreeTableColumn<Seller, String> peselCol;
    @FXML TreeTableColumn<Seller, String> emailCol;
    private SellersModel sellersModel = new SellersModel();
    private TreeItem<Seller> root = new TreeItem<>();


    @FXML void initialize() {
        initTable();
    }

    @FXML private void initTable() {
        nameCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        surnameCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("surname"));
        phoneCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("phoneNumber"));
        peselCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("pesel"));
        emailCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("email"));

        ObservableList<Seller> sellers = FXCollections.observableArrayList();
        sellers.addAll(sellersModel.getSellers());

        for(int i = 0; i < sellers.size(); i++)
            root.getChildren().add(new TreeItem<>(sellers.get(i)));
        sellersTable.getColumns().setAll(nameCol, surnameCol, phoneCol, peselCol, emailCol);
        sellersTable.setRoot(root);
        sellersTable.setShowRoot(false);
    }

}
