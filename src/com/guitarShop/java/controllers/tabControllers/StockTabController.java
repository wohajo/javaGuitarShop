package com.guitarShop.java.controllers.tabControllers;

import com.guitarShop.java.helpers.AlertFactory;
import com.guitarShop.java.models.objects.Guitar;
import com.guitarShop.java.models.StockModel;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.sql.SQLException;
import java.util.ArrayList;

public class StockTabController {

    @FXML private StackPane stockStackPane;
    @FXML private JFXTreeTableView<Guitar> stockTable;
    @FXML private TreeTableColumn<Guitar, String> manufacturerCol;
    @FXML private TreeTableColumn<Guitar, String> modelCol;
    @FXML private TreeTableColumn<Guitar, Double> priceCol;
    @FXML private TreeTableColumn<Guitar, String> typeCol;
    @FXML private TreeTableColumn<Guitar, String> pickupsCol;
    @FXML private TreeTableColumn<Guitar, String> bridgeCol;
    @FXML private TreeTableColumn<Guitar, Integer> stringsCol;
    @FXML private TreeTableColumn<Guitar, String> tunersCol;
    @FXML private TreeTableColumn<Guitar, String> quantityCol;
    private AlertFactory alertFactory = new AlertFactory();
    private StockModel stockModel = new StockModel();
    private TreeItem<Guitar> root = new TreeItem<>();

    @FXML void initialize() throws SQLException {
        initTable();
    }

    @FXML
    public void initTable() throws SQLException {
        manufacturerCol.setCellValueFactory(new TreeItemPropertyValueFactory<Guitar, String>("manufacturer"));
        modelCol.setCellValueFactory(new TreeItemPropertyValueFactory<Guitar, String>("model"));
        priceCol.setCellValueFactory(new TreeItemPropertyValueFactory<Guitar, Double>("guitarPrice"));
        typeCol.setCellValueFactory(new TreeItemPropertyValueFactory<Guitar, String>("guitarType"));
        pickupsCol.setCellValueFactory(new TreeItemPropertyValueFactory<Guitar, String>("pickupsType"));
        bridgeCol.setCellValueFactory(new TreeItemPropertyValueFactory<Guitar, String>("bridgeType"));
        stringsCol.setCellValueFactory(new TreeItemPropertyValueFactory<Guitar, Integer>("numberOfStrings"));
        tunersCol.setCellValueFactory(new TreeItemPropertyValueFactory<Guitar, String>("lockingTuners"));
        quantityCol.setCellValueFactory(new TreeItemPropertyValueFactory<Guitar, String>("numberOfGuitars"));

        ObservableList<Guitar> stock = FXCollections.observableArrayList();
        stock.addAll(stockModel.getStock());

        for(int i = 0; i < stock.size(); i++)
            root.getChildren().add(new TreeItem<Guitar>(stock.get(i)));
        stockTable.getColumns().setAll(manufacturerCol, modelCol, priceCol, typeCol, pickupsCol,
                bridgeCol, stringsCol, tunersCol, quantityCol);
        stockTable.setRoot(root);
        stockTable.setShowRoot(false);
    }

    @FXML
    public void view() {
        try {

            GridPane dialogGrid = new GridPane();

            Label manufacturerLabel = new Label("Manufacturer");
            Label modelLabel = new Label("Model");
            Label priceLabel = new Label("Price");
            Label typeLabel = new Label("Type");
            Label pickupsLabel = new Label("Pickups");
            Label bridgeLabel = new Label("Bridge");
            Label tunersLabel = new Label("Tuners");
            Label quantityLabel = new Label("Quantity");
            Label descLabel = new Label("Description");
            Label stringsLabel = new Label("Strings");

            Text manufacturerText = new Text(getSelectedItem().getManufacturer());
            Text modelText = new Text(getSelectedItem().getModel());
            Text priceText = new Text(String.valueOf(getSelectedItem().getGuitarPrice()));
            Text typeText = new Text(getSelectedItem().getGuitarType());
            Text pickupsText = new Text(getSelectedItem().getPickupsType());
            Text bridgeText = new Text(getSelectedItem().getBridgeType());
            Text tunersText = new Text(String.valueOf(getSelectedItem().getLockingTuners()));
            Text quantityText = new Text(String.valueOf(getSelectedItem().getNumberOfGuitars()));
            TextArea descText = new TextArea(getSelectedItem().getModelDescription());
            descText.setMaxWidth(200);
            descText.setMaxWidth(200);
            descText.setEditable(false);
            Text stringsText = new Text(String.valueOf(getSelectedItem().getNumberOfStrings()));

            JFXButton closeButton = new JFXButton("close");

            dialogGrid.add(manufacturerLabel, 0, 0);
            dialogGrid.add(modelLabel, 0,1);
            dialogGrid.add(priceLabel, 0 , 2);
            dialogGrid.add(typeLabel, 0, 3);
            dialogGrid.add(pickupsLabel, 0, 4);
            dialogGrid.add(bridgeLabel, 0, 5);
            dialogGrid.add(tunersLabel, 0, 6);
            dialogGrid.add(quantityLabel, 0, 7);
            dialogGrid.add(descLabel, 0, 8);
            dialogGrid.add(stringsLabel, 0, 9);

            dialogGrid.add(manufacturerText, 1, 0);
            dialogGrid.add(modelText, 1,1);
            dialogGrid.add(priceText, 1, 2);
            dialogGrid.add(typeText, 1, 3);
            dialogGrid.add(pickupsText, 1, 4);
            dialogGrid.add(bridgeText, 1, 5);
            dialogGrid.add(tunersText, 1, 6);
            dialogGrid.add(quantityText, 1, 7);
            dialogGrid.add(descText, 1, 8);
            dialogGrid.add(stringsText, 1, 9);

            dialogGrid.setAlignment(Pos.CENTER);
            dialogGrid.setVgap(10);
            dialogGrid.setHgap(10);

            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            dialogLayout.setHeading(new Text("Guitar"));
            dialogLayout.setBody(dialogGrid);
            dialogLayout.setActions(closeButton);

            JFXDialog viewDialog = new JFXDialog(stockStackPane, dialogLayout, JFXDialog.DialogTransition.BOTTOM);

            closeButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    viewDialog.close();
                }
            });
            viewDialog.show();

        } catch (NullPointerException e) {
            alertFactory.makeAlertDialog(stockStackPane, "Error", "Choose a guitar to display informations.", "Close");
        }
    }

    private Guitar getSelectedItem() {
        return stockTable.getSelectionModel().getSelectedItem().getValue();
    }
}
