package com.guitarShop.java.controllers;

import com.guitarShop.java.controllers.tabControllers.StockTabController;
import com.guitarShop.java.helpers.AlertFactory;
import com.guitarShop.java.helpers.PasswordManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class MainController {

    Stage primaryStage;
    PasswordManager passwordManager = new PasswordManager();
    @FXML private TextField loginField;
    @FXML private PasswordField passwordField;
    @FXML private TabPane tabPane;

    @FXML private void initialize() {

    }

    public void setStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        AlertFactory.preventSpecial(loginField);
        AlertFactory.preventSpecial(passwordField);
    }

    @FXML
    private void login() throws IOException {
        tabPane = FXMLLoader.load(getClass().getResource("/com/guitarShop/resources/loggedIn.fxml"));
        this.primaryStage.setScene(new Scene(tabPane));
        primaryStage.setResizable(false);

        tabPane.getSelectionModel().selectedIndexProperty().addListener((obs, oldValue, newValue) -> {
            var selectedIndex = (int) newValue;
            switch (selectedIndex) {
                case 0 -> {
                    //System.out.println(selectedIndex);
                }
            }
        });
    }

    @FXML
    private void checkEnteredCredentials() throws IOException {
        String login = loginField.getText();
        String password = passwordField.getText();
        if (passwordManager.checkCredentials(login, password))
            login();
    }
}
