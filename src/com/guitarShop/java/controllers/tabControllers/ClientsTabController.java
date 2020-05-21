package com.guitarShop.java.controllers.tabControllers;

import com.guitarShop.java.models.ClientsModel;
import com.guitarShop.java.models.objects.Client;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

public class ClientsTabController {

    @FXML TableView<Client> clientsTable;
    @FXML TableColumn<Client, String> nameCol;
    @FXML TableColumn<Client, String> surnameCol;
    @FXML TableColumn<Client, String> phoneCol;
    @FXML TableColumn<Client, String> peselCol;
    @FXML TableColumn<Client, String> emailCol;
    @FXML TextArea addressBox;
    private ClientsModel clientsModel = new ClientsModel();

    @FXML void initialize() {
        initTable();
    }

    @FXML private void initTable() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        peselCol.setCellValueFactory(new PropertyValueFactory<>("pesel"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        this.clientsTable.setItems(clientsModel.getClients());
    }

    @FXML private void changeAddressBox() {
        int clientAddressID = this.clientsTable.getSelectionModel().getSelectedItem().getAddressID();
        addressBox.setText(clientsModel.getAddressByID(clientAddressID));
    }
}
