package com.guitarShop.java.controllers;

import com.guitarShop.java.helpers.PasswordManager;
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
    @FXML private TextField loginField;
    @FXML private TextField passwordField;

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
