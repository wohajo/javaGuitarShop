package com.guitarShop.java;

import com.guitarShop.java.controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/guitarShop/resources/login.fxml"));
        Parent root = (Parent) loader.load();
        MainController mainController = (MainController) loader.getController();

        primaryStage.setTitle("Guitar Shop");
        primaryStage.setScene(new Scene(root, 600, 400));
        mainController.setStage(primaryStage);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
