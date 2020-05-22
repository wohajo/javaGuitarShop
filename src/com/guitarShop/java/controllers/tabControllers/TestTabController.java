package com.guitarShop.java.controllers.tabControllers;

import com.guitarShop.java.models.ClientsModel;
import com.guitarShop.java.models.objects.Client;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;

public class TestTabController {

    @FXML
    private JFXTreeTableView<Client> testTable;
    @FXML
    private TreeTableColumn<Client, String> nameCol;
    private TreeItem<Client> root = new TreeItem<>();

    public void initialize() {
        ClientsModel clientsModel = new ClientsModel();

        nameCol.setCellValueFactory(new TreeItemPropertyValueFactory<Client, String>("name"));

        ObservableList<Client> clients = FXCollections.observableArrayList();
        clients.addAll(clientsModel.getClients());

        for(int i = 0; i < clients.size(); i++)
            root.getChildren().add(new TreeItem<>(clients.get(i)));
        testTable.getColumns().setAll(nameCol);
        testTable.setRoot(root);
        testTable.setShowRoot(false);
    }

    @FXML
    public void add() {
        root.getChildren().add(new TreeItem<>(new Client(5, "RRRRR", "cgh", "888", "333", "333", 1)));
    }
    @FXML
    public void get() {
        System.out.println(testTable.getSelectionModel().getSelectedItem().getValue().getName());
    }
    @FXML
    public void delete() {
        TreeItem<Client> selectedItem = testTable.getSelectionModel().getSelectedItem();
        TreeItem<Client> parentItem = selectedItem.getParent();
        parentItem.getChildren().remove(selectedItem);
    }

    @FXML
    public void edit() {
        testTable.getSelectionModel().getSelectedItem().getValue().name = "aaaaaaaa";
        testTable.refresh();
    }
}

