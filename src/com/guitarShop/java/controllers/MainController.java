package com.guitarShop.java.controllers;

import com.guitarShop.java.controllers.tabControllers.ClientsTabController;
import com.guitarShop.java.controllers.tabControllers.OrdersTabController;
import com.guitarShop.java.controllers.tabControllers.StockTabController;
import com.guitarShop.java.controllers.tabControllers.WorkersTabController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class MainController {

    Stage primaryStage;
    PasswordManager passwordManager = new PasswordManager();
    @FXML StockTabController stockTabController;
    @FXML OrdersTabController ordersTabController;
    @FXML WorkersTabController workersTabController;
    @FXML ClientsTabController clientsTabController;
    @FXML TextField loginField;
    @FXML TextField passwordField;

    public MainController() {}

    public void setStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private void login() throws IOException {
        Parent loggedIn = FXMLLoader.load(getClass().getResource("/com/guitarShop/resources/loggedIn.fxml"));
        this.primaryStage.setScene(new Scene(loggedIn));
        primaryStage.setResizable(false);
    }

    @FXML
    private void checkEnteredCredentials() throws IOException {
        String login = loginField.getText();
        String password = passwordField.getText();
        if (passwordManager.checkCredentials(login, password))
            login();
    }
}
