package com.guitarShop.java.controllers.tabControllers;

import com.guitarShop.java.models.ClientsModel;
import com.guitarShop.java.models.objects.Client;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class TestTabController {

    @FXML
    private JFXTreeTableView<Client> testTable;
    @FXML
    private TreeTableColumn<Client, String> nameCol;
    @FXML
    private StackPane testStackPane;
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
    public void get() throws IOException {
        String name = (testTable.getSelectionModel().getSelectedItem().getValue().getName());

        Parent parent = FXMLLoader.load(getClass().getResource("/com/guitarShop/resources/tabs/dialogs/testDialog.fxml"));
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        dialogLayout.setBody(parent);
        JFXDialog dialog = new JFXDialog(testStackPane, dialogLayout, JFXDialog.DialogTransition.LEFT);
        dialog.setPrefSize(300, 300);
        dialog.show();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/guitarShop/resources/tabs/dialogs/testDialog.fxml"));
        Parent root = loader.load();
        TestDialogController testTabController = loader.<TestDialogController>getController();
        testTabController.setTestButton(name);
    }

    public String getName() {
        return (testTable.getSelectionModel().getSelectedItem().getValue().getName());
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

    public void dialogTest(String input) {
        System.out.println(input + "works!");
    }

}

