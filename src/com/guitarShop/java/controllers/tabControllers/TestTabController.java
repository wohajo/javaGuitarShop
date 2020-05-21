package com.guitarShop.java.controllers.tabControllers;

import com.guitarShop.java.models.ClientsModel;
import com.guitarShop.java.models.objects.Client;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;

public class TestTabController {

    @FXML
    private JFXTreeTableView<Client> testTable;

    public void initialize() {
        ClientsModel clientsModel = new ClientsModel();
        JFXTreeTableColumn<Client, String> nameCol = new JFXTreeTableColumn<>("Name");
        nameCol.setMinWidth(200);
        nameCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Client, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Client, String> clientStringCellDataFeatures) {
                return clientStringCellDataFeatures.getValue().getValue().name;
            }
        });

        ObservableList<Client> clients = FXCollections.observableArrayList();
        clients.addAll(clientsModel.getClients());

        final TreeItem<Client> root = new RecursiveTreeItem<Client>(clients, RecursiveTreeObject::getChildren);
        testTable.getColumns().setAll(nameCol);
        testTable.setRoot(root);
        testTable.setShowRoot(false);
    }
}
