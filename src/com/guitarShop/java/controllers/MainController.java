package com.guitarShop.java.controllers;

import com.guitarShop.java.helpers.AlertFactory;
import com.guitarShop.java.helpers.PasswordManager;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;

public class MainController {

    public static String loggedUserLogin;
    Stage primaryStage;
    private PasswordManager passwordManager = new PasswordManager();
    @FXML private TextField loginField;
    @FXML private PasswordField passwordField;
    @FXML private TabPane tabPane;
    @FXML private StackPane loginStackPane;

    @FXML void initialize() {

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
        if (passwordManager.checkCredentials(login, password, loginStackPane)) {
            login();
            loggedUserLogin = login;
        }
    }

    @FXML private void forgotPassword() {
        GridPane dialogGrid = getGridPaneWithText();

        TextField peselText = new TextField();
        TextField emailText = new TextField();
        TextField password1 = new TextField();
        TextField password2 = new TextField();

        AlertFactory.restrictToNumbers(peselText);
        AlertFactory.preventSpecial(emailText);
        AlertFactory.preventSpecial(passwordField);
        AlertFactory.preventSpecial(passwordField);

        JFXButton closeButton = new JFXButton("Close");
        JFXButton acceptButton = new JFXButton("Accept");

        dialogGrid.add(peselText, 1, 0);
        dialogGrid.add(emailText, 1, 1);
        dialogGrid.add(password1, 1, 2);
        dialogGrid.add(password2, 1, 3);
        dialogGrid.add(acceptButton, 1, 4);

        dialogGrid.setAlignment(Pos.CENTER);
        dialogGrid.setVgap(10);
        dialogGrid.setHgap(10);
        dialogGrid.setPadding(new Insets(10));

        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        dialogLayout.setHeading(new Text("Change password"));
        dialogLayout.setBody(dialogGrid);

        dialogLayout.setActions(acceptButton, closeButton);

        JFXDialog viewDialog = new JFXDialog(loginStackPane, dialogLayout, JFXDialog.DialogTransition.BOTTOM);

        acceptButton.setOnAction(e -> {
            passwordManager.changePassword(viewDialog, loginStackPane, peselText.getText(), emailText.getText(), password1.getText(), password2.getText());
        });

        closeButton.setOnAction(actionEvent -> viewDialog.close());
        viewDialog.show();
    }

    private GridPane getGridPaneWithText() {
        GridPane dialogGrid = new GridPane();

        Label peselLabel = new Label("Pesel: ");
        Label emailLabel = new Label("Email: ");
        Label passwordLabel1 = new Label("Password: ");
        Label passwordLabel2 = new Label("Repeat password: ");

        dialogGrid.add(peselLabel, 0, 0);
        dialogGrid.add(emailLabel, 0, 1);
        dialogGrid.add(passwordLabel1, 0, 2);
        dialogGrid.add(passwordLabel2, 0, 3);

        return dialogGrid;
    }
}
