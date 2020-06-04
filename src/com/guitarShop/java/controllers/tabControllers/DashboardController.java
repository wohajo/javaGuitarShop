package com.guitarShop.java.controllers.tabControllers;

import com.guitarShop.java.controllers.MainController;
import com.guitarShop.java.models.SellersModel;
import com.guitarShop.java.models.objects.Seller;
import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;

public class DashboardController {

    private SellersModel sellersModel = new SellersModel();
    private Seller loggedUser;
    @FXML JFXTextArea nameField;
    @FXML JFXTextArea surnameField;
    @FXML JFXTextArea emailField;

    @FXML
    private void initialize() {
        loggedUser = sellersModel.getSellerByEmail(MainController.loggedUserLogin);
        nameField.setText(loggedUser.getName());
        surnameField.setText(loggedUser.getSurname());
        emailField.setText(loggedUser.getEmail());
    }

    @FXML private void changeEmail() {

    }

    @FXML private void changePassword() {

    }
}
