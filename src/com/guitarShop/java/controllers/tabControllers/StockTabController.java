package com.guitarShop.java.controllers.tabControllers;

import com.guitarShop.java.helpers.AlertFactory;
import com.guitarShop.java.models.ManufacturerModel;
import com.guitarShop.java.models.PartsModel;
import com.guitarShop.java.models.objects.Guitar;
import com.guitarShop.java.models.StockModel;
import com.guitarShop.java.models.objects.Manufacturer;
import com.guitarShop.java.models.objects.parts.Bridge;
import com.guitarShop.java.models.objects.parts.GuitarType;
import com.guitarShop.java.models.objects.parts.Pickups;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import java.sql.SQLException;
import java.util.ArrayList;

public class StockTabController {

    @FXML private StackPane stockStackPane;
    @FXML private TableView<Guitar> stockTable;
    @FXML private TableColumn<Guitar, String> manufacturerCol;
    @FXML private TableColumn<Guitar, String> modelCol;
    @FXML private TableColumn<Guitar, Double> priceCol;
    @FXML private TableColumn<Guitar, String> typeCol;
    @FXML private TableColumn<Guitar, String> quantityCol;
    @FXML private TextField manSearchText;
    @FXML private TextField modelSearchText;
    @FXML private TextField typeSearchText;
    @FXML private TextField minPriceText;
    @FXML private TextField maxPriceText;
    @FXML private TextField minCountText;
    @FXML private TextField maxCountText;

    private AlertFactory alertFactory = new AlertFactory();
    private StockModel stockModel = new StockModel();
    private PartsModel partsModel = new PartsModel();
    private ManufacturerModel manufacturerModel = new ManufacturerModel();

    @FXML private void initialize() {
        initTable();
    }

    @FXML
    public void initTable() {
        manufacturerCol.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        modelCol.setCellValueFactory(new PropertyValueFactory<>("model"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("guitarPrice"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("guitarType"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("numberOfGuitars"));

        ObservableList<Guitar> stock = FXCollections.observableArrayList();
        stock.addAll(stockModel.getStock());
        FilteredList<Guitar> filteredStock = new FilteredList<>(stock, p -> true);

        initSearchFields(filteredStock);

        SortedList<Guitar> sortedList = new SortedList<>(filteredStock);
        sortedList.comparatorProperty().bind(stockTable.comparatorProperty());
        stockTable.setItems(sortedList);
        stockTable.getColumns().setAll(manufacturerCol, modelCol, priceCol, typeCol, quantityCol);
    }

    private void initSearchFields(FilteredList<Guitar> filteredList) {
        manSearchText.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(Guitar -> {
                if(newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowercaseFilter = newValue.toLowerCase();
                if(Guitar.getManufacturer().toLowerCase().contains(lowercaseFilter))
                    return true;

                return false;
            });
        });
        modelSearchText.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(Guitar -> {
                if(newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowercaseFilter = newValue.toLowerCase();
                if(Guitar.getModel().toLowerCase().contains(lowercaseFilter))
                    return true;

                return false;
            });
        });
        typeSearchText.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(Guitar -> {
                if(newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowercaseFilter = newValue.toLowerCase();
                if(Guitar.getGuitarType().toLowerCase().contains(lowercaseFilter))
                    return true;

                return false;
            });
        });
        minCountText.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(Guitar -> {
                if(newValue == null || newValue.isEmpty()) {
                    return true;
                }
                int filter = Integer.parseInt(newValue);
                if(Guitar.getNumberOfGuitars() >= filter && maxCountText.getText().isEmpty()) {
                    return true;
                } else {
                    if(Guitar.getNumberOfGuitars() >= filter && Guitar.getNumberOfGuitars() <= Integer.parseInt(maxCountText.getText()))
                        return true;
                }
                return false;
            });
        });
        maxCountText.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(Guitar -> {
                if(newValue == null || newValue.isEmpty()) {
                    return true;
                }
                int filter = Integer.parseInt(newValue);
                if(Guitar.getNumberOfGuitars() <= filter) {
                    if (minCountText.getText().isEmpty()) {
                        return true;
                    } else {
                        if (Guitar.getNumberOfGuitars() <= filter && Guitar.getNumberOfGuitars() >= Integer.parseInt(minCountText.getText()))
                            return true;
                    }
                }
                return false;
            });
        });
        minPriceText.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(Guitar -> {
                if(newValue == null || newValue.isEmpty()) {
                    return true;
                }
                int filter = Integer.parseInt(newValue);
                if(Guitar.getGuitarPrice() >= filter && maxPriceText.getText().isEmpty()) {
                    return true;
                } else {
                    if(Guitar.getGuitarPrice() >= filter && Guitar.getGuitarPrice() <= Integer.parseInt(maxPriceText.getText()))
                        return true;
                }
                return false;
            });
        });
        maxPriceText.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(Guitar -> {
                if(newValue == null || newValue.isEmpty()) {
                    return true;
                }
                int filter = Integer.parseInt(newValue);
                if(Guitar.getGuitarPrice() <= filter) {
                    if (minPriceText.getText().isEmpty()) {
                        return true;
                    } else {
                        if (Guitar.getGuitarPrice() <= filter && Guitar.getGuitarPrice() >= Integer.parseInt(minPriceText.getText()))
                            return true;
                    }
                }
                return false;
            });
        });
    }

    public void refreshTable() {
        initTable();
    }

    private GridPane getGridPaneWithText() {
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

        dialogGrid.setAlignment(Pos.CENTER);
        dialogGrid.setVgap(10);
        dialogGrid.setHgap(10);

        return dialogGrid;
    }

    private Guitar getSelectedItem() {
        return stockTable.getSelectionModel().getSelectedItem();
    }

    @FXML public void view() {
        try {
            GridPane dialogGrid = getGridPaneWithText();

            Text manufacturerText = new Text(getSelectedItem().getManufacturer());
            Text modelText = new Text(getSelectedItem().getModel());
            Text priceText = new Text(String.valueOf(getSelectedItem().getGuitarPrice()));
            Text typeText = new Text(getSelectedItem().getGuitarType());
            Text pickupsText = new Text(getSelectedItem().getPickupsType());
            Text bridgeText = new Text(getSelectedItem().getBridgeType());
            Text tunersText = new Text(getSelectedItem().getLockingTuners());
            Text quantityText = new Text(String.valueOf(getSelectedItem().getNumberOfGuitars()));
            TextArea descText = new TextArea(getSelectedItem().getModelDescription());
            descText.setMaxWidth(200);
            descText.setMaxHeight(130);
            descText.setEditable(false);
            Text stringsText = new Text(String.valueOf(getSelectedItem().getNumberOfStrings()));
            JFXButton closeButton = new JFXButton("close");

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

    @FXML public void update() {
        try {
            GridPane dialogGrid = getGridPaneWithText();
            ArrayList<TextField> textFields = new ArrayList<>();

            JFXListView<Manufacturer> manufacturerJFXListView = new JFXListView<>();
            manufacturerJFXListView.setMaxWidth(200);
            manufacturerJFXListView.setMaxHeight(50);
            ObservableList<Manufacturer> manufacturerObservableList = manufacturerModel.getManufacturers();
            manufacturerJFXListView.setItems(manufacturerObservableList);
            for (Manufacturer m : manufacturerObservableList)
                if(getSelectedItem().getManufacturerID() == m.getManufacturerID())
                    manufacturerJFXListView.getSelectionModel().select(m);

            JFXListView<GuitarType> guitarTypeJFXListView = new JFXListView<>();
            guitarTypeJFXListView.setMaxWidth(200);
            guitarTypeJFXListView.setMaxHeight(50);
            ObservableList<GuitarType> guitarTypeObservableList = partsModel.getGuitarTypes();
            guitarTypeJFXListView.setItems(guitarTypeObservableList);
            for (GuitarType gt : guitarTypeObservableList)
                if(getSelectedItem().getGuitarTypeID() == gt.getTypeID())
                    guitarTypeJFXListView.getSelectionModel().select(gt);

            JFXListView<Pickups> pickupsJFXListView = new JFXListView<>();
            pickupsJFXListView.setMaxWidth(200);
            pickupsJFXListView.setMaxHeight(50);
            ObservableList<Pickups> pickupsObservableList = partsModel.getPickups();
            pickupsJFXListView.setItems(pickupsObservableList);
            for (Pickups p : pickupsObservableList)
                if(getSelectedItem().getPickupsTypeID() == p.getPickupsID())
                    pickupsJFXListView.getSelectionModel().select(p);

            JFXListView<Bridge> bridgeJFXListView = new JFXListView<>();
            bridgeJFXListView.setMaxWidth(200);
            bridgeJFXListView.setMaxHeight(50);
            ObservableList<Bridge> bridgeObservableList = partsModel.getBridges();
            bridgeJFXListView.setItems(bridgeObservableList);
            for (Bridge b : bridgeObservableList)
                if(getSelectedItem().getBridgeTypeID() == b.getBridgeID())
                    bridgeJFXListView.getSelectionModel().select(b);

            TextField modelText = new TextField(getSelectedItem().getModel());
            TextField priceText = new TextField(String.valueOf(getSelectedItem().getGuitarPrice()));

            JFXToggleButton lockingTunersToggle = new JFXToggleButton();
            lockingTunersToggle.setText("Locking");
            if(getSelectedItem().getLockingTuners().equals("Locking"))
                lockingTunersToggle.setSelected(true);

            TextField quantityText = new TextField(String.valueOf(getSelectedItem().getNumberOfGuitars()));
            TextField descText = new TextField(getSelectedItem().getModelDescription());
            TextField stringsText = new TextField(String.valueOf(getSelectedItem().getNumberOfStrings()));

            textFields.add(modelText);
            textFields.add(priceText);
            textFields.add(quantityText);
            textFields.add(descText);
            textFields.add(stringsText);

            for (TextField t : textFields) {
                t.setMaxWidth(200);
                t.setMaxHeight(50);
            }

            dialogGrid.add(manufacturerJFXListView, 1, 0);
            dialogGrid.add(modelText, 1,1);
            dialogGrid.add(priceText, 1, 2);
            dialogGrid.add(guitarTypeJFXListView, 1, 3);
            dialogGrid.add(pickupsJFXListView, 1, 4);
            dialogGrid.add(bridgeJFXListView, 1, 5);
            dialogGrid.add(lockingTunersToggle, 1, 6);
            dialogGrid.add(quantityText, 1, 7);
            dialogGrid.add(descText, 1, 8);
            dialogGrid.add(stringsText, 1, 9);

            JFXButton closeButton = new JFXButton("Close");
            JFXButton acceptButton = new JFXButton("Accept");
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            dialogLayout.setHeading(new Text("Guitar"));
            dialogLayout.setBody(dialogGrid);
            dialogLayout.setActions(acceptButton, closeButton);

            JFXDialog viewDialog = new JFXDialog(stockStackPane, dialogLayout, JFXDialog.DialogTransition.BOTTOM);

            closeButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    viewDialog.close();
                }
            });

            acceptButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    if (modelText.getText() == "" || descText.getText() == "" || stringsText.getText() == "" || priceText.getText() == "" || quantityText.getText() == ""
                            || manufacturerJFXListView.getSelectionModel().getSelectedItem() == null || guitarTypeJFXListView.getSelectionModel().getSelectedItem() == null ||
                            pickupsJFXListView.getSelectionModel().getSelectedItem() == null || bridgeJFXListView.getSelectionModel().getSelectedItem() == null) {
                        AlertFactory.makeFillAllFieldsError(stockStackPane);
                    } else {
                        try {
                            int guitarID = getSelectedItem().getGuitarID();
                            int manufacturerID = manufacturerJFXListView.getSelectionModel().getSelectedItem().getManufacturerID();
                            String model = modelText.getText();
                            String modelDesc = descText.getText();
                            int numbersOfStrings = Integer.valueOf(stringsText.getText());
                            int guitarPrice = Integer.valueOf(priceText.getText());
                            int guitarTypeID = guitarTypeJFXListView.getSelectionModel().getSelectedItem().getTypeID();
                            int pickupsTypeID = pickupsJFXListView.getSelectionModel().getSelectedItem().getPickupsID();
                            int bridgeTypeID = bridgeJFXListView.getSelectionModel().getSelectedItem().getBridgeID();
                            Boolean lockingTuners = lockingTunersToggle.isSelected();
                            int quantity = Integer.valueOf(quantityText.getText());

                            try {
                                stockModel.updateGuitar(stockStackPane, guitarID, manufacturerID, model, modelDesc, numbersOfStrings, guitarPrice,
                                        guitarTypeID, pickupsTypeID, bridgeTypeID, lockingTuners, quantity);
                                refreshTable();
                                viewDialog.close();
                            } catch (SQLException e) {
                                alertFactory.makeAlertDialog(stockStackPane, "Error", "Error updating guitar.", "Close");
                            }
                        } catch (NumberFormatException e) {
                            AlertFactory.makeNotNumberError(stockStackPane);
                        }
                    }
                }
            });

            viewDialog.show();

        } catch (NullPointerException e) {
            alertFactory.makeAlertDialog(stockStackPane, "Error", "Choose a guitar to edit informations.", "Close");
        }
    }

    @FXML public void add() {
        try {
            GridPane dialogGrid = getGridPaneWithText();
            ArrayList<TextField> textFields = new ArrayList<>();

            JFXListView<Manufacturer> manufacturerJFXListView = new JFXListView<>();
            manufacturerJFXListView.setMaxWidth(200);
            manufacturerJFXListView.setMaxHeight(50);
            ObservableList<Manufacturer> manufacturerObservableList = manufacturerModel.getManufacturers();
            manufacturerJFXListView.setItems(manufacturerObservableList);

            JFXListView<GuitarType> guitarTypeJFXListView = new JFXListView<>();
            guitarTypeJFXListView.setMaxWidth(200);
            guitarTypeJFXListView.setMaxHeight(50);
            ObservableList<GuitarType> guitarTypeObservableList = partsModel.getGuitarTypes();
            guitarTypeJFXListView.setItems(guitarTypeObservableList);

            JFXListView<Pickups> pickupsJFXListView = new JFXListView<>();
            pickupsJFXListView.setMaxWidth(200);
            pickupsJFXListView.setMaxHeight(50);
            ObservableList<Pickups> pickupsObservableList = partsModel.getPickups();
            pickupsJFXListView.setItems(pickupsObservableList);

            JFXListView<Bridge> bridgeJFXListView = new JFXListView<>();
            bridgeJFXListView.setMaxWidth(200);
            bridgeJFXListView.setMaxHeight(50);
            ObservableList<Bridge> bridgeObservableList = partsModel.getBridges();
            bridgeJFXListView.setItems(bridgeObservableList);

            TextField modelText = new TextField();
            TextField priceText = new TextField();

            JFXToggleButton lockingTunersToggle = new JFXToggleButton();
            lockingTunersToggle.setText("Locking");

            TextField quantityText = new TextField();
            TextField descText = new TextField();
            TextField stringsText = new TextField();

            textFields.add(modelText);
            textFields.add(priceText);
            textFields.add(quantityText);
            textFields.add(descText);
            textFields.add(stringsText);

            for (TextField t : textFields) {
                t.setMaxWidth(200);
                t.setMaxHeight(50);
            }

            dialogGrid.add(manufacturerJFXListView, 1, 0);
            dialogGrid.add(modelText, 1,1);
            dialogGrid.add(priceText, 1, 2);
            dialogGrid.add(guitarTypeJFXListView, 1, 3);
            dialogGrid.add(pickupsJFXListView, 1, 4);
            dialogGrid.add(bridgeJFXListView, 1, 5);
            dialogGrid.add(lockingTunersToggle, 1, 6);
            dialogGrid.add(quantityText, 1, 7);
            dialogGrid.add(descText, 1, 8);
            dialogGrid.add(stringsText, 1, 9);

            JFXButton closeButton = new JFXButton("Close");
            JFXButton acceptButton = new JFXButton("Accept");
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            dialogLayout.setHeading(new Text("Guitar"));
            dialogLayout.setBody(dialogGrid);
            dialogLayout.setActions(acceptButton, closeButton);

            JFXDialog viewDialog = new JFXDialog(stockStackPane, dialogLayout, JFXDialog.DialogTransition.BOTTOM);

            closeButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    viewDialog.close();
                }
            });

            acceptButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    if (modelText.getText() == "" || descText.getText() == "" || stringsText.getText() == "" || priceText.getText() == "" || quantityText.getText() == ""
                    || manufacturerJFXListView.getSelectionModel().getSelectedItem() == null || guitarTypeJFXListView.getSelectionModel().getSelectedItem() == null ||
                            pickupsJFXListView.getSelectionModel().getSelectedItem() == null || bridgeJFXListView.getSelectionModel().getSelectedItem() == null) {
                        AlertFactory.makeFillAllFieldsError(stockStackPane);
                    } else {
                        int manufacturerID = manufacturerJFXListView.getSelectionModel().getSelectedItem().getManufacturerID();
                        String model = modelText.getText();
                        String modelDesc = descText.getText();
                        int numbersOfStrings = Integer.valueOf(stringsText.getText());
                        int guitarPrice = Integer.valueOf(priceText.getText());
                        int guitarTypeID = guitarTypeJFXListView.getSelectionModel().getSelectedItem().getTypeID();
                        int pickupsTypeID = pickupsJFXListView.getSelectionModel().getSelectedItem().getPickupsID();
                        int bridgeTypeID = bridgeJFXListView.getSelectionModel().getSelectedItem().getBridgeID();
                        Boolean lockingTuners = lockingTunersToggle.isSelected();
                        int quantity = Integer.valueOf(quantityText.getText());
                        stockModel.addGuitar(stockStackPane, manufacturerID, model, modelDesc, numbersOfStrings, guitarPrice,
                                guitarTypeID, pickupsTypeID, bridgeTypeID, lockingTuners, quantity);
                        refreshTable();
                        viewDialog.close();
                    }
                }
            });

            viewDialog.show();

        } catch (NullPointerException e) {
            alertFactory.makeAlertDialog(stockStackPane, "Error", "Choose a guitar to display informations.", "Close");
        }
    }

    @FXML public void delete() throws SQLException {
        if (getSelectedItem() == null) {
            AlertFactory.makeItemNotChoosenDialog(stockStackPane);
        } else {
            stockModel.deleteGuitar(stockStackPane, getSelectedItem().getGuitarID());
            refreshTable();
        }
    }
}
