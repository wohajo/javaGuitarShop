package com.guitarShop.java.controllers.tabControllers;

import com.guitarShop.java.controllers.MainController;
import com.guitarShop.java.models.ClientsModel;
import com.guitarShop.java.models.objects.Client;
import com.jfoenix.controls.*;
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

    @FXML void initialize() throws IOException {
        ClientsModel clientsModel = new ClientsModel();

        nameCol.setCellValueFactory(new TreeItemPropertyValueFactory<Client, String>("name"));

        ObservableList<Client> clients = FXCollections.observableArrayList();
        clients.addAll(clientsModel.getClients());

        for(int i = 0; i < clients.size(); i++)
            root.getChildren().add(new TreeItem<>(clients.get(i)));
        testTable.getColumns().setAll(nameCol);
        testTable.setRoot(root);
        testTable.setShowRoot(false);

        /*FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/guitarShop/resources/login.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        MainController controller = fxmlLoader.getController();
        controller.print("Test controller up");*/
    }

    @FXML
    public void add() {
        root.getChildren().add(new TreeItem<>(new Client(5, "RRRRR", "cgh", "888", "333", "333", 1)));

        /*JFXDialogLayout content = new JFXDialogLayout();
        content.setStyle("-fx-background-color: black");
        content.setHeading(new Text("TEST"));
        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(new Text("test"), new JFXButton("ssss"), new JFXButton("aaa"));
        content.setBody(vBox);
        content.setActions(new JFXButton("test"), new JFXButton("test2"));
        JFXDialog dialog2 = new JFXDialog(testStackPane, content, JFXDialog.DialogTransition.BOTTOM);
        dialog2.show();*/

    }
    @FXML
    public void get() throws IOException {
        System.out.println(testTable.getSelectionModel().getSelectedItem().getValue().getName());
        Parent parent = FXMLLoader.load(getClass().getResource("/com/guitarShop/resources/tabs/dialog.fxml"));
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        dialogLayout.setBody(parent);
        JFXDialog dialog = new JFXDialog(testStackPane, dialogLayout, JFXDialog.DialogTransition.LEFT);
        dialog.setPrefSize(300, 300);
        dialog.show();

    }

    public String getName() {
        return (testTable.getSelectionModel().getSelectedItem().getValue().name);
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

