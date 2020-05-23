package com.guitarShop.java.controllers.tabControllers;

import com.guitarShop.java.helpers.AlertFactory;
import com.guitarShop.java.models.SellersModel;
import com.guitarShop.java.models.objects.Seller;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class SellersTabController {

    @FXML StackPane sellersStackPane;
    @FXML JFXTreeTableView<Seller> sellersTable;
    @FXML TreeTableColumn<Seller, String> nameCol;
    @FXML TreeTableColumn<Seller, String> surnameCol;
    @FXML TreeTableColumn<Seller, String> phoneCol;
    @FXML TreeTableColumn<Seller, String> peselCol;
    @FXML TreeTableColumn<Seller, String> emailCol;
    private AlertFactory alertFactory = new AlertFactory();
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

    @FXML private void view() {
        try {
            GridPane dialogGrid = new GridPane();

            Label nameLabel = new Label("Name: ");
            Label surnameLabel = new Label("Surname: ");
            Label phoneLabel = new Label("Phone: ");
            Label peselLabel = new Label("Pesel: ");
            Label emailLabel = new Label("Email: ");
            Text nameText = new Text(sellersTable.getSelectionModel().getSelectedItem().getValue().getName());
            Text surnameText = new Text(sellersTable.getSelectionModel().getSelectedItem().getValue().getSurname());
            Text phoneText = new Text(sellersTable.getSelectionModel().getSelectedItem().getValue().getPhoneNumber());
            Text peselText = new Text(sellersTable.getSelectionModel().getSelectedItem().getValue().getPesel());
            Text emailText = new Text(sellersTable.getSelectionModel().getSelectedItem().getValue().getEmail());
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
            dialogLayout.setHeading(new Text("Seller"));
            dialogLayout.setBody(dialogGrid);
            dialogLayout.setActions(closeButton);

            JFXDialog viewDialog = new JFXDialog(sellersStackPane, dialogLayout, JFXDialog.DialogTransition.BOTTOM);

            closeButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    viewDialog.close();
                }
            });
            viewDialog.show();

        } catch (NullPointerException e) {
            alertFactory.makeAlertDialog(sellersStackPane, "Error", "Choose a person to display informations.", "Close");
        }
    }
}
