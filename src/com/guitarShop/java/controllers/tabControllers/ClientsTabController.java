package com.guitarShop.java.controllers.tabControllers;

import com.guitarShop.java.models.ClientsModel;
import com.guitarShop.java.models.objects.Client;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;

public class ClientsTabController {

    @FXML JFXTreeTableView<Client> clientsTable;
    @FXML TreeTableColumn<Client, String> nameCol;
    @FXML TreeTableColumn<Client, String> surnameCol;
    @FXML TreeTableColumn<Client, String> phoneCol;
    @FXML TreeTableColumn<Client, String> peselCol;
    @FXML TreeTableColumn<Client, String> emailCol;
    private ClientsModel clientsModel = new ClientsModel();
    private TreeItem<Client> root = new TreeItem<>();

    @FXML void initialize() {
        initTable();
    }

    @FXML private void initTable() {
        nameCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        surnameCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("surname"));
        phoneCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("phoneNumber"));
        peselCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("pesel"));
        emailCol.setCellValueFactory(new TreeItemPropertyValueFactory<>("email"));

        ObservableList<Client> clients = FXCollections.observableArrayList();
        clients.addAll(clientsModel.getClients());

        for(int i = 0; i < clients.size(); i++)
            root.getChildren().add(new TreeItem<Client>(clients.get(i)));
        clientsTable.getColumns().setAll(nameCol, surnameCol, phoneCol, peselCol, emailCol);
        clientsTable.setRoot(root);
        clientsTable.setShowRoot(false);
    }
}
