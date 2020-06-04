package com.guitarShop.java.controllers.tabControllers;

import com.guitarShop.java.helpers.AlertFactory;
import com.guitarShop.java.models.AddressModel;
import com.guitarShop.java.models.ClientsModel;
import com.guitarShop.java.models.objects.Address;
import com.guitarShop.java.models.objects.Client;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class ClientsTabController {

    @FXML private StackPane clientsStackPane;
    @FXML private TableView<Client> clientsTable;
    @FXML private TableColumn<Client, String> nameCol;
    @FXML private TableColumn<Client, String> surnameCol;
    @FXML private TableColumn<Client, String> peselCol;
    @FXML private TextField nameSearchText;
    @FXML private TextField surnameSearchText;
    @FXML private TextField peselSearchText;
    private AlertFactory alertFactory = new AlertFactory();
    private ClientsModel clientsModel = new ClientsModel();
    private AddressModel addressModel = new AddressModel();
    private TreeItem<Client> root = new TreeItem<>();

    @FXML void initialize() {
        initTable();
    }

    @FXML private void initTable() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
        peselCol.setCellValueFactory(new PropertyValueFactory<>("pesel"));

        ObservableList<Client> clients = FXCollections.observableArrayList();
        clients.addAll(clientsModel.getClients());

        FilteredList<Client> filteredList = new FilteredList<>(clients, p -> true);
        initSearchFields(filteredList);
        SortedList<Client> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(clientsTable.comparatorProperty());
        clientsTable.setItems(sortedList);
        clientsTable.getColumns().setAll();

        clientsTable.getColumns().setAll(nameCol, surnameCol, peselCol);
    }

    private void initSearchFields(FilteredList<Client> filteredList) {
        AlertFactory.preventInjection(nameSearchText);
        AlertFactory.preventInjection(surnameSearchText);
        AlertFactory.numbersWithQuantity(peselSearchText);

        nameSearchText.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(Client -> {
                if(newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowercaseFilter = newValue.toLowerCase();
                if(Client.getName().toLowerCase().contains(lowercaseFilter))
                    return true;

                return false;
            });
        });
        surnameSearchText.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(Client -> {
                if(newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowercaseFilter = newValue.toLowerCase();
                if(Client.getSurname().toLowerCase().contains(lowercaseFilter))
                    return true;

                return false;
            });
        });
        peselSearchText.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(Client -> {
                if(newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowercaseFilter = newValue.toLowerCase();
                if(Client.getPesel().toLowerCase().contains(lowercaseFilter))
                    return true;

                return false;
            });
        });
    }

    private GridPane getGridPaneWithText() {
        GridPane dialogGrid = new GridPane();

        Label nameLabel = new Label("Name: ");
        Label surnameLabel = new Label("Surname: ");
        Label phoneLabel = new Label("Phone: ");
        Label peselLabel = new Label("Pesel: ");
        Label emailLabel = new Label("Email: ");
        Label addressLabel = new Label("Address: ");

        dialogGrid.add(nameLabel, 0, 0);
        dialogGrid.add(surnameLabel, 0, 1);
        dialogGrid.add(phoneLabel, 0, 2);
        dialogGrid.add(peselLabel, 0, 3);
        dialogGrid.add(emailLabel, 0, 4);
        dialogGrid.add(addressLabel, 0, 5);

        return dialogGrid;
    }

    private void refreshTable() {
        initTable();
    }

    private Client getSelectedItem() {
        return clientsTable.getSelectionModel().getSelectedItem();
    }

    @FXML private void view() {
        try {
            GridPane dialogGrid = getGridPaneWithText();

            Text nameText = new Text(getSelectedItem().getName());
            Text surnameText = new Text(getSelectedItem().getSurname());
            Text phoneText = new Text(getSelectedItem().getPhoneNumber());
            Text peselText = new Text(getSelectedItem().getPesel());
            Text emailText = new Text(getSelectedItem().getEmail());
            Text addressText = new Text(addressModel.getAddress(getSelectedItem().getAddressID()).toString());
            JFXButton closeButton = new JFXButton("Close");

            dialogGrid.add(nameText, 1, 0);
            dialogGrid.add(surnameText, 1, 1);
            dialogGrid.add(phoneText, 1, 2);
            dialogGrid.add(peselText, 1, 3);
            dialogGrid.add(emailText, 1, 4);
            dialogGrid.add(addressText, 1, 5);

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

    @FXML private void update() {
        try {
            GridPane dialogGrid = getGridPaneWithText();
            ArrayList<TextField> textAreaArrayList = new ArrayList<>();

            TextField nameText = new TextField(getSelectedItem().getName());
            TextField surnameText = new TextField(getSelectedItem().getSurname());
            TextField phoneText = new TextField(getSelectedItem().getPhoneNumber());
            TextField peselText = new TextField(getSelectedItem().getPesel());
            TextField emailText = new TextField(getSelectedItem().getEmail());

            AlertFactory.preventInjection(nameText);
            AlertFactory.preventInjection(surnameText);
            AlertFactory.preventSpecial(emailText);
            //AlertFactory.numbersWithQuantity(peselText);
            //AlertFactory.preventSpecial();

            JFXListView<Address> addressJFXListView = new JFXListView<>();
            addressJFXListView.setMaxWidth(200);
            addressJFXListView.setMaxHeight(100);
            ObservableList<Address> addressObservableList = addressModel.getAddresses();
            addressJFXListView.setItems(addressObservableList);
            for (Address a : addressObservableList)
                if(getSelectedItem().getAddressID() == a.getAddressID())
                    addressJFXListView.getSelectionModel().select(a);

            JFXButton closeButton = new JFXButton("Close");
            JFXButton acceptButton = new JFXButton("Accept");

            textAreaArrayList.add(nameText);
            textAreaArrayList.add(surnameText);
            textAreaArrayList.add(phoneText);
            textAreaArrayList.add(peselText);
            textAreaArrayList.add(emailText);
            for (TextField t : textAreaArrayList) {
                t.setMaxWidth(200);
                t.setMaxHeight(60);
            }
            dialogGrid.add(nameText, 1, 0);
            dialogGrid.add(surnameText, 1, 1);
            dialogGrid.add(phoneText, 1, 2);
            dialogGrid.add(peselText, 1, 3);
            dialogGrid.add(emailText, 1, 4);
            dialogGrid.add(addressJFXListView, 1, 5);

            dialogGrid.setAlignment(Pos.CENTER);
            dialogGrid.setVgap(10);
            dialogGrid.setHgap(10);
            dialogGrid.setPadding(new Insets(10));

            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            dialogLayout.setHeading(new Text("Person"));
            dialogLayout.setBody(dialogGrid);
            dialogLayout.setActions(acceptButton, closeButton);

            JFXDialog viewDialog = new JFXDialog(clientsStackPane, dialogLayout, JFXDialog.DialogTransition.BOTTOM);

            acceptButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    if (getSelectedItem() == null || nameText.getText().isEmpty() || surnameText.getText().isEmpty() ||
                            phoneText.getText().isEmpty() || peselText.getText().isEmpty() || emailText.getText().isEmpty()) {
                        AlertFactory.makeFillAllFieldsError(clientsStackPane);
                    } else {
                        clientsModel.updateClient(clientsStackPane, getSelectedItem().getClientID(),
                                nameText.getText(), surnameText.getText(),
                                phoneText.getText(), peselText.getText(),
                                addressJFXListView.getSelectionModel().getSelectedItem().getAddressID(), emailText.getText());
                        refreshTable();
                        viewDialog.close();
                    }
                }
            });

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

    @FXML private void add() {
        try {
            GridPane dialogGrid = getGridPaneWithText();
            ArrayList<TextField> textAreaArrayList = new ArrayList<>();

            TextField nameText = new TextField();
            TextField surnameText = new TextField();
            TextField phoneText = new TextField();
            TextField peselText = new TextField();
            TextField emailText = new TextField();

            AlertFactory.preventInjection(nameText);
            AlertFactory.preventInjection(surnameText);
            AlertFactory.preventSpecial(emailText);
            //AlertFactory.preventSpecial();
            //AlertFactory.preventSpecial();

            JFXListView<Address> addressJFXListView = new JFXListView<>();
            addressJFXListView.setMaxWidth(200);
            addressJFXListView.setMaxHeight(100);
            ObservableList<Address> addressObservableList = addressModel.getAddresses();
            addressJFXListView.setItems(addressObservableList);

            JFXButton closeButton = new JFXButton("Close");
            JFXButton acceptButton = new JFXButton("Accept");

            textAreaArrayList.add(nameText);
            textAreaArrayList.add(surnameText);
            textAreaArrayList.add(phoneText);
            textAreaArrayList.add(peselText);
            textAreaArrayList.add(emailText);
            for (TextField t : textAreaArrayList) {
                t.setMaxWidth(200);
                t.setMaxHeight(60);
            }
            dialogGrid.add(nameText, 1, 0);
            dialogGrid.add(surnameText, 1, 1);
            dialogGrid.add(phoneText, 1, 2);
            dialogGrid.add(peselText, 1, 3);
            dialogGrid.add(emailText, 1, 4);
            dialogGrid.add(addressJFXListView, 1, 5);

            dialogGrid.setAlignment(Pos.CENTER);
            dialogGrid.setVgap(10);
            dialogGrid.setHgap(10);
            dialogGrid.setPadding(new Insets(10));

            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            dialogLayout.setHeading(new Text("Person"));
            dialogLayout.setBody(dialogGrid);
            dialogLayout.setActions(acceptButton, closeButton);

            JFXDialog viewDialog = new JFXDialog(clientsStackPane, dialogLayout, JFXDialog.DialogTransition.BOTTOM);

            acceptButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    if (getSelectedItem() == null || nameText.getText().isEmpty() || surnameText.getText().isEmpty() ||
                            phoneText.getText().isEmpty() || peselText.getText().isEmpty() || emailText.getText().isEmpty()) {
                        AlertFactory.makeFillAllFieldsError(clientsStackPane);
                    } else {
                        clientsModel.addClient(clientsStackPane,
                                nameText.getText(), surnameText.getText(),
                                phoneText.getText(), peselText.getText(),
                                addressJFXListView.getSelectionModel().getSelectedItem().getAddressID(), emailText.getText());
                        refreshTable();
                        viewDialog.close();
                    }
                }
            });

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

    @FXML private void delete() {
        clientsModel.deleteClient(clientsStackPane, getSelectedItem().getClientID());
        refreshTable();
    }
}
