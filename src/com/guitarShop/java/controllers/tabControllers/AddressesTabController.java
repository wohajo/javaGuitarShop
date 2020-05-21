package com.guitarShop.java.controllers.tabControllers;

import com.guitarShop.java.models.AddressModel;
import com.guitarShop.java.models.objects.Address;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class AddressesTabController {

    @FXML private TableView<Address> addressTable;
    @FXML private TableColumn<Address, String> cityCol;
    @FXML private TableColumn<Address, String> postcodeCol;
    @FXML private TableColumn<Address, String> streetCol;
    @FXML private TableColumn<Address, Integer> buildingCol;
    @FXML private TableColumn<Address, Integer> flatCol;
    private AddressModel addressModel = new AddressModel();

    @FXML void initialize() throws SQLException {
        initTable();
    }

    @FXML private void initTable() {
        cityCol.setCellValueFactory(new PropertyValueFactory<Address, String>("city"));
        postcodeCol.setCellValueFactory(new PropertyValueFactory<Address, String>("postcode"));
        streetCol.setCellValueFactory(new PropertyValueFactory<Address, String>("street"));
        buildingCol.setCellValueFactory(new PropertyValueFactory<Address, Integer>("buildingNumber"));
        flatCol.setCellValueFactory(new PropertyValueFactory<Address, Integer>("flatNumber"));
        this.addressTable.setItems(addressModel.getAddresses());
    }

}
