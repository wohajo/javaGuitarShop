package com.guitarShop.java.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Controller {

    Stage primaryStage;
    ConnectionFactory connectionFactory;

    public Controller() {}

    public void setStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private void login() throws IOException {
        Parent loggedIn = FXMLLoader.load(getClass().getResource("/com/guitarShop/resources/loggedIn.fxml"));
        this.primaryStage.setScene(new Scene(loggedIn));
    }

    @FXML TextField loginField;
    @FXML TextField passwordField;

    @FXML
    private void checkCredentials() throws IOException, SQLException {
        try(Connection connection = connectionFactory.getConnection()) {
            String login = loginField.getText();
            String password = passwordField.getText();
            String email = "";
            String downloadedPassword = "";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT Email, PasswordHash FROM Sellers WHERE Email = '" + login + "'");
            while (resultSet.next()) {
                email = resultSet.getString("Email");
                downloadedPassword = resultSet.getString("PasswordHash");
            }
            if (email.equals(""))
                System.out.println("wrong email");
            else if(!password.equals(downloadedPassword)) {
                // TODO KNOWN ISSUE: Password is stored as a hash, so it adds missing chars as spaces
                // TODO: Add hashing function
                System.out.println("wrong password");
            } else {
                login();
            }

            System.out.println("logged");
        } catch (SQLException e) {
            System.out.println("failed to login");
        }
    }
}
