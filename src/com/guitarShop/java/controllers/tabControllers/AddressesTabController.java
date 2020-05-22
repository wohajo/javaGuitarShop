package com.guitarShop.java.controllers.tabControllers;

import com.guitarShop.java.models.AddressModel;
import com.guitarShop.java.models.objects.Address;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;

public class AddressesTabController {

    @FXML private JFXTreeTableView<Address> addressTable;
    @FXML private TreeTableColumn<Address, String> cityCol;
    @FXML private TreeTableColumn<Address, String> postcodeCol;
    @FXML private TreeTableColumn<Address, String> streetCol;
    @FXML private TreeTableColumn<Address, Integer> buildingCol;
    @FXML private TreeTableColumn<Address, Integer> flatCol;
    private AddressModel addressModel = new AddressModel();
    private TreeItem<Address> root = new TreeItem<>();

    @FXML void initialize() {
        initTable();
    }

    @FXML private void initTable() {
        cityCol.setCellValueFactory(new TreeItemPropertyValueFactory<Address, String>("city"));
        postcodeCol.setCellValueFactory(new TreeItemPropertyValueFactory<Address, String>("postcode"));
        streetCol.setCellValueFactory(new TreeItemPropertyValueFactory<Address, String>("street"));
        buildingCol.setCellValueFactory(new TreeItemPropertyValueFactory<Address, Integer>("buildingNumber"));
        flatCol.setCellValueFactory(new TreeItemPropertyValueFactory<Address, Integer>("flatNumber"));

        ObservableList<Address> addresses = FXCollections.observableArrayList();
        addresses.addAll(addressModel.getAddresses());
        for(int i = 0; i < addresses.size(); i ++)
            root.getChildren().add(new TreeItem<>(addresses.get(i)));

        addressTable.getColumns().setAll(cityCol, postcodeCol, streetCol, buildingCol, flatCol);
        addressTable.setRoot(root);
        addressTable.setShowRoot(false);
    }

}
