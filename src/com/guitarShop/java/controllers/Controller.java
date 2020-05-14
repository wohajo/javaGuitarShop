package com.guitarShop.java.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    @FXML
    private void checkCredentials() throws IOException, SQLException {
        try(Connection connection = connectionFactory.getConnection()) {
            String login;
            String password;

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT FROM ");
            while (resultSet.next()) {
                int id = resultSet.getInt("");
            }

            System.out.println("logged");
        } catch (SQLException e) {
            System.out.println("failed to login");
        }
    }
}
