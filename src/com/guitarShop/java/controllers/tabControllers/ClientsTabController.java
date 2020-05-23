package com.guitarShop.java.controllers.tabControllers;

import com.guitarShop.java.helpers.AlertFactory;
import com.guitarShop.java.models.ClientsModel;
import com.guitarShop.java.models.objects.Client;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class ClientsTabController {

    @FXML StackPane clientsStackPane;
    @FXML JFXTreeTableView<Client> clientsTable;
    @FXML TreeTableColumn<Client, String> nameCol;
    @FXML TreeTableColumn<Client, String> surnameCol;
    @FXML TreeTableColumn<Client, String> phoneCol;
    @FXML TreeTableColumn<Client, String> peselCol;
    @FXML TreeTableColumn<Client, String> emailCol;
    private AlertFactory alertFactory = new AlertFactory();
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

    @FXML private void view() {
        try {
            GridPane dialogGrid = new GridPane();

            Label nameLabel = new Label("Name: ");
            Label surnameLabel = new Label("Surname: ");
            Label phoneLabel = new Label("Phone: ");
            Label peselLabel = new Label("Pesel: ");
            Label emailLabel = new Label("Email: ");
            Text nameText = new Text(clientsTable.getSelectionModel().getSelectedItem().getValue().getName());
            Text surnameText = new Text(clientsTable.getSelectionModel().getSelectedItem().getValue().getSurname());
            Text phoneText = new Text(clientsTable.getSelectionModel().getSelectedItem().getValue().getPhoneNumber());
            Text peselText = new Text(clientsTable.getSelectionModel().getSelectedItem().getValue().getPesel());
            Text emailText = new Text(clientsTable.getSelectionModel().getSelectedItem().getValue().getEmail());
            JFXButton closeButton = new JFXButton("Close");

            dialogGrid.add(nameLabel, 0, 0);
            dialogGrid.add(surnameLabel, 0, 1);
            dialogGrid.add(phoneLabel, 0, 2);
            dialogGrid.add(peselLabel, 0, 3);
            dialogGrid.add(emailLabel, 0, 4);

            dialogGrid.add(nameText, 1, 0);
            dialogGrid.add(surnameText, 1, 1);
            dialogGrid.add(phoneText, 1, 2);
            dialogGrid.add(peselText, 1, 3);
            dialogGrid.add(emailText, 1, 4);

            dialogGrid.setAlignment(Pos.CENTER);
            dialogGrid.setVgap(10);
            dialogGrid.setHgap(10);
            dialogGrid.setPadding(new Insets(10));

            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            dialogLayout.setHeading(new Text("Person"));
            dialogLayout.setBody(dialogGrid);

            dialogLayout.setActions(closeButton);

            JFXDialog viewDialog = new JFXDialog(clientsStackPane, dialogLayout, JFXDialog.DialogTransition.BOTTOM);

            closeButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    viewDialog.close();
                }
            });
            viewDialog.show();

        } catch (NullPointerException e) {
            alertFactory.makeAlertDialog(clientsStackPane, "Error", "Choose a person to display informations.", "Close");
        }
    }
}
